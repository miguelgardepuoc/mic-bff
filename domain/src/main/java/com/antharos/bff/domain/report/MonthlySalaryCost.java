package com.antharos.bff.domain.report;

import java.time.LocalDate;

public record MonthlySalaryCost(LocalDate month, long totalSalary) {}
