package com.antharos.bff.domain.employee;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Employee(
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
