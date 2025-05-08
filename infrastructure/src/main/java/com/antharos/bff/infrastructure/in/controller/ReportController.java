package com.antharos.bff.infrastructure.in.controller;

import com.antharos.bff.application.model.DashboardData;
import com.antharos.bff.application.queries.kpi.GetDashboardDataHandler;
import com.antharos.bff.application.queries.kpi.GetDashboardDataQuery;
import com.antharos.bff.infrastructure.in.dto.report.DashboardMapper;
import com.antharos.bff.infrastructure.in.dto.report.DashboardResponse;
import com.antharos.bff.infrastructure.security.ManagementOnly;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/reports")
@Tag(name = "Report", description = "Analytics and KPIs related to employees and salaries")
public class ReportController {

  private final GetDashboardDataHandler getDashboardDataHandler;
  private final DashboardMapper dashboardMapper;

  @ManagementOnly
  @GetMapping("/dashboard")
  @Operation(
          summary = "Get dashboard data",
          description = "Returns summarized analytics and KPIs for dashboard display"
  )
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Successfully retrieved dashboard data",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = DashboardResponse.class))),
          @ApiResponse(responseCode = "403", description = "Access denied"),
          @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public ResponseEntity<DashboardResponse> getDashboardData() {
    DashboardData dashboardData = this.getDashboardDataHandler.handle(new GetDashboardDataQuery());
    return ResponseEntity.ok(this.dashboardMapper.toDashboardResponse(dashboardData));
  }
}
