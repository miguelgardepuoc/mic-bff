package com.antharos.bff.application.queries.candidate;

import com.antharos.bff.domain.repository.BlobRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FindSasUrlQueryHandler {
  private final BlobRepository blobRepository;

  public String handle(FindSasUrlQuery query) {
    return blobRepository.generateSasUrl(query.getFilename());
  }
}
