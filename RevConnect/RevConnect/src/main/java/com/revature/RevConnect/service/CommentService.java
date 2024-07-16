package com.revature.RevConnect.service;

import com.revature.RevConnect.models.Comment;
import com.revature.RevConnect.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    void addComment(Comment c) {
        commentRepository.save(c);
    }

    void deleteComment(Comment c) {
        commentRepository.delete(c);
    }

    //TODO: update
    void updateComment(Comment c) {

    }

    List<Comment> getCommentsByPost(int postID) {

        return commentRepository.findByPostID(postID);
    }

}
