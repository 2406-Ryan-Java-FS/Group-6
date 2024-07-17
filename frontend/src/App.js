
import './App.css';
import { Route, Routes } from 'react-router-dom';
import NavBar from './components/nav-bar';
import Home from './components/home-Manager';
import PartsManager from './components/parts-manager';


function App() {

  return(<>
    <NavBar />

    <Routes>
      <Route path='' element={<Home/>} />
      <Route path='parts' element={<PartsManager/>} />

    </Routes>
  </>)
  
}

export default App;
