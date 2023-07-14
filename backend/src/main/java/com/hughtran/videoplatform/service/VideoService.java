package com.hughtran.videoplatform.service;

import com.hughtran.videoplatform.dto.UploadVideoResponse;
import com.hughtran.videoplatform.dto.VideoDto;
import com.hughtran.videoplatform.exception.YoutubeCloneException;
import com.hughtran.videoplatform.mapper.VideoMapper;
import com.hughtran.videoplatform.model.Video;
import com.hughtran.videoplatform.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final S3Service s3Service;
    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;

    public UploadVideoResponse uploadVideo(MultipartFile file, String userId) {
        //upload file -> get url response
        String url = s3Service.upload(file);
        //set up video
        var video = new Video();
        video.setUrl(url);
        Objects.requireNonNull(userId);
        video.setUserId(userId);
        //save video
        videoRepository.save(video);
        return new UploadVideoResponse(video.getId(), url);
    }

    public VideoDto editVideoMetadata(VideoDto videoMetaDataDto) {
        var video = getVideoById(videoMetaDataDto.getVideoId());
        video.setTitle(videoMetaDataDto.getVideoName());
        video.setDescription(videoMetaDataDto.getDescription());
        video.setUrl(videoMetaDataDto.getUrl());
        video.setTags(videoMetaDataDto.getTags());
        video.setVideoStatus(videoMetaDataDto.getVideoStatus());
        videoRepository.save(video);
        return videoMapper.mapToDto(video);
    }

    private Video getVideoById(String id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> new YoutubeCloneException("Cannot find Video with ID - " + id));
    }

    public String uploadThumbnail(MultipartFile file, String videoId) {
        //get video
        Video video = getVideoById(videoId);
        // upload thumbnail -> get url response
        String url = s3Service.upload(file);
        //set up video thumbnail
        video.setThumbnailUrl(url);
        //save thumbnail
        videoRepository.save(video);
        return url;
    }
}
