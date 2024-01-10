import Http from "../configs/Http";

export const getAllTicket = (id, page, limit) =>{
    return Http.get(`/ticket/api/get/all?id=${id}&page=${page}&limit=${limit}`);
}