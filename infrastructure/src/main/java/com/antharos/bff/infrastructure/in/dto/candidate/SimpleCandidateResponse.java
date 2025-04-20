package com.antharos.bff.infrastructure.in.dto.candidate;

import java.util.UUID;

public record SimpleCandidateResponse(
    UUID id, String fullName, String status, String personalEmail, String cvFilename) {}
