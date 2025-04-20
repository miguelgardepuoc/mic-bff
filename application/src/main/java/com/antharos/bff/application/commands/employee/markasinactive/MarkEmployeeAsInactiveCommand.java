package com.antharos.bff.application.commands.employee.markasinactive;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class MarkEmployeeAsInactiveCommand {
  String userId;
}
