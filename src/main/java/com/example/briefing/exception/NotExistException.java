package com.example.briefing.exception;

public class NotExistException extends RuntimeException {
  private String name;

  public NotExistException(String name, String message) {
    super(message);
    this.name = name;
  }

  @Override
  public String getMessage() {
    return this.name + " " + super.getMessage();
  }
}
