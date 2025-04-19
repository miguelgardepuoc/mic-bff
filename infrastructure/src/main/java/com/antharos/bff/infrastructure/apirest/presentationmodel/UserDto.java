package com.antharos.bff.infrastructure.apirest.presentationmodel;

import com.antharos.bff.domain.employee.Role;
import java.math.BigDecimal;
import java.util.Date;

public record UserDto(
    String id,
    String dni,
    String name,
    String surname,
    String telephoneNumber,
    BigDecimal salary,
    String departmentId,
    Date hiringDate,
    Role role,
    String jobTitleId) {}
