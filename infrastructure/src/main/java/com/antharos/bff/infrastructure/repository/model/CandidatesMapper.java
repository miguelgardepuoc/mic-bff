package com.antharos.bff.infrastructure.repository.model;

import com.antharos.bff.domain.candidate.Candidate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CandidatesMapper {

  public static List<Candidate> toDomainList(List<FindCandidatesResponse> dtoList) {
    return dtoList.stream().map(CandidatesMapper::toDomain).collect(Collectors.toList());
  }

  public static Candidate toDomain(FindCandidatesResponse dto) {
    Candidate candidate = new Candidate();
    candidate.setId(dto.id());
    candidate.setStatus(dto.status());
    candidate.setPersonalEmail(dto.personalEmail());
    candidate.setCvFilename(dto.cvFilename());

    String fullName = null;
    if (dto.name() != null) {
      fullName = dto.name();
      if (dto.surname() != null) {
        fullName += " " + dto.surname();
      }
    }
    candidate.setFullName(fullName);

    return candidate;
  }
}
