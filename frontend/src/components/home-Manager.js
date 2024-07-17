import { Link } from "react-router-dom";
import '../styles/home.css'
import '../styles/nav.css'
export default function Home(){

    return(<>
        <div className="headHome">
            <h1>Auto Parts Store</h1>    
        </div>
        <div className="introHome">
            <p>Welcome to AutoParts Store, your ultimate destination for high-quality auto parts 
                and accessories. Whether you're a professional mechanic or a DIY enthusiast, we offer 
                an extensive selection of parts for all makes and models. Our commitment to exceptional 
                service and unbeatable prices ensures you find exactly what you need to keep your 
                vehicle running smoothly.
            </p>
            <p>
                Your Car, Our Parts, Perfect Fit.
            </p>
        </div>
        <div className="accountHome">
            <h2>Account</h2>
            <div className="accountHome-content">
                <Link className="accountHome-content-login" to="/" >Login</Link><br />
                <Link className="accountHome-content-login" to="/" >Make New Account</Link>
            </div>
        </div>
        <div className="partsHome">
            <h2>Parts</h2>
            <div className="partsHome-content">
                <div>
                    <p>Input few parts to view</p>
                    <form className="d-flex" role="search">
                        <input className="form-control me-2" type="search" placeholder="Search for Part" aria-label="Search" />
                        <button className="btn btn-outline-success" type="submit">Search</button>
                    </form>
                </div>
            </div>
        </div>
        <div className="ordersHome">
            <h2>Orders</h2>
            <div className="orderHome-content">
            <Link className="accountHome-content-login" to="/" >Place Order</Link><br />
            <Link className="accountHome-content-login" to="/" >View Orders</Link>
            </div>
        </div>
    </>)
}