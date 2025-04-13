package com.antharos.bff.infrastructure.repository;

import com.antharos.bff.domain.repository.BlobRepository;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import java.io.IOException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class AzureBlobStorageRepositoryImpl implements BlobRepository {

  @Value("${azure.storage.connection-string}")
  private String connectionString;

  @Override
  public String uploadFile(MultipartFile file) throws IOException {

    BlobServiceClient blobServiceClient =
        new BlobServiceClientBuilder().connectionString(this.connectionString).buildClient();

    BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient("cvs");
    if (!containerClient.exists()) {
      containerClient.create();
    }

    BlobClient blobClient =
        containerClient.getBlobClient(Objects.requireNonNull(file.getOriginalFilename()));
    blobClient.upload(file.getInputStream(), file.getSize(), true);

    return blobClient.getBlobUrl();
  }
}
