package com.hughtran.videoplatform.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileService {
    public String upload(MultipartFile file);

    public void deleteFile(String fileName);
}
