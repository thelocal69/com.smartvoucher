import Http from "../configs/Http"


export const getAllChain = (page, limit, sortBy, sortField) => {
    return Http.get(`/chain/api/getAll?page=${page}&limit=${limit}&sortBy=${sortBy}&sortField=${sortField}`);
}