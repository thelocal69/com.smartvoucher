import Http from "../configs/Http"


export const getUserInforLogin = () => {
    return Http.get(`/user/api/profile`);
}

export const editUserInfor = (obj) => {
    return Http.put(`/user/api/editAdmin`, obj);
}

export const editAvatarUser = (obj) => {
    return Http.post(`/user/api/uploadAdmin`, obj);
}