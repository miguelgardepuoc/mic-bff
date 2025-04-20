package com.antharos.bff.application.commands.joboffer.withdraw;

import com.antharos.bff.domain.repository.JobOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WithdrawJobOfferCommandHandler {

  private final JobOfferRepository jobOfferRepository;

  public void doHandle(final WithdrawJobOfferCommand command) {
    this.jobOfferRepository.withdrawJobOffer(command.getId());
  }
}
