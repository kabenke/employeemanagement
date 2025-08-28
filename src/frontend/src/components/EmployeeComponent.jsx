import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { createEmployee, getEmployeeById, updateEmployee } from "../services/EmployeeService";

const EmployeeComponent = () => {
  const { id } = useParams();           
  const navigate = useNavigate();

  const [employee, setEmployee] = useState({
    firstName: "",
    lastName: "",
    email: "",
  });
  const [loading, setLoading] = useState(!!id);

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
        alert("Failed to load employee.");
        navigate("/employees");
      } finally {
        setLoading(false);
      }
    };
    if (id) load();
  }, [id, navigate]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setEmployee((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (id) {
        await updateEmployee(id, employee);
      } else {
        await createEmployee(employee);
      }
      navigate("/employees");
    } catch (err) {
      console.error("Save failed:", err);
      alert("Failed to save employee.");
    }
  };

  if (loading) return <div className="container mt-4">Loading...</div>;

  return (
    <div className="container mt-4">
      <h2 className="text-center">{id ? "Edit Employee" : "Add Employee"}</h2>
      <form onSubmit={handleSubmit} className="mt-4">
        <div className="mb-3">
          <label className="form-label">First Name</label>
          <input
            type="text"
            name="firstName"
            className="form-control"
            value={employee.firstName}
            onChange={handleChange}
            placeholder="Enter first name"
            required
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Last Name</label>
          <input
            type="text"
            name="lastName"
            className="form-control"
            value={employee.lastName}
            onChange={handleChange}
            placeholder="Enter last name"
            required
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Email ID</label>
          <input
            type="email"
            name="email"
            className="form-control"
            value={employee.email}
            onChange={handleChange}
            placeholder="Enter email address"
            required
          />
        </div>

        <button type="submit" className="btn btn-primary">
          {id ? "Update" : "Save"}
        </button>
        <button
          type="button"
          className="btn btn-secondary ms-2"
          onClick={() => navigate("/employees")}
        >
          Cancel
        </button>
      </form>
    </div>
  );
};

export default EmployeeComponent;
