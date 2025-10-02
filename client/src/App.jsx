import './App.css'
import { useState, useEffect } from 'react';

async function getDocument() {
  let doc = await fetch("http://127.0.0.1:8080/docs/1");
  let data = await doc.json();
  console.log(data.content);
  return data.content;
}

function App() {
  const [documentText, setDocumentText] = useState('');

  useEffect(() => {
    async function loadDoc() {
      try {
        let text = await getDocument();
        setDocumentText(text);
      } catch (err) {
        console.log("Failed to load document", err);
      }
    }
    loadDoc();
  }, []);

  return (
    <textarea value={documentText} onChange={e => setDocumentText(e.target.value)}></textarea>
  );
}

export default App
