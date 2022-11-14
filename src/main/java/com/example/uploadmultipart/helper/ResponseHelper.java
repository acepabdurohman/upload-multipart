package com.example.uploadmultipart.helper;

import lombok.Data;

import java.util.List;

@Data
public class ResponseHelper<T> {
  private boolean success;
  private String message;
  private List<T> data;
}
