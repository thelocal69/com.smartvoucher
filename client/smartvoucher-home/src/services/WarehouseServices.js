import Http from '../configs/Http';

export const searchAllByWarehouseName = (name) => {
    return Http.get(`/warehouse/api/search_name?name=${name}`);
}

export const getAllWarehouseByLabel = (slug, page, limit) =>{
    return Http.get(`/warehouse/api/get/label?slug=${slug}&page=${page}&limit=${limit}`);
}

export const getAllWarehouseByCategory = (name, page, limit) =>{
    return Http.get(`/warehouse/api/category_name?name=${name}&page=${page}&limit=${limit}`);
}

export const getAllWarehouse = () =>{
    return Http.get(`/warehouse/api/all`);
}

export const getWarehouseById = (id) =>{
    return Http.get(`/warehouse/api/${id}`);
}