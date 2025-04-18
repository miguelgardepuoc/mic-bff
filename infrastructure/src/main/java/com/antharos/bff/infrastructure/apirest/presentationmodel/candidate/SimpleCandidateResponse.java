package com.antharos.bff.infrastructure.apirest.presentationmodel.candidate;

import java.util.UUID;

public record SimpleCandidateResponse(
    UUID id,
    String fullName,
    String status,
    String personalEmail,
    String cvUrl,
    String phoneNumber) {}
