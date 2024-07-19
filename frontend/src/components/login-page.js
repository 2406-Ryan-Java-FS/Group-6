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
                let errorMessage = 'Login failed';
                
                // Check status code for specific error messages
                if (httpResponse.status === 401) {
                    errorMessage = 'Invalid username or password';
                } else if (httpResponse.status === 403) {
                    errorMessage = 'Access forbidden';
                } else if (httpResponse.status === 404) {
                    errorMessage = 'Resource not found';
                }

                throw new Error(errorMessage);
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

    const handleRegisterClick = () => {
        navigate('/register');
    };

    return (
        <>
            <h1 className="heading">Login</h1>
            <div className="formContainer">
                <div className="form">
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
                        <button type="submit" className="formButton">Login</button>
                    </form>
                    <p className="registerLink">Don't have an account? <button onClick={handleRegisterClick}>Register here</button>.</p>
                </div>
            </div>
        </>
    );
}
