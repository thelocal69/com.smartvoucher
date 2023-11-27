import http from "configs/http";
export const getAllDiscountType = () => {
  return http.get(`/discount`);
};
