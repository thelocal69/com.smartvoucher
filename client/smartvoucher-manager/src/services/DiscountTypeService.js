import Http from '../configs/Http';

export const getAllDiscountType = (page, limit, sortBy, sortField) => {
    return Http.get(`/discount/api/getAll?page=${page}&limit=${limit}&sortBy=${sortBy}&sortField=${sortField}`);
}

export const searchAllByName = (name) => {
    return Http.get(`/discount/api/search?name=${name}`);
}

export const getAllDiscountName = () => {
    return Http.get(`/discount/api/getName`);
}

export const insertDiscountType = (obj) => {
    return Http.post(`/discount/api/insert`, obj);
}

export const editDiscountType = (obj) => {
    return Http.put(`/discount/api/${obj.id}`, obj);
}

export const deleteDiscountType = (obj) => {
    return Http.delete(`/discount/api/${obj.id}`, {data: obj});
}