import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/login.css';

export default function LoginPage() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);
    const [loginSuccess, setLoginSuccess] = useState(false);
    const navigate = useNavigate();

    async function login() {
        console.log("Logging in...");

        const url = "http://localhost:8080/auth/login";
        const credentials = { username, password };

        try {
            const httpResponse = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(credentials),
            });


            if (!httpResponse.ok) {
                throw new Error('Login failed');
            }

            const data = await httpResponse.json();
            const accessToken = data.accessToken;

            // Store the access token in localStorage
            localStorage.setItem('accessToken', accessToken);

            console.log('Access Token:', accessToken);

            // Clear any previous errors
            setError(null);

            // Indicate login success
            setLoginSuccess(true);

             // Redirect to the home page after a short delay
             setTimeout(() => {
                navigate('/');
            }, 2000);

        } catch (error) {
            setError(error.message);
            console.error('Error:', error);
        }
    }

    const handleSubmit = (e) => {
        e.preventDefault();

        login();
    };

    return (
        <>
            <h1 className="headLogin">Login</h1>
            <div className="loginContainer">
                <div className="loginForm">
                    {error && <div className="error">{error}</div>}
                    {loginSuccess && <div className="success">Login successful! Redirecting...</div>}
                    <form onSubmit={handleSubmit}>
                        <div className="formGroup">
                            <label>Username:</label>
                            <input type="text" id="username" value={username}
                                onChange={(e) => setUsername(e.target.value)}
                            />
                        </div>
                        <div className="formGroup">
                            <label>Password:</label>
                            <input type="password" id="password" value={password}
                                onChange={(e) => setPassword(e.target.value)}
                            />
                        </div>
                        <button type="submit" className="loginButton">Login</button>
                    </form>
                </div>
            </div>
        </>
    );
}
