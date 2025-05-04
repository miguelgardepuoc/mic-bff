package com.antharos.bff.domain.employee;

import lombok.Getter;

@Getter
public enum Role {
  COMPANY_MANAGEMENT("Dirección de la empresa"),
  DEPARTMENT_HEAD("Responsable de departamento"),
  EMPLOYEE("Empleado");

  private final String description;

  Role(String description) {
    this.description = description;
  }
}
