package com.revature.RevConnect.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="User")
public class User {

    //User info

    //Primary key for identification
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int userID;

    //Display info
    String username;
    String firstname;
    String lastname;
    String bio;

    //authentication
    String password;

    /* TODO: refactor
    //reference to posts, we hold all the data for each post.
    //TODO: remove post
    List<Post> posts = new ArrayList<>();

    //For our likes, we can just keep a reference ID
    //TODO: unlike
    List<Integer> likedPosts = new ArrayList<>();

    //For shares, we also only need a reference ID TODO: add share functionality
    List<Integer> sharedPosts = new ArrayList<>();

    //For followers, we can give a reference ID.
    //TODO: unfollow
    List<Integer> following = new ArrayList<>();
    List<Integer> followers = new ArrayList<>();

    //TODO: add connection functionality
    List<Integer> connections = new ArrayList<>();
    */
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*
    public void addPost(Post post) {
        posts.add(post);
    }

    public void likePost(int postID) {
        likedPosts.add(postID);
    }

    public void followUser(int userID) {
        following.add(userID);
        //TODO: add to other users followers list
    }

    public void newFollower(int userID) {
        followers.add(userID);
    }
     */
}
