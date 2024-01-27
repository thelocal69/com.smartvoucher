import Http from "../configs/Http";

export const getAllTicket = (id, page, limit) =>{
    return Http.get(`/ticket/api/get/all?id=${id}&page=${page}&limit=${limit}`);
}

export const buyTicket = (payload) =>{
    return Http.post(`/ticket/api/buy-ticket`, payload);
}

export const userUseVoucher = (serialCode) =>{
    return Http.put(`/ticket/api/use_ticket?serial=${serialCode}`);
}