package com.hughtran.videoplatform.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service implements FileService {

    public static final String BUCKET_NAME = "hughtranbucket";
    private final AmazonS3Client awsAmazonS3Client;

    @Override
    public String uploadFile(MultipartFile file) {

        // prepared a key
        var filenameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        var key = UUID.randomUUID().toString() + "." + filenameExtension;

        var metadata = new ObjectMetadata();

        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        try {//upload file to aws s3
            awsAmazonS3Client.putObject(BUCKET_NAME, key, file.getInputStream(), metadata);
        } catch (IOException ex) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "an exception occurred while uploading the file");
        }
        //set privacy
        awsAmazonS3Client.setObjectAcl(BUCKET_NAME,key, CannedAccessControlList.PublicRead);
        // get video url
        return awsAmazonS3Client.getResourceUrl(BUCKET_NAME,key);
    }
}
