package com.antharos.bff.domain.report;

import java.util.UUID;

public record DepartmentEmployees(UUID departmentId, long totalEmployees) {}
