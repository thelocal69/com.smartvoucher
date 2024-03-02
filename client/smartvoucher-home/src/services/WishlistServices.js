import Http from "../configs/Http";

export const getAllWishList = (idUser, page, limit) =>{
    return Http.get(`/wishlist/api/get_all?idUser=${idUser}&page=${page}&limit=${limit}`);
}

export const getWishListEntity = (idUser, idWarehouse) =>{
    return Http.get(`/wishlist/api/get?idUser=${idUser}&idWarehouse=${idWarehouse}`);
}

export const insertWishList = (obj) =>{
    return Http.post(`/wishlist/api/insert`, obj);
}

export const deleteWishList = (obj) => {
    return Http.delete(`/wishlist/api/delete/${obj?.id}`, {data: obj});
}