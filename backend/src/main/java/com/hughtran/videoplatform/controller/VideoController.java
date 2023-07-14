package com.hughtran.videoplatform.controller;

import com.hughtran.videoplatform.dto.VideoDto;
import com.hughtran.videoplatform.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadVideo(@RequestParam("file") MultipartFile file){
        videoService.uploadVideo(file);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public VideoDto editVideoMetadata(@RequestBody @Validated VideoDto videoMetaDataDto) {
        return videoService.editVideoMetadata(videoMetaDataDto);
    }
}
