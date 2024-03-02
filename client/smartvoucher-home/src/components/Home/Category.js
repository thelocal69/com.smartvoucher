import React from "react";
import { Carousel, Container, Image, Row, Col } from "react-bootstrap";
import { getAllCategory } from "../../services/CategoryServices";
import { toast } from "react-toastify";
import "../Home/Category.scss";

const Category = () => {
  const [listCategory, setListCategory] = React.useState([]);

  React.useEffect(() => {
    getCategory();
  }, []);

  const getCategory = async () => {
    await getAllCategory()
      .then((rs) => {
        if (rs) {
          setListCategory(rs.data);
        }
      })
      .catch((err) => toast.error(err.message));
  };

  return (
    <>
      <Container>
        <div className="gofolina">
          <Row xs={1} md='auto' className="justify-content-md-between">
            <Col>
              <div>
                <Carousel
                >
                  {listCategory.map((item, key) => {
                    return (
                      <Carousel.Item key={key}>
                        <img
                          src={item.bannerUrl}
                          alt={item.name}
                        />
                        <Carousel.Caption>
                          <h3 style={{
                            background: "rgba(0, 0, 0)"
                          }}>{item.name}</h3>
                        </Carousel.Caption>
                      </Carousel.Item>
                    );
                  })}
                </Carousel>
              </div>
            </Col>
            <Col>
              <Row xs={2} md='auto'>
                <Col md='auto'>
                  <div className="pb-sm-3 lp">
                    <Image
                      src="https://giadinh.mediacdn.vn/296230595582509056/2022/12/21/an-gi-102-16715878746102005998080.jpg"
                    />
                  </div>
                  <div className="pt-sm-3 lp">
                    <Image
                      src="https://giadinh.mediacdn.vn/296230595582509056/2022/12/21/an-gi-102-16715878746102005998080.jpg"
                    />
                  </div>
                </Col>
                <Col md='auto'>
                  <div className="pb-sm-3 lp">
                    <Image
                      src="https://file.hstatic.net/200000472237/file/tong-quan-flash-sale_e1c32736c1ce4449b845bc1fb8dc98aa_grande.png"
                    />
                  </div>
                  <div className="pt-sm-3 lp">
                    <Image
                      src="https://file.hstatic.net/200000472237/file/tong-quan-flash-sale_e1c32736c1ce4449b845bc1fb8dc98aa_grande.png"
                    />
                  </div>
                </Col>
              </Row>
            </Col>
          </Row>
            <Row xs={2} md={4} className="justify-content-md-between">
              <Col md='auto'>
                <div className="lp pt-sm-4">
                  <Image
                    src="https://www.anarapublishing.com/wp-content/uploads/elementor/thumbs/photo-1506157786151-b8491531f063-o67khcr8g8y3egfjh6eh010ougiroekqaq5cd8ly88.jpeg"
                  />
                </div>
              </Col>
              <Col md='auto'>
                <div className="lp pt-sm-4">
                  <Image
                    src="https://img.freepik.com/free-vector/special-offer-creative-sale-banner-design_1017-16284.jpg?1"
                  />
                </div>
              </Col>
              <Col md='auto'>
                <div className="lp pt-sm-4">
                  <Image
                    src="https://media.defense.gov/2020/Apr/30/2002291608/1920/1080/0/200501-F-PO640-0034.JPG"
                  />
                </div>
              </Col>
              <Col md='auto'>
                <div className="lp pt-sm-4">
                  <Image
                    src="https://webstar.ug/wp-content/uploads/2023/06/Unveiling-Modern-Trends-in-Technology.jpeg"
                  />
                </div>
              </Col>
            </Row>
        </div>
      </Container>
    </>
  );
};

export default Category;
