package com.revature.RevConnect.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.revature.RevConnect.models.Comment;
import com.revature.RevConnect.models.Like;
import com.revature.RevConnect.models.User;
import com.revature.RevConnect.service.CommentService;
import com.revature.RevConnect.service.LikeService;
import com.revature.RevConnect.service.PostService;
import com.revature.RevConnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        if (user.getPassword().length() >= 4 && !user.getUsername().isEmpty()) {
            if (userService.getUser(user.getUsername()) == null) {
                User result = userService.addUser(user);
                try {
                    String jsonStr = ow.writeValueAsString(result);
                    return ResponseEntity.status(200).body(jsonStr);
                }
                catch (JsonProcessingException e) {
                    return ResponseEntity.status(500).body("Internal Server Error");
                }

            }
            else return ResponseEntity.status(409).body("Username Taken");
        } else return ResponseEntity.status(400).body("Invalid Username or Password");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        if (userService.getUser(user.getUsername()) != null) {
            User result = userService.getUser(user.getUsername());
            if (result.getPassword().equals(user.getPassword())) {
                try {
                    String jsonStr = ow.writeValueAsString(result);
                    return ResponseEntity.status(200).body(jsonStr);
                } catch (JsonProcessingException e) {
                    return ResponseEntity.status(500).body("Internal Server Error");
                }
            }
            else return ResponseEntity.status(409).body("Password was incorrect.");
        }
        else return ResponseEntity.status(409).body("Username not found.");
    }

    @PutMapping("/like/{postID}/{userID}")
    public ResponseEntity<String> addLike(@PathVariable int postID, @PathVariable int userID) { // TODO: Refactor with cookie
        if (likeService.findByPostIDAndUserID(postID, userID).isEmpty()) {
            Like like = new Like(postID, userID);
            likeService.addLike(like);
            return ResponseEntity.status(200).body("Like added.");
        }
        return ResponseEntity.status(400).body("You have already liked this post.");
    }

    @DeleteMapping("/like/{postID}/{userID}")
    public ResponseEntity<String> deleteLike(@PathVariable int postID, @PathVariable int userID) { // TODO: Refactor with cookie
        if (!likeService.findByPostIDAndUserID(postID, userID).isEmpty()) {
            Like like = new Like(postID, userID);
            likeService.deleteLike(like);
            return ResponseEntity.status(200).body("Like deleted.");
        }
        return ResponseEntity.status(404).body("Like not found.");
    }

    @PutMapping("/comment/{postID}")
    public ResponseEntity<String> addComment(@PathVariable int postID) {
        Comment comment = new Comment(postID);
        commentService.addComment(comment);
        return ResponseEntity.status(200).body("Comment added.");
    }

    @DeleteMapping("/comment/{postID}/{commentID}")
    public ResponseEntity<String> deleteComment(@PathVariable int postID, @PathVariable int commentID) {
        if (!commentService.getCommentsByPostAndUser(postID, commentID).isEmpty()) {
            return ResponseEntity.status(200).body("Comment deleted.");
        }
        return ResponseEntity.status(404).body("Comment not found.");
    }


}
