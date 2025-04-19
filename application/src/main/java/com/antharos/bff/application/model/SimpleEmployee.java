package com.antharos.bff.application.model;

import com.antharos.bff.domain.employee.Status;
import java.math.BigDecimal;
import java.util.Date;

public record SimpleEmployee(
    String id,
    Long employeeNumber,
    String fullName,
    BigDecimal salary,
    String dni,
    String jobTitle,
    Date hiringDate,
    String department,
    Status status) {}
