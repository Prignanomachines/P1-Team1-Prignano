import React from 'react';
import axios from "axios";
import { useState } from 'react'
import logo from '../../logo.png';
import { user } from '../User';
import { Link, useNavigate } from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.css';


function Register() {

    let [userName, setUser] = useState(user.userName);
    let [firstName, setFirst] = useState(user.firstName);
    let [lastName, setLast] = useState(user.lastName);
    let [bio, setBio] = useState(user.bio);

    async function reg() {
        await regRequest();
        updateUser();
    }

    const navigate = useNavigate();
    function updateUser() {
        setUser(user.userName);
        setFirst(user.firstName);
        setLast(user.lastName);
        setBio(user.bio);

        if (user.userName !== "") {
            navigate("/");
        }

    }

    return (
        <div className="App">

            {userName}

            <div>
                <header className="App-header">
                    <img className="App-logo" src={logo} width="64" height="64" alt="logo" />
                </header>
            </div>
            <br />
            <br />
            <div className = "form-group">
                <form>
                    <div data-mdb-input-init className="form-outline mb-4">
                        <label className="form-label" htmlFor="form2Example1">Username</label>
                        <input id="usernameForm" className="form-control" />
                    </div>

                    <div data-mdb-input-init className="form-outline mb-4">
                        <label className="form-label" htmlFor="form2Example1">First name</label>
                        <input id="firstnameForm" className="form-control" />
                    </div>

                    <div data-mdb-input-init className="form-outline mb-4">
                        <label className="form-label" htmlFor="form2Example1">Last name</label>
                        <input id="lastnameForm" className="form-control" />
                    </div>

                    <div data-mdb-input-init className="form-outline mb-4">
                        <label className="form-label" htmlFor="form2Example2">Password</label>
                        <input type="password" id="passwordForm" className="form-control" />
                    </div>

                    <div className="row mb-4">

                        <div className="col">
                            <Link to="/Login">Login instead</Link>
                        </div>
                    </div>

                    <button onClick={reg} type="button" data-mdb-button-init data-mdb-ripple-init className="btn btn-primary btn-block mb-4">Register</button>
                </form>
            </div>
        </div>
    );
}

async function regRequest() {

    const url = "http://localhost:8080/register";

    let firstNameForm: HTMLInputElement = document.getElementById('firstnameForm') as HTMLInputElement;
    let lastNameForm: HTMLInputElement = document.getElementById('lastnameForm') as HTMLInputElement;
    let usernameForm: HTMLInputElement = document.getElementById('usernameForm') as HTMLInputElement;
    let passwordForm: HTMLInputElement = document.getElementById('passwordForm') as HTMLInputElement;

    let postData = {
        firstName: firstNameForm.value,
        lastName: lastNameForm.value,
        username: usernameForm.value,
        password: passwordForm.value
    }

    try {
        const response = await axios(url, {
            method: 'post',
            headers: {
                'Content-Type': 'application/json'
            },
            withCredentials: true,
            data: postData
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


export default Register;
