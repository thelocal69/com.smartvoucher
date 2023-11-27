import http from "../configs/http";

export const signIn = async (payload) => {
  return http.post("/account/api/signin", null, {
    params: payload,
  });
};
export const signOut = async (refreshToken) => {
  return http.post("/account/api/logout", null, {
    headers: {
      Authorization: "Bearer " + refreshToken,
    },
  });
};
