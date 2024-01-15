import Http from '../configs/Http';

export const getAllWarehouseByLabel = (slug, page, limit) =>{
    return Http.get(`/warehouse/api/get/label?slug=${slug}&page=${page}&limit=${limit}`);
}

export const getAllWarehouse = () =>{
    return Http.get(`/warehouse/api/all`);
}

export const getWarehouseById = (id) =>{
    return Http.get(`/warehouse/api/${id}`);
}