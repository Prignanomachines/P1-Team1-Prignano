package com.revature.RevConnect.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.revature.RevConnect.models.Like;
import com.revature.RevConnect.models.Post;
import com.revature.RevConnect.models.User;
import com.revature.RevConnect.service.CommentService;
import com.revature.RevConnect.service.LikeService;
import com.revature.RevConnect.service.PostService;
import com.revature.RevConnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ControllerREST {

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (user.getPassword().length() >= 4 && !user.getUsername().isEmpty()) {
            if (userService.getUser(user.getUsername()) == null) {
                User result = userService.addUser(user);
                return ResponseEntity.ok(result);
            }
            else return ResponseEntity.status(409).body("Username Taken");
        } else return ResponseEntity.status(400).body("Invalid Username or Password");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        if (userService.getUser(user.getUsername()) != null) {
            User result = userService.getUser(user.getUsername());
            if (result.getPassword().equals(user.getPassword())) {
                return ResponseEntity.ok(result);
            }
            else return ResponseEntity.status(409).body("Password was incorrect.");
        }
        else return ResponseEntity.status(409).body("Username not found.");
    }

    @PutMapping("/like/{postId}/{userId}")
    public ResponseEntity<String> addLike(@PathVariable int postId, @PathVariable int userId) { // TODO: Refactor with cookie
        if (likeService.findByPostIdAndUserId(postId, userId).isEmpty()) {
            Like like = new Like(postId, userId);
            likeService.addLike(like);
            return ResponseEntity.status(200).body("Like added.");
        }
        return ResponseEntity.status(400).body("You have already liked this post.");
    }

    //Getting all of the posts associated by UserID
    @GetMapping("/post/{userID}")
    public ResponseEntity<?> getPosts(@PathVariable int userID){
        List<Post> posts = postService.getPostsByAuthor(userID);
        return ResponseEntity.ok(posts);
    }

}
