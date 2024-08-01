import React, {useState} from "react";
import axios from "axios";
import './PostForUserID.css';
import likeButton from './likeButton.png';
import commentButton from './commentButton.png';

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

function GetPostsForUserID({poster}: any){

    const [posts, setPost] = useState(Array<Post>);
    const [updatedContent, setUpdatedContent] = useState<{[key: number]: string}>({});

    
    async function feedRefresh(){
        let postList: Array<Post> = await getPosts(poster);
        setPost(postList);
    }

    async function addLike(postID: number){

    }

    async function addComment(postID: number) {

    }

    async function handleDelete(postID: number){
        await deletePost(postID);
        feedRefresh();
    }

    async function updatePost(postID: number) {
        const url = `http://localhost:8080/post/` + postID;
    
        try{
            const response = await axios.patch(url, {
                post: updatedContent[postID]
            },{ 
                headers: {
                    'Content-Type': 'application/json'
                },
                withCredentials: true,
                
            });
            if(response.status !== 200){
                throw new Error(`Response status: ${response.status}`);
            }
            alert("post updated");
            setUpdatedContent(prevState => ({
                ...prevState,
                [postID]: ""
            }));
            feedRefresh();
        }catch(error: any){
            console.error(error.message);
        } 
    }

    function handleInputChange(postID: number, value: string){
        setUpdatedContent(prevState => ({
            ...prevState,
            [postID]: value
        }));
    }

    function handleSubmit(event: any, postID: number){
        event.preventDefault();
        updatePost(postID);
    }


    return (
        <div>
            <div className="App">
                <h2>User's Posts</h2>
            </div>
            {posts.map(post => (
                <div className="container post-container" key={post.postID}>
                    <div className="row">
                        <div className="">
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
                                        <input className="comment-input" id="commentForm" />
                                        <p>{post.likes} likes</p>
                                    </div>
                                    <div>
                                        <button className="delete-button" onClick={() => handleDelete(post.postID)}>
                                            Deleted
                                        </button>
                                    </div>
                                    <div>
                                        <form className="update-form" onSubmit={(e) => handleSubmit(e, post.postID)}>
                                            <input type='text'
                                                placeholder="Update Post Content"
                                                value={updatedContent[post.postID] || ''}
                                                onChange={(e) => handleInputChange(post.postID, e.target.value)}
                                            />
                                            <button className="update button" type='submit'>Update</button>
                                        </form>
                                    </div>
                                </div>
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

async function getPosts(poster: string): Promise<Post[]> {

    const url = "http://localhost:8080/post/" + poster;
    try {
        const response = await axios.get(url, {
            method: 'get',
            headers: {
                'Content-Type': 'application/json'
            },
            withCredentials: true // Ensure cookies are sent with the request
        });

        if (response.status !== 200) {
            throw new Error(`Response status: ${response.status}`);
        }
        console.log(response.data);
        return response.data;
    } catch (error: any) {
        console.error(error.message);
    }
    return [];
};

async function deletePost(postID: number){
    const url = 'http://localhost:8080/post/' + postID;

    try{
        const response = await axios.delete(url, {
            headers: {
                'Content-Type': 'application/json'
            },
            withCredentials: true
        });
        if(response.status !== 200){
            throw new Error(`Response status: ${response.status}`);
        }
        alert("Deleted successfully!");
    }catch(error: any){
        console.error(error.message);
    }
}



export default GetPostsForUserID;
