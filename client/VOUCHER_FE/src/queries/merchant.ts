import http from "configs/http";
export const getMerchants = () => {
  return http.get(`/merchant`);
};
