import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const navigate = useNavigate();

  const [username, setUsername] = useState();
  const [password, setPassword] = useState();

  const loginHandler = async () => {
    try {

      const response = await axios
        .post(
          `http://localhost:8080/login`, //URL
          { username: username, password: password} ,//PARAM
          
        )
        console.log(response.data);
        if(response.data.token)
          localStorage.setItem("token", response.data.token);   //로컬저장소에 저장

    } catch (error) {
      console.error("error.response.data", error);
    }
  };

  return (
    <div>
      <div>
        <h1>LOGIN</h1>
      </div>
      <div>
        <input
          type="text"
          onChange={(e) => {
            setUsername(e.target.value);
          }}
        />
      </div>
      <div>
        <input
          type="password"
          onChange={(e) => {
            setPassword(e.target.value);
          }}
        />
      </div>
      <div>
        <button onClick={loginHandler}>로그인 요청</button>
      </div>
    </div>
  );
};

export default Login;
