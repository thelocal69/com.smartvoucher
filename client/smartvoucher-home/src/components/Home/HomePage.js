import React from "react";
import { Container, Button, Card, Badge } from "react-bootstrap";
import '../Home/HomePage.scss';

const HomePage = () => {
  return (
    <>
    <div className="my-3 label-font">
        <h4>Sản phẩm nổi bật</h4>
    </div>

      <div className="">
        <Card style={{ width: "18rem" }}>
          <Card.Img
            variant="top"
            src="https://cdn.divineshop.vn/image/catalog/Anh-SP/Netflix/Divine-Shop-NETFLIX-1-thang-23298.jpg?hash=1658829694"
          />
          <Card.Body>
            <Card.Link className="a">
              Tài Khoản Netflix Premium 1 tháng - Xem phim chất lượng 4k và Full
              HD
            </Card.Link>
            <Card.Text className="d-flex justify-content-between my-2">
              <span className="price">89.000đ</span>
              <span className="discount-price">
                260.000đ <Badge bg="danger">-66%</Badge>
              </span>
            </Card.Text>
          </Card.Body>
        </Card>
      </div>
    </>
  );
};

export default HomePage;
