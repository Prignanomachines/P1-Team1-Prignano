package com.revature.RevConnect.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Posts")
public class Post {

    //Post info

    //Primary key for identification
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int postID;

    //TODO: add image support, also expand into post header, body text, body image. For now, its just a text.
    String post;

    int userID;

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
