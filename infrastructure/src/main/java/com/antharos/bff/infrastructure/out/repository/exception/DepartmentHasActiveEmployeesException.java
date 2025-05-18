package com.antharos.bff.infrastructure.out.repository.exception;

import com.antharos.bff.infrastructure.in.util.ErrorResponse;
import lombok.Getter;

@Getter
public class DepartmentHasActiveEmployeesException extends RuntimeException {
  private final ErrorResponse errorResponse;

  public DepartmentHasActiveEmployeesException(ErrorResponse errorResponse) {
    super("Department has active employees exception");
    this.errorResponse = errorResponse;
  }
}
