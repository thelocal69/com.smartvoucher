import Http from "../configs/Http"


export const loginAdmin = (obj) => {
    return Http.post(`/account/api/signinAdmin`, obj);
}

export const logOutAsync = (refreshToken) => {
    return Http.post(`/account/api/logout`, null, {
      headers: {
        Authorization: "Bearer " + refreshToken,
      },
    });
  };