package com.antharos.bff.application.find;

import lombok.Value;

@Value(staticConstructor = "of")
public class FindCandidatesByJobOfferQuery {
  String jobOfferId;
}
