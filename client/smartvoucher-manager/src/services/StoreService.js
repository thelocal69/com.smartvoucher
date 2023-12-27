import Http from "../configs/Http"


export const getAllStore = (page, limit, sortBy, sortField) => {
    return Http.get(`/store/api/getAll?page=${page}&limit=${limit}&sortBy=${sortBy}&sortField=${sortField}`);
}

export const searchAllByName = (name) => {
    return Http.get(`/store/api/search?name=${name}`);
}

export const insertStore = (obj) => {
    return Http.post(`/store/api/insert`, obj);
}

export const editStore = (obj) => {
    return Http.put(`/store/api/${obj.id}`, obj);
}

export const deleteStore = (obj) => {
    return Http.delete(`/store/api/${obj.id}`, {data: obj});
}