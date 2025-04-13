package com.antharos.bff.infrastructure.apirest.presentationmodel;

import com.antharos.bff.domain.candidate.Candidate;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CandidateMapper {
  SimpleCandidateResponse toSimpleCandidateResponse(Candidate candidate);

  List<SimpleCandidateResponse> toSimpleCandidatesResponse(List<Candidate> candidates);
}
