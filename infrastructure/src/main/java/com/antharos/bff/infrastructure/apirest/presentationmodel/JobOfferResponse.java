package com.antharos.bff.infrastructure.apirest.presentationmodel;

import java.math.BigDecimal;
import java.util.UUID;

public record JobOfferResponse(
    UUID id,
    String jobTitle,
    String photoUrl,
    BigDecimal minSalary,
    BigDecimal maxSalary,
    Float remote,
    String description,
    String requirement) {}
