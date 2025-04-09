package com.antharos.bff.application.create;

import com.antharos.bff.domain.employee.Role;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Value
public class HireEmployeeCommand {
    String userId;
    String dni;
    String name;
    String surname;
    String telephoneNumber;
    BigDecimal salary;
    String departmentId;
    LocalDate hiringDate;
    Role role;
    String jobTitleId;
    String createdBy;
}
