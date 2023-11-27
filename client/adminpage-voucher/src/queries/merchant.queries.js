import http from "../configs/http";

export const getAllMerchant = () => {
  return http.get(`/merchant`);
};

export const addMerchant = (obj) => {
  return http.post(`/merchant/api/insert`, obj);
};

export const editMerchant = (obj) => {
  return http.put(`/merchant/api/${obj?.id}`, obj);
};

export const deleteMerchant = (obj) => {
  return http.delete(`/merchant/api/${obj.id}`, { data: obj });
};
