package com.snob.busmanagmenttool.service.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
public class S3Service{
    private final AmazonS3 amazonS3;
    private final String bucketName;
    public S3Service(AmazonS3 amazonS3, @Value("${application.bucket.name}") String bucketName) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
    }
    public String uploadBusImage(MultipartFile imageFile) throws IOException {
        String fileName = generateUniqueFileName(Objects.requireNonNull(imageFile.getOriginalFilename()));
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(imageFile.getSize());

        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, imageFile.getInputStream(), metadata));
        return fileName;
    }

    private String generateUniqueFileName(String originalFileName) {
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return UUID.randomUUID() + extension;
    }

    public String deleteFile(final String fileName) {
        amazonS3.deleteObject(bucketName, fileName);
        return "Deleted File: " + fileName;
    }
}
