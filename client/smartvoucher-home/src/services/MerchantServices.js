import Http from "../configs/Http";

export const getAllMerchant = () =>{
    return Http.get(`/merchant`);
}