import { Link, useNavigate, useHistory } from "react-router-dom";
import { useContext } from "react";
import { AuthContext } from "../components/auth-context";

export default function NavBar() {
    const { isLoggedIn, logout } = useContext(AuthContext);
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/login', { state: { message: 'You have been logged out successfully.' } });
    };

    return (<>
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
                {/* <Link className="nav-link" to="#">Pricing</Link> */}
                <Link className="nav-link" to="/settings">Settings</Link>
                <form className="d-flex" role="search">
                    <input 
                        className="form-control me-2" 
                        type="search" 
                        placeholder="Search for a Part" 
                        aria-label="Search"
                     />
                    <button 
                    className="btn btn-outline-success" 
                    type="submit">
                        Search
                    </button>
                </form>
            </div>
        </nav>
    </>);
}
