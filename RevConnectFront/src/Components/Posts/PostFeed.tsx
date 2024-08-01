import React, { useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import { TwitterShareButton, TwitterIcon } from "react-share";
import likeButton from './likeButton.png';
import commentButton from './commentButton.png';
import './PostFeed.css';
import 'bootstrap/dist/css/bootstrap.css';

interface comment {
    commentID: number;
    author: string;
    comment: string;
}
interface Post {
    postID: number;
    post: string;
    author: string;
    likes: number;
    comments: Array<comment>;
}

function GetPostsForFeed(){

    const [posts, setPost] = useState(Array<Post>);

    
    async function feedRefresh(){
        let postList: Array<Post> = await getPosts();
        setPost(postList);
    }

    async function addLike(postID: number) {
        await modifyLike(postID);
        feedRefresh();
    }

    async function addComment(postID: number) {
        await postComment(postID)
        feedRefresh();
    }

return (
    <div className="post-feed-container">
            <div className="App">
                <h2>User Posts</h2>
            </div>
            {posts.map(post => (
                <div className="post-container" key={post.postID}>
                    <div className="post-content">
                        <div className="post-detail">
                            <div className="user-info">
                                <h5>{post.author}</h5>
                            </div>
                            <div className="line-divider"></div>
                            <div className="post-text">
                                <p> - "{post.post}" <i className="em em-anguished"></i> <i className="em em-anguished"></i> <i className="em em-anguished"></i></p>
                            </div>
                            <div className="reaction">
                                <button className="reaction-button" onClick={() => addLike(post.postID)}>
                                    <img width="25" height="25" src={likeButton} alt="LIKE" />
                                </button>
                                <button className="reaction-button" onClick={() => addComment(post.postID)}>
                                    <img width="25" height="25" src={commentButton} alt="COMMENT" />
                                </button>
                                <input className="comment-input" id={post.postID.toString()} />
                                <p>{post.likes} likes</p>
                            </div>
                            <div className="comments-section">
                                {post.comments.map(comment => (
                                    <div key={comment.commentID} className="comment">
                                        <strong>{comment.author}</strong>: {comment.comment}
                                    </div>
                                ))}
                            </div>
                            <div className="share-button">
                                <TwitterShareButton
                                    url={`https://localhost:8080/post/${post.postID}`}
                                    title={post.post}
                                >
                                    <TwitterIcon size={32} round={true} />
                                </TwitterShareButton>
                            </div>
                        </div>
                    </div>
                </div>
            ))}
            <div className="App">
                <button className="refresh-button" onClick={feedRefresh}>Refresh Feed</button>
            </div>
        </div>
);
}

async function getPosts(): Promise<Array<Post>>{

    const url = "http://localhost:8080/post";
    
    try {
        const response = await axios(url, {
            method: 'get',
            headers: {
                'Content-Type': 'application/json'
            },
            withCredentials: true
        });

        if (!(response.status === 200)) {
            throw new Error(`Response status: ${response.status}`);
        }
        console.log(response.data);
        return response.data;
    } catch (error: any) {
        console.error(error.message);
    }
    return [];
};

async function modifyLike(id: number) {
    const url = "http://localhost:8080/like/" + id;
    try {
        const response = await axios(url, {
            method: 'put',
            headers: {
                'Content-Type': 'application/json'
            },
            withCredentials: true
        });

        if (!(response.status === 200)) {
            throw new Error(`Response status: ${response.status}`);
        }

    } catch (error: any) {
        console.error(error.message);
    }
}

async function postComment(id: number) {
    const url = "http://localhost:8080/comment/" + id;

    let commentForm: HTMLInputElement = document.getElementById(id.toString()) as HTMLInputElement;

    try {
        const response = await axios(url, {
            method: 'put',
            headers: {
                'Content-Type': 'application/json'
            },
            withCredentials: true,
            data: commentForm.value
        });

        if (!(response.status === 200)) {
            throw new Error(`Response status: ${response.status}`);
        }

    } catch (error: any) {
        console.error(error.message);
    }
}

export default GetPostsForFeed;