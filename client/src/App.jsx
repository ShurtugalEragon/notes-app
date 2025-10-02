import './App.css'
import {BrowserRouter, Routes, Route} from 'react-router-dom';
import HomePage from './HomePage';
import DocumentEditor from './DocumentEditor';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="docs/:id" element={<DocumentEditor />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
