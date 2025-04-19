package com.antharos.bff.infrastructure.apirest.presentationmodel.employee;

import com.antharos.bff.application.model.SimpleEmployee;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
  EmployeeResponse toEmployeeResponse(SimpleEmployee employee);

  List<EmployeeResponse> toEmployeeResponse(List<SimpleEmployee> employees);
}
