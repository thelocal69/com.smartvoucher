import Http from "../configs/Http"


export const getAllMerchant = (page, limit, sortBy, sortField) =>{
    return Http.get(`/merchant/api/getAll?page=${page}&limit=${limit}&sortBy=${sortBy}&sortField=${sortField}`);
}

export const searchMerchantByName = (name) => {
    return Http.get(`/merchant/api/search?name=${name}`);
}

export const getAllName = () => {
    return Http.get(`/merchant/api/getName`);
}

export const insertMerchant = (obj) => {
    return Http.post(`/merchant/api/insert`, obj);
}

export const uploadImage = (obj) => {
    return Http.post(`/merchant/api/upload`, obj);
}

export const uploadLocalImage = (obj) => {
    return Http.post(`/merchant/api/local_upload`, obj);
}

export const updateMerchant = (obj) => {
    return Http.put(`/merchant/api/${obj?.id}`, obj);
}

export const deleteMerchant = (obj) => {
    return Http.delete(`/merchant/api/${obj?.id}`, {data: obj});
}