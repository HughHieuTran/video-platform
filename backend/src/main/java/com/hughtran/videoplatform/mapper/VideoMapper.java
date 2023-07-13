package com.hughtran.videoplatform.mapper;


import com.hughtran.videoplatform.dto.VideoDto;
import com.hughtran.videoplatform.model.Video;
import org.springframework.stereotype.Service;

@Service
public class VideoMapper {
    public VideoDto mapToDto(Video video) {
        return VideoDto.builder()
                .videoId(video.getId())
                .url(video.getUrl())
                .description(video.getDescription())
                .tags(video.getTags())
                .videoName(video.getTitle())
                .videoStatus(video.getVideoStatus())
                .userId(video.getUserId())
                .thumbnailUrl(video.getThumbnailUrl())
                .likeCount(video.getLikes().get())
                .dislikeCount(video.getDisLikes().get())
                .build();
    }
}