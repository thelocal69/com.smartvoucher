import Http from '../configs/Http';

export const getAllWarehouse = (page, limit, sortBy, sortField) => {
    return Http.get(`/warehouse/api/getAll?page=${page}&limit=${limit}&sortBy=${sortBy}&sortField=${sortField}`);
}

export const searchAllByName = (name) => {
    return Http.get(`/warehouse/api/search?name=${name}`);
}

export const insertWarehouse = (obj) => {
    return Http.post(`/warehouse/api/insert`, obj);
}

export const uploadWarehouseBanner = (obj) => {
    return Http.post(`/warehouse/api/upload/banner`, obj);
}

export const uploadWarehouseThumbnail = (obj) => {
    return Http.post(`/warehouse/api/upload/thumbnail`, obj);
}

export const uploadLocalWarehouseBanner = (obj) => {
    return Http.post(`/warehouse/api/local_banner`, obj);
}

export const uploadLocalWarehouseThumbnail = (obj) => {
    return Http.post(`/warehouse/api/local_thumbnail`, obj);
}

export const editWarehouse = (obj) => {
    return Http.put(`/warehouse/api/${obj.id}`, obj);
}

export const deleteWarehouse = (obj) => {
    return Http.delete(`/warehouse/api/${obj.id}`, {data: obj});
}