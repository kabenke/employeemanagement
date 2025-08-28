import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080/api",
  headers: {
    "Content-Type": "application/json",
  },
});

export const request = async (url, method = "GET", data = null, config = {}) => {
  try {
    const token = localStorage.getItem("token");

    const response = await api({
      url,
      method,
      data,
      headers: {
        ...(config.headers || {}),
        ...(token ? { Authorization: `Bearer ${token}` } : {}),
      },
      ...config,
    });

    return response.data;
  } catch (error) {
    console.error("API error:", error);
    throw error;
  }
};
