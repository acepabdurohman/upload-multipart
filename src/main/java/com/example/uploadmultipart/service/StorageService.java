package com.example.uploadmultipart.service;

import com.example.uploadmultipart.entity.Storage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {

  List<Storage> save(String username, List<MultipartFile> files);

  List<Storage> getAll();
}
