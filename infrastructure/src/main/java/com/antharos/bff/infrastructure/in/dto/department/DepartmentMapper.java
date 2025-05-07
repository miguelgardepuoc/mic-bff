package com.antharos.bff.infrastructure.in.dto.department;

import com.antharos.bff.application.model.Department;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
  DepartmentResponse toDepartmentResponse(Department department);

  List<DepartmentResponse> toDepartmentResponse(List<Department> departments);
}
