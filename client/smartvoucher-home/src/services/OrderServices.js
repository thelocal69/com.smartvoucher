import Http from "../configs/Http";

export const getAllOrder = (page, limit, sortBy, sortField) => {
  return Http.get(
    `/order/api/get/all?page=${page}&limit=${limit}&sortBy=${sortBy}&sortField=${sortField}`
  );
};

export const getOrder = (id) => {
  return Http.get(`/order/api/get/${id}`);
};

export const addOrder = (obj) => {
  return Http.post(`/order/api/add-order`, obj);
};
