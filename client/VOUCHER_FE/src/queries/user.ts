import http from "configs/http";
export const editUser = (obj: any) => {
  return http.put(`/user/api/edit`, obj);
};
export const userUpload = (obj: any) => {
  return http.post(`/user/api/upload`, obj);
};
