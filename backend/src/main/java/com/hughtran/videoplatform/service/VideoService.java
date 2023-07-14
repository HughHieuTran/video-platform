package com.hughtran.videoplatform.service;

import com.hughtran.videoplatform.dto.VideoDto;
import com.hughtran.videoplatform.exception.YoutubeCloneException;
import com.hughtran.videoplatform.mapper.VideoMapper;
import com.hughtran.videoplatform.model.Video;
import com.hughtran.videoplatform.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final S3Service s3Service;
    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;

    public void uploadVideo(MultipartFile file) {
        String videoUrl = s3Service.uploadFile(file);
        Video video = new Video();
        video.setUrl(videoUrl);

        videoRepository.save(video);
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
}
