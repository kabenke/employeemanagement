import { useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import "../styles/app.css";

export default function RegisterPage() {
  const [form, setForm] = useState({ username: "", password: "", confirm: "" });
  const [errors, setErrors] = useState({});
  const [message, setMessage] = useState("");
  const [submitting, setSubmitting] = useState(false);

  const strongPwd = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&.()[\]{}~^_+\-=|\\:;'",<>/?`])[A-Za-z\d@$!%*?&.()[\]{}~^_+\-=|\\:;'",<>/?`]{8,}$/;

  // Helpers for live checklist
  const hasLower = /[a-z]/.test(form.password);
  const hasUpper = /[A-Z]/.test(form.password);
  const hasDigit = /\d/.test(form.password);
  const hasSpecial = /[@$!%*?&.()[\]{}~^_+\-=|\\:;'",<>/?`]/.test(form.password);
  const hasLen = form.password.length >= 8;

  const onChange = (e) => {
    const { name, value } = e.target;
    setForm((f) => ({ ...f, [name]: value }));
    setErrors((err) => ({ ...err, [name]: undefined }));
    setMessage("");
  };

  const validate = () => {
    const e = {};
    if (!form.username.trim()) e.username = "Username is required";
    if (form.username.includes(" ")) e.username = "No spaces allowed in username";

    if (!form.password) e.password = "Password is required";
    else if (!strongPwd.test(form.password)) {
      e.password =
        "Password must be at least 8 characters and include uppercase, lowercase, number, and special character";
    }

    if (form.confirm !== form.password) e.confirm = "Passwords do not match";
    setErrors(e);
    return Object.keys(e).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage("");
    if (!validate()) return;
    setSubmitting(true);
    try {
      await axios.post("http://localhost:8080/auth/register", {
        username: form.username.trim(),
        password: form.password,
      });
      setMessage("Registration successful! You can now log in.");
      setForm({ username: "", password: "", confirm: "" });
    } catch (err) {
      const serverMsg =
        err.response?.data && typeof err.response.data === "string"
          ? err.response.data
          : err.message;
      setMessage("❌ " + serverMsg);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="auth-wrap">
      <div className="auth-card">
        <h2 className="auth-title">Create your account</h2>
        <p className="auth-subtitle">
          Please choose a <strong>username</strong> and password to sign up.
        </p>

        <form onSubmit={handleSubmit} className="auth-form">
          <label className="auth-label">
            Username or Email
            <input
              name="username"
              placeholder="e.g. johndoe"
              value={form.username}
              onChange={onChange}
              className={`auth-input ${errors.username ? "error" : ""}`}
              autoComplete="username"
            />
            {errors.username && <span className="auth-error">{errors.username}</span>}
          </label>

          <label className="auth-label">
            Password
            <input
              type="password"
              name="password"
              placeholder="••••••••"
              value={form.password}
              onChange={onChange}
              className={`auth-input ${errors.password ? "error" : ""}`}
              autoComplete="new-password"
            />
            {errors.password && <span className="auth-error">{errors.password}</span>}

            {/* Live checklist */}
            <ul className="pwd-rules">
              <li className={hasLen ? "ok" : "fail"}>At least 8 characters</li>
              <li className={hasUpper ? "ok" : "fail"}>Uppercase letter (A–Z)</li>
              <li className={hasLower ? "ok" : "fail"}>Lowercase letter (a–z)</li>
              <li className={hasDigit ? "ok" : "fail"}>Number (0–9)</li>
              <li className={hasSpecial ? "ok" : "fail"}>Special character (!@#$…)</li>
            </ul>
          </label>

          <label className="auth-label">
            Confirm password
            <input
              type="password"
              name="confirm"
              placeholder="••••••••"
              value={form.confirm}
              onChange={onChange}
              className={`auth-input ${errors.confirm ? "error" : ""}`}
              autoComplete="new-password"
            />
            {errors.confirm && <span className="auth-error">{errors.confirm}</span>}
          </label>

          <button type="submit" className="auth-button" disabled={submitting}>
            {submitting ? "Signing up..." : "Sign Up"}
          </button>
        </form>

        {message && (
          <div className="auth-message" role="alert" aria-live="polite">
            {message}
          </div>
        )}

        <p className="auth-footer">
          Already have an account?{" "}
          <Link to="/login" className="auth-link">
            Log in
          </Link>
        </p>
      </div>
    </div>
  );
}
