package com.antharos.bff.domain.joboffer;

import java.math.BigDecimal;
import java.util.Objects;

public record SalaryRange(BigDecimal min, BigDecimal max) {

  public SalaryRange {
    Objects.requireNonNull(min, "Minimum salary must not be null");
    Objects.requireNonNull(max, "Maximum salary must not be null");

    if (min.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Minimum salary must be non-negative");
    }

    if (max.compareTo(min) < 0) {
      throw new IllegalArgumentException(
          "Maximum salary must be greater than or equal to minimum salary");
    }
  }
}
