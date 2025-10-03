import {useEffect, useState} from 'react';
import {useParams, useNavigate} from 'react-router';

function DocumentEditor() {
    const [text, setText] = useState('');
    const {id} = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        async function getDoc() {
            let res = await fetch(`http://127.0.0.1:8080/docs/${id}`);
            let data = await res.json();
            setText(data.content);
        }
        getDoc();
    }, [id]);

    async function saveDoc() {
        await fetch(`http://127.0.0.1:8080/docs/${id}`, {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({content: text})
        });
    }

    return (
        <>
            <h1>Editing Document {id}</h1>
            <div>
                <textarea value={text} onChange={e => setText(e.target.value)} rows="20" cols="75"/>
            </div>
            <button onClick={saveDoc}>Save</button>
            <button onClick={() => {navigate("/")}}>Home</button>
        </>
    )
}

export default DocumentEditor