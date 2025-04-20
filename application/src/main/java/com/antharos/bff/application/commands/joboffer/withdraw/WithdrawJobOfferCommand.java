package com.antharos.bff.application.commands.joboffer.withdraw;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class WithdrawJobOfferCommand {
  String id;
}
