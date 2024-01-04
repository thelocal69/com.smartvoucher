import axios from 'axios';
import {toast} from 'react-toastify';

const Http = axios.create({
    baseURL: `http://localhost:8082`
});

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