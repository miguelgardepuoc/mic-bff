package com.antharos.bff.infrastructure.in.dto.login;

public record LoginResponse(String token, long expiresIn) {}
