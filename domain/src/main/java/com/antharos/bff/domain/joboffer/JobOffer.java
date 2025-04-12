package com.antharos.bff.domain.joboffer;

import java.math.BigDecimal;
import java.util.UUID;

public record JobOffer(
    UUID id,
    UUID jobTitleId,
    String photoUrl,
    BigDecimal minSalary,
    BigDecimal maxSalary,
    Float remote,
    String description,
    String requirement) {}
