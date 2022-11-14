package com.example.uploadmultipart;

import com.example.uploadmultipart.entity.Storage;
import com.example.uploadmultipart.helper.ApiPath;
import com.example.uploadmultipart.service.StorageService;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class UploadFileTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StorageService storageService;

  private static final String USERNAME = "Budi";

  @AfterEach
  public void tearDown() {
    verifyNoMoreInteractions(storageService);
  }

  @Test
  public void uploadFile() throws Exception {
    MockMultipartFile mockMultipartFile1 = new MockMultipartFile("files",
        "test.txt", "text/plain", "payload".getBytes());

    MockMultipartFile mockMultipartFile2 = new MockMultipartFile("files",
        "test.jpg", "image/jpeg", "payload".getBytes());

    this.mockMvc.perform(
        multipart(ApiPath.UPLOAD_BASE_PATH).file(mockMultipartFile1)
            .file(mockMultipartFile2)
            .param("username", USERNAME)
            .contentType(MediaType.MULTIPART_FORM_DATA))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.message", AllOf.allOf(
            StringContains.containsString("OK"))));

    List<MultipartFile> multipartFiles = new ArrayList<>();
    multipartFiles.add(mockMultipartFile1);
    multipartFiles.add(mockMultipartFile2);

    verify(this.storageService).save(USERNAME, multipartFiles);
  }

  @Test
  public void getAllFiles() throws Exception {
    Storage storage = new Storage();
    storage.setId("1234");
    storage.setUsername(USERNAME);
    storage.setFileName("testing.txt");

    List<Storage> storages = new ArrayList<>();
    storages.add(storage);

    when(this.storageService.getAll()).thenReturn(storages);

    this.mockMvc.perform(get(ApiPath.UPLOAD_BASE_PATH))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message", AllOf.allOf(
            StringContains.containsString("OK")
        )));

    verify(this.storageService).getAll();
  }
}
