import Http from "../configs/Http";

export const getIdStore = (idWarehouse) =>{
    return Http.get(`/warehouse_store/api/getId_Store?idWarehouse=${idWarehouse}`);
}

export const getAllWarehouseStore = () =>{
    return Http.get(`/warehouse_store`);
}