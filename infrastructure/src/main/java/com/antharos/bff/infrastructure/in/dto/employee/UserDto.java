package com.antharos.bff.infrastructure.in.dto.employee;

import com.antharos.bff.domain.employee.Role;
import java.math.BigDecimal;
import java.time.LocalDate;

public record UserDto(
    String id,
    String dni,
    String name,
    String surname,
    String telephoneNumber,
    BigDecimal salary,
    String departmentId,
    LocalDate hiringDate,
    Role role,
    String jobTitleId) {}
