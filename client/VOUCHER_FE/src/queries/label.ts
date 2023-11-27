import http from "configs/http";
export const getLabel = () => {
  return http.get(`/label/api/all`);
};
