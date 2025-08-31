// src/App.jsx
// main app component with routing and layout
// includes header, footer, and main content area
// uses PrivateRoute to protect certain routes
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import HeaderComponent from "./components/HeaderComponent";
import FooterComponent from "./components/FooterComponent";
import ListeEmployeeComponent from "./components/ListeEmployeeComponent";
import EmployeeComponent from "./components/EmployeeComponent";
import PrivateRoute from "./components/PrivateRoute";
import LoginPage from "./components/LoginPage";
import RegisterPage from "./components/RegisterPage";

function App() {
  return (
    <div className="d-flex flex-column min-vh-100">
      <Router>
        <HeaderComponent />
        <main className="flex-grow-1 container-fluid">
        <Routes>
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          {/* Protected routes */}
          <Route element={<PrivateRoute />}>
            <Route path="/" element={<ListeEmployeeComponent />} />
            <Route path="/employees" element={<ListeEmployeeComponent />} />
            <Route path="/add-employee" element={<EmployeeComponent />} />
            <Route path="/edit-employee/:id" element={<EmployeeComponent />} />
          </Route>
        </Routes>
        </main>
        <FooterComponent />
      </Router>
    </div>
  );
}

export default App;
