package com.revature.RevConnect.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.revature.RevConnect.models.Follow;
import com.revature.RevConnect.models.Post;
import com.revature.RevConnect.models.Like;
import com.revature.RevConnect.models.User;
import com.revature.RevConnect.service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.revature.RevConnect.models.Message;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    MessageService messageService;

    @Autowired
    JwtTokenService tokenService;

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user, HttpServletResponse response) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        if (user.getPassword().length() >= 4 && !user.getUsername().isEmpty()) {
            if (userService.getUser(user.getUsername()) == null) {
                User result = userService.addUser(user);

                Map<String, String> claimsMap = new HashMap<>();
                claimsMap.put("username", result.getUsername());
                claimsMap.put("userID", String.valueOf(result.getUserID()));

                Cookie cookie = new Cookie("Authentication", this.tokenService.generateToken(claimsMap));
                response.addCookie(cookie);

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
    public ResponseEntity<String> loginUser(@RequestBody User user, HttpServletResponse response) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        if (userService.getUser(user.getUsername()) != null) {
            User result = userService.getUser(user.getUsername());
            if (result.getPassword().equals(user.getPassword())) {

                Map<String, String> claimsMap = new HashMap<>();
                claimsMap.put("username", result.getUsername());
                claimsMap.put("userID", String.valueOf(result.getUserID()));

                Cookie cookie = new Cookie("Authentication", this.tokenService.generateToken(claimsMap));
                response.addCookie(cookie);

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

    //This is an example of using the auth to grab the userID, You guys can set up the rest of the methods to use auth
    @PutMapping("/like/{postID}")
    public ResponseEntity<String> addLike(@CookieValue("Authentication") String bearerToken, @PathVariable int postID) {
        Integer userID = authenticateAndReturnID(bearerToken);
        if (userID != null) {
            if (likeService.findByPostIDAndUserID(postID, userID).isEmpty()) {
                Like like = new Like(postID, userID);
                likeService.addLike(like);
                return ResponseEntity.status(200).body("Like added.");
            }
        }
        return ResponseEntity.status(400).body("You have already liked this post.");
    }

    // TODO: Refactor authentication required methods bellow with cookie

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

    @PostMapping("/message")
    public ResponseEntity<String> sendMessage(@RequestBody Message message) {
        message.setTimestamp(LocalDateTime.now());
        messageService.addMessage(message);
        return ResponseEntity.status(200).body("Message sent.");
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.findAllMessages();
        return ResponseEntity.status(200).body(messages);
    }

    @GetMapping("/messages/sender/{senderID}")
    public ResponseEntity<List<Message>> getMessagesBySender(@PathVariable int senderID) {
        User sender = userService.getUser(senderID);
        if (sender != null) {
            List<Message> messages = messageService.findBySender(sender);
            return ResponseEntity.status(200).body(messages);
        }
        return ResponseEntity.status(404).body(null);
    }

    @DeleteMapping("/message/{messageID}")
    public ResponseEntity<String> deleteMessage(@PathVariable Long messageID) {
        if (messageService.existsById(messageID)) {
            messageService.deleteMessage(messageID);
            return ResponseEntity.status(200).body("Message deleted.");
        }
        return ResponseEntity.status(404).body("Message not found.");
    }

    @GetMapping("/messages/{senderID}/{receiverID}")
    public ResponseEntity<List<Message>> getMessagesBetweenUsers(@PathVariable int senderID, @PathVariable int receiverID) {
        User sender = userService.getUser(senderID);
        User receiver = userService.getUser(receiverID);
        if (sender != null && receiver != null) {
            List<Message> messages = messageService.findMessagesBetweenUsers(sender, receiver);
            return ResponseEntity.status(200).body(messages);
        }
        return ResponseEntity.status(404).body(null);
    }

    //Given a token string, return the authenticated users ID
    private Integer authenticateAndReturnID(String token) {
        //Get token
        int userID = tokenService.returnAuthID(token);

        System.out.println(userID);

        //Using the ID, get the user associated with it
        User u = userService.getUser(userID);

        //Authenticate
        if (tokenService.validateAuthentication(token, u.getUsername())) {
            //return token
            return userID;
        }

        //Authentication failed
        return null;
    }
}
