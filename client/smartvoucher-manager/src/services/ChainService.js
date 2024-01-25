import Http from "../configs/Http"


export const getAllChain = (page, limit, sortBy, sortField) => {
    return Http.get(`/chain/api/getAll?page=${page}&limit=${limit}&sortBy=${sortBy}&sortField=${sortField}`);
}

export const searchAllChainByName = (name) => {
    return Http.get(`/chain/api/search?name=${name}`);
}

export const getAllChainName = () => {
    return Http.get(`/chain/api/getName`);
}

export const insertChain = (obj) => {
    return Http.post(`/chain/api/insert`, obj);
}

export const uploadLogoChain = (obj) => {
    return Http.post(`/chain/api/upload`, obj);
}

export const uploadLocalChain = (obj) => {
    return Http.post(`/chain/api/local_upload`, obj);
}

export const editChain = (obj) => {
    return Http.put(`/chain/api/${obj.id}`, obj);
}

export const deleteChain = (obj) => {
    return Http.delete(`/chain/api/${obj.id}`, {data: obj});
}