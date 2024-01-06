import Http from "../configs/Http";

export const loginUser = (obj) =>{
    return Http.post(`/account/api/signin`, obj);
}

export const logoutUser = (refreshToken) =>{
    return Http.post(`/account/api/logout`,null,  {
        headers: {
          Authorization: "Bearer " + refreshToken,
        },
      });
}

export const registerUser = (obj) =>{
    return Http.post(`/account/api/signup`, obj);
}