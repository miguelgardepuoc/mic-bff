package com.antharos.bff.infrastructure.in.controller;

import com.antharos.bff.application.commands.department.CreateDepartmentCommand;
import com.antharos.bff.application.commands.department.CreateDepartmentCommandHandler;
import com.antharos.bff.application.commands.department.DisableDepartmentCommand;
import com.antharos.bff.application.commands.department.DisableDepartmentCommandHandler;
import com.antharos.bff.application.commands.department.RenameDepartmentCommand;
import com.antharos.bff.application.commands.department.RenameDepartmentCommandHandler;
import com.antharos.bff.application.queries.department.FindDepartmentsQueryHandler;
import com.antharos.bff.infrastructure.in.dto.department.CreateDepartmentRequest;
import com.antharos.bff.infrastructure.in.dto.department.DepartmentMapper;
import com.antharos.bff.infrastructure.in.dto.department.DepartmentResponse;
import com.antharos.bff.infrastructure.in.dto.department.RenameDepartmentRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {
  private final FindDepartmentsQueryHandler findDepartmentsQueryHandler;
  private final RenameDepartmentCommandHandler renameDepartmentCommandHandler;
  private final DisableDepartmentCommandHandler disableDepartmentCommandHandler;
  private final CreateDepartmentCommandHandler createDepartmentCommandHandler;
  private final DepartmentMapper departmentMapper;

  @GetMapping
  public ResponseEntity<List<DepartmentResponse>> findDepartments() {
    return ResponseEntity.ok(
        this.departmentMapper.toDepartmentResponse(
            this.findDepartmentsQueryHandler.handle().stream().toList()));
  }

  @PatchMapping("/{id}/renaming")
  public ResponseEntity<Void> renameDepartment(
      @PathVariable String id, @RequestBody RenameDepartmentRequest request) {
    RenameDepartmentCommand command =
        RenameDepartmentCommand.builder()
            .departmentId(id)
            .description(request.getDescription())
            .build();
    this.renameDepartmentCommandHandler.handle(command);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> disableDepartment(@PathVariable String id) {
    DisableDepartmentCommand command = DisableDepartmentCommand.builder().departmentId(id).build();
    this.disableDepartmentCommandHandler.handle(command);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("")
  public ResponseEntity<Void> createDepartment(@RequestBody CreateDepartmentRequest request) {
    CreateDepartmentCommand command =
        CreateDepartmentCommand.builder()
            .id(request.id())
            .description(request.description())
            .build();

    this.createDepartmentCommandHandler.doHandle(command);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
