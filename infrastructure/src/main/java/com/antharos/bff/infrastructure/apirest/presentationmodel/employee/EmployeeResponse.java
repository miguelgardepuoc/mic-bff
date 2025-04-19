package com.antharos.bff.infrastructure.apirest.presentationmodel.employee;

import com.antharos.bff.domain.employee.Status;
import java.math.BigDecimal;
import java.util.Date;

public record EmployeeResponse(
    String id,
    Long employeeNumber,
    String fullName,
    BigDecimal salary,
    String dni,
    String jobTitle,
    Date hiringDate,
    String department,
    Status status) {}
