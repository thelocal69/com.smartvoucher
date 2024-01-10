import Http from "../configs/Http";

export const getAllOrder = (page, limit, sortBy, sortField) => {
  return Http.get(
    `/order/api/get/all?page=${page}&limit=${limit}&sortBy=${sortBy}&sortField=${sortField}`
  );
};
