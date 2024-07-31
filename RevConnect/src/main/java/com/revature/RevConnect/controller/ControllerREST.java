package com.revature.RevConnect.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.revature.RevConnect.DTOS.CommentDTO;
import com.revature.RevConnect.DTOS.PostDTO;
import com.revature.RevConnect.models.Follow;
import com.revature.RevConnect.models.Like;
import com.revature.RevConnect.models.User;
import com.revature.RevConnect.models.Comment;
import com.revature.RevConnect.models.Post;
import com.revature.RevConnect.service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    JwtTokenService tokenService;

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @PostMapping("/register")
    @CrossOrigin(allowCredentials = "true", origins = "http://localhost:3000", methods=RequestMethod.POST)
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
    @CrossOrigin(allowCredentials = "true", origins = "http://localhost:3000", methods=RequestMethod.POST)
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

    @PostMapping("/cookie-auth")
    @CrossOrigin(allowCredentials = "true", origins = "http://localhost:3000", methods=RequestMethod.POST)
    public ResponseEntity<User> authUser(@CookieValue("Authentication") String bearerToken) {
        Integer userID = authenticateAndReturnID(bearerToken);
        if (userService.getUser(userID) != null) {
            User result = userService.getUser(userID);
            return ResponseEntity.status(200).body(result);
        }
        else return ResponseEntity.status(409).body(null);

    }

    //This is an example of using the auth to grab the userID, You guys can set up the rest of the methods to use auth
    @PutMapping("/like/{postID}")
    @CrossOrigin(allowCredentials = "true", origins = "http://localhost:3000", methods=RequestMethod.PUT)
    public ResponseEntity<String> addLike(@CookieValue("Authentication") String bearerToken, @PathVariable int postID) {
        System.out.println("Post ID = " + postID);
        Integer userID = authenticateAndReturnID(bearerToken);
        if (userID != null) {
            if (likeService.findByPostIDAndUserID(postID, userID).isEmpty()) {
                Like like = new Like(postID, userID);
                likeService.addLike(like);
                return ResponseEntity.status(200).body("Like added.");
            }
            else {
                Like like = likeService.findByPostIDAndUserID(postID, userID).get(0);
                likeService.deleteLike(like);
                return ResponseEntity.status(200).body("Like deleted.");
            }
        }
        return ResponseEntity.status(400).body("Not authenticated.");
    }

    // TODO: Refactor authentication required methods bellow with cookie
    @PostMapping("/follow")
    @CrossOrigin(allowCredentials = "true", origins = "http://localhost:3000", methods=RequestMethod.POST)
    public ResponseEntity<String> addFollow(@RequestBody Follow follow) {
        followService.addFollow(follow);
        return ResponseEntity.status(200).body("Successfully followed.");
    }

    @GetMapping("/follows/follower/{followerID}")
    @CrossOrigin(allowCredentials = "true", origins = "http://localhost:3000", methods=RequestMethod.GET)
    public ResponseEntity<List<Follow>> getFollowsByFollowerID(@PathVariable int followerID) {
        List<Follow> follows = followService.findByFollowerID(followerID);
        return ResponseEntity.status(200).body(follows);
    }

    @GetMapping("/follows/following/{followingID}")
    @CrossOrigin(allowCredentials = "true", origins = "http://localhost:3000", methods=RequestMethod.GET)
    public ResponseEntity<List<Follow>> getFollowsByFollowingID(@PathVariable int followingID) {
        List<Follow> follows = followService.findByFollowingID(followingID);
        return ResponseEntity.status(200).body(follows);
    }

    @GetMapping("/follows/check")
    @CrossOrigin(allowCredentials = "true", origins = "http://localhost:3000", methods=RequestMethod.GET)
    public ResponseEntity<Boolean> isFollowerIDFollowingFollowingID(@RequestParam int followerID, @RequestParam int followingID) {
        boolean isFollowing = followService.existsByFollowerIDAndFollowingID(followerID, followingID);
        return ResponseEntity.status(200).body(isFollowing);
    }

    @DeleteMapping("/unfollow")
    @CrossOrigin(allowCredentials = "true", origins = "http://localhost:3000", methods=RequestMethod.DELETE)
    public ResponseEntity<String> unfollow(@RequestParam int followerID, @RequestParam int followingID) {
        followService.unfollow(followerID, followingID);
        return ResponseEntity.status(200).body("Successfully unfollowed.");
    }

    //This is Zachs code: TODO use Auth cookie to get userID serverside.
    //Getting all of the posts associated by UserID
    @GetMapping("/post/{username}")
    @CrossOrigin(allowCredentials = "true", origins = "http://localhost:3000")
    public ResponseEntity<?> getPostsByUser(@PathVariable String username){
        User u = userService.getUser(username);
        List<Post> posts = postService.getPostsByAuthor(u.getUserID());
        List<PostDTO> toReturn = new ArrayList<>();
        for (int i = 0; i < posts.size(); i++) {
            String author = userService.getUser(posts.get(i).getUserID()).getUsername();
            List<Like> likes = likeService.getLikesByPostID(posts.get(i).getPostID());
            List<Comment> comments = commentService.getCommentsByPost(posts.get(i).getPostID());
            List<CommentDTO> commentDTOS = new ArrayList<>();

            for (int j = 0; j < comments.size(); j++) {
                String a = userService.getUser(comments.get(i).getAuthorID()).getUsername();
                commentDTOS.add(new CommentDTO(comments.get(j).getCommentID(), comments.get(j).getComment(), a));
            }

            PostDTO d = new PostDTO(posts.get(i).getPostID(), posts.get(i).getPost(), author, likes.size(), commentDTOS);

            toReturn.add(d);
        }
        return ResponseEntity.ok(toReturn);
    }

    //Get the feed of posts
    @GetMapping("/post")
    @CrossOrigin(allowCredentials = "true", origins = "http://localhost:3000")
    public ResponseEntity<?> getPostsAll(){
        List<Post> posts = postService.getAllPosts();
        List<PostDTO> toReturn = new ArrayList<>();
        for (int i = 0; i < posts.size(); i++) {
            String author = userService.getUser(posts.get(i).getUserID()).getUsername();
            List<Like> likes = likeService.getLikesByPostID(posts.get(i).getPostID());
            List<Comment> comments = commentService.getCommentsByPost(posts.get(i).getPostID());
            List<CommentDTO> commentDTOS = new ArrayList<>();

            for (int j = 0; j < comments.size(); j++) {
                String a = userService.getUser(comments.get(j).getAuthorID()).getUsername();
                commentDTOS.add(new CommentDTO(comments.get(j).getCommentID(), comments.get(j).getComment(), a));
            }

            PostDTO d = new PostDTO(posts.get(i).getPostID(), posts.get(i).getPost(), author, likes.size(), commentDTOS);

            toReturn.add(d);
        }
        return ResponseEntity.ok(toReturn);
    }

    //Need to verify that a specific
    @PostMapping("/post")
    @CrossOrigin(allowCredentials = "true", origins = "http://localhost:3000")
    public ResponseEntity<?> createPost(@CookieValue ("Authentication") String bearerToken, @RequestBody String post){
        Integer userID = authenticateAndReturnID(bearerToken);

        if (userID != null) {
            Post p = new Post(post, userID);
            Post newPost = postService.addPost(p);
            return ResponseEntity.ok(newPost);
        }
        return ResponseEntity.status(400).body("Not authenticated");

    }

    //authentication wait for userID
    @DeleteMapping("/post/{postID}")
    @CrossOrigin(allowCredentials = "true", origins = "http://localhost:3000")
    public ResponseEntity<?> deletePost(@PathVariable int postID){
        //Get USERID for post using authenticateAndReturnID();
        Post newPost = postService.getPostById(postID);
        if(newPost == null){
            return ResponseEntity.status(400).body("Post is null, not found?");
        }
        postService.deletePost(newPost);
        return ResponseEntity.ok("Post deleted");
    }

    @PatchMapping("/post")
    @CrossOrigin(allowCredentials = "true", origins = "http://localhost:3000")
    public ResponseEntity<?> updatePost(@RequestBody Post post){
        //Get USERID for post using authenticateAndReturnID();
        Post updatedPost = postService.updatePost(post);
        return ResponseEntity.ok(updatedPost);
    }

    @GetMapping("/like/{postID}")
    @CrossOrigin(allowCredentials = "true", origins = "http://localhost:3000")
    public ResponseEntity<?> getLikesByPostID(@PathVariable int postID) {
        List<Like> likes = likeService.getLikesByPostID(postID);
        return ResponseEntity.ok(likes);
    }

    @PutMapping("/comment/{postID}")
    @CrossOrigin(allowCredentials = "true", origins = "http://localhost:3000")
    public ResponseEntity<String> addComment(@PathVariable int postID, @CookieValue("Authentication") String bearerToken, @RequestBody String content) {
        Integer userID = authenticateAndReturnID(bearerToken);
        if (userID != null) {
            Comment comment = new Comment(postID, userID, content);
            commentService.addComment(comment);
            return ResponseEntity.status(200).body("Comment added.");
        }
        return ResponseEntity.status(400).body("Not authenticated.");
    }

    @DeleteMapping("/comment/{commentID}")
    @CrossOrigin(allowCredentials = "true", origins = "http://localhost:3000")
    public ResponseEntity<String> deleteComment(@PathVariable int commentID, @CookieValue("Authentication") String bearerToken) {
        Integer userID = authenticateAndReturnID(bearerToken);
        if (userID != null) {
            Comment comment = commentService.findById(commentID);
            if (comment != null && comment.getAuthorID() == userID) {
                commentService.deleteComment(comment);
                return ResponseEntity.status(200).body("Comment deleted.");
            }
            return ResponseEntity.status(404).body("Comment not found.");
        }
        return ResponseEntity.status(400).body("Not authenticated.");
    }
    //Given a token string, return the authenticated users ID
    private Integer authenticateAndReturnID(String token) {
        //Get token
        Integer userID = tokenService.returnAuthID(token);
        if(userID != null) {
            System.out.println(userID);

            //Using the ID, get the user associated with it
            User u = userService.getUser(userID);

            //Authenticate
            if (tokenService.validateAuthentication(token, u.getUsername())) {
                //return token
                return userID;
            }
        }
        //Authentication failed
        return null;
    }

    @GetMapping("/profile/{userID}")
    public ResponseEntity<?> getProfile(@PathVariable int userID){
        User user = userService.getUser(userID);
        if(user == null){
            return ResponseEntity.status(400).body("User not found");
        }
        return ResponseEntity.ok(user);
    }

    //Json does not work have to use text format
    @PatchMapping("/profile")
    @CrossOrigin(allowCredentials = "true", origins =
            "http://localhost:3000", methods = RequestMethod.PATCH)
    public ResponseEntity<?> updateBio(@RequestBody String bio,@CookieValue("Authentication") String bearerToken){
        Integer userIDCheck = authenticateAndReturnID(bearerToken);

        if(userIDCheck == null){
            return ResponseEntity.status(400).body("User not found or User does not have cookie");
        }

        userService.updateBio(userIDCheck,bio);
        return ResponseEntity.ok("successfully updated");
    }

    @GetMapping("/profile/user")
    @CrossOrigin(allowCredentials = "true", origins =
            "http://localhost:3000")
    public ResponseEntity<?> userProfile(@CookieValue("Authentication") String bearerToken){
        Integer userIDCheck = authenticateAndReturnID(bearerToken);


        if((userIDCheck == null)){
            return ResponseEntity.status(400).body("User not found or User does not have cookie");
        }

       User user =  userService.getUser(userIDCheck);

        return ResponseEntity.ok(user);
    }
}
