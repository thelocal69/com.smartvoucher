import Http from "../configs/Http";

export const getAllReply = () =>{
    return Http.get(`/reply/api/getAll`);
}

export const insertReply = (obj) =>{
    return Http.post(`/reply/api/insert`, obj);
}