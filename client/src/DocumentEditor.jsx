import {useEffect, useState, useRef} from 'react';
import {useParams, useNavigate} from 'react-router';
import './DocumentEditor.css'
import { Client } from '@stomp/stompjs';
import { v4 as uuidv4 } from 'uuid';

function DocumentEditor() {
    const [text, setText] = useState('');
    const [title, setTitle] = useState('');
    const {id} = useParams();
    const navigate = useNavigate();
    const [status, setStatus] = useState('');
    const client = useRef(null);
    const clientId = useRef('');

    function handleMessage(message) {
        const body = JSON.parse(message.body);
        if (body.clientId === clientId.current) {
            return;
        }

        console.log(`Received message: ${message}`);
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
                <textarea value={text} onChange={e => {setText(e.target.value); saveDoc(e.target.value);}} rows="20" cols="75"/>
            </div>

            <div id="footer">
                <button onClick={() => {navigate("/")}}>Home</button>
                <button onClick={deleteDoc}>Delete</button>
            </div>
        </div>
    )
}

export default DocumentEditor