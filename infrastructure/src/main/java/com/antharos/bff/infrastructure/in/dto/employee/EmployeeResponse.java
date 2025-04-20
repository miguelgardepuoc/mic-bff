package com.antharos.bff.infrastructure.in.dto.employee;

import com.antharos.bff.domain.employee.Status;
import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeResponse(
    String id,
    Long employeeNumber,
    String fullName,
    BigDecimal salary,
    String dni,
    String jobTitle,
    LocalDate hiringDate,
    String department,
    Status status) {}
