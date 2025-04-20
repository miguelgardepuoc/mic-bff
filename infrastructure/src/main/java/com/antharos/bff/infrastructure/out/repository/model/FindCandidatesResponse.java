package com.antharos.bff.infrastructure.out.repository.model;

import com.antharos.bff.domain.candidate.CandidateStatus;
import java.util.UUID;

public record FindCandidatesResponse(
    UUID id,
    UUID jobOfferId,
    CandidateStatus status,
    String personalEmail,
    String cvFilename,
    String name,
    String surname) {}
