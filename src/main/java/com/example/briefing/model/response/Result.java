package com.example.briefing.model.response;

import lombok.Data;

@Data
public class Result<T> {
  private int code;

  private String msg;

  private T data;
}
