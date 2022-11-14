package com.example.uploadmultipart.service.impl;

import com.example.uploadmultipart.entity.Storage;
import com.example.uploadmultipart.repository.StorageRepository;
import com.example.uploadmultipart.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StorageServiceImpl implements StorageService {

  @Autowired
  private StorageRepository storageRepository;

  @Override
  @Transactional
  public List<Storage> save(String username, List<MultipartFile> files) {
    List<Storage> storages = files.stream().map(file -> {
      Storage storage = new Storage();
      storage.setUsername(username);
      storage.setFileName(file.getOriginalFilename());
      return storage;
    }).collect(Collectors.toList());
    storageRepository.saveAll(storages);
    return storages;
  }

  @Override
  public List<Storage> getAll() {
    return storageRepository.findAll();
  }
}
