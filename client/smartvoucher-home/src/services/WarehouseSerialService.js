import Http from "../configs/Http";

export const getAllWarehouseSerial = (id, page, limit) =>{
    return Http.get(`/warehouse_serial/api/all?id=${id}&page=${page}&limit=${limit}`);
}