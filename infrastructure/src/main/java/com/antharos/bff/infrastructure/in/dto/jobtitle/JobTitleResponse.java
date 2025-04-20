package com.antharos.bff.infrastructure.in.dto.jobtitle;

import java.util.UUID;

public record JobTitleResponse(UUID id, String description, String photoUrl) {}
