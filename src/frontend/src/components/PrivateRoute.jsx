import { Navigate, Outlet } from "react-router-dom";
import { isAuthed } from "../services/AuthService";

export default function PrivateRoute() {
  return isAuthed() ? <Outlet /> : <Navigate to="/login" replace />;
}
