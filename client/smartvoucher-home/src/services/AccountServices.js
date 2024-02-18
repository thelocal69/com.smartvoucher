import Http from "../configs/Http";

export const loginUser = (obj) => {
  return Http.post(`/account/api/signin`, obj);
};

export const logoutUser = (refreshToken) => {
  return Http.post(`/account/api/logout`, null, {
    headers: {
      Authorization: "Bearer " + refreshToken,
    },
  });
};

export const registerUser = (obj) => {
  return Http.post(`/account/api/signup`, obj);
};

export const registerVerify = (obj) => {
  return Http.put(`/account/api/verify_email`, obj);
};

export const forgotPassword = (email) => {
  return Http.post(`/account/api/forgot_password?email=${email}`);
};

export const resetPassword = (obj) => {
  return Http.put(`/account/api/set_password`, obj);
};

export const resendActiveAccount = (email) => {
  return Http.post(`/account/api/resend_token?email=${email}`);
};

export const oauth2GoogleLogin = (obj) => {
  return Http.post(`/account/api/oauth2/signin`, obj);
};

export const getAccessToken = () =>{
  return Http.post(`/account/api/refresh_token`);
}
