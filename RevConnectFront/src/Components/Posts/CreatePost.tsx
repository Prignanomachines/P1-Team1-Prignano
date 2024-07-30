import React from "react";
import axios from "axios";

function CreatePost() {
    
    async function handleSubmit() {
        await submitPost();

    }

    return (
        <div>
            <h1>Create a New Post</h1>
                <input id="postForm"
                    type="text"
                    required
                />
                <button onClick={handleSubmit}>Submit</button>
        </div>
    );
};


async function submitPost() {

    const url = "http://localhost:8080/post";

    let postForm: HTMLInputElement = document.getElementById("postForm") as HTMLInputElement;

    let postData = postForm.value;
    
    try {
        const response = await axios(url, {
            method: 'post',
            headers: {
                'Content-Type': 'application/json'
            },
            withCredentials: true,
            data: postData
        });

        if (!(response.status === 200)) {
            throw new Error(`Response status: ${response.status}`);
        }
        alert("successful");
    } catch (error: any) {
        console.error(error.message);
    }
};


export default CreatePost;


