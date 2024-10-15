import React, { useState,useEffect } from "react";
import axios from "axios";

const Join = ()=>{
    const [username,setUsername] = useState();
    const [password,setPassword] = useState();

    const joinHandler = ()=>{
        axios
        .post(
          `http://localhost:8080/join`,                          //URL
          {"username":username,"password":password},    //PARAM
          {headers: {'Content-Type': 'application/json'}}             //CONTENT_TYPE
        )
        .then(response => {
          console.log(response.data);
        })
        .catch(error => {
          console.error('error.response.data', error.response.data);
          
        });

    }
    return (
        <div>

            <h1>AUTH JOIN</h1>
            <div>
                <input type="text"  onChange={e=>setUsername(e.target.value)} />
            </div>
            <div>
                <input type="text"  onChange={e=>setPassword(e.target.value)} />
            </div>
            <div>
               <button onClick={joinHandler}>회원가입</button>
            </div>

        </div>

    )
}

export default Join;