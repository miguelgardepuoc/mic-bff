package com.antharos.bff.infrastructure.out.repository;

import com.antharos.bff.domain.repository.BlobRepository;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class AzureBlobStorageRepositoryImpl implements BlobRepository {

  @Value("${azure.storage.connection-string}")
  private String connectionString;

  @Value("${azure.storage.container-name}")
  private String containerName;

  @Override
  public String uploadFile(MultipartFile file) throws IOException {
    BlobServiceClient blobServiceClient =
        new BlobServiceClientBuilder().connectionString(this.connectionString).buildClient();

    BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
    if (!containerClient.exists()) {
      containerClient.create();
    }

    String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
    String uniqueFileName = UUID.randomUUID() + "_" + originalFilename;

    BlobClient blobClient = containerClient.getBlobClient(uniqueFileName);
    blobClient.upload(file.getInputStream(), file.getSize(), true);

    return blobClient.getBlobName();
  }

  @Override
  public String generateSasUrl(String filename) {
    BlobServiceClient blobServiceClient =
        new BlobServiceClientBuilder().connectionString(this.connectionString).buildClient();

    BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient("cvs");

    if (!containerClient.exists()) {
      throw new RuntimeException("Container does not exist.");
    }

    BlobClient blobClient = containerClient.getBlobClient(filename);

    if (!blobClient.exists()) {
      throw new RuntimeException("File not found.");
    }

    BlobSasPermission permission = new BlobSasPermission().setReadPermission(true);

    OffsetDateTime expiryTime = OffsetDateTime.now(ZoneOffset.UTC).plusMinutes(15);

    BlobServiceSasSignatureValues sasValues =
        new BlobServiceSasSignatureValues(expiryTime, permission);

    String sasToken = blobClient.generateSas(sasValues);

    return blobClient.getBlobUrl() + "?" + sasToken;
  }
}
