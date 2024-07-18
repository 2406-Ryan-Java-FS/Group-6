import React, { useState } from 'react';
import '../styles/login.css';

export default function LoginPage() {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = () => {

        // TODO: send request to backend to login

        console.log('Username:', username);
        console.log('Password:', password);
    };

    return (
        <>
            <h1 className="headLogin">Login</h1>
            <label>Username:</label>
            <input type="text" id="username" value={username}
                onChange={(e) => setUsername(e.target.value)}
            />
            <label>Password:</label>
            <input type="password" id="password" value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <button onClick={handleSubmit} className="loginButton">Login</button>
        </>
    );
}
