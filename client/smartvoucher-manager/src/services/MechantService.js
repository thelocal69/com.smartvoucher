import Http from "../configs/Http"


export const getAllMerchant = (page, limit, sortBy, sortField) =>{
    return Http.get(`/merchant/api/getAll?page=${page}&limit=${limit}&sortBy=${sortBy}&sortField=${sortField}`);
}