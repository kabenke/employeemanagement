import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { login } from "../services/AuthService";
import "../styles/app.css";

export default function LoginPage() {
  const [form, setForm] = useState({ username: "", password: "" });
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) =>
    setForm((p) => ({ ...p, [e.target.name]: e.target.value }));

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage("");
    try {
      await login(form.username.trim(), form.password);
      navigate("/employees");
    } catch (err) {
      console.error(err);
      setMessage("❌ Login failed. Check credentials.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-wrap">
      <div className="auth-card">
        <h2 className="auth-title">Welcome back</h2>
        <p className="auth-subtitle">
          Sign in with your <strong>username</strong> and password.
        </p>

        <form onSubmit={handleSubmit} className="auth-form">
          <label className="auth-label">
            Username or Email
            <input
              name="username"
              className="auth-input"
              value={form.username}
              onChange={handleChange}
              autoComplete="username"
            />
          </label>

          <label className="auth-label">
            Password
            <input
              type="password"
              name="password"
              className="auth-input"
              value={form.password}
              onChange={handleChange}
              autoComplete="current-password"
            />
          </label>

          <button
            type="submit"
            className="auth-button"
            disabled={loading}
          >
            {loading ? "Signing in..." : "Login"}
          </button>
        </form>

        {message && <div className="auth-message">{message}</div>}

        <p className="auth-footer">
          Don’t have an account?{" "}
          <Link to="/register" className="auth-link">
            Register here
          </Link>
        </p>
      </div>
    </div>
  );
}
