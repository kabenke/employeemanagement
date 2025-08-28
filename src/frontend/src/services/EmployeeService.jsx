import { request } from "../http/http";

export const listEmployees = () => request("/employees", "GET");

export const createEmployee = (employee) => request("/employees", "POST", employee);

export const getEmployeeById = (id) => request(`/employees/${id}`, "GET");

export const updateEmployee = (id, employee) =>
  request(`/employees/${encodeURIComponent(id)}`, "PUT", employee);

export const deleteEmployee = (id) => request(`/employees/${id}`, "DELETE");
