import http from "configs/http";
export interface IOrder {
  status: Number;
  idUserDTO: {
    id: String;
  };
  quantity: Number;
  idWarehouseDTO: {
    id: String;
  };
}
export const addOrderAsync = (obj: IOrder) => {
  return http.post("/order/api/add-order", obj);
};

export const getOrderByUser = (id: Number) => {
  return http.get(`/order/api/get_all_oder_user/${id}`);
};
