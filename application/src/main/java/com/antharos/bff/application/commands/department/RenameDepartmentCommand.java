package com.antharos.bff.application.commands.department;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class RenameDepartmentCommand {
  String departmentId;
  String description;
}
