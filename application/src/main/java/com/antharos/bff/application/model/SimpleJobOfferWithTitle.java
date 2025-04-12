package com.antharos.bff.application.model;

import java.math.BigDecimal;
import java.util.UUID;

public record SimpleJobOfferWithTitle(
    UUID id, String jobTitle, String photoUrl, BigDecimal minSalary, BigDecimal maxSalary) {}
