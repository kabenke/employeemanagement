import axios from "axios";

export const API_HOST = "http://localhost:8080";

export function setToken(token) {
  if (token) localStorage.setItem("token", token);
  else localStorage.removeItem("token");
}

export function getToken() {
  return localStorage.getItem("token");
}

export async function login(username, password) {
  const { data } = await axios.post(`${API_HOST}/auth/login`, { username, password });
  setToken(data.token);
  return data.token;
}

export function logout() {
  setToken(null);
}

export function isAuthed() {
  return !!getToken();
}
