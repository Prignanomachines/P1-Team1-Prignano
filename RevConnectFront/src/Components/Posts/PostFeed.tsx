import React, { useState } from "react";
import axios from "axios";

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

return (
    <div>
        <h2>User Posts</h2>
        <ul>
            {posts.map(post => (
                <li key={post.postID}>
                    <p>{post.author}</p>
                    <p>{post.post}</p>
                    <p>{post.likes}</p>

                    <ul>
                        {post.comments.map(comment => (
                            <li key={comment.commentID}>
                                <p>{comment.author}</p>
                                <p>{comment.comment}</p>
                            </li>
                        ))}
                    </ul>

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