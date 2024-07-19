package com.revature.RevConnect.service;

import com.revature.RevConnect.models.Follow;
import com.revature.RevConnect.repositories.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    // Create a new follow
    public void addFollow(Follow follow) {
        followRepository.save(follow);
    }

    // Find all follows by follower ID
    public List<Follow> findByFollowerID(int followerID) {
        return followRepository.findByFollowerID(followerID);
    }

    // Find all follows by following ID
    public List<Follow> findByFollowingID(int followingID) {
        return followRepository.findByFollowingID(followingID);
    }

    // Check if a user is following another user
    public boolean existsByFollowerIDAndFollowingID(int followerID, int followingID) {
        return followRepository.existsByFollowerIDAndFollowingID(followerID, followingID);
    }

    // Unfollow a user
    public void unfollow(int followerID, int followingID) {
        followRepository.deleteByFollowerIDAndFollowingID(followerID, followingID);
    }
}
