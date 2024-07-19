import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/login.css'; // Reuses the login.css styles

export default function RegisterPage() {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [error, setError] = useState(null);
    const [registrationSuccess, setRegistrationSuccess] = useState(false);
    const navigate = useNavigate();

    async function register() {
        console.log("Registering...");

        const url = "http://localhost:8080/users";
        const userData = {
            username,
            email,
            password,
            role: 'Customer' // Default role
        };

        try {
            const httpResponse = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(userData),
            });

            const data = await httpResponse.json();

            if (!httpResponse.ok) {
                let errorMessage = 'Registration failed';

                // Check status code for specific error messages
                if (httpResponse.status === 400) {
                    errorMessage = 'Invalid data provided';
                } else if (httpResponse.status === 409) {
                    errorMessage = 'Username or email already exists';
                }

                throw new Error(errorMessage);
            }

            // Extract username from response data
            const registeredUsername = data.username;

            // Store the username in localStorage
            localStorage.setItem('registeredUsername', registeredUsername);

            // Clear any previous errors
            setError(null);

            // Indicate registration success
            setRegistrationSuccess(true);

            // Redirect to the login page after a short delay
            setTimeout(() => {
                navigate('/login');
            }, 2000); // Adjust the delay time as needed

        } catch (error) {
            setError(error.message);
            console.error('Error:', error);
        }
    }

    const handleSubmit = (e) => {
        e.preventDefault();

        if (password !== confirmPassword) {
            setError('Passwords do not match');
            return;
        }

        // Send request to backend to register
        register();
    };

    return (
        <>
            <h1 className="heading">Register</h1>
            <div className="formContainer">
                <div className="form">
                    {error && !registrationSuccess && <div className="error">{error}</div>}
                    {registrationSuccess && !error && <div className="success">Registration successful! Redirecting to login...</div>}
                    <form onSubmit={handleSubmit}>
                        <div className="formGroup">
                            <label>Username:</label>
                            <input type="text" id="username" value={username}
                                onChange={(e) => setUsername(e.target.value)}
                            />
                        </div>
                        <div className="formGroup">
                            <label>Email:</label>
                            <input type="email" id="email" value={email}
                                onChange={(e) => setEmail(e.target.value)}
                            />
                        </div>
                        <div className="formGroup">
                            <label>Password:</label>
                            <input type="password" id="password" value={password}
                                onChange={(e) => setPassword(e.target.value)}
                            />
                        </div>
                        <div className="formGroup">
                            <label>Confirm Password:</label>
                            <input type="password" id="confirmPassword" value={confirmPassword}
                                onChange={(e) => setConfirmPassword(e.target.value)}
                            />
                        </div>
                        <button type="submit" className="formButton">Register</button>
                    </form>
                    <p className="registerLink">Already have an account? <button type="button" onClick={() => navigate('/login')}>Login here</button>.</p>
                </div>
            </div>
        </>
    );
}
