import { useEffect, useRef, useState } from 'react'
import '../styles/user-settings.css'
import Cookies from "js-cookie"

export default function AdminPage() {

    const inputRef = useRef();

    const AUTOSHOP_URL = 'http://localhost:8080/users'
    const [errorMessage, setErrorMessage] = useState("");
    const [data, setData] = useState([])
    const [dataList, setDataList] = useState([])
    const [dataError, setDataError] = useState("");

    const fetchUserData = async () => {
        // const token = Cookies.get('accessToken')
        const token = localStorage.getItem('accessToken');

        if (inputRef.current.value > 0) {
            try {
                const response = await fetch(`${AUTOSHOP_URL}/${inputRef.current.value}`, {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${token}`,
                    }
                })
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

    const fetchAllUserData = async () => {
        // const token = Cookies.get('accessToken')
        const token = localStorage.getItem('accessToken');

        try {
            const response = await fetch(AUTOSHOP_URL, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
                }
            })
            const json = await response.json()

            if (response.ok) {
                setDataList(json)
                console.log(json)
            }
        } catch (error) {
            setDataList([])
            console.error(error);
        }

    }

    useEffect(() => {
fetchAllUserData()
    }, [data])


    const deleteUserData = async () => {
        // const token = Cookies.get('accessToken')
        const token = localStorage.getItem('accessToken');
        
        try {
            const response = await fetch(`${AUTOSHOP_URL}/admin/${inputRef.current.value}`,
                {
                    method: 'DELETE',
                    headers: {
                        'Authorization': `Bearer ${token}`,
                    }
                },
            )
            if (response.ok) {
                console.log("User deleted")
                inputRef.current.value = '';
                setData([])
            } else {
                console.error("Error")
                setErrorMessage("User not found")
                setTimeout(() => setErrorMessage(''), 1500);
            }
        } catch (error) {
            console.error(error);
        }
    }

    const handleKeyDown = (e) => {
        if (e.key === 'Enter') {
            fetchUserData();
        }
    }

    return (
        <>

            <div className='adminMainContainer'>
                <h1 className='admin'>
                    Admin Settings
                </h1>

                <div className='adminContent'>
                    <div className='adminDeleteContainer'>
                        <p>Search for user</p>
                        <div>
                            <input ref={inputRef} onKeyDown={handleKeyDown} />
                            <button onClick={fetchUserData}>search</button>

                            {data.length > 0 && (
                                <table className='userTable'>
                                    <thead>
                                        <tr>
                                        <th>user_id</th>
                                        <th>Username</th>
                                        <th>Email</th>
                                        <th>Role</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>{data[0].userId}</td>
                                            <td>{data[0].username}</td>
                                            <td>{data[0].email}</td>
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

                    <div className='adminViewAllContainer'>
                        AutoParts Users
                        <div>
                            <table className='userTable'>
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Username</th>
                                        <th>Email</th>
                                        <th>Role</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {dataList && dataList.map((x, index) => (
                                        <tr key={index}>
                                            <td>{x.userId}</td>
                                            <td>{x.username}</td>
                                            <td>{x.email}</td>
                                            <td>{x.role}</td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>

                        </div>
                    </div>
                </div>

            </div>

        </>
    )
}