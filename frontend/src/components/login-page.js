import React, { useState } from 'react';
import '../styles/login.css';

export default function LoginPage() {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);

    async function login() {
        console.log("Logging in...")

        const url = "http://localhost:8080/auth/login";
        const credentials = { username, password };

        try {
            console.log(credentials);

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
            // TODO: Redirect user to the appropriate page after successful login

        } catch (error) {
            setError(error.message);
            console.error('Error:', error);
        }
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log('Username:', username);
        console.log('Password:', password);

        login();

        // can now access the accessToken via localStorage:
        console.log(localStorage.accessToken);
    };

    return (
        <>
            <h1 className="headLogin">Login</h1>
            <div className="loginContainer">
                <div className="loginForm">
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
                    <button onClick={handleSubmit} className="loginButton">Login</button>
                </div>
            </div>
        </>
    );
}
