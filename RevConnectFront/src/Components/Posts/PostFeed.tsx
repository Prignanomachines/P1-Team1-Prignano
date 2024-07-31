import React, { useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
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

    }

    async function addComment(postID: number) {

    }

return (
    <div>
        <div className="App">
            <h2>User Posts</h2>
        </div>
            {posts.map(post => (
                <div className="container">
                    <div className="row">
                        <div className="">
                            <div className="post-content">
                                <div className="post-detail">
                                    <div className="user-info">
                                        <h5>{ post.author }</h5>
                                    </div>
                                    <div className="line-divider"></div>
                                    <div className="post-text">
                                        <p> - "{post.post}" <i className="em em-anguished"></i> <i className="em em-anguished"></i> <i className="em em-anguished"></i></p>
                                    </div>
                                    <div className="reaction">
                                        <button onClick={() => addLike(post.postID)}> <img width="25" height="25" src={likeButton} alt="LIKE" /> </button> <button onClick={() => addComment(post.postID)}> <img width="25" height="25" src={commentButton} alt="LIKE" /> </button> <input id="commentForm" />
                                        <p>{ post.likes } likes</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                    ))
        }
        <div className="App">
            <button onClick={feedRefresh}>Refresh Feed</button>
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

export default GetPostsForFeed;