package com.antharos.bff.infrastructure.in.controller;

import com.antharos.bff.application.commands.employee.hire.HireEmployeeCommand;
import com.antharos.bff.application.commands.employee.hire.HireEmployeeCommandHandler;
import com.antharos.bff.application.commands.employee.markasinactive.MarkEmployeeAsInactiveCommand;
import com.antharos.bff.application.commands.employee.markasinactive.MarkEmployeeAsInactiveCommandHandler;
import com.antharos.bff.application.commands.employee.putonleave.PutEmployeeOnLeaveCommand;
import com.antharos.bff.application.commands.employee.putonleave.PutEmployeeOnLeaveCommandHandler;
import com.antharos.bff.application.commands.employee.terminate.TerminateEmployeeCommand;
import com.antharos.bff.application.commands.employee.terminate.TerminateEmployeeCommandHandler;
import com.antharos.bff.application.queries.employee.FindEmployeesQueryHandler;
import com.antharos.bff.infrastructure.in.dto.employee.EmployeeMapper;
import com.antharos.bff.infrastructure.in.dto.employee.EmployeeResponse;
import com.antharos.bff.infrastructure.in.dto.employee.UserDto;
import java.util.List;

import com.antharos.bff.infrastructure.security.ManagementOnly;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@Tag(name = "Employee", description = "Operations related to employees")
public class EmployeeController {

  private final HireEmployeeCommandHandler hireEmployeeCommandHandler;
  private final FindEmployeesQueryHandler findEmployeesQueryHandler;
  private final TerminateEmployeeCommandHandler terminateEmployeeCommandHandler;
  private final PutEmployeeOnLeaveCommandHandler putEmployeeOnLeaveCommandHandler;
  private final MarkEmployeeAsInactiveCommandHandler markEmployeeAsInactiveCommandHandler;

  private final EmployeeMapper employeeMapper;

  @ManagementOnly
  @PostMapping("/hiring")
  @Operation(
          summary = "Hire a new employee",
          description = "Adds a new employee to the organization")
  @ApiResponses(
          value = {
                  @ApiResponse(responseCode = "201", description = "Employee hired successfully"),
                  @ApiResponse(responseCode = "400", description = "Invalid request"),
                  @ApiResponse(responseCode = "403", description = "Forbidden")
          })
  public ResponseEntity<Void> hireEmployee(@RequestBody UserDto request) {
    HireEmployeeCommand command =
        HireEmployeeCommand.builder()
            .userId(request.id())
            .dni(request.dni())
            .name(request.name())
            .surname(request.surname())
            .telephoneNumber(request.telephoneNumber())
            .salary(request.salary())
            .departmentId(request.departmentId())
            .hiringDate(request.hiringDate())
            .candidateId(request.candidateId())
            .build();

    this.hireEmployeeCommandHandler.doHandle(command);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @ManagementOnly
  @GetMapping
  @Operation(summary = "List all employees", description = "Returns a list of all employees")
  @ApiResponses(
          value = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "List of employees",
                          content =
                          @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = EmployeeResponse.class))),
                  @ApiResponse(responseCode = "403", description = "Forbidden")
          })
  public ResponseEntity<List<EmployeeResponse>> findEmployees() {
    return ResponseEntity.ok(
        this.employeeMapper.toEmployeeResponse(
            this.findEmployeesQueryHandler.handle().stream().toList()));
  }

  @ManagementOnly
  @PatchMapping("/{id}/on-leave")
  @Operation(summary = "Put employee on leave", description = "Marks the employee as on leave")
  @ApiResponses(
          value = {
                  @ApiResponse(responseCode = "200", description = "Employee put on leave"),
                  @ApiResponse(responseCode = "404", description = "Employee not found"),
                  @ApiResponse(responseCode = "403", description = "Forbidden")
          })
  public ResponseEntity<Void> putUserOnLeave(@PathVariable String id) {
    PutEmployeeOnLeaveCommand command = PutEmployeeOnLeaveCommand.builder().userId(id).build();
    this.putEmployeeOnLeaveCommandHandler.doHandle(command);
    return ResponseEntity.ok().build();
  }

  @ManagementOnly
  @PatchMapping("/{id}/termination")
  @Operation(
          summary = "Terminate employee",
          description = "Terminates the employment of the employee")
  @ApiResponses(
          value = {
                  @ApiResponse(responseCode = "200", description = "Employee terminated"),
                  @ApiResponse(responseCode = "404", description = "Employee not found"),
                  @ApiResponse(responseCode = "403", description = "Forbidden")
          })
  public ResponseEntity<Void> terminateUser(@PathVariable String id) {
    TerminateEmployeeCommand command = TerminateEmployeeCommand.builder().userId(id).build();
    this.terminateEmployeeCommandHandler.doHandle(command);
    return ResponseEntity.ok().build();
  }

  @ManagementOnly
  @PatchMapping("/{id}/inactivation")
  @Operation(
          summary = "Mark employee as inactive",
          description = "Marks the employee record as inactive")
  @ApiResponses(
          value = {
                  @ApiResponse(responseCode = "200", description = "Employee marked as inactive"),
                  @ApiResponse(responseCode = "404", description = "Employee not found"),
                  @ApiResponse(responseCode = "403", description = "Forbidden")
          })
  public ResponseEntity<Void> markUserAsInactive(@PathVariable String id) {
    MarkEmployeeAsInactiveCommand command =
        MarkEmployeeAsInactiveCommand.builder().userId(id).build();
    this.markEmployeeAsInactiveCommandHandler.doHandle(command);
    return ResponseEntity.ok().build();
  }
}
