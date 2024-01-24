import axios from "axios";
import { toast } from "react-toastify";
import Store from "../Redux/Store";

const Http = axios.create({
  baseURL: `http://localhost:8082`,
});

Http.interceptors.request.use(
  function (request) {
    const token = Store.getState().auth.accessToken;
    if (token) {
      request.headers.Authorization = `Bearer ${token}`;
    }
    return request;
  },
  function (error) {
    return Promise.reject(error);
  }
);

Http.defaults.withCredentials = true;

Http.interceptors.response.use(
  function (response) {
    return response.data;
  },
  function (error) {
    console.log(error.response.data.error);
    return Promise.reject(error);
  }
);

export default Http;
