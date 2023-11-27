import http from "configs/http";
export const editUser = (obj: any) => {
  return http.put(`/user/api/edit`, obj);
};
export const userUpload = (obj: any) => {
  return http.post(`/user/api/upload`, obj);
};

export const editPassword = (obj: any) => {
  return http.put(`/user/api/change_pwd`, obj);
};
