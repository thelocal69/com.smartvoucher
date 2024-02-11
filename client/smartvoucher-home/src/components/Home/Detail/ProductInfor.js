import React from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getWarehouseById } from "../../../services/WarehouseServices";
import { Badge, Col, Container, Row } from "react-bootstrap";
import "../Detail/ProductInfor.scss";
import { useDispatch } from "react-redux";
import { addToCart } from "../../../Redux/data/CartSlice";
import parse from "html-react-parser";
import Comment from "./Comment";

const ProductInfor = () => {
  const { id } = useParams();
  const [warehouse, setWareHouse] = React.useState({});
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [description, setDescription] = React.useState("");

  let thumbnailUrl = warehouse.thumbnailUrl;
  let title = warehouse.name;
  let status = warehouse.status;
  let voucherChannel = warehouse.voucherChannel;
  let categoryName = warehouse.categoryName;
  let price = warehouse.price;
  let maxDiscountAmount = warehouse.maxDiscountAmount;
  let originalPrice = warehouse.originalPrice;
  let warehouseCode = warehouse.warehouseCode;

  React.useEffect(() => {
    getWareHouse();
  }, [id]);

  const getWareHouse = async () => {
    await getWarehouseById(id)
      .then((rs) => {
        if (rs.data) {
          setWareHouse(rs.data);
          setDescription(rs.data.description);
        }
      })
      .catch((err) => console.log(err.message));
  };

  return (
    <>
      <Container>
        <Row className="gofo justify-content-md-between">
          <Col>
            <div className="RZ">
              <img
                alt=""
                src={thumbnailUrl}
              />
            </div>
          </Col>
          <Col>
            <Row xs={1} md={1} className="HE">
              <Col>
                <h3 className="fW">{title}</h3>
              </Col>
              <Col>
                <h4 className="fW">Mã sản phẩm: {warehouseCode}</h4>
              </Col>
              <Col>
                <div className="d-flex lT">
                  <p className="fW">
                    Tình trạng:
                    <span className={status ? "ps-3 ac active" : "ps-3 ac deactive"}>
                      {status ? "Còn hàng" : "Hết hàng"}
                    </span>
                  </p>
                  <p className="fW ps-md-3">
                    Hình thức:
                    <span
                      className={
                        voucherChannel ? "ps-3 ac active" : "ps-3 ac deactive"
                      }
                    >
                      {voucherChannel ? "Online" : "Offline"}
                    </span>
                  </p>
                </div>
              </Col>
              <Col>
                <p>
                  Thể loại:
                  <span
                    className={categoryName ? "ps-3 ac active" : "ps-3 ac deactive"}
                  >
                    {categoryName}
                  </span>
                </p>
              </Col>
              <Col>
                <div className="d-flex">
                  <h4 className="fW">
                    {new Intl.NumberFormat("vi-VN", {
                      style: "currency",
                      currency: "VND",
                    }).format(price)}
                  </h4>
                  <span className="ps-3">
                    <i class="fa-solid fa-heart"></i>
                  </span>
                </div>
              </Col>
              <Col>
                <h6 className="dp fW">
                  {maxDiscountAmount > 0 && (
                    <>
                      {new Intl.NumberFormat("vi-VN", {
                        style: "currency",
                        currency: "VND",
                      }).format(originalPrice)}
                      <span className="ps-3">
                        <Badge bg="danger">-{maxDiscountAmount}%</Badge>
                      </span>
                    </>
                  )}
                </h6>
                <hr />
              </Col>
              <Col>
                <div className="YB">
                  <span className="pe-3">
                    <button
                      className="btn btn-primary"
                      onClick={() => {
                        let element = null;
                        element = { ...warehouse, quantity: 1 };
                        dispatch(addToCart(element));
                        navigate("/Cart ");
                      }}
                    >
                      <i class="fa-solid fa-credit-card"></i>
                      Mua ngay
                    </button>
                  </span>
                  <span>
                    <button
                      className="btn btn-info"
                      onClick={() => {
                        let element = null;
                        element = { ...warehouse, quantity: 1 };
                        dispatch(addToCart(element));
                      }}
                    >
                      <i class="fa-solid fa-cart-shopping"></i>
                      Thêm vào giỏ hàng
                    </button>
                  </span>
                </div>
              </Col>
              <Col>
                <div className="pt-3">
                  <h6 className="fW">{warehouse.termOfUse}</h6>
                </div>
              </Col>
            </Row>
          </Col>
        </Row>
        {parse(description)}
        <div>
          <Comment idWarehouse={id} />
        </div>
      </Container>
    </>
  );
};

export default ProductInfor;
