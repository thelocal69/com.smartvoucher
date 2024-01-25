import Http from "../configs/Http"


export const getAllCategory = (page, limit, sortBy, sortField) =>{
    return Http.get(`/category/api/getAll?page=${page}&limit=${limit}&sortBy=${sortBy}&sortField=${sortField}`);
}

export const searchAllByName = (name) => {
    return Http.get(`/category/api/search?name=${name}`);
}

export const getAllCategoryName = () => {
    return Http.get(`/category/api/getName`);
}

export const insertCategory = (obj) => {
    return Http.post(`/category/api/insert`, obj);
}

export const uploadBanner = (obj) => {
    return Http.post(`/category/api/upload`, obj);
}

export const uploadLocalBanner = (obj) => {
    return Http.post(`/category/api/local_upload`, obj);
}

export const editCategory = (obj) => {
    return Http.put(`/category/api/${obj.id}`, obj);
}

export const deleteCategory = (obj) => {
    return Http.delete(`/category/api/${obj.id}`, {data: obj});
}