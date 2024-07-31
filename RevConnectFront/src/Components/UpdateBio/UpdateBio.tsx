import axios, { Axios } from "axios";
import React, { useEffect, useState } from "react";
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

function UpdateBio(){
    let [newBio, setBio] = useState({bio:""});

function changeBio(){
    /*
 console.log(bioForm.value)
 newBio.bio = bioForm.value
 console.log(newBio.bio)
*/

update()
 }

return(
   <>

    
     <p><input id="userIDForm" className="form-control" />
    <button onClick={changeBio}>Submit</button></p>
    Bio: <input id="bioForm" className="form-control" />
   </>
)

}
async function update(){
    let bioForm: HTMLInputElement = document.getElementById('bioForm') as HTMLInputElement;
    let userIDForm: HTMLInputElement = document.getElementById('userIDForm') as HTMLInputElement;

    let url = 'http://localhost:8080/profile/'+ userIDForm.value

    let headers = {
        "Content-Type":"application/json"
    }
    let request = {method:"PATCH",
    //body: JSON.stringify(),
    headers: headers,
   data: bioForm.value
    }
    try{
        let  response = await axios(url,request);

        let responseBody = await response.data;
    
    
         console.log(responseBody);
    }
    catch(error:any){
        console.log(error.message)
    }
    
}
export default UpdateBio

