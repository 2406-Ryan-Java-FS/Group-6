import React, { useState, useContext, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { AuthContext } from "../components/auth-context";
import '../styles/login.css';

export default function LoginPage() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);
    const [loginSuccess, setLoginSuccess] = useState(false);
    const { login } = useContext(AuthContext);
    const navigate = useNavigate();
    const location = useLocation();
    const [logoutMessage, setLogoutMessage] = useState(null);
    
    useEffect(() => {
        if (location.state?.message) {
            setLogoutMessage(location.state.message);
            window.history.replaceState({}, document.title); // Clear the state
        }
    }, [location.state]);

    async function handleLogin() {
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

            // Clear any previous errors
            setError(null);
            setLogoutMessage(null);

            // Indicate login success
            setLoginSuccess(true);

            // Update the auth context
            login();

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
        
        handleLogin();
    };

    return (
        <>
            <h1 className="heading">Login</h1>
            <div className="formContainer">
                <div className="form">
                    {error && <div className="error">{error}</div>}
                    {loginSuccess && <div className="success">Login successful! Redirecting...</div>}
                    {logoutMessage && <div className="success">{logoutMessage}</div>}
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
                    <p className="registerLink">Don't have an account? <button onClick={() => navigate('/register')}>Register here</button>.</p>
                </div>
            </div>
        </>
    );
}
