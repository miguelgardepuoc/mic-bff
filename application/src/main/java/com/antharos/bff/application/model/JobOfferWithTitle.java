package com.antharos.bff.application.model;

import java.math.BigDecimal;
import java.util.UUID;

public record JobOfferWithTitle(
    UUID id,
    String jobTitle,
    String photoUrl,
    BigDecimal minSalary,
    BigDecimal maxSalary,
    Float remote,
    String description,
    String requirement) {}
