import { useEffect, useReducer, useRef, useState } from "react";
import AdminPage from "./admin-page";
import Cookies from "js-cookie";

export default function UserSettings () {

    const usernameRef = useRef()
    const passwordRef = useRef()



    const AUTOSHOP_URL = 'http://localhost:8080/users'
    const [data, setData] = useState([])
    const [dataError, setDataError] = useState("");
    const [reducerValue, forceUpdate] = useReducer(x => x + 1, 0)

    useEffect(() => {
        const fetchUserInfo = async () => {
            const token = Cookies.get('accessToken');
        
            try {
                const response = await fetch('http://localhost:8080/users/current', {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json',
                    },
                });
        
                if (!response.ok) {
                    throw new Error('Failed to fetch user info');
                }
        
                const json = await response.json();
                console.log('User info:', json);
                setData([json])
                setDataError("");
            } catch (error) {
                setDataError("An error occurred while fetching data");
                console.error(error);
            }
        };
        fetchUserInfo();
    }, [reducerValue]);

    const changeCredentials = async () => {
        const token = Cookies.get('accessToken');
        console.log(usernameRef.current.value)
        console.log(passwordRef.current.value)
        try {
            // Create an empty object to hold the fields
            const requestBody = {};
    
            // Conditionally add fields based on whether the refs have values
            if (usernameRef.current.value) {
                requestBody.usernameNew = usernameRef.current.value;
            }
            if (passwordRef.current.value) {
                requestBody.passwordNew = passwordRef.current.value;
            }
    
            // Send the request only if there's at least one field to update
            if (Object.keys(requestBody).length > 0) {
                const response = await fetch(`${AUTOSHOP_URL}`, {
                    method: 'PUT',
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(requestBody),
                });
    
                const json = await response.json();
                const { accessToken } = json;
                console.log('Password or username changed' + json);

                Cookies.set('accessToken', accessToken, { expires: 7 });
                console.log('Token set in cookies:', accessToken);

                usernameRef.current.value = '';
                passwordRef.current.value = '';

                forceUpdate();
            } else {
                console.log('No new credentials provided');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    const handleLogin = async (username, password) => {
        try {
            const response = await fetch('http://localhost:8080/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ username, password }),
            });
    
            if (!response.ok) {
                throw new Error('Login failed');
            }
    
            const data = await response.json();
            const { accessToken } = data;
    
            // Set the token in a cookie
            Cookies.set('accessToken', accessToken, { expires: 7 }); // Set the cookie to expire in 7 days (you can adjust this as needed)
            console.log('Token set in cookies:', accessToken);
    
            // Proceed with other login actions, such as redirecting the user
        } catch (error) {
            console.error('Error during login:', error);
        }
    };


    return (
        <>
        {data.length > 0 ?

            (<div className="userContainerMain">
                <h1 style={{ marginBottom: "60px" }}>User Settings</h1>
                <div className="userContainerContent">
                    <div style={{ fontWeight: "600", fontSize: "24px", marginLeft: "20px", paddingBottom: "10px" }}>Account details</div>
                    <table>
                        <tr>
                            <td>Username</td>
                            <td>
                                <input 
                                    placeholder={data && data[0].username} 
                                    ref={usernameRef} 
                                />
                            </td>
                        </tr>
                        <tr>
                            <td>Email</td>
                            <td style={{ fontWeight: "normal" }}>{data && data[0].email}</td>
                        </tr>
                        <tr>
                            <td>Account type</td>
                            <td style={{ fontWeight: "normal" }}>{data && data[0].role}</td>
                        </tr>
                        <tr>
                            <td id="changePassword" style={{ paddingBottom: "10px" }}>Change Password</td>
                        </tr>
                        {/* <tr>
                            <td>Current password</td>
                            <td><input type="password" /></td>
                        </tr> */}
                        <tr>
                            <td>New password</td>
                            <td>
                                <input 
                                    type="password" 
                                    ref={passwordRef} 
                                />
                            </td>
                        </tr>
                        {/* <tr>
                            <td>Confirm new password</td>
                        </tr> */}

                    </table>
                    <button 
                        type="button" 
                        className="btn btn-primary" 
                        style={{ marginTop: "20px" }}
                        onClick={changeCredentials}
                    >
                        Save Changes
                    </button>
                </div>


                { data[0].role == 'Admin' &&
                (<div style={{ marginTop: '100px' }}>
                    <AdminPage />
                </div>)}

            </div>)
            : (<>
            <div>loading...</div>
            <button onClick={() => handleLogin("ilovehondas1", "newpassword")}>TEST LOGIN</button>
            <button onClick={() => handleLogin("admin", "hashed_password_3")}>TEST LOGIN (ADMIN)</button>
            </>)

        }
        </>
    )

}