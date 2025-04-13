package com.antharos.bff.domain.repository;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface BlobRepository {
  String uploadFile(MultipartFile file) throws IOException;
}
