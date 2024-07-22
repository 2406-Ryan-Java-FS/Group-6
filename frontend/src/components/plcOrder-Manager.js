import { Link } from "react-router-dom";
import { useState, useEffect, useContext } from "react";
import Form from 'react-bootstrap/Form';
import '../styles/plcOrder.css'
import '../styles/nav.css'

export default function PlaceOrder(){
    // too do, find the customer token and us it to add the orders
    const customerID = 1;
    const [authUser, setAuthUser] = useState(0)

    const [partID, setPartID] = useState('');
    const [quantity, setQuantitiy] = useState(1);
    const [order, setOrder] = useState([]);

    const handlePartIDChange = (event) => {
        setPartID(event.target.value);
    };

    const handleNumberChange = (event) => {
        setQuantitiy(event.target.value);
    };

    const handleSubmit = async () => {
        const orderData = {
            customerId: customerID,
            partId: parseInt(partID, 10),
            quantity: parseInt(quantity, 10)
        };

        try {
            const response = await fetch('http://localhost:8080/orders', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
                },
                body: JSON.stringify(orderData)
            });
            const responseData = await response.json();
            console.log('Server response:', responseData);

            if (response.ok) {
                setOrder([...order, orderData]);
                alert('Order placed successfully!');
                setPartID('');
                setQuantitiy(1);
            } else {
                alert('Failed to place order. Please try again.');
            }
        } catch (error) {
            console.error('Error placing order:', error);
            alert('Error placing order. Please try again.');
        }
    };

    return (
        <>
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
                        value={quantity} 
                        onChange={handleNumberChange}
                    />
                    <label className="form-label" htmlFor="typeNumber">Number input</label>
                </div>
                <button onClick={handleSubmit}>Place Order</button>
            </div>
    </>);
}