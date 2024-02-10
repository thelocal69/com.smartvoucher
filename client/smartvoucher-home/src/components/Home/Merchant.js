import React from "react";
import { getAllMerchant } from "../../services/MerchantServices";
import "../Home/Merchant.scss";
import { Col, Container, Row } from "react-bootstrap";
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
      .catch((err) => console.log(err.message));
  };

  return (
    <>
      <Container>
        <Row xs={1} md={1}>
          <Col>
            <div className="p-3 ct">
              <h4>Thuơng hiệu</h4>
            </div>
          </Col>
          <Col>
            <div className="p-3">
              <Slider {...settings}>
                {listMerchantLogo.map((item, key) => {
                  return (
                    <div className="size-img">
                      <img
                        src={item}
                        alt=""
                      />
                    </div>
                  );
                })}
              </Slider>
            </div>
          </Col>
        </Row>
      </Container>
    </>
  );
};

export default Merchant;
