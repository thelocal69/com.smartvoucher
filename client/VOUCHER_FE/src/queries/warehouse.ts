import http from "configs/http";
export const getWarehouseByLabel = (id: number) => {
  return http.get(`/warehouse/by-label-id/${id}`);
};

export const getAllWarehouse = () => {
  return http.get(`/warehouse/api/all`);
};

export const getWarehouseDetail = (id: any) => {
  return http.get(`/warehouse/api/${id}`);
};
export const getWarehouseByCategoryId = (id: any) => {
  return http.get(`/warehouse/CategoryId/${id}`);
};
