package com.example.uploadmultipart.repository;

import com.example.uploadmultipart.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRepository extends JpaRepository<Storage, String> {
}
