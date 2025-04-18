package com.antharos.bff.application.update;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class UpdateJobOfferCommand {
  String id;
  String description;
  BigDecimal minSalary;
  BigDecimal maxSalary;
  short remote;
  String requirement;
}
