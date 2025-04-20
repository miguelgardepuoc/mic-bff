package com.antharos.bff.infrastructure.in.dto.department;

import com.antharos.bff.domain.department.Department;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
  DepartmentResponse toDepartmentResponse(Department department);

  List<DepartmentResponse> toDepartmentResponse(List<Department> departments);
}
