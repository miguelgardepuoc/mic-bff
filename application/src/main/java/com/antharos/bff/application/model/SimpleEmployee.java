package com.antharos.bff.application.model;

import com.antharos.bff.domain.employee.Status;
import java.math.BigDecimal;
import java.time.LocalDate;

public record SimpleEmployee(
    String id,
    Long employeeNumber,
    String username,
    String fullName,
    BigDecimal salary,
    String dni,
    String jobTitle,
    LocalDate hiringDate,
    String department,
    Status status) {}
