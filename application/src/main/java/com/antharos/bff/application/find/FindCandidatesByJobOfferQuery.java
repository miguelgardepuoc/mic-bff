package com.antharos.bff.application.find;

import java.util.UUID;
import lombok.Value;

@Value(staticConstructor = "of")
public class FindCandidatesByJobOfferQuery {
  UUID jobOfferId;
}
