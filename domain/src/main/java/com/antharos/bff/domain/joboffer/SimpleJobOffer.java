package com.antharos.bff.domain.joboffer;

import java.math.BigDecimal;

public record SimpleJobOffer(
    String id, String jobTitleId, BigDecimal minSalary, BigDecimal maxSalary) {}
