import http from "configs/http";
export const getAllCategory = () => {
  return http.get("/category/api/all");
};
