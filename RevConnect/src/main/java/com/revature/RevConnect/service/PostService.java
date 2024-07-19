package com.revature.RevConnect.service;

import com.revature.RevConnect.models.Post;
import com.revature.RevConnect.repositories.PostRepository;
import com.revature.RevConnect.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post addPost(Post post) {
        if(post.getPost().isEmpty() || post.getPost().length() >= 4000){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message is either blank, or non existent");
        }else{
            return postRepository.save(post);
        }
    }

    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    //TODO: update
    public Post updatePost(int postID, Post post) {
        Post existingPost = postRepository.findByPostID(postID);
        if(existingPost != null){
            existingPost.setPost(post.getPost());
            return postRepository.save(existingPost);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message is either blank, or cannot be found");
        }
    }

    public Post getPostById(int postID) {
        return postRepository.findByPostID(postID);
    }

    public List<Post> getPostsByAuthor(int userID) {
        return postRepository.findByUserID(userID);
    }

    //TODO: Get post by headline (once posts have headlines)

}
