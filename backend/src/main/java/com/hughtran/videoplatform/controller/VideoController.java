package com.hughtran.videoplatform.controller;

import com.hughtran.videoplatform.dto.UploadVideoResponse;
import com.hughtran.videoplatform.dto.VideoDto;
import com.hughtran.videoplatform.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping("upload")
    public ResponseEntity<UploadVideoResponse> uploadVideo(@RequestParam("file") MultipartFile file,
                                                           @RequestParam("userId") String userId,
                                                           UriComponentsBuilder uriComponentsBuilder) {
        UploadVideoResponse videoResponse = videoService.uploadVideo(file, userId);
        var uriComponents = uriComponentsBuilder.path("/{id}").buildAndExpand(videoResponse.getVideoId());
        return ResponseEntity.created(uriComponents.toUri())
                .body(videoResponse);
    }

    @PostMapping("thumbnail/upload")
    public ResponseEntity<String> uploadThumbnail(@RequestParam("file") MultipartFile file,
                                                  @RequestParam("videoId") String videoId,
                                                  UriComponentsBuilder uriComponentsBuilder) {
        String thumbnailUrl = videoService.uploadThumbnail(file, videoId);
        var uriComponents = uriComponentsBuilder.path("/{id}").buildAndExpand(thumbnailUrl);
        return ResponseEntity.created(uriComponents.toUri())
                .body("Thumbnail Uploaded Successfully");
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public VideoDto editVideoMetadata(@RequestBody @Validated VideoDto videoMetaDataDto) {
        return videoService.editVideoMetadata(videoMetaDataDto);
    }
}
