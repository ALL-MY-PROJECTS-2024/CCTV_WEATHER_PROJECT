import React, { useState,useEffect } from "react";
import axios from "axios";
//COOKIE
import Cookies from 'js-cookie';
const Main = ()=>{
    const [response , setResponse] = useState({});
    const [token, setToken] = useState(localStorage.getItem('token')); // 초기 token 상태 설정
    useEffect(()=>{
       
        const reqFunc = async ()=>{

            const tokenFromStorage = localStorage.getItem('token');
            try{
            const response = await axios
                            .get( 
                                `http://localhost:8080/main`, 
                                { 
                                    headers: {'Authorization': `Bearer ${tokenFromStorage}`,'Content-Type': 'application/json',},
                                    withCredentials: true, // 쿠키를 받기 위한 설정
                                },
                                 
                            );
            console.log("response",response);
         
            if (response.data.token) {
                localStorage.setItem("token", response.data.token); // 로컬 저장소에 저장
                setToken(response.data.token); // 상태 업데이트
            }
            setResponse(response.data); // 응답 데이터 저장

            }catch(error){
                console.log(error)
            }
        }

        reqFunc();


    },[token])


    return (
        <div>
            <h1>MAIN</h1>
            <div>
                <div>계정명 : {response.username}</div>
                <div>인증여부 : {response.isAuthenticated?"TRUE":"FASLE"}</div>
                <div>ROLE : { response.roles && response.roles.length > 0 ? response.roles[0].authority : "No Role" }</div>
                <div>SHOW JWT TOKEN : {localStorage.getItem('token') == null ? 'None' : localStorage.getItem('token')}</div>
                <div>MESSAGE : {response.message}</div>

            </div>
        </div>
    )
}

export default Main;