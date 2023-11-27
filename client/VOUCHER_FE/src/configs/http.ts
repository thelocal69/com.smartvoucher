import axios from "axios";
import { toast } from "react-toastify";

const http = axios.create({
  baseURL: `http://localhost:8082`,
});
http.interceptors.request.use(
  function (config) {
    let token = localStorage.getItem("token") || "";
    if (token) config.headers.Authorization = `Bearer ${token}`;
    return config;
  },
  function (error) {
    return Promise.reject(error);
  }
);

// Add a response interceptor
http.interceptors.response.use(
  function (response) {
    if (response.data) {
      return response.data;
    }
    return response;
  },
  function (error) {
    toast.error(error.response.data.error);
    return Promise.reject(error);
  }
);
export default http;
