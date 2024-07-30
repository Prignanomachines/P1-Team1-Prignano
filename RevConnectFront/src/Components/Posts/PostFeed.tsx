import React, { useState } from "react";
import axios from "axios";

interface Post {
    postID: number;
    userID: number;
    post: string;
}

function GetPostsForFeed(){

    const [posts, setPost] = useState(Array<Post>);

    
    async function feedRefresh(){
        let postList: Array<Post> = await getPosts();
        setPost(postList);
    }

    
    



return (
    <div>
        <h2>User Posts</h2>
        <ul>
            {posts.map(post => (
                <li key={post.postID}>
                    <p>{post.post}</p>
                </li>
            ))}
        </ul>
        <button onClick={feedRefresh}>Refresh Feed</button>
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