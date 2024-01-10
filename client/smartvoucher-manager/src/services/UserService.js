import Http from '../configs/Http';

export const getAllUser = (page, limit, sortBy, sortField) =>{
    return Http.get(`/user/api/getAll?page=${page}&limit=${limit}&sortBy=${sortBy}&sortField=${sortField}`);
}

export const searchAllByUserEmail = (email) => {
    return Http.get(`/user/api/search?email=${email}`);
}

export const blockUser = (obj) => {
    return Http.put(`/user/api/block/${obj.id}`, obj);
}

