import React from "react";
import { Container } from "react-bootstrap";
import { selectIdCarts } from "../../Redux/data/CartSlice";
import { useSelector, useDispatch } from "react-redux";
import {
  incrementQuantity,
  decrementQuantity,
  removeItem,
} from "../../Redux/data/CartSlice";
import "./Cart.scss";
import Account from "../Security/Account";
import { selectAccessToken } from "../../Redux/data/AuthSlice";

const Cart = () => {
  const dispatch = useDispatch();
  const cart = useSelector(selectIdCarts);
  const [isShowModalLogin, setIsShowModalLogin] = React.useState(false);
  const accessToken = useSelector(selectAccessToken);

  const handleClose = () => {
    setIsShowModalLogin(false);
  };

  return (
    <>
      <Container>
        <div>
          <div className="row my-4">
            <div className="col-md-12">
              <div className="card">
                <div className="card-body">
                  <div
                    className={
                      cart.length < 1
                        ? "d-flex flex-column justify-content-center align-items-center"
                        : "pb-3"
                    }
                  >
                    <h2>{cart.length < 1 ? "Giỏ hàng trống!" : "Giỏ hàng"}</h2>
                  </div>
                  {cart.length < 1 && (
                    <div className="d-flex flex-column justify-content-center align-items-center">
                      <p>
                        Thêm sản phẩm vào giỏ và quay lại trang này để thanh
                        toán nha bạn
                      </p>
                      <img
                        alt=""
                        src="https://cdn.divineshop.vn/static/4e0db8ffb1e9cac7c7bc91d497753a2c.svg"
                      />
                    </div>
                  )}
                  <div className="d-flex justify-content-between">
                    <div>
                      {cart.map((item, key) => {
                        return (
                          <div className="mb-3 pb-3 d-flex" key={key}>
                            <div className="pe-2">
                              <img
                                alt=""
                                src={item.bannerUrl}
                                style={{
                                  width: 20 + "rem",
                                  height: 10 + "rem",
                                  borderRadius: 1 + "rem",
                                }}
                              />
                            </div>
                            <div className="ps-2 d-flex flex-column justify-content-between">
                              <div className="d-flex justify-content-between">
                                <div className="mg">
                                  <h5>
                                    <b>{item.name}</b>
                                  </h5>
                                </div>
                                <div className="">
                                  <button
                                    onClick={() => {
                                      dispatch(incrementQuantity(item));
                                    }}
                                    style={{
                                      border: "none",
                                      background: "none",
                                      padding: 0,
                                    }}
                                  >
                                    <i class="fa-solid fa-caret-up"></i>
                                  </button>
                                  <input
                                    className="text-center s  "
                                    value={item.quantity}
                                    style={{
                                      width: 2 + "rem",
                                      border: "none",
                                      padding: 0,
                                    }}
                                  />
                                  <button
                                    onClick={() => {
                                      dispatch(decrementQuantity(item));
                                    }}
                                    style={{
                                      border: "none",
                                      background: "none",
                                      padding: 0,
                                    }}
                                  >
                                    <i class="fa-solid fa-caret-down"></i>
                                  </button>
                                </div>
                                <div>
                                  <h6>{item.price * item.quantity}đ</h6>
                                </div>
                              </div>
                              <div>
                                <hr />
                                <div className="d-flex justify-content-between">
                                  <div>
                                    <i class="fa-solid fa-box"></i>
                                    Tình trạng:{" "}
                                    <span
                                      className={
                                        item.status
                                          ? "Ct active"
                                          : "Ct deactive"
                                      }
                                    >
                                      {item.status ? "Còn hàng" : "Hết hàng"}
                                    </span>
                                  </div>
                                  <div>
                                    <i
                                      class="fa-solid fa-trash-can text-danger"
                                      onClick={() => {
                                        dispatch(removeItem(item));
                                      }}
                                      style={{
                                        cursor: "pointer",
                                      }}
                                    ></i>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                        );
                      })}
                    </div>
                    {cart.length < 1 ? (<></>) : (
                      <>
                        <div>
                          <h5>Thanh toán</h5>
                          <div>Số dư: đ</div>
                          <div className="pt-2 pb-2 d-flex justify-content-between">
                            <div>
                              <p>Tổng giá trị phải thanh toán:</p>
                            </div>
                            <div>
                              <p>
                                <b>
                                  {cart.reduce(
                                    (acc, item) =>
                                      (acc += item.price * item.quantity),
                                    0
                                  )}
                                  đ
                                </b>
                              </p>
                            </div>
                          </div>
                          <div>
                            {accessToken ? (
                              <>
                                <button className="btn btn-primary">
                                  Thanh toán
                                </button>
                              </>
                            ) : (
                              <>
                                <button
                                  className="btn btn-primary"
                                  onClick={() => setIsShowModalLogin(true)}
                                >
                                  Đăng nhập để thanh toán
                                </button>
                              </>
                            )}
                          </div>
                        </div>
                      </>
                    )}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <Account show={isShowModalLogin} handleClose={handleClose} />
      </Container>
    </>
  );
};

export default Cart;
