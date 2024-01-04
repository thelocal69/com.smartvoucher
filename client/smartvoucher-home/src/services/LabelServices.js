import Http from "../configs/Http";

export const getAllLabelName = () =>{
    return Http.get(`/label/api/all`);
}