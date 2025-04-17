package com.antharos.bff.infrastructure.apirest.presentationmodel;

import java.util.UUID;

public record JobTitleResponse(UUID id, String description, String photoUrl) {}
