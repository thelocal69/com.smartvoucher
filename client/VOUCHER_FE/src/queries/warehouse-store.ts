import http from "configs/http";
export const getAllWarehouseStore = () => {
  return http.get(`/warehouse_store`);
};
