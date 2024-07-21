import { useEffect, useReducer, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import AdminPage from "./admin-page";
import Cookies from "js-cookie";

export default function UserSettings () {

    const usernameRef = useRef()
    const passwordRef = useRef()



    const AUTOSHOP_URL = 'http://localhost:8080/users'
    const [data, setData] = useState([])
    const [dataError, setDataError] = useState("");
    const [reducerValue, forceUpdate] = useReducer(x => x + 1, 0)
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')
    const [showAlert, setShowAlert] = useState(false);

    const [confirmDelete, setConfirmDelete] = useState(false);

    const navigate = useNavigate();

    useEffect(() => {
        const fetchUserInfo = async () => {
            // const token = Cookies.get('accessToken');

            // localStorage.setItem('accessToken', accessToken);
            const token = localStorage.getItem('accessToken')
        
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
        // const token = Cookies.get('accessToken');
        const token = localStorage.getItem('accessToken');
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

                // Cookies.set('accessToken', accessToken, { expires: 7 });
                localStorage.setItem('accessToken', accessToken);
                console.log('Token set in cookies:');

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

    const deleteAccount = async () => {
        // const token = Cookies.get('accessToken')
        const token = localStorage.getItem('accessToken');
        
        try {
            const response = await fetch(`${AUTOSHOP_URL}`,
                {
                    method: 'DELETE',
                    headers: {
                        'Authorization': `Bearer ${token}`,
                    }
                },
            )
            if (response.ok) {
                console.log("User deleted")
                // to redirect to home
                const userConfirmed = window.confirm("Your account has been deleted. You will be redirected to the home page.");
                if (userConfirmed) {
                  navigate('/'); // Redirect to the root URL if the user confirms
                }
            } else {
                console.error("Error")
            }
        } catch (error) {
            console.error(error);
        }
    }

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
            // Cookies.set('accessToken', accessToken, { expires: 7 }); // Set the cookie to expire in 7 days (you can adjust this as needed)
            localStorage.setItem('accessToken', accessToken);
            
            console.log('Token set in cookies:', accessToken);
    
            // Proceed with other login actions, such as redirecting the user
        } catch (error) {
            console.error('Error during login:', error);
        }
    };

    const handleInputChange = () => {
        setUsername(usernameRef.current.value)
        setPassword(passwordRef.current.value)
    }


    return (
        <>
        {data.length > 0 ?

            (
            
                <div className="userContainerMainHolder">
            <div className="userContainerMain">
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
                                    onChange={handleInputChange}
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
                                    onChange={handleInputChange}
                                />
                            </td>
                        </tr>
                        {/* <tr>
                            <td>Confirm new password</td>
                        </tr> */}

                                </table>
                                <div className="buttonContainer">
                                    {(username.length || password) && (
                                    <button
                                        type="button"
                                        className="btn btn-primary saveButton"
                                        onClick={changeCredentials}
                                    >
                                        Save Changes
                                    </button>
                                    )}
                                    
                                    { confirmDelete ?
                                    (<div>
                                        <div onClick={deleteAccount}>
                                        delete?
                                        </div>
                                        </div> ):

                                    (
                                        <button
                                        type="button"
                                        class="btn btn-danger deleteButton"
                                        onClick={() => setConfirmDelete(true)}
                                    >
                                        Delete Account
                                    </button>
                                    )
                                    }

                                </div>
                </div>


                { data[0].role == 'Admin' &&
                (<div style={{ marginTop: '100px' }}>
                    <AdminPage  />
                </div>)}
                </div>

            </div>)
            : (<>
            <div>loading...</div>
            <button onClick={() => handleLogin("ilovehondas1", "hondas")}>TEST LOGIN</button>
            <button onClick={() => handleLogin("Bobisback", "pass")}>LOGIN TO DELETE</button>
            <button onClick={() => handleLogin("admin", "hashed_password_3")}>TEST LOGIN (ADMIN)</button>
            </>)

        }
        </>
    )

}