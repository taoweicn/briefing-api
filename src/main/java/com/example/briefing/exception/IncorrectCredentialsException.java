package com.example.briefing.exception;

public class IncorrectCredentialsException extends RuntimeException {
  public IncorrectCredentialsException(String message) {
    super(message);
  }
}
