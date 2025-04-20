package com.antharos.bff.infrastructure.in.dto.joboffer;

import java.math.BigDecimal;
import java.util.UUID;

public record SimpleJobOfferResponse(
    UUID id, String jobTitle, String photoUrl, BigDecimal minSalary, BigDecimal maxSalary) {}
