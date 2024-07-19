package com.revature.RevConnect.repositories;

import com.revature.RevConnect.models.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Integer> {
    // Find all follows by follower ID
    List<Follow> findByFollowerID(int followerID);

    // Find all follows by following ID
    List<Follow> findByFollowingID(int followingID);

    // Check if a user is following another user
    boolean existsByFollowerIDAndFollowingID(int followerID, int followingID);

    // Unfollow a user
    void deleteByFollowerIDAndFollowingID(int followerID, int followingID);
}
