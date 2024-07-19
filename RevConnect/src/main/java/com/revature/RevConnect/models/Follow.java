package com.revature.RevConnect.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Follows")
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FOLLOW_ID")
    private int followID;

    // The ID of the user who is following
    @Column(name = "FOLLOWER_ID")
    private int followerID;

    // The ID of the user being followed
    @Column(name = "FOLLOWING_ID")
    private int followingID;

    public Follow() {}

    public Follow(int followerID, int followingID) {
        this.followerID = followerID;
        this.followingID = followingID;
    }

    public int getFollowID() {
        return followID;
    }

    public void setFollowID(int followID) {
        this.followID = followID;
    }

    public int getFollowerID() {
        return followerID;
    }

    public void setFollowerID(int followerID) {
        this.followerID = followerID;
    }

    public int getFollowingID() {
        return followingID;
    }

    public void setFollowingID(int followingID) {
        this.followingID = followingID;
    }
}
