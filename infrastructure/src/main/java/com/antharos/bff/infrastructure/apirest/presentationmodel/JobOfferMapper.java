package com.antharos.bff.infrastructure.apirest.presentationmodel;

import com.antharos.bff.domain.joboffer.JobOffer;
import com.antharos.bff.domain.joboffer.JobOfferId;
import java.util.List;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JobOfferMapper {

  @Mapping(source = "salaryRange.min", target = "minSalary")
  @Mapping(source = "salaryRange.max", target = "maxSalary")
  SimpleJobOffer toSimpleJobOffer(JobOffer jobOffer);

  List<SimpleJobOffer> toSimpleJobOffers(List<JobOffer> jobOffers);

  default UUID map(JobOfferId id) {
    return id == null ? null : UUID.fromString(id.getValueAsString());
  }
}
