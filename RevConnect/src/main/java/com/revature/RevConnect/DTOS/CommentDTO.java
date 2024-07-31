package com.revature.RevConnect.DTOS;

public class CommentDTO {


    public CommentDTO(int commentID, String comment, String Author) {
        this.commentID = commentID;
        this.comment = comment;
        this.Author = Author;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    int commentID;
    String comment;
    String Author;

}
