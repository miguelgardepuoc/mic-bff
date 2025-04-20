package com.antharos.bff.application.queries.candidate;

import lombok.Value;

@Value(staticConstructor = "of")
public class FindSasUrlQuery {
  String filename;
}
