package com.antharos.bff.application.commands.employee.terminate;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class TerminateEmployeeCommand {
  String userId;
}
