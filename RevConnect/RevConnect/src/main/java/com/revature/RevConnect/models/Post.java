package com.revature.RevConnect.models;

import java.util.ArrayList;
import java.util.List;

public class Post {

    //Post info

    //Primary key for identification
    int postID;

    //TODO: add image support, also expand into post header, body text, body image. For now, its just a text.
    String post;

    //The list of all comments, so we can see who posted what comments
    //TODO: add delete comment support
    List<Comment> commentList = new ArrayList<>();

    //This will just hold the reference ID of the users who posted.
    //TODO: add unlike support
    List<Integer> likeList = new ArrayList<>();

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

    public void addComment(Comment comment) {
        commentList.add(comment);
    }

    public void addLike(int like) {
        likeList.add(like);
    }

}
