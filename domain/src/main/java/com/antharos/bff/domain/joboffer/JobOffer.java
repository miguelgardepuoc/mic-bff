package com.antharos.bff.domain.joboffer;

import java.math.BigDecimal;

public record JobOffer(
    String id,
    String jobTitleId,
    BigDecimal minSalary,
    BigDecimal maxSalary,
    short remote,
    String description,
    String requirement) {}
