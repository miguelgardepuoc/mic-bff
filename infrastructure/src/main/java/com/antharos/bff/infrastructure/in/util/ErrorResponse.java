package com.antharos.bff.infrastructure.in.util;

import java.util.List;

public record ErrorResponse(List<FieldError> errors) {}
