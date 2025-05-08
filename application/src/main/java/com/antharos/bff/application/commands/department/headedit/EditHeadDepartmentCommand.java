package com.antharos.bff.application.commands.department.headedit;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class EditHeadDepartmentCommand {
  String id;
  String username;
}
