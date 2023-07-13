package com.hughtran.videoplatform.repository;

import com.hughtran.videoplatform.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<Video,String> {


}
