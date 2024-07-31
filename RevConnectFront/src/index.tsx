import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import Login from './Components/LoginRegister/Login';
import Register from './Components/LoginRegister/Register';
import reportWebVitals from './reportWebVitals';
import Nav from './Components/Nav';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import CreatePost from './Components/Posts/CreatePost';
import GetPostsForFeed from './Components/Posts/PostFeed';
import GetProfile from './Components/Getprofile/GetProfile';
import UpdateBio from './Components/UpdateBio/UpdateBio';
import ViewProfile from './Components/ViewProfile/ViewProfile';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
    <BrowserRouter>
        <Nav />
        <Routes>
            <Route path="/" element={<App />}></Route>
            <Route path='/login' element={<Login />}></Route>
            <Route path='/register' element={<Register />}></Route>
            <Route path='/post' element={<CreatePost />}></Route>
            <Route path='/feed' element={<GetPostsForFeed/>}></Route>
            <Route path='/profile' element = {<GetProfile/>}></Route>
            <Route path='/updateprofile' element = {<UpdateBio/>}></Route>
            <Route path='/viewprofile' element = {<ViewProfile/>}></Route>
        </Routes>
    </BrowserRouter>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
