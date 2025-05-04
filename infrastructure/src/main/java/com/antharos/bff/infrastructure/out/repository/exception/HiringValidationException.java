package com.antharos.bff.infrastructure.out.repository.exception;

import com.antharos.bff.infrastructure.in.util.ErrorResponse;
import lombok.Getter;

@Getter
public class HiringValidationException extends RuntimeException {
  private final ErrorResponse errorResponse;

  public HiringValidationException(ErrorResponse errorResponse) {
    super("Hiring validation failed");
    this.errorResponse = errorResponse;
  }
}
