package com.revature.RevConnect.models;

import jakarta.persistence.*;

@Entity
@Table(name="Comments")
public class Comment {

    //Comment info

    //Primary key for identification
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="COMMENT_ID")
    int commentID;

    //This is the content of the comment
    @Column(name="COMMENT")
    String comment;

    //The ID of the post
    @Column(name="POST_ID")
    int postID;

    //The ID of the author
    @Column(name="AUTHOR_ID")
    int authorID;

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public Comment(int postID, int authorID) {
        this.postID = postID;
        this.authorID = authorID;
    }

    public Comment() {}

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
