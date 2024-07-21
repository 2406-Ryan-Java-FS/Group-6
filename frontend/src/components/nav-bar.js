import { Link, useNavigate } from "react-router-dom";
import { useContext, useState } from "react";
import { AuthContext } from "../components/auth-context";

export default function NavBar() {
    const { isLoggedIn, logout } = useContext(AuthContext);
    const navigate = useNavigate();
    const [searchQuery, setSearchQuery] = useState('');

    const handleLogout = () => {
        logout();
        navigate('/login', { state: { message: 'You have been logged out successfully.' } });
    };

    const handleSearchInputChange = (event) => {
        setSearchQuery(event.target.value);
    };

    const handleSearchSubmit = (event) => {
        event.preventDefault();
        const formattedQuery = encodeURIComponent(searchQuery);
        navigate(`/parts/search?partName=${formattedQuery}`);
    };

    return (
        <nav className="navbar navbar-expand-lg bg-body-tertiary">
            <div className="container-fluid">
                <Link className="navbar-brand" to="/">AutoParts</Link>
                <Link className="nav-link" to="/parts">Parts</Link>
                <Link className="nav-link" to="/orders">Place Order</Link>
                <Link className="nav-link" to="/">View Order</Link>
                {isLoggedIn ? (
                    <button className="nav-link btn" onClick={handleLogout}>Logout</button>
                ) : (
                    <Link className="nav-link" to="/login">Login</Link>
                )}
                <Link className="nav-link" to="/settings">Settings</Link>
                <form className="d-flex" role="search" onSubmit={handleSearchSubmit}>
                    <input 
                        className="form-control me-2" 
                        type="search" 
                        placeholder="Search for a Part" 
                        aria-label="Search"
                        value={searchQuery}
                        onChange={handleSearchInputChange}
                     />
                    <button 
                        className="btn btn-outline-success" 
                        type="submit">
                        Search
                    </button>
                </form>
            </div>
        </nav>
    );
}