package com.antharos.bff.infrastructure.in.dto.report;

import com.antharos.bff.application.model.DashboardData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DashboardMapper {
  DashboardResponse toDashboardResponse(DashboardData dashboardData);
}
