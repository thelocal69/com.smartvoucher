import React from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getWarehouseById } from "../../../services/WarehouseServices";
import { toast } from "react-toastify";
import { Badge } from "react-bootstrap";
import "../Detail/ProductInfor.scss";
import { useDispatch } from "react-redux";
import { addToCart } from "../../../Redux/data/CartSlice";
import parse from "html-react-parser";

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
      .catch((err) => toast.error(err.message));
  };

  return (
    <>
      <div className="m-2 p-2 d-flex justify-content-between">
        <div>
          <img
            alt=""
            src={thumbnailUrl}
            style={{
              width: 30 + "rem",
              height: 20 + "rem",
              borderRadius: 2 + "rem",
            }}
          />
        </div>
        <div className="ms-3">
          <h3 className="fW">{title}</h3>
          <h4 className="fW">Mã sản phẩm: {warehouseCode}</h4>
          <div className="d-flex">
            <p className="fW">
              Tình trạng:
              <span className={status ? "ps-3 ac active" : "ps-3 ac deactive"}>
                {status ? "Còn hàng" : "Hết hàng"}
              </span>
            </p>
            <p className="fW ps-3">
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
          <p>
            Thể loại:
            <span
              className={categoryName ? "ps-3 ac active" : "ps-3 ac deactive"}
            >
              {categoryName}
            </span>
          </p>
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
          <div className="">
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
          <div className="pt-3">
            <h6 className="fW">{warehouse.termOfUse}</h6>
          </div>
        </div>
      </div>
      {parse(description)}
    </>
  );
};

export default ProductInfor;
