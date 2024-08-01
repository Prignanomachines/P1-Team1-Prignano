package com.revature.RevConnect.service;

import com.revature.RevConnect.models.Post;
import com.revature.RevConnect.repositories.PostRepository;
import com.revature.RevConnect.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post addPost(Post post) {
        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    //huh
    public Post updatePost(int postID, String post) {
        Post existingPost = postRepository.findByPostID(postID);
        if(existingPost != null){
            existingPost.setPost(post);
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
