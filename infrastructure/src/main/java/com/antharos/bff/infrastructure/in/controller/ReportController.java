package com.antharos.bff.infrastructure.in.controller;

import com.antharos.bff.application.model.DashboardData;
import com.antharos.bff.application.queries.kpi.*;
import com.antharos.bff.infrastructure.in.dto.report.DashboardMapper;
import com.antharos.bff.infrastructure.in.dto.report.DashboardResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/reports")
public class ReportController {

  private final GetDashboardDataHandler getDashboardDataHandler;
  private final DashboardMapper dashboardMapper;

  @GetMapping("/dashboard")
  public ResponseEntity<DashboardResponse> getDashboardData() {
    DashboardData dashboardData = this.getDashboardDataHandler.handle(new GetDashboardDataQuery());
    return ResponseEntity.ok(this.dashboardMapper.toDashboardResponse(dashboardData));
  }
}
