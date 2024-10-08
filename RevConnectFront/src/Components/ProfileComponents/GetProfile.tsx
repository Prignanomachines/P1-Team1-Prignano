import axios from "axios";
import React, { useState, useEffect } from "react";
import { json } from "stream/consumers";

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

function GetProfile(){
  
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
    
    async function getProfile(){
        let userIDForm: HTMLInputElement = document.getElementById('userIDForm') as HTMLInputElement;
       
            let url = 'http://localhost:8080/profile/'+ userIDForm.value
            let headers = {
                "Content-Type":"application/json"
            }
            let request = {method:"GET",
            headers:headers
        }
        try{
            let response = await axios.get(url,request);
            let responseBody = await response.data;
            
           setProfile(responseBody)
           console.log(userIDForm.value)
           console.log(responseBody);
        }
        catch(error:any){
            console.log(error.message)
        }
           
        
     console.log(profile)
   
    }

userp = profile
    
   return(<div>
    
  <input id="userIDForm" className="form-control"/>
  <button onClick={getProfile}>Submit</button>  
    <>
     <p> Username: {profile.username}</p>
     <p>First name: {profile.firstname}</p>
     <p>Last Name: {profile.lastname}</p>
     Bio: {profile.bio}
     </>
     </div>
  
   )
}

export default GetProfile