import http from "../configs/http";
export const getAllChain = () => {
  return http.get(`/chain`);
};
export const addChain = (obj) => {
  return http.post(`/chain/api/insert`, obj);
};

export const editChain = (obj) => {
  return http.put(`/chain/api/${obj?.id}`, obj);
};

export const deleteChain = (id, obj) => {
  return http.delete(`/chain/api/${id}`, { data: obj });
};
