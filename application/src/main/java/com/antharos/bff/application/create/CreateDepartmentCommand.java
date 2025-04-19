package com.antharos.bff.application.create;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CreateDepartmentCommand {
  String id;
  String description;
}
