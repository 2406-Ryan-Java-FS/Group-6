import { Link } from "react-router-dom";
import { useState, useEffect } from "react";
import Table from 'react-bootstrap/Table';
import '../styles/parts.css'
import '../styles/nav.css'

export default function PartsManager(){
    
    const [parts, setParts] = useState([]);

    const partsTableRows = parts.map(p =>(
        <tr key={p.partId}>
            <td>{p.partName}</td>
            <td>{p.description}</td>
            <td>{p.price}</td>
            <td>{p.inventory}</td>
            <td><button>Place Order</button></td>
        </tr>
    ));

    async function getAllParts(){
        const url = "http://localhost:8080/parts";
        const httpResponse = await fetch(url);
        const partList = await httpResponse.json();
        setParts(partList);
    }

    useEffect(() =>{
        getAllParts();
    },[]);
     
    return(<>
        <div className="headParts">
            <h1>AutoParts Parts</h1>
        </div>
        <div className="container">

            <Table striped bordered hover>
                <thead>
                    <tr>
                        <td>Part Name</td>
                        <td>Description</td>
                        <td>Price</td>
                        <td>Inventory</td>
                        <td></td>
                    </tr>
                </thead>
                <tbody>
                    {partsTableRows}
                </tbody>
            </Table>

        </div>
    </>);
}