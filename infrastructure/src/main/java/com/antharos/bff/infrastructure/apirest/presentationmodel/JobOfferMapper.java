package com.antharos.bff.infrastructure.apirest.presentationmodel;

import com.antharos.bff.domain.joboffer.SimpleJobOffer;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobOfferMapper {

  SimpleJobOfferDto toSimpleJobOffer(SimpleJobOffer jobOffer);

  List<SimpleJobOfferDto> toSimpleJobOffers(List<SimpleJobOffer> jobOffers);
}
