import http from "../configs/http";

export const getAllCategory = () => {
  return http.get(`/category/api/all`);
};
export const addcategory = (obj) => {
  return http.post(`/category/api/insert`, obj);
};

export const editcategory = (obj) => {
  return http.put(`/category/api/${obj?.id}`, obj);
};

export const deletecategory = (id, obj) => {
  return http.delete(`/category/api/${id}`, { data: obj });
};
