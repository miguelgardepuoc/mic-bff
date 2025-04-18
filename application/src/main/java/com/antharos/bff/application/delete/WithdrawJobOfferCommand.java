package com.antharos.bff.application.delete;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class WithdrawJobOfferCommand {
  String id;
}
