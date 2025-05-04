package com.antharos.bff.infrastructure.in.dto.employee;

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
    String candidateId) {}
