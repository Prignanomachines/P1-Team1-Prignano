package com.revature.RevConnect.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.revature.RevConnect.models.Comment;
import com.revature.RevConnect.models.Follow;
import com.revature.RevConnect.models.Post;
import com.revature.RevConnect.models.Like;
import com.revature.RevConnect.models.Post;
import com.revature.RevConnect.models.User;
import com.revature.RevConnect.service.CommentService;
import com.revature.RevConnect.service.FollowService;
import com.revature.RevConnect.service.LikeService;
import com.revature.RevConnect.service.PostService;
import com.revature.RevConnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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

    @Autowired
    FollowService followService;

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

    @PutMapping("/like/{postID}/{userID}")
    public ResponseEntity<String> addLike(@PathVariable int postID, @PathVariable int userID) { // TODO: Refactor with cookie
        if (likeService.findByPostIDAndUserID(postID, userID).isEmpty()) {
            Like like = new Like(postID, userID);
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

    @GetMapping("/post")

    //Need to verify that a specific
    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody Post post){
        //need any flow control?
        Post newPost = postService.addPost(post);
        return ResponseEntity.ok(newPost);
    }

    //authentication wait for userID
    @DeleteMapping("/post/{postID}")
    public ResponseEntity<?> deletePost(@PathVariable int postID){
        Post newPost = postService.getPostById(postID);
        if(newPost == null){
            return ResponseEntity.status(400).body("Post is null, not found?");
        }
        postService.deletePost(newPost);
        return ResponseEntity.ok("Post deleted");
    }

    //dont need {post} in uri right? because we aren't pathing to the {post}
    @PatchMapping("/post/{postID}")
    public ResponseEntity<?> updatePost(@PathVariable int postID, @RequestBody Post post){
        Post updatedPost = postService.updatePost(postID, post);
        return ResponseEntity.ok(updatedPost);
    }




=======
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

    @PostMapping("/follow")
    public ResponseEntity<String> addFollow(@RequestBody Follow follow) {
        followService.addFollow(follow);
        return ResponseEntity.status(200).body("Successfully followed.");
    }

    @GetMapping("/follows/follower/{followerID}")
    public ResponseEntity<List<Follow>> getFollowsByFollowerID(@PathVariable int followerID) {
        List<Follow> follows = followService.findByFollowerID(followerID);
        return ResponseEntity.status(200).body(follows);
    }

    @GetMapping("/follows/following/{followingID}")
    public ResponseEntity<List<Follow>> getFollowsByFollowingID(@PathVariable int followingID) {
        List<Follow> follows = followService.findByFollowingID(followingID);
        return ResponseEntity.status(200).body(follows);
    }

    @GetMapping("/follows/check")
    public ResponseEntity<Boolean> isFollowerIDFollowingFollowingID(@RequestParam int followerID, @RequestParam int followingID) {
        boolean isFollowing = followService.existsByFollowerIDAndFollowingID(followerID, followingID);
        return ResponseEntity.status(200).body(isFollowing);
    }

    @DeleteMapping("/unfollow")
    public ResponseEntity<String> unfollow(@RequestParam int followerID, @RequestParam int followingID) {
        followService.unfollow(followerID, followingID);
        return ResponseEntity.status(200).body("Successfully unfollowed.");
    }
}
