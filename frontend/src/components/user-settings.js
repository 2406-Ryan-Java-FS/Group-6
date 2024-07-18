import { useEffect, useRef, useState } from "react";
import AdminPage from "./admin-page";

export default function UserSettings () {

    const usernameRef = useRef()
    const passwordRef = useRef()



    const AUTOSHOP_URL = 'http://localhost:8080/users'
    const [data, setData] = useState([])
    const [dataError, setDataError] = useState("");

    useEffect(() => {
        const fetchUserData = async () => {
            try {
                const response = await fetch(`${AUTOSHOP_URL}/2`);
                // const response = await fetch(`${AUTO_SHOP_URL}1`);
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                const json = await response.json();
                    setData([json]);
                    console.log(json);
                    setDataError("");
                
            } catch (error) {
                setDataError("An error occurred while fetching data");
                console.error(error);
            }
        };
        fetchUserData();
    }, []);

    // const changeCredentials = () => {

    //     const putUserCredentials = async () => {
    //         try {
    //             const response = await fetch(`${AUTOSHOP_URL}/2`,
    //                 {
    //                     method: 'PUT',
    //                     headers: {
    //                         'Content-Type': 'application/json'
    //                     },
    //                     body: JSON.stringify({
    //                         "user_id": usernameRef.current.value
    //                     }),
    //                 }
    //             );
    //         }
    //     }

    // }


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
                    <button type="button" className="btn btn-primary" style={{ marginTop: "20px" }}>Save Changes</button>
                </div>


                <div style={{ marginTop: '100px' }}>
                    <AdminPage />
                </div>

            </div>)
            : (<div>loading...</div>)

        }
        </>
    )

}