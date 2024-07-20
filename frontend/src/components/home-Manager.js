import { Link } from "react-router-dom";
import '../styles/home.css'
import '../styles/nav.css'
export default function Home(){

    return(<>
        <div className="headHome">
            <h1>AutoParts Store</h1>    
        </div>
        <div className="introHome">
            <p>
                Welcome to AutoParts Store, your ultimate destination for high-quality auto parts 
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
                <Link className="accountHome-content-login" to="/" >
                    <button>Log In</button><br />
                </Link>
                <Link className="accountHome-content-createAccount" to="/">
                    <button>Create Account</button>
                </Link>
            </div>
        </div>
        <div className="partsHome">
            <h2>Parts</h2>
            <div className="partsHome-content">
                <div className="partsCatagories">
                    <div className="group1">
                        {/* ToDo: work on parts page and connect it to the buttons */}
                        <div className="partsContainer">
                            <button className="partsButton partsOil"></button>
                            <div className="partsText">Oil Parts</div>
                        </div>
                        <div className="partsContainer">
                            <button className="partsButton partsMechanical"></button>
                            <div className="partsText">Batteries</div>
                        </div>
                    </div>
                    <div className="group2">
                        <div className="partsContainer">
                            <button className="partsButton partsLights"></button>
                            <div className="partsText">Light Bulbs</div>
                        </div>
                        <div className="partsContainer">
                            <Link to="/parts">
                                <button className="partsButton partsRandom"></button>
                                <div className="partsText">All Parts</div>
                            </Link>
                        </div>
                    </div>                                  
                </div>
            </div>
        </div>
        <div className="ordersHome">
            <h2>Orders</h2>
            <div className="orderHome-content">
                <Link to="/orders">
                    <button>Place Order</button><br />
                </Link>
                <Link to="/">
                    <button>View Orders</button>
                </Link>
            </div>
        </div>
    </>)
}