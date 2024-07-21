import { Link, useLocation } from "react-router-dom";
import { useState, useEffect } from "react";
import Table from 'react-bootstrap/Table';
import '../styles/parts.css';
import '../styles/nav.css';

export default function SearchResultPage() {
    const [results, setResults] = useState([]);
    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);
    const partName = searchParams.get('partName');

    const searchResultsTableRows = results.map(p => (
        <tr key={p.partId}>
            <td>{p.partId}</td>
            <td>{p.partName}</td>
            <td>{p.description}</td>
            <td>{p.price}</td>
            <td>{p.inventory}</td>
            <td><button>Place Order</button></td>
        </tr>
    ));

    async function searchParts() {
        const url = `http://localhost:8080/parts/search?partName=${partName}`;
        const httpResponse = await fetch(url);
        const searchResults = await httpResponse.json();
        setResults(searchResults);
    }

    useEffect(() => {
        searchParts();
    }, [partName]);

    return (
        <>
            <div className="headParts">
                <h1>Search Results for "{partName}"</h1>
            </div>
            <div className="container">
                <Table striped bordered hover>
                    <thead>
                        <tr>
                            <td>Part ID</td>
                            <td>Part Name</td>
                            <td>Description</td>
                            <td>Price</td>
                            <td>Inventory</td>
                            <td></td>
                        </tr>
                    </thead>
                    <tbody>
                        {searchResultsTableRows}
                    </tbody>
                </Table>
            </div>
        </>
    );
}