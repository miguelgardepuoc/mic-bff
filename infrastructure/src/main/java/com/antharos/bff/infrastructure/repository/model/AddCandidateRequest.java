package com.antharos.bff.infrastructure.repository.model;

import java.util.UUID;

public record AddCandidateRequest (
  UUID id,
  UUID jobOfferId,
  String personalEmail,
  String cvUrl)
{}
