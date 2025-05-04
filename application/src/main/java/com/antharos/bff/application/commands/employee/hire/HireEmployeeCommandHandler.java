package com.antharos.bff.application.commands.employee.hire;

import com.antharos.bff.domain.candidate.Candidate;
import com.antharos.bff.domain.employee.Employee;
import com.antharos.bff.domain.joboffer.JobOffer;
import com.antharos.bff.domain.repository.CorporateOrganizationRepository;
import com.antharos.bff.domain.repository.JobOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HireEmployeeCommandHandler {
  private final CorporateOrganizationRepository corporateOrganizationRepository;
  private final JobOfferRepository jobOfferRepository;

  public void doHandle(final HireEmployeeCommand command) {
    final Candidate candidate = this.jobOfferRepository.findCandidateById(command.getCandidateId());
    final JobOffer jobOffer =
        this.jobOfferRepository.findById(candidate.getJobOfferId().toString());
    final Employee employee =
        new Employee(
            command.getUserId(),
            null,
            command.getDni(),
            command.getName(),
            command.getSurname(),
            null,
            null,
            command.getTelephoneNumber(),
            command.getSalary(),
            command.getDepartmentId(),
            command.getHiringDate(),
            null,
            jobOffer.jobTitleId(),
            null);
    this.corporateOrganizationRepository.hire(employee);
    this.jobOfferRepository.hireCandidate(command.getCandidateId());
  }
}
