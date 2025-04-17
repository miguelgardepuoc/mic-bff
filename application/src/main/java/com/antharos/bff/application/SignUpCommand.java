package com.antharos.bff.application;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class SignUpCommand {
  String username;
  String password;
}
