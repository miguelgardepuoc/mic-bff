package com.antharos.bff.application.model;

import java.math.BigDecimal;

public record SimpleJobOfferWithTitle(
    String id, String jobTitle, String photoUrl, BigDecimal minSalary, BigDecimal maxSalary) {}
