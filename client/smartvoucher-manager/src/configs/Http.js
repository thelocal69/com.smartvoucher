import axios from "axios";
import { toast } from 'react-toastify';

const Http = axios.create({
    baseURL: `http://localhost:8082`
});

//Http.defaults.withCredentials = true;

Http.interceptors.response.use(
    function(response) {
        return response.data;
    },
    function(error){
        if (error.response.status === 403) {
            toast.error("Please login by admin !");
        } 
        return Promise.reject(error);
    }
);

export default Http