import { Link } from "react-router-dom";

export default function NavBar(){

    return(<>
        <nav className="navbar navbar-expand-lg bg-body-tertiary">
            <div className="container-fluid">
                <Link className="navbar-brand" to="/">AutoParts</Link>
                <Link className="nav-link" to="/parts">Parts</Link>
                <Link className="nav-link" to="/orders">Place Order</Link>
                <Link className="nav-link" to="/">View Order</Link>
                <Link className="nav-link" to="/login">Login</Link>
                {/* <Link className="nav-link" to="#">Pricing</Link> */}
                <form className="d-flex" role="search">
                    <input className="form-control me-2" type="search" placeholder="Search for Part" aria-label="Search" />
                    <button className="btn btn-outline-success" type="submit">Search</button>
                </form>
            </div>
        </nav>
    </>)
}