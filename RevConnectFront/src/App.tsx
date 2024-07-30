import React from 'react';
import axios from "axios";
import { useState } from 'react'
import logo from './logo.png';
import { user } from './Components/User';
import './App.css';

function App() {

    let [userName, setUser] = useState(user.userName);
    let [firstName, setFirst] = useState(user.firstName);
    let [lastName, setLast] = useState(user.lastName);
    let [bio, setBio] = useState(user.bio);

    async function auth() {
        await authRequest();
        updateUser();
    }

    if (user.userID === -1) {
        auth();
    }

    function updateUser() {
        setUser(user.userName);
        setFirst(user.firstName);
        setLast(user.lastName);
        setBio(user.bio);
    }

  return (
    <div className="App">
          <h1> Welcome to RevConnect {userName}! </h1>

    </div>
  );
}


async function authRequest() {

    const url = "http://localhost:8080/cookie-auth";

    try {
        const response = await axios(url, {
            method: 'post',
            headers: {
                'Content-Type': 'application/json'
            },
            withCredentials: true
        });

        const json = response.data;

        user.userName = json.username;
        user.firstName = json.firstname;
        user.lastName = json.lastname;
        user.bio = json.bio;

    } catch (error: any) {
        console.error(error.message);
    }

}

export default App;
