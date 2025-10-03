import {useEffect, useState} from 'react';
import {useParams, useNavigate} from 'react-router';
import './DocumentEditor.css'

function DocumentEditor() {
    const [text, setText] = useState('');
    const [title, setTitle] = useState('');
    const {id} = useParams();
    const navigate = useNavigate();
    const [status, setStatus] = useState('');

    useEffect(() => {
        async function getDoc() {
            let res = await fetch(`http://127.0.0.1:8080/docs/${id}`);
            let data = await res.json();
            setText(data.content);
            setTitle(data.title);
        }
        getDoc();
    }, [id]);

    async function saveDoc() {
        setStatus('Saving');
        await fetch(`http://127.0.0.1:8080/docs/${id}`, {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({content: text})
        });
        setStatus('Saved');
        setTimeout(() => {setStatus('')}, 2000);
    }

    async function deleteDoc() {
        await fetch(`http://127.0.0.1:8080/docs/${id}`, {
            method: "DELETE",
            headers: {"Content-Type": "application/json"}
        });
        navigate("/");
    }

    return (
        <div id="editor">
            <ul id="navbar">
                <li><h1>{title}</h1></li>
                <li><p>{status}</p></li>
            </ul>
            <div>
                <textarea value={text} onChange={e => {setText(e.target.value); saveDoc();}} rows="20" cols="75"/>
            </div>

            <div id="footer">
                <button onClick={() => {navigate("/")}}>Home</button>
                <button onClick={deleteDoc}>Delete</button>
            </div>
        </div>
    )
}

export default DocumentEditor