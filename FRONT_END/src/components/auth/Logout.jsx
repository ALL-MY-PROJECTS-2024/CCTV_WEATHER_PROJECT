import React, { useState, useEffect } from "react";
import axios from "axios";

const Logout =  ()=>{

    const reqFunc = async()=>{
        console.log('logout...')
        const token = localStorage.getItem('token');
        try{
            const response = await axios.get(
                "http://localhost:8080/logout",
                { headers: {'Authorization': `Bearer ${token}`,'Content-Type': 'application/json',},}
            )
            console.log(response)
            const status = response.data.status;
            if(status==200)
                localStorage.removeItem('token');

        }catch(error){
            console.log(error)
        }
    }
    reqFunc();
    
}

export default Logout;