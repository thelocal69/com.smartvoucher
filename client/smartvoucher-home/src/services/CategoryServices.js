import Http from "../configs/Http";

export const getAllCategory = () => {
    return Http.get(`/category/api/all`);
}