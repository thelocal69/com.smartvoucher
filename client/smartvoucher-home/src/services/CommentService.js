import Http from "../configs/Http";

export const getAllComment = (idWarehouse, page, limit, sortBy, sortField) => {
  return Http.get(
    `/comment/api/getAll?idWarehouse=${idWarehouse}&page=${page}&limit=${limit}&sortBy=${sortBy}&sortField=${sortField}`
  );
};

export const insertComment = (obj) => {
  return Http.post(`/comment/api/insert`, obj);
};
