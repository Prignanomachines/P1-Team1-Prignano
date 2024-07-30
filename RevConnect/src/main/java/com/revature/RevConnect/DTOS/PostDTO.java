package com.revature.RevConnect.DTOS;

import java.util.ArrayList;
import java.util.List;
import com.revature.RevConnect.DTOS.CommentDTO;

public class PostDTO {

    public PostDTO(int postID, String post, String Author, int likes, List<CommentDTO> comments) {
        this.postID = postID;
        this.post = post;
        this.author = Author;
        this.likes = likes;
        this.comments = comments;
    }

    int postID;
    String post;
    String author;
    int likes;
    List<CommentDTO> comments;


    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

}
