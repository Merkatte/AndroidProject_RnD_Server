import './Login.css'
import { useRef, useState, useEffect, useContext } from "react";
import AuthContext from '../context/AuthProvider';

import axios from '../api/axios';

const LOGIN_URL = '/users/signin';

const Login = () => {

    const { setAuth } = useContext(AuthContext);
    const userRef = useRef();
    const errRef = useRef();

    const [user, setUser] = useState("");
    const [pwd, setPwd] = useState("");
    const [errMsg, setErrMsg] = useState("");
    const [success, setSuccess] = useState(false);

    useEffect(() => {
        userRef.current.focus();
    }, [])

    useEffect(() => {
        setErrMsg("");
    }, [user, pwd])

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            console.log(user, pwd);
            const response = await axios.post(LOGIN_URL,
                JSON.stringify({ email: user, password: pwd }),
                {
                    headers: { 'Content-Type': 'application/json' },
                    withCredentials: false
                });

            const accessToken = response?.data?.accessToken;
            const userId = response?.data?.userId;
            const userRoles = response?.data?.appUserRoles;

            console.log(accessToken, userId, userRoles, response.data);
            setAuth = ({ user, pwd, userRoles, accessToken })
            setUser("");
            setPwd("");
            setSuccess(true);
        }
        catch (err) {
            if (!err.response) setErrMsg("No Server Response")
            else if (err.response?.status === 400) {
                setErrMsg("Mssing Username or Password");
            } else if (err.response?.status === 401) {
                setErrMsg("Unauthorized");
            } else {
                setErrMsg("Login Failed");
            }
            errRef.current.focus();
        }
    }

    return (
        <>
            {success ? (
                <section>
                    <h1>You are logged in !!</h1>
                    <br />
                    <p>
                        <a href="#">Home</a>
                    </p>
                </section>
            ) : (
                <div>
                    <div className="frame-3-6jm">
                        <form onSubmit={handleSubmit}>
                            <input className="frame-5-nMh"
                                type="text"
                                id="username"
                                ref={userRef}
                                autoComplete="off"
                                onChange={(e) => setUser(e.target.value)}
                                value={user}
                                required></input>
                            <input className="frame-6-2vF"
                                type="text"
                                id="password"
                                onChange={(e) => setPwd(e.target.value)}
                                value={pwd}
                                required></input>

                            <div className='errMsg' ref={errRef}>
                                {errMsg}
                            </div>
                            <button className="frame-7-tBm">로그인</button>
                        </form>
                        <div className="auto-group-idia-Hjh">
                            <p className="item--aio">회원가입</p>
                            <div className="line-3-dBH">
                            </div>
                            <p className="item--jk7">아이디찾기</p>
                            <div className="line-4-Ctb">
                            </div>
                            <p className="item--8GT">비밀번호찾기</p>
                        </div>
                        <div className="frame-8-HoZ">간편 로그인</div>
                        <p className="item--u4F">간편 로그인 계정 찾기</p>
                    </div>
                </div>
            )};
        </>
    );

}

export default Login;
