import axios from "axios";
import React, { useState, useEffect } from "react";
import { json } from "stream/consumers";
import UpdateBio from "./UpdateBio";

class user{
    username:string
    firstname:string
    lastname:string
    bio:string
   

    constructor() {
        this.username = "";
        this.firstname = "";
        this.lastname = "";
        this.bio = "";
    }

  }
let userp = new user()

function ViewProfile(){
  
    let [profile, setProfile] = useState({username: "",firstname:"",lastname:"",bio:""});

    /*
 useEffect(() => {
        async function get(userD:any = 1){
            let url = 'http://localhost:8080/profile/'+userD
            let headers = {
                "Content-Type":"application/json"
            }
            let request = {method:"GET",
            headers:headers
        }
            let response = await axios.get(url,request);
            let responseBody = await response.data;
            
           setProfile(responseBody)
           
           console.log(responseBody);
        }
        get();
       
    }, []);

   */
    
    async function vProfile(){
       
            let url = 'http://localhost:8080/profile/user'
            let headers = {
                "Content-Type":"application/json"
            }
            let request = {
            method:"GET",
            headers:headers,
            withCredentials: true
        };
        try{
            let response = await axios.get(url,request);
            let responseBody = await response.data;
            
            setProfile(responseBody)
       
            console.log(responseBody);
        
            console.log(profile)
        } catch(error:any){
            console.log(error.message)
        }
    }

    userp = profile
    
   return (

        <div className="profile-container">
            <div className="profile-section">
                <button onClick={vProfile}>ViewProfile</button>  
                <p> Username: {profile.username}</p>
                <p>First name: {profile.firstname}</p>
                <p>Last Name: {profile.lastname}</p>
                <p>Bio: {profile.bio}</p>
            </div>
     </div>
        
   )
}

export default ViewProfile;