import { useRef, useState } from 'react'
import '../styles/admin.css'

export default function AdminPage() {

    const inputRef = useRef();

    const AUTOSHOP_URL = 'http://localhost:8080/users'
    const [data, setData] = useState([])
    const [errorMessage, setErrorMessage] = useState("");
    const [dataError, setDataError] = useState("");

    const fetchUserData = async () => {
        if (inputRef.current.value > 0) {
            try {
                const response = await fetch(`${AUTOSHOP_URL}/${inputRef.current.value}`)
                // const response = await fetch(`${AUTO_SHOP_URL}1`)
                const json = await response.json()

                if (response.ok) {
                    setData([json])
                    console.log(json)
                    setDataError("")
                }
            } catch (error) {
                setData([])
                console.error(error);
                setDataError("User not found")
                setTimeout(() => setDataError(""), 1500);
            }
        } else {
            console.log("Put a number")
            setDataError("Please enter a number")
            setTimeout(() => setDataError(""), 1500);
            setData([])
        }
    }

    const handleKeyDown = (e) => {
        if (e.key === 'Enter') {
            fetchUserData();
        }
    }

    const deleteUserData = async () => {
        try {
            const response = await fetch(`${AUTOSHOP_URL}`,
                {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        "user_id": inputRef.current.value
                    }),
                },
            )
            if (response.ok) {
                console.log("User delete")
            } else {
                console.error("Error")
                setErrorMessage("User not found")
                setTimeout(() => setErrorMessage(''), 1500);
            }
        } catch (error) {
            console.error(error);
        }
    }

    return (
        <>

            <div className='mainContainer'>
                <h1 className='admin'>
                    Admin Dashboard
                </h1>

                <div>
                    <p>Users</p>
                    <div>
                        <input ref={inputRef} onKeyDown={handleKeyDown} />
                        <button onClick={fetchUserData}>search</button>

                        {data.length > 0 && (
                            <table>
                                <tbody>
                                    <tr>
                                        <th>user_id</th>
                                        <td>{data[0].user_id}</td>
                                    </tr>
                                    <tr>
                                        <th>Username</th>
                                        <td>{data[0].username}</td>
                                    </tr>
                                    <tr>
                                        <th>Email</th>
                                        <td>{data[0].email}</td>
                                    </tr>
                                    <tr>
                                        <th>Role</th>
                                        <td>{data[0].role}</td>
                                    </tr>
                                </tbody>
                            </table>
                        )}
                        {dataError && (
                            <div className='errorEl'>{dataError}</div>
                        )}

                        {data.length > 0 && (
                            <button onClick={deleteUserData}>delete user</button>
                        )}
                        {errorMessage && (<div className='errorEl'>{errorMessage}</div>)}
                    </div>
                </div>
            </div>

        </>
    )
}