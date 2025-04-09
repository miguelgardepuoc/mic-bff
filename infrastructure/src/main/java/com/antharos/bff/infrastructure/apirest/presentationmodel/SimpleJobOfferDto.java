package com.antharos.bff.infrastructure.apirest.presentationmodel;

import java.math.BigDecimal;
import java.util.UUID;

public record SimpleJobOfferDto(
    UUID id, UUID jobTitleId, byte[] photo, BigDecimal minSalary, BigDecimal maxSalary) {}
