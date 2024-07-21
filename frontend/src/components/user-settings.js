import { useEffect, useReducer, useRef, useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "./auth-context";
import AdminPage from "./admin-page";

export default function UserSettings() {

    const usernameRef = useRef()
    const passwordRef = useRef()



    const AUTOSHOP_USER_URL = 'http://localhost:8080/users'
    const [data, setData] = useState([])
    // const [dataError, setDataError] = useState("");
    const [reducerValue, forceUpdate] = useReducer(x => x + 1, 0)
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')
    const [updatedCredentials, setUpdatedCredentials] = useState(false);

    const [confirmDelete, setConfirmDelete] = useState(false);

    const navigate = useNavigate();

    const { logout } = useContext(AuthContext);

    useEffect(() => {
        const fetchUserInfo = async () => {
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
                console.log('Retrieved user info');
                setData([json])
                // setDataError("");
            } catch (error) {
                // setDataError("An error occurred while fetching data");
                console.error(error);
            }
        };
        fetchUserInfo();
    }, [reducerValue]);

    const changeCredentials = async () => {
        const token = localStorage.getItem('accessToken');
        console.log(usernameRef.current.value)
        console.log(passwordRef.current.value)
        try {
            const requestBody = {};

            // Conditionally add fields if refs have value
            if (usernameRef.current.value) {
                requestBody.usernameNew = usernameRef.current.value;
            }
            if (passwordRef.current.value) {
                requestBody.passwordNew = passwordRef.current.value;
            }

            // Send the request only if there's at least one field to update
            if (Object.keys(requestBody).length > 0) {
                const response = await fetch(`${AUTOSHOP_USER_URL}`, {
                    method: 'PUT',
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(requestBody),
                });

                const json = await response.json();
                const { accessToken } = json;
                console.log('Password or username changed');

                localStorage.setItem('accessToken', accessToken);

                // refreshes user's info fields
                forceUpdate();

                usernameRef.current.value = '';
                passwordRef.current.value = '';

                setUsername("")
                setPassword("")

                setUpdatedCredentials(true);
                setTimeout(() => {
                    setUpdatedCredentials(false);
                }, 800);

            } else {
                console.log('No new credentials provided');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    const deleteAccount = async () => {
        const token = localStorage.getItem('accessToken');

        try {
            const response = await fetch(`${AUTOSHOP_USER_URL}`,
                {
                    method: 'DELETE',
                    headers: {
                        'Authorization': `Bearer ${token}`,
                    }
                },
            )
            if (response.ok) {
                console.log("User deleted")
                const userConfirmed = window.confirm("Your account has been deleted. You will be redirected to the home page.");
                if (userConfirmed) {
                    logout();
                    navigate('/'); // Redirect to HomePage when user confirms
                }
            } else {
                console.error("Error")
            }
        } catch (error) {
            console.error(error);
        }
    }

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
                                
                                <table className="userSettingContainer">
                                    <tbody>
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
                                            <td style={{ fontWeight: "normal" }}>
                                                {data && data[0].email}
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Account type</td>
                                            <td style={{ fontWeight: "normal" }}>
                                                {data && data[0].role}
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="changePassword" style={{ paddingBottom: "10px" }}>Change Password</td>
                                        </tr>
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
                                    </tbody>
                                </table>

                                <div className="buttonContainer">
                                    {
                                    (username.length || password) && !updatedCredentials && 
                                    (
                                        <button
                                            type="button"
                                            className="btn btn-primary saveButton"
                                            onClick={changeCredentials}
                                        >
                                            Save Changes
                                        </button>
                                    )
                                    }

                                    {updatedCredentials && 
                                    (
                                        <div
                                            className="updatedButton"
                                        >
                                            Updated!
                                        </div>
                                    )
                                    }

                                    {confirmDelete ?
                                        (<div className="deleteConfirmContainer">
                                            <div className="deleteText">
                                                DELETE ACCOUNT?
                                            </div>
                                            <div className="yesContainer" onClick={deleteAccount}>
                                                <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="green" className="bi bi-check-lg" viewBox="0 0 16 16">
                                                    <path d="M12.736 3.97a.733.733 0 0 1 1.047 0c.286.289.29.756.01 1.05L7.88 12.01a.733.733 0 0 1-1.065.02L3.217 8.384a.757.757 0 0 1 0-1.06.733.733 0 0 1 1.047 0l3.052 3.093 5.4-6.425z" />
                                                </svg>
                                                <div> Yes </div>
                                            </div>
                                            <div className="noContainer" onClick={() => setConfirmDelete(false)}>
                                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="red" className="bi bi-x-lg" viewBox="0 0 16 16">
                                                    <path d="M2.146 2.854a.5.5 0 1 1 .708-.708L8 7.293l5.146-5.147a.5.5 0 0 1 .708.708L8.707 8l5.147 5.146a.5.5 0 0 1-.708.708L8 8.707l-5.146 5.147a.5.5 0 0 1-.708-.708L7.293 8z" />
                                                </svg>
                                                <div>No</div>
                                            </div>
                                        </div>) :

                                        (
                                            <button
                                                type="button"
                                                className="btn btn-danger deleteButton"
                                                onClick={() => setConfirmDelete(true)}
                                            >
                                                Delete Account
                                            </button>
                                        )
                                    }

                                </div>
                            </div>


                            {data[0].role === 'Admin' &&
                                (<div style={{ marginTop: '100px' }}>
                                    <AdminPage AUTOSHOP_USER_URL={AUTOSHOP_USER_URL} />
                                </div>)
                            }
                        </div>

                    </div>)
                : (<>
                    <div className="loading">loading...</div>
                </>)

            }
        </>
    )

}