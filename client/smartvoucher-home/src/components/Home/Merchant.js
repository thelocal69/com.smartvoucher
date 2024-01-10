import React from "react";
import { getAllMerchant } from "../../services/MerchantServices";
import { toast } from "react-toastify";
import "../Home/Merchant.scss";
import { Container } from "react-bootstrap";
import Slider from "react-slick";

const Merchant = () => {
  const [listMerchantLogo, setListMerchantLogo] = React.useState([]);

  const settings = {
    slidesToShow: 6,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 1000,
    arrows: false,
    dots: false,
    pauseOnHover: false,
    responsive: [
      {
        breakpoint: 768,
        settings: {
          slidesToShow: 4,
        },
      },
      {
        breakpoint: 520,
        settings: {
          slidesToShow: 2,
        },
      },
    ],
  };

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
        <div className="p-3">
          <Slider {...settings}>
            {listMerchantLogo.map((item, key) => {
              return (
                <div className="">
                  <img
                    src={item}
                    alt=""
                    style={{
                      width: 8 + "rem",
                      borderRadius: 10,
                    }}
                  />
                </div>
              );
            })}
          </Slider>
        </div>
      </Container>
    </>
  );
};

export default Merchant;
