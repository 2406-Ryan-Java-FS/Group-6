

import { useEffect, useContext, useState, useRef } from 'react';
//import { AppContext } from '../AppContext';
import PlaceOrder from './plcOrder-Manager';
import Table from 'react-bootstrap/Table';
import '../styles/parts.css'
import '../styles/nav.css'


export default function OrderTable() {


    const [authUser, setAuthUser] = useState(0)

    const orderInput = useRef();
    function formDate(date) { 
        const d = new Date(date)
        const df = d.toLocaleDateString('en-GB', {
        month: '2-digit',
        day: '2-digit',
        year: 'numeric',
      });
      return df;
    }
    const [orders, setOrders] = useState([]); 
    const orderTableRows = orders.map(o =>
        <tr key={parseInt(o.orderId)}>
            <td>{o.orderId}</td>
            <td>{o.customerId}</td>
            <td>{formDate(o.orderDate)}</td>
            <td>{o.status}</td>
            <td>{o.total}</td>
            <td>{o.partId}</td>
            <td>{o.quantity}</td>
            <td><button onClick = {() => updateOrder(o)} class = "buttons" >Update Order</button></td>
            <td><button onClick = {() => deleteOrder(o)} class = "buttons">Cancel Order</button></td>
        </tr>);
        
       


    async function getAllOrders() {
        console.log("Getting Orders....")
        try {
            const url = "http://localhost:8080/orders/current";
            const httpResponse = await fetch(url, { 
                method: 'GET',  
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
                }})
            console.log(httpResponse.status);
            const orderList = await httpResponse.json();

            setOrders(orderList);
        } catch (e) {
            console.log(e);
            alert("Network Error")
        }
    }
    
    async function viewOrder() {
        console.log(orderInput.current.value);

        try {
            const url = `http://localhost:8080/orders/${orderInput.current.value}`;
            const httpResponse = await fetch(url, { 
                method: 'GET',  
                mode: 'cors'});
            console.log(httpResponse.status);
            if (httpResponse.status !=200) {
                alert("No Order Found");
            } else {
                const body = await httpResponse.json();
                let orderList = [];
                console.log(orderList);
                console.log(typeof(orderList));
                orderList.push(body);
                setOrders(orderList);
            }
        } catch (e) {
            console.log(e);
            alert("Network Error")
        }
        orderInput.current.value = "";
    }

    function updateOrder(or) {
        console.log(or)
        
    }

    async function deleteOrder(o) {
        console.log(o);
        try {
            const url = `http://localhost:8080/orders/${o.orderId}`;
            const httpResponse = await fetch(url, { 
                method: 'DELETE',  
                mode: 'cors'});
            console.log(httpResponse.status);
            const body = await httpResponse.json();
            
        } catch (e) {
            console.log(e);}
        try {
                const url = "http://localhost:8080/orders";
                const httpResponse = await fetch(url, { 
                    method: 'GET',  
                    mode: 'cors'});
                console.log(httpResponse.status);
                const orderList = await httpResponse.json();
    
                setOrders(orderList);
            } catch (e) {
                console.log(e);
            }
    }
    

    return (<>
        
        <div class="container">
        <h1>View Orders</h1>
            <div class="button-holder">
            <input type="number" ref={orderInput} placeholder='Enter Order ID' id = "order-input"/>
            <button onClick={viewOrder} class = "buttons">Click Here To Get Order</button>
            <button onClick={getAllOrders} class = "buttons">Get All Orders</button> 
            </div>  
        </div>
        
        {orders.length > 0 && (
            <Table striped bordered hover responsive="sm">
                <thead>
                    <tr>
                        <td>ID</td>
                        <td>Customer ID</td>
                        <td>Order Date</td>
                        <td>Status</td>
                        <td>Total</td>
                        <td>Part ID</td>
                        <td>Quantity</td>
                        <td>Update Order</td>
                        <td>Cancel Order</td>
                    </tr>
                </thead>
                <tbody>
                    {orderTableRows}
                </tbody>
            </Table>
        )}
        </>)

}
