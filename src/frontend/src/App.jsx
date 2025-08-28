
import './App.css'
import { HeaderComponent } from './components/HeaderComponent'
import ListeEmployeeComponent from './components/ListeEmployeeComponent'
import { FooterComponent } from './components/FooterComponent'
import EmployeeComponent from './components/EmployeeComponent'
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'

function App() {

  return (
    <>
      <Router>
          <HeaderComponent />
          <Routes>
            <Route path="/" element={<ListeEmployeeComponent />} />
            <Route path="/employees" element={<ListeEmployeeComponent />} />
            <Route path="/add-employee" element={<EmployeeComponent />} />
            <Route path="/edit-employee/:id" element={<EmployeeComponent />} />
          </Routes>
          <FooterComponent />
      </Router> 

    </>
  )
}

export default App
