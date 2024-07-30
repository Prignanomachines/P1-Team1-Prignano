package com.revature.RevConnect.DTOS;

public class CommentDTO {


    public CommentDTO(int commentID, String comment, String Author) {
        this.commentID = commentID;
        this.comment = comment;
        this.Author = Author;
    }

    int commentID;
    String comment;
    String Author;

}
