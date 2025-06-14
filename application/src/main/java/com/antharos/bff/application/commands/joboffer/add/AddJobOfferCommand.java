package com.antharos.bff.application.commands.joboffer.add;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class AddJobOfferCommand {
  String id;
  String jobTitleId;
  String description;
  BigDecimal minSalary;
  BigDecimal maxSalary;
  short remote;
  String requirement;
}
