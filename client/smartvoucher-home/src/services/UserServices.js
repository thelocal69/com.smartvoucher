import Http from "../configs/Http";

export const  getUserInfor = () =>{
    return Http.get(`/user/api/infor`);
}

export const  updateImage = (obj) =>{
    return Http.post(`/user/api/upload`, obj);
}

export const  editProfile = (obj) =>{
    return Http.put(`/user/api/edit`, obj);
}