import { useNavigate } from "react-router-dom";
import { isAuthed, logout } from "../services/AuthService";

export default function HeaderComponent() {
  const navigate = useNavigate();
  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <header className="bg-light py-3">
      <div className="container d-flex justify-content-between align-items-center">
        <h2 className="mb-0 text-center flex-grow-1">Employee App</h2>
        {isAuthed() && (
          <button className="btn btn-outline-danger ms-3" onClick={handleLogout}>
            Logout
          </button>
        )}
      </div>
    </header>
  );
}