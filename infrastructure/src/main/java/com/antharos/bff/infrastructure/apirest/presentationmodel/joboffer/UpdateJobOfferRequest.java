package com.antharos.bff.infrastructure.apirest.presentationmodel.joboffer;

import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class UpdateJobOfferRequest {
  String id;
  String description;
  BigDecimal minSalary;
  BigDecimal maxSalary;
  short remote;
  String requirement;
}
