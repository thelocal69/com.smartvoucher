export const getAllSerial = () => {
  var requestOptions = {
    method: "GET",
  };
  return fetch(`${process.env.REACT_APP_VOUCHER_API}/serial/api/list-serial`, requestOptions)
    .then((response) => response.json())
    .then((result) => result.data)
    .catch((error) => console.log("error", error));
};

export const addSerial = (obj) => {
  var requestOptions = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(obj),
  };
  return fetch(
    `${process.env.REACT_APP_VOUCHER_API}/serial`,
    requestOptions
  )
    .then((response) => response.json())
    .then((result) => result)
    .catch((error) => console.log("error", error));
};

export const editSerial = (obj) => {
  var requestOptions = {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(obj),
  };
  return fetch(
    `${process.env.REACT_APP_VOUCHER_API}/serial`,
    requestOptions
  )
    .then((response) => response.json())
    .then((result) => result)
    .catch((error) => console.log("error", error));
};

export const deleteSerial = (id) => {
  var requestOptions = {
    method: "DELETE",
    redirect: "follow",
  };
  return fetch(
    `${process.env.REACT_APP_VOUCHER_API}/serial?id=${id}`,
    requestOptions
  )
    .then((response) => response.json())
    .then((result) => result)
    .catch((error) => console.log("error", error));
};
