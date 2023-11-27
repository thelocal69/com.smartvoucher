import http from "configs/http";
export interface IBodyBuyTicket {
  idWarehouseDTO: {
    id: Number;
  };
  idCategoryDTO: { id: Number };
  idOrderDTO: { id: Number };
  status: Number;
  discountType: String;
  discountAmount: Number;
  idStoreDTO: {
    id: Number;
  };
  idUserDTO: {
    id: Number;
  };
}
export interface IBuyTicket {
  obj: IBodyBuyTicket;
  email: String;
  numberOfSerial: Number;
}
export const buyTicket = (payload: IBuyTicket) => {
  return http.post(
    `/ticket/api/buy-ticket?userEmail=${payload.email}&numberOfSerial=${payload.numberOfSerial}`,
    payload.obj
  );
};
export const detailTicket = (payload: any) => {
  return http.get(`/ticket/api/ticket_detail`, { data: payload });
};
export const onUseTicket = (code: any) => {
  return http.put(`/ticket/api/use-ticket?serialCode=${code}`);
};
