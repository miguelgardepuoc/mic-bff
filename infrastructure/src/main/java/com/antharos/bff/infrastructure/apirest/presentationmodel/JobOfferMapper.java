package com.antharos.bff.infrastructure.apirest.presentationmodel;

import com.antharos.bff.application.model.JobOfferWithTitle;
import com.antharos.bff.application.model.SimpleJobOfferWithTitle;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobOfferMapper {

  SimpleJobOfferResponse toSimpleJobOffer(SimpleJobOfferWithTitle jobOfferWithTitle);

  List<SimpleJobOfferResponse> toSimpleJobOffers(List<SimpleJobOfferWithTitle> jobOffersWithTitle);

  JobOfferResponse toJobOfferResponse(JobOfferWithTitle jobOfferWithTitle);
}
