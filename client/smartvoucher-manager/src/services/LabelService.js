import Http from "../configs/Http";


export const getAllLabel = (page, limit, sortBy, sortField) => {
    return Http.get(`/label/api/getAll?page=${page}&limit=${limit}&sortBy=${sortBy}&sortField=${sortField}`);
}

export const getAllLabelName = () => {
    return Http.get(`/label/api/getName`);
}