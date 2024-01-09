import React from "react";
import { useParams } from "react-router-dom";
import { getWarehouseById } from "../../../services/WarehouseServices";
import { toast } from "react-toastify";
import { Badge } from "react-bootstrap";
import "../Detail/ProductInfor.scss";

const ProductInfor = () => {
  const { id } = useParams();
  const [warehouse, setWareHouse] = React.useState({});

  React.useEffect(() => {
    getWareHouse();
  }, [id]);

  const getWareHouse = async () => {
    await getWarehouseById(id)
      .then((rs) => {
        if (rs.data) {
          setWareHouse(rs.data);
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
            src={warehouse.thumbnailUrl}
            style={{
              width: 30 + "rem",
              height: 20 + "rem",
              borderRadius: 2 + "rem",
            }}
          />
        </div>
        <div>
          <h3 className="fW">{warehouse.name}</h3>
          <p className="fW">
            Tình trạng:
            <span
              className={
                warehouse.status ? "ps-3 ac active" : "ps-3 ac deactive"
              }
            >
              {warehouse.status ? "Còn hàng" : "Hết hàng"}
            </span>
          </p>
          <p>
            Thể loại:
            <span
              className={
                warehouse.categoryName ? "ps-3 ac active" : "ps-3 ac deactive"
              }
            >
              {warehouse.categoryName}
            </span>
          </p>
          <div className="d-flex">
            <h4 className="fW">{warehouse.price}đ</h4>
            <span className="ps-3">
              <i class="fa-solid fa-heart"></i>
            </span>
          </div>
          <h6 className="dp fW">
            {warehouse.maxDiscountAmount > 0 && (
              <>
                {warehouse.originalPrice}đ
                <span className="ps-3">
                  <Badge bg="danger">-{warehouse.maxDiscountAmount}%</Badge>
                </span>
              </>
            )}
          </h6>
          <hr />
          <div className="d-flex justify-content-between">
            <span>
              <button className="btn btn-primary">
                <i class="fa-solid fa-credit-card"></i>
                Mua ngay
              </button>
            </span>
            <span>
              <button className="btn btn-info">
                <i class="fa-solid fa-cart-shopping"></i>
                Thêm vào giỏ hàng
              </button>
            </span>
          </div>
        </div>
        <div>
          <h4 className="fW">Mã sản phẩm: {warehouse.warehouseCode}</h4>
        </div>
      </div>
      <div>
        <hr />
      </div>
    </>
  );
};

export default ProductInfor;
