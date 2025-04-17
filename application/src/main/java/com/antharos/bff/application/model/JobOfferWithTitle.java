package com.antharos.bff.application.model;

import java.math.BigDecimal;

public record JobOfferWithTitle(
    String id,
    String jobTitle,
    String photoUrl,
    BigDecimal minSalary,
    BigDecimal maxSalary,
    short remote,
    String description,
    String requirement) {}
