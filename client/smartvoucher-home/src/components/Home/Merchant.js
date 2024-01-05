import React from "react";
import { getAllMerchant } from "../../services/MerchantServices";
import { toast } from "react-toastify";
import "../Home/Merchant.scss";
import { Container } from "react-bootstrap";

const Merchant = () => {
  const [listMerchantLogo, setListMerchantLogo] = React.useState([]);

  React.useEffect(() => {
    getMerchant();
  }, []);

  const getMerchant = async () => {
    await getAllMerchant()
      .then((rs) => {
        if (rs) {
          const logo = rs.data?.map((item) => item.logoUrl);
          setListMerchantLogo(logo);
        }
      })
      .catch((err) => toast.error(err.message));
  };

  return (
    <>
      <Container>
        <div className="p-3">
          <h4>Thuơng hiệu mới</h4>
        </div>
        <div className="d-flex justify-content-between flex-wrap">
          <div className="p-3">
            <img
              src={listMerchantLogo[0]}
              alt=""
              style={{
                width: 8 + "rem",
                borderRadius: 10
              }}
            />
          </div>
          <div className="p-3">
            <img
              src={listMerchantLogo[1]}
              alt=""
              style={{
                width: 8 + "rem",
                borderRadius: 10
              }}
            />
          </div>
          <div className="p-3">
            <img
              src={listMerchantLogo[2]}
              alt=""
              style={{
                width: 8 + "rem",
                borderRadius: 10
              }}
            />
          </div>
          <div className="p-3">
            <img
              src={listMerchantLogo[3]}
              alt=""
              style={{
                width: 8 + "rem",
                borderRadius: 10
              }}
            />
          </div>
          <div className="p-3">
            <img
              src={listMerchantLogo[4]}
              alt=""
              style={{
                width: 8 + "rem",
                borderRadius: 10
              }}
            />
          </div>
          <div className="p-3">
            <img
              src={listMerchantLogo[5]}
              alt=""
              style={{
                width: 8 + "rem",
                borderRadius: 10
              }}
            />
          </div>
          <div className="p-3">
            <img
              src={listMerchantLogo[6]}
              alt=""
              style={{
                width: 8 + "rem",
                borderRadius: 10
              }}
            />
          </div>
          <div className="p-3">
            <img
              src={listMerchantLogo[7]}
              alt=""
              style={{
                width: 8 + "rem",
                borderRadius: 10
              }}
            />
          </div>
          <div className="p-3">
            <img
              src={listMerchantLogo[8]}
              alt=""
              style={{
                width: 8 + "rem",
                borderRadius: 10
              }}
            />
          </div>
          <div className="p-3">
            <img
              src={listMerchantLogo[9]}
              alt=""
              style={{
                width: 8 + "rem",
                borderRadius: 10
              }}
            />
          </div>
          <div className="p-3">
            <img
              src={listMerchantLogo[10]}
              alt=""
              style={{
                width: 8 + "rem",
                borderRadius: 10
              }}
            />
          </div>
          <div className="p-3">
            <img
              src={listMerchantLogo[11]}
              alt=""
              style={{
                width: 8 + "rem",
                borderRadius: 10
              }}
            />
          </div>
          <div className="p-3">
            <img
              src={listMerchantLogo[12]}
              alt=""
              style={{
                width: 8 + "rem",
                borderRadius: 10
              }}
            />
          </div>
          <div className="p-3">
            <img
              src={listMerchantLogo[13]}
              alt=""
              style={{
                width: 8 + "rem",
                borderRadius: 10
              }}
            />
          </div>
          {/* <div className="p-3">
            <img
              src={listMerchantLogo[14]}
              alt=""
              style={{
                width: 8 + "rem",
                borderRadius: 10
              }}
            />
          </div>
          <div className="p-3">
            <img
              src={listMerchantLogo[15]}
              alt=""
              style={{
                width: 8 + "rem",
                borderRadius: 10
              }}
            />
          </div> */}
        </div>
      </Container>
    </>
  );
};

export default Merchant;
