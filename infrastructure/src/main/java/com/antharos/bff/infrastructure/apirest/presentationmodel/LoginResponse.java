package com.antharos.bff.infrastructure.apirest.presentationmodel;

public record LoginResponse(String token, long expiresIn) {}
