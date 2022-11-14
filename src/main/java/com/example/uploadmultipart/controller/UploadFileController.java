package com.example.uploadmultipart.controller;

import com.example.uploadmultipart.entity.Storage;
import com.example.uploadmultipart.helper.ApiPath;
import com.example.uploadmultipart.helper.ResponseHelper;
import com.example.uploadmultipart.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@RestController
public class UploadFileController {

  @Autowired
  private StorageService storageService;

  @PostMapping(value = ApiPath.UPLOAD_BASE_PATH, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ResponseBody
  public ResponseEntity<ResponseHelper<Storage>> uploadFile(@RequestParam("username") String username,
      @RequestPart("files") List<MultipartFile> files) {
    try {
      List<Storage> storages = storageService.save(username, files);
      return ResponseEntity.ok(constructResponseHelper(true, "OK", storages));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(constructResponseHelper(false, e.getMessage(),
              Collections.emptyList()));
    }
  }

  private ResponseHelper<Storage> constructResponseHelper(boolean success,
      String message, List<Storage> storages) {
    ResponseHelper<Storage> responseHelper = new ResponseHelper<>();
    responseHelper.setSuccess(success);
    responseHelper.setMessage(message);
    responseHelper.setData(storages);
    return responseHelper;
  }

  @GetMapping(ApiPath.UPLOAD_BASE_PATH)
  @ResponseBody
  public ResponseEntity<ResponseHelper<Storage>> getFile() {
    List<Storage> storages = storageService.getAll();
    return ResponseEntity.ok(constructResponseHelper(true, "OK", storages));
  }
}
