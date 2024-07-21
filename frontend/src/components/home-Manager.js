import { Link, useNavigate } from "react-router-dom";
import { useContext, useState } from "react";
import '../styles/home.css'
import '../styles/nav.css'
import { AuthContext } from "../components/auth-context";


export default function Home(){

    const { isLoggedIn } = useContext(AuthContext);

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
        <div>
            {isLoggedIn ? (
                        <div>
                            <div className="accountHome"> 
                                <h2>Account</h2>
                                <div className="accountHome-content">
                                    <h3>Welcome AutoParts customer! You are logged in!</h3>
                                </div>
                            </div>
                            <div className="partsHome">
                                    <h2>Parts</h2>
                                    <div className="partsHome-content">
                                        <div className="partsCatagories">
                                            <div className="group1">
                                                {/* ToDo: work on parts page and connect it to the buttons */}
                                                <div className="partsContainer">
                                                    <Link to="/parts/searchsearch?partName=Engine%20Oil%20Filter">
                                                        <button className="partsButton partsOil"></button>
                                                        <div className="partsText">Oil Parts</div>
                                                    </Link>
                                                </div>
                                                <div className="partsContainer">
                                                    <Link>
                                                        <button className="partsButton partsMechanical"></button>
                                                        <div className="partsText">Batteries</div>
                                                    </Link>
                                                </div>
                                            </div>
                                            <div className="group2">
                                                <div className="partsContainer">
                                                    <Link>
                                                        <button className="partsButton partsLights"></button>
                                                        <div className="partsText">Light Bulbs</div>
                                                    </Link>
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
                        </div>
                    ) : (
                        <div>
                            <div className="accountHome">
                                <h2>Account</h2>
                                <div className="accountHome-content">
                                    <Link className="accountHome-content-login" to="/login" >
                                        <button>Log In</button><br />
                                    </Link>
                                    <Link className="accountHome-content-createAccount" to="/register">
                                        <button>Create Account</button>
                                    </Link>
                                </div>
                            </div>
                            <div className="partsHome">
                                <h2>Parts</h2>
                                <div className="partsHome-content">
                                    <div className="partsCatagories">
                                        <div className="group1">
                                            <div className="partsContainer">
                                                <Link to="/parts/searchsearch?partName=Engine%20Oil%20Filter">
                                                    <button className="partsButton partsOil"></button>
                                                    <div className="partsText">Oil Parts</div>
                                                </Link>
                                            </div>
                                            <div className="partsContainer">
                                                <Link>
                                                    <button className="partsButton partsMechanical"></button>
                                                    <div className="partsText">Batteries</div>
                                                </Link>
                                            </div>
                                        </div>
                                        <div className="group2">
                                            <div className="partsContainer">
                                                <Link>
                                                    <button className="partsButton partsLights"></button>
                                                    <div className="partsText">Light Bulbs</div>
                                                </Link>
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

                        </div>
                    )}
        </div>
    </>)
}