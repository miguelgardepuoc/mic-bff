package com.antharos.bff.application.queries.joboffer;

import lombok.Value;

@Value(staticConstructor = "of")
public class FindJobOfferQuery {
  String jobOfferId;
}
