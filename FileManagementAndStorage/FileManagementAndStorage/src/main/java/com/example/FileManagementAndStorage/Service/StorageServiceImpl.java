package com.example.FileManagementAndStorage.Service;


import com.example.FileManagementAndStorage.Config.S3Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.time.Duration;

@Service
public class StorageServiceImpl implements StorageService{

    @Autowired
    private S3Presigner presigner; // AWS SDK v2

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public StorageServiceImpl(S3Presigner presigner) {
        this.presigner = presigner;
    }

    @Override
    public String generateDownloadUrl(String objectKey) {

        GetObjectRequest getObjectRequest =
                GetObjectRequest.builder()
                        .bucket(bucket)
                        .key(objectKey)
                        .build();

        GetObjectPresignRequest presignRequest =
                GetObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(10))
                        .getObjectRequest(getObjectRequest)
                        .build();

        return presigner.presignGetObject(presignRequest)
                .url()
                .toString();
    }

}
