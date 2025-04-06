package com.antharos.bff.domain.globalexceptions;

public class AlreadyExistsException extends RuntimeException {
  public AlreadyExistsException(String message) {
    super(message);
  }
}
