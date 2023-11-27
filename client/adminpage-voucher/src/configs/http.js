import axios from "axios";
import { toast } from "react-toastify";
const http = axios.create({
  baseURL: `http://localhost:8082`,
});
http.interceptors.request.use(
  function (config) {
    let token = localStorage.getItem("token");
    console.log(token);
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
    return response.data;
  },
  function (err) {
    console.log(err);
    if (err.response.status === 403) {
      toast.error("Please sign in as admin role");
    } else if (err.response.status === 500) {
      toast.error("Token Expired");
    } else {
      toast.error(err.response.data.error);
    }
    return Promise.reject(err);
  }
);
export default http;
