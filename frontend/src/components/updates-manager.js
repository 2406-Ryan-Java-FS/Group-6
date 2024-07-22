import { useEffect, useContext, useState, useRef } from 'react';
//import { AppContext } from '../AppContext';
import Table from 'react-bootstrap/Table';
import '../styles/order.css'
import '../styles/nav.css'


export default function UpdatesManager() {



    const orderInput1 = useRef();
    const orderInput2 = useRef();
    const orderInput3 = useRef();

    async function updatesOrder() {
        try {
            const orderData = {
                orderId: orderInput1.current.value,
                partId: orderInput2.current.value,
                quantity: orderInput3.current.value
            };
            const bodyStuff = `{"orderId" : ${orderInput1.current.value}, "partId": ${orderInput2.current.value}, "quantity": ${orderInput3.current.value}}`;
            const url = `http://localhost:8080/orders/${orderInput1.current.value}`;
            const httpResponse = await fetch(url, { 
                method: 'PUT', 
                headers: {
                    'Content-Type': 'application/json'
                    }, 
                body: JSON.stringify(orderData)});
            console.log(httpResponse.status);
            const body = await httpResponse.json();
            console.log(body)
            
        } catch (e) {
            console.log(e);
            alert("Didn't work")
        }

    }

    
    return (<>
        
        <div class="container">
        <h1>View Orders</h1>
            <div class="button-holder">
            <input type="number" ref={orderInput1} placeholder='Enter Order ID' class = "order-input"/>
            <input type="number" ref={orderInput2} placeholder='Enter Part ID' class = "order-input"/>
            <input type="number" ref={orderInput3} placeholder='Enter Quantity' class = "order-input"/>

            <button onClick={updatesOrder} class = "buttons">Click Here To Update Order</button>
            
            </div>  
        </div>

        </>)

}
