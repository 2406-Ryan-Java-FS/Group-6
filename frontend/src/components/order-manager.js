import { useEffect, useContext, useState, useRef } from 'react';
//import { AppContext } from '../AppContext';





export default function OrderTable() {

    const orderInput = useRef();

    const [orders, setOrders] = useState([]);
    const orderTableRows = orders.map(o =>
        <tr key={o.orderId}>
            <td>{o.orderId}</td>
            <td>{o.customerId}</td>
            <td>{o.orderDate}</td>
            <td>{o.status}</td>
            <td>{o.total}</td>
            <td>{o.partId}</td>
            <td>{o.quantity}</td>
            <td><button onClick = {() => updateOrder(o)} >Update Order</button></td>
            <td><button onClick = {() => deleteOrder(o)} >Cancel Order</button></td>
        </tr>
    );


    async function getAllOrders() {
        console.log("Getting Orders....")
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
    
    async function viewOrder() {
        console.log(orderInput.current.value);

        try {
            const url = `http://localhost:8080/orders/${orderInput.current.value}`;
            const httpResponse = await fetch(url, { 
                method: 'GET',  
                mode: 'cors'});
            console.log(httpResponse.status);
            const body = await httpResponse.json();
            let orderList = [];
            console.log(orderList);
            console.log(typeof(orderList));
            orderList.push(body);
            setOrders(orderList);
        } catch (e) {
            console.log(e);
        }
        orderInput.current.value = "";
    }

    function updateOrder(or) {
        console.log(or)
        return(<>
            <td>{or.orderId}</td>
            <td>{or.customerId}</td>
            <td>{or.orderDate}</td>
            <td>{or.status}</td>
            <td>{or.total}</td>
            <td>{or.partId}</td>
            <td>{or.quantity}</td>
        </>)
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
    

    const tableStyle = {
        backgroundColor: "lightgrey",
        border: "1px solid black"
    }

    return (<>
        
        
        <button onClick={getAllOrders}>Get All Orders</button>     
        <input type="number" ref={orderInput} />
        <button onClick={viewOrder}>Enter Number of Order</button>

        {orders.length > 0 && (
            <table style = {tableStyle}>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Customer ID</th>
                        <th>Order Date</th>
                        <th>Status</th>
                        <th>Total</th>
                        <th>Part ID</th>
                        <th>Quantity</th>
                    </tr>
                </thead>
                <tbody>
                    {orderTableRows}
                </tbody>
            </table>
        )}
    </>);

}