import { Link } from "react-router-dom";
import { useState, useEffect } from "react";
import Form from 'react-bootstrap/Form';
import '../styles/plcOrder.css'
import '../styles/nav.css'

export default function PlaceOrder(){
    const [partID, setPartID] = useState('');
    const [number, setNumber] = useState(1);

    const handlePartIDChange = (event) => {
        setPartID(event.target.value);
    };

    const handleNumberChange = (event) => {
        setNumber(event.target.value);
    };

    return(<>
        <div className="headPlcOrder">
            <h1>AutoParts Place Orders</h1>
        </div>
        <div className="containerPlcOrder">
            <h2>Order Form</h2>
            <div data-mdb-input-init className="form-outline">
                <input 
                    type="number" 
                    id="partID" 
                    className="form-control" 
                    min="0"
                    value={partID} 
                    onChange={handlePartIDChange}
                />
                <label className="form-label" htmlFor="partID">Part ID</label>
            </div>
            <div data-mdb-input-init className="form-outline">
                <input 
                    type="number" 
                    id="typeNumber" 
                    className="form-control" 
                    min="1" 
                    value={number} 
                    onChange={handleNumberChange}
                />
                <label className="form-label" htmlFor="typeNumber">Number input</label>
            </div>
            <button>Place Order</button>
        </div>
    </>);
}