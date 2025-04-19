package com.antharos.bff.domain.employee;

import java.math.BigDecimal;
import java.util.Date;

public record Employee(
    String id,
    Long employeeNumber,
    String dni,
    String name,
    String surname,
    String telephoneNumber,
    BigDecimal salary,
    String departmentId,
    Date hiringDate,
    Role role,
    String jobTitleId,
    Status status) {}
