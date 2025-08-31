import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { createEmployee, getEmployeeById, updateEmployee } from "../services/EmployeeService";
import "../styles/app.css"; 

const EmployeeComponent = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [employee, setEmployee] = useState({
    firstName: "",
    lastName: "",
    email: "",
  });
  const [errors, setErrors] = useState({});
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(!!id);
  const isEdit = Boolean(id);

  useEffect(() => {
    const load = async () => {
      try {
        const data = await getEmployeeById(id);
        setEmployee({
          firstName: data.firstName ?? "",
          lastName: data.lastName ?? "",
          email: data.email ?? "",
        });
      } catch (e) {
        console.error("Failed to load employee:", e);
        setMessage("❌ Failed to load employee.");
      } finally {
        setLoading(false);
      }
    };
    if (id) load();
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setEmployee((prev) => ({ ...prev, [name]: value }));
    setErrors((prev) => ({ ...prev, [name]: undefined }));
    setMessage("");
  };

  const validate = () => {
    const e = {};
    if (!employee.firstName.trim()) e.firstName = "First name is required";
    if (!employee.lastName.trim()) e.lastName = "Last name is required";
    if (!employee.email.trim()) e.email = "Email is required";
    else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(employee.email))
      e.email = "Enter a valid email";
    setErrors(e);
    return Object.keys(e).length === 0;
  };

  const handleSubmit = async (ev) => {
    ev.preventDefault();
    if (!validate()) return;
    try {
      if (isEdit) {
        await updateEmployee(id, employee);
        setMessage("✅ Employee updated.");
      } else {
        await createEmployee(employee);
        setMessage("✅ Employee created.");
        setEmployee({ firstName: "", lastName: "", email: "" });
      }
      navigate("/employees");
    } catch (err) {
      console.error("Save failed:", err);
      setMessage("❌ Failed to save employee.");
    }
  };

  if (loading) {
    return (
      <div className="auth-wrap">
        <div className="auth-card">
          <h2 className="auth-title">{isEdit ? "Edit Employee" : "Add Employee"}</h2>
          <p className="auth-subtitle">Loading…</p>
        </div>
      </div>
    );
  }

  return (
    <div className="auth-wrap">
      <div className="auth-card">
        <h2 className="auth-title">{isEdit ? "Edit Employee" : "Add Employee"}</h2>
        <p className="auth-subtitle">
          {isEdit ? "Update the employee details below." : "Fill in the details to create a new employee."}
        </p>

        <form onSubmit={handleSubmit} className="auth-form">
          <label className="auth-label">
            First name
            <input
              name="firstName"
              className={`auth-input ${errors.firstName ? "error" : ""}`}
              placeholder="e.g. Jane"
              value={employee.firstName}
              onChange={handleChange}
            />
            {errors.firstName && <span className="auth-error">{errors.firstName}</span>}
          </label>

          <label className="auth-label">
            Last name
            <input
              name="lastName"
              className={`auth-input ${errors.lastName ? "error" : ""}`}
              placeholder="e.g. Doe"
              value={employee.lastName}
              onChange={handleChange}
            />
            {errors.lastName && <span className="auth-error">{errors.lastName}</span>}
          </label>

          <label className="auth-label">
            Email
            <input
              type="email"
              name="email"
              className={`auth-input ${errors.email ? "error" : ""}`}
              placeholder="jane.doe@example.com"
              value={employee.email}
              onChange={handleChange}
              autoComplete="email"
            />
            {errors.email && <span className="auth-error">{errors.email}</span>}
          </label>

          <div className="auth-actions">
            <button type="submit" className="auth-button">
              {isEdit ? "Update" : "Save"}
            </button>
            <button
              type="button"
              className="auth-button ghost"
              onClick={() => navigate("/employees")}
            >
              Cancel
            </button>
          </div>
        </form>

        {message && (
          <div className="auth-message" role="alert" aria-live="polite">
            {message}
          </div>
        )}
      </div>
    </div>
  );
};

export default EmployeeComponent;
