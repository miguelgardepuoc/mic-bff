package com.antharos.bff.domain.joboffer;

import java.math.BigDecimal;
import java.util.UUID;

public record SimpleJobOffer(
    UUID id, UUID jobTitleId, byte[] photo, BigDecimal minSalary, BigDecimal maxSalary) {}
