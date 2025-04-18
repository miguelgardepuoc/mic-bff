package com.antharos.bff.infrastructure.apirest.presentationmodel;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class UpdateJobOfferRequest {
  String id;
  String description;
  BigDecimal minSalary;
  BigDecimal maxSalary;
  short remote;
  String requirement;
}
