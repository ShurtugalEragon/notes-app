import {useState, useEffect} from 'react';
import {Link} from 'react-router-dom';

function HomePage() {
    const [docs, setDocs] = useState([]);

    useEffect(() => {
        async function getDocs() {
            let res = await fetch('http://127.0.0.1:8080/docs');
            let data = await res.json();
            setDocs(data);
        }
        getDocs();
    }, []);

    return (
        <>
            <h1>My Documents</h1>
            <ul>
                {docs.map(doc => 
                    <li key={doc.id}>
                        <Link to={`/docs/${doc.id}`}>Document {doc.id}</Link>
                    </li>
                )}
            </ul>
        </>
    )
}

export default HomePage