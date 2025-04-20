package com.antharos.bff.infrastructure.in.dto.joboffer;

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
