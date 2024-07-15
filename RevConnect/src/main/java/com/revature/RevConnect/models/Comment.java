package com.revature.RevConnect.models;

import jakarta.persistence.*;

@Entity
@Table(name="Comment")
public class Comment {

    //Comment info

    //Primary key for identification
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int commentID;

    //This is the content of the comment
    String comment;

    //The ID of the post
    int postID;

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }
}
