package com.revature.RevConnect.service;

import com.revature.RevConnect.models.Post;
import com.revature.RevConnect.repositories.PostRepository;
import com.revature.RevConnect.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public void addPost(Post post) {
        postRepository.save(post);
    }

    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    //TODO: update
    public void updatePost(Post post) {
    }

    public Post getPostById(int postID) {
        return postRepository.findByPostID(postID);
    }

    public List<Post> getPostsByAuthor(int userID) {
        return postRepository.findByUserID(userID);
    }

    //TODO: Get post by headline (once posts have headlines)

}
