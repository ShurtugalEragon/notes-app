import {useState, useEffect} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import './HomePage.css';

function HomePage() {
    const [docs, setDocs] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        async function getDocs() {
            let res = await fetch('http://127.0.0.1:8080/docs');
            let data = await res.json();
            setDocs(data);
        }
        getDocs();
    }, []);

    async function createDocument() {
        let res = await fetch('http://127.0.0.1:8080/docs', {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({title: "No Title"})
        });
        let data = await res.json();
        let id = data.id;
        navigate(`docs/${id}`);
    }

    return (
        <div id="home">
            <h1>My Documents</h1>
            <button onClick={createDocument}>Create Document</button>
            <div id="documentList">
                {docs.map(doc => 
                    // <li key={doc.id}>
                        <Link to={`/docs/${doc.id}`} id="documentLink" key={doc.id}>Document {doc.id}</Link>
                    // </li>
                )}
            </div>
        </div>
    )
}

export default HomePage