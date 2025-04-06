package com.antharos.bff.domain.joboffer.repository;

import com.antharos.bff.domain.joboffer.JobOffer;
import java.util.List;

public interface JobOfferRepository {

  List<JobOffer> findAllActive();
}
