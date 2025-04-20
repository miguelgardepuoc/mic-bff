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
import com.antharos.bff.infrastructure.in.dto.employee.UserDto;
import com.antharos.bff.infrastructure.in.dto.employee.EmployeeMapper;
import com.antharos.bff.infrastructure.in.dto.employee.EmployeeResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

  private final HireEmployeeCommandHandler hireEmployeeCommandHandler;
  private final FindEmployeesQueryHandler findEmployeesQueryHandler;
  private final TerminateEmployeeCommandHandler terminateEmployeeCommandHandler;
  private final PutEmployeeOnLeaveCommandHandler putEmployeeOnLeaveCommandHandler;
  private final MarkEmployeeAsInactiveCommandHandler markEmployeeAsInactiveCommandHandler;

  private final EmployeeMapper employeeMapper;

  @PostMapping("/hiring")
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
            .role(request.role())
            .jobTitleId(request.jobTitleId())
            .createdBy("admin")
            .build();

    this.hireEmployeeCommandHandler.doHandle(command);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<EmployeeResponse>> findEmployees() {
    return ResponseEntity.ok(
        this.employeeMapper.toEmployeeResponse(
            this.findEmployeesQueryHandler.handle().stream().toList()));
  }

  @PatchMapping("/{id}/on-leave")
  public ResponseEntity<Void> putUserOnLeave(@PathVariable String id) {
    PutEmployeeOnLeaveCommand command = PutEmployeeOnLeaveCommand.builder().userId(id).build();
    this.putEmployeeOnLeaveCommandHandler.doHandle(command);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/{id}/termination")
  public ResponseEntity<Void> terminateUser(@PathVariable String id) {
    TerminateEmployeeCommand command = TerminateEmployeeCommand.builder().userId(id).build();
    this.terminateEmployeeCommandHandler.doHandle(command);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/{id}/inactivation")
  public ResponseEntity<Void> markUserAsInactive(@PathVariable String id) {
    MarkEmployeeAsInactiveCommand command =
        MarkEmployeeAsInactiveCommand.builder().userId(id).build();
    this.markEmployeeAsInactiveCommandHandler.doHandle(command);
    return ResponseEntity.ok().build();
  }
}
