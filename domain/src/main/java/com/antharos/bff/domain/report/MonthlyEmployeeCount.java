package com.antharos.bff.domain.report;

import java.time.LocalDate;

public record MonthlyEmployeeCount(LocalDate month, long totalEmployees) {}
