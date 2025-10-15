import {useEffect, useState, useRef} from 'react';
import {useParams, useNavigate} from 'react-router';
import './DocumentEditor.css'
import { Client } from '@stomp/stompjs';
import { v4 as uuidv4 } from 'uuid';

function DocumentEditor() {
    const [text, setText] = useState('');
    const [title, setTitle] = useState('');
    const [deleted, setDeleted] = useState(false);
    const {id} = useParams();
    const navigate = useNavigate();
    const [status, setStatus] = useState('');
    const [connectionStatus, setConnectionStatus] = useState('');
    const client = useRef(null);
    const clientId = useRef('');

    function handleMessage(message) {
        const body = JSON.parse(message.body);

        if (body.clientId === clientId.current) {
            return;
        }

        if (body.delete === true) {
            setDeleted(true);
            return;
        }

        setText(body.content);
    } 

    useEffect(() => {
        async function getDoc() {
            let res = await fetch(`http://127.0.0.1:8080/docs/${id}`);
            let data = await res.json();
            setText(data.content);
            setTitle(data.title);
        }
        function createClient() {
            clientId.current = uuidv4();

            client.current = new Client({
                brokerURL: 'ws://127.0.0.1:8080/ws',
                debug: (str) => {
                    console.log(str);
                },
                onConnect: (frame) => {
                    client.current.subscribe(`/topic/docs/${id}`, handleMessage);
                    setConnectionStatus('Connected');
                },
                onDisconnect: (frame) => {
                    setConnectionStatus('Disconnected');
                }
            });
            client.current.activate();
        }
        getDoc();
        createClient();
        return async function cleanup() {
            await client.current.deactivate();
        }
    }, [id]);

    async function saveDoc(newText) {
        setStatus('Saving');

        client.current.publish({
            destination: `/app/docs/${id}/edit`,
            body: JSON.stringify({ content: newText, clientId: clientId.current})
        });

        await fetch(`http://127.0.0.1:8080/docs/${id}`, {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({content: newText})
        });
        setStatus('Saved');
        setTimeout(() => {setStatus('')}, 2000);
    }

    async function deleteDoc() {
        client.current.publish({
            destination: `/app/docs/${id}/edit`,
            body: JSON.stringify({ clientId: clientId.current, delete: true})
        });

        await fetch(`http://127.0.0.1:8080/docs/${id}`, {
            method: "DELETE",
            headers: {"Content-Type": "application/json"}
        });

        navigate("/");
    }
    
    if (deleted) {
        return (
            <div id="footer">
                <p>Document was deleted</p>
                <button onClick={() => {navigate("/")}}>Home</button>
            </div>
        )
    }
    return (
        <div id="editor">
            <ul id="navbar">
                <div id="title">
                    <li><h1>{title}</h1></li>
                </div>
                <div id="statusbar">
                    <li><p>{status}</p></li>
                    <li><p>{connectionStatus}</p></li>
                </div>
            </ul>
            <div>
                <textarea id ="text" value={text} onChange={e => {setText(e.target.value); saveDoc(e.target.value);}} rows="20" cols="75"/>
            </div>

            <div id="footer">
                <button onClick={() => {navigate("/")}}>Home</button>
                <button onClick={deleteDoc}>Delete</button>
            </div>
        </div>
    )
}

export default DocumentEditor