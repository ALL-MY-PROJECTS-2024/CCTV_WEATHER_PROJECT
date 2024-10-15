import logo from "./logo.svg";
import "./App.css";

import Param from "./components/Param";
import MemoPost from "./components/memo/Post";
import MemoList from "./components/memo/List";
import Exception from "./components/Exception";
// FILE UP DOWNLOAD
import FileUpload from "./components/updownload/Upload";
// import FileDownload from "./components/updownload/Download";
import FileList from "./components/updownload/List";
import { BrowserRouter as Router, Route, Routes, Link } from "react-router-dom";

// API
import OpenData from "./components/api/OpenData";
import KakaoLogin from "./components/api/KakaoLogin"

// AUTH(SPRING SECURITY)
import AuthLogin from "./components/auth/Login";
import AuthLogout from "./components/auth/Logout";
import AuthMain from "./components/auth/Main";
import AuthJoin from "./components/auth/Join";



function App() {
  return (
    <Router>
      <div>
        <Link to="/param">PARAM TEST</Link> <br/>
        <hr></hr>
        <Link to="/memo/post">MEMO POST</Link> <br/>
        <Link to="/memo/list">MEMO LIST(EXCEPTION TEST)</Link> <br/><br/>
        <hr></hr>
        <Link to="/file/upload">UPLOAD</Link> <br/>
        <Link to="/file/list">LIST</Link> <br/>
        <hr></hr>
        <Link to="/api/opendata">API OPENDATA</Link> <br/>
        <Link to="/api/kakaoLogin">KAKAO LOGIN</Link> <br/>
        <hr></hr>
        <Link to="/auth/join">AUTH JOIN</Link> &nbsp;&nbsp; | &nbsp;&nbsp;
        <Link to="/auth/login">AUTH LOGIN</Link> &nbsp;&nbsp; | &nbsp;&nbsp;
        <Link to="/auth/main">AUTH MAIN</Link> &nbsp;&nbsp; | &nbsp;&nbsp;
        <Link to="/auth/logout">AUTH LOGOUT</Link> <br/>


      </div>
      <Routes>
        {/* Route 설정 */}
        <Route path="/param" element={<Param />} />
        
        {/*  */}
        <Route path="/memo/post" element={<MemoPost />} />
        <Route path="/memo/list" element={<MemoList />} />
        <Route path="/exception" element={<Exception />} />
        
        {/*  */}
        <Route path="/file/upload" element={<FileUpload />} />
        {/* <Route path="/file/download" element={<FileDownload />} /> */}
        <Route path="/file/list" element={<FileList />} />
        
        {/* API */}
        <Route path="/api/opendata" element={<OpenData />} />
        <Route path="/api/kakaoLogin" element={<KakaoLogin />} />
        
        {/* AUTH(SPRING SECURITY) */}
        <Route path="/auth/login" element={<AuthLogin />} />
        <Route path="/auth/logout" element={<AuthLogout />} />
        <Route path="/auth/main" element={<AuthMain />} />
        <Route path="/auth/join" element={<AuthJoin />} />

        
      </Routes>
      
    </Router>
   
  );
}

export default App;
