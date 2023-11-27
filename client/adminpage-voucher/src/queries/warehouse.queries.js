export const getAllWarehouse = () => {
  var requestOptions = {
    method: "GET",
  };
  return fetch("http://localhost:8082/warehouse/api/all", requestOptions)
    .then((response) => response.json())
    .then((result) => result.data)
    .catch((error) => console.log("error", error));
};
export const addWarehouse = (obj) => {
  var requestOptions = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(obj),
  };
  return fetch("http://localhost:8082/warehouse/api/insert", requestOptions)
    .then((response) => response.json())
    .then((result) => result)
    .catch((error) => console.log("error", error));
};

export const editwarehouse = (obj) => {
  var requestOptions = {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(obj),
  };
  return fetch(
    `${process.env.REACT_APP_VOUCHER_API}/warehouse/api/${obj?.id}`,
    requestOptions
  )
    .then((response) => response.json())
    .then((result) => result)
    .catch((error) => console.log("error", error));
};

export const deletewarehouse = (id, obj) => {
  var requestOptions = {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(obj),
  };
  return fetch(
    `${process.env.REACT_APP_VOUCHER_API}/warehouse/api/${id}`,
    requestOptions
  )
    .then((response) => response.json())
    .then((result) => result)
    .catch((error) => console.log("error", error));
};
