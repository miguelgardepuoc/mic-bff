package com.antharos.bff.infrastructure.out.repository.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterUserRequest {
  private String username;
  private String password;
}
