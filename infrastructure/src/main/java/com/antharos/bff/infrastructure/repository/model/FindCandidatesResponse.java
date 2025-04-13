package com.antharos.bff.infrastructure.repository.model;

import com.antharos.bff.domain.candidate.CandidateStatus;
import java.util.UUID;

public record FindCandidatesResponse(
    UUID id,
    UUID jobOfferId,
    CandidateStatus status,
    String personalEmail,
    String cvUrl,
    String name,
    String surname,
    String phoneNumber) {}
