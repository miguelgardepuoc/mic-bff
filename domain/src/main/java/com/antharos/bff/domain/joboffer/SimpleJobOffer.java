package com.antharos.bff.domain.joboffer;

import java.math.BigDecimal;
import java.util.UUID;

public record SimpleJobOffer(
    UUID id, UUID jobTitleId, BigDecimal minSalary, BigDecimal maxSalary) {}
