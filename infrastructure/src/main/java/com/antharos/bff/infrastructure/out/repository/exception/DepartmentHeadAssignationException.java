package com.antharos.bff.infrastructure.out.repository.exception;

import com.antharos.bff.infrastructure.in.util.ErrorResponse;
import lombok.Getter;

@Getter
public class DepartmentHeadAssignationException extends RuntimeException {
  private final ErrorResponse errorResponse;

  public DepartmentHeadAssignationException(ErrorResponse errorResponse) {
    super("Hiring validation failed");
    this.errorResponse = errorResponse;
  }
}
