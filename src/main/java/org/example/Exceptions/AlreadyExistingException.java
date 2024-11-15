package org.example.Exceptions;

public class AlreadyExistingException extends RuntimeException {
  public AlreadyExistingException(String message) {
    super(message);
  }
}
