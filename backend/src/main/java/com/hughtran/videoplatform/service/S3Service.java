package com.hughtran.videoplatform.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.hughtran.videoplatform.exception.YoutubeCloneException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service implements FileService {

    public static final String BUCKET_NAME = "hughtranbucket";
    private final AmazonS3Client awsAmazonS3Client;

    @Override
    public String upload(MultipartFile file) {
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        var key = String.format("%s.%s", UUID.randomUUID(), extension);
        var metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        try {
            awsAmazonS3Client.putObject(BUCKET_NAME, key, file.getInputStream(), metadata);
        } catch (IOException e) {
            throw new YoutubeCloneException("An Exception Occurred while uploading file");
        }
        awsAmazonS3Client.setObjectAcl(BUCKET_NAME, key, CannedAccessControlList.PublicRead);
        return awsAmazonS3Client.getResourceUrl(BUCKET_NAME, key);
    }

    @Override
    public void deleteFile(String fileName) {
        // TODO:
    }


}
