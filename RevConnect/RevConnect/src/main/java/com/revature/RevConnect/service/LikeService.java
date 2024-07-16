package com.revature.RevConnect.service;

import com.revature.RevConnect.models.Like;
import com.revature.RevConnect.repositories.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    void addLike(Like l) {
        likeRepository.save(l);
    }

}
