import axios from "axios";
import { toast } from 'react-toastify';
import Store from "../redux/Store";

const Http = axios.create({
    baseURL: `http://localhost:8082`
});

Http.interceptors.request.use(
    function (config) {
        const token = Store.getState().auth.accessToken;
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    function (error) {
        return Promise.reject(error);
    }
);

Http.defaults.withCredentials = true;

Http.interceptors.response.use(
    function(response) {
        return response.data;
    },
    function(error){
        toast.error(error.response.data.error);
        return Promise.reject(error);
    }
);

export default Http