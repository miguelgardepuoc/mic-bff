package com.antharos.bff.infrastructure.in.dto.jobtitle;

import com.antharos.bff.domain.jobtitle.JobTitle;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobTitleMapper {
  JobTitleResponse toJobTitleResponse(JobTitle jobTitle);

  List<JobTitleResponse> toJobTitleResponse(List<JobTitle> jobTitles);
}
