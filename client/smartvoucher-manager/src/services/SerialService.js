import Http from '../configs/Http';

export const getAllSerial = (page, limit, sortBy, sortField) => {
    return Http.get(`/serial/api/getAll?page=${page}&limit=${limit}&sortBy=${sortBy}&sortField=${sortField}`);
}