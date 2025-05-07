package com.antharos.bff.domain.employee;

import lombok.Getter;

@Getter
public enum Role {
  ROLE_COMPANY_MANAGEMENT("Dirección de la empresa"),
  ROLE_DEPARTMENT_HEAD("Responsable de departamento"),
  ROLE_EMPLOYEE("Empleado");

  private final String description;

  Role(String description) {
    this.description = description;
  }
}
