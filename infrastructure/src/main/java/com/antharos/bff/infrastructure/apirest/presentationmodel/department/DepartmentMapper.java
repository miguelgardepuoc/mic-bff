package com.antharos.bff.infrastructure.apirest.presentationmodel.department;

import com.antharos.bff.domain.department.Department;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
  DepartmentResponse toDepartmentResponse(Department department);

  List<DepartmentResponse> toDepartmentResponse(List<Department> departments);
}
