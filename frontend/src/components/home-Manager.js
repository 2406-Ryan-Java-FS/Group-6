import { Link } from "react-router-dom";
import '../styles/home.css'
import '../styles/nav.css'
export default function Home(){

    return(<>
        <div className="headHome">
            <h1>Auto Parts Store</h1>    
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
                <button onClick={<Link className="accountHome-content-login" to="/" >Login</Link>}>Log In</button><br />
                <button onClick={<Link className="accountHome-content-createAccount" to="/" >Make New Account</Link>}>Create Account</button>
                {/* <Link className="accountHome-content-login" to="/" >Login</Link><br />
                <Link className="accountHome-content-login" to="/" >Make New Account</Link> */}
            </div>
        </div>
        <div className="partsHome">
            <h2>Parts</h2>
            <div className="partsHome-content">
                <div className="partsCatagories">
                    <div className="group1">
                        {/* <div className="partsOil">
                            
                        </div>  
                        <div className="partsMechanical">
                            
                        </div>  */}
                         {/* <button className="partsButton partsOil" ></button><br></br>
                         <div className="partsText">Oil</div>
                         <button className="partsButton partsMechanical">Mechanical</button> */}
                        <div className="partsContainer">
                            <button className="partsButton partsOil"></button>
                            <div className="partsText">Oil Parts</div>
                        </div>
                        <div className="partsContainer">
                            <button className="partsButton partsMechanical"></button>
                            <div className="partsText">Mechanical Parts</div>
                        </div>
                    </div>
                    <div className="group2">
                        {/* <div className="partsLights">
                            
                        </div> 
                        <div className="partsRandom">
                            
                        </div>   */}
                        {/* <button className="partsButton partsLights"><span>Lights</span></button>
                        <button className="partsButton partsRandom">Random</button> */}
                        <div className="partsContainer">
                            <button className="partsButton partsLights"></button>
                            <div className="partsText">Light Bulbs</div>
                        </div>
                        <div className="partsContainer">
                            <button className="partsButton partsRandom"></button>
                            <div className="partsText">Other Parts</div>
                        </div>
                    </div>                                  
                </div>
            </div>
        </div>
        <div className="ordersHome">
            <h2>Orders</h2>
            <div className="orderHome-content">
            <button onClick={<Link className="orderHome-content-placeOrder" to="/" >place new order</Link>}>Place Order</button><br />
            <button onClick={<Link className="orderHome-content-viewOrder" to="/" >View orders</Link>}>View Orders</button>
            {/* <Link className="accountHome-content-login" to="/" >Place Order</Link><br />
            <Link className="accountHome-content-login" to="/" >View Orders</Link> */}
            </div>
        </div>
    </>)
}