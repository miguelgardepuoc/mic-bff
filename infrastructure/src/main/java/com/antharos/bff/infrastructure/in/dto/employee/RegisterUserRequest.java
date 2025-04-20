package com.antharos.bff.infrastructure.in.dto.employee;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserRequest {
  private String username;
  private String password;
}
