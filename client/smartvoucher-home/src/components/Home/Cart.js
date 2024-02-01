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
import { addOrder } from "../../services/OrderServices";
import { buyTicket } from "../../services/TicketServices";
import Loading from "../Util/Loading";
import { selectUserId, selectUserBalance } from "../../Redux/data/UserSlice";
import { toast } from "react-toastify";
import { getIdStore } from "../../services/WarehouseStoreSrvices";
import { buyVoucher } from "../../services/UserServices";
import { balance } from "../../Redux/data/UserSlice";

const Cart = () => {
  const dispatch = useDispatch();
  const cart = useSelector(selectIdCarts);
  const [isShowModalLogin, setIsShowModalLogin] = React.useState(false);
  const [loading, setLoading] = React.useState(false);
  const accessToken = useSelector(selectAccessToken);
  const idUser = useSelector(selectUserId);
  const balanceL = useSelector(selectUserBalance);
  const [status, setStatus] = React.useState(1);

  React.useEffect(() => {
    const timer = setTimeout(() => {
      cart.map((item) => {
        dispatch(removeItem(item));
      });
    }, 1800000);
    return () => clearTimeout(timer);
  }, []);

  const handleBuyTicket = async () => {
    if (cart) {
      for (let index = 0; index < cart.length; index++) {
        const element = cart[index];
        const total = cart.reduce(
          (acc, item) => (acc += item.price * item.quantity),
          0
        );
        const objBuyVoucher = {
          balance: balanceL.balance,
          total: total,
        };
        setLoading(true);
        await buyVoucher(objBuyVoucher)
          .then((rs) => {
            if (rs) {
              toast.success("Cập nhật số dư trong tài khoản !");
              dispatch(
                balance({
                  balance: rs.data.balance,
                })
              );
              const objOrder = {
                idUser: idUser.id,
                idWarehouse: element.id,
                status: status,
                quantity: element.quantity,
              };
              addOrder(objOrder)
                .then((rs) => {
                  if (rs) {
                    let objStore = {
                      idOrder: rs.data.id,
                      idWarehouse: rs.data.idWarehouse,
                      idUser: rs.data.idUser,
                      numberOfSerial: element.quantity,
                      idCategory: element.idCategory,
                      idStore: 0,
                      status: status,
                      discountType: element.discountTypeName,
                      discountAmount: element.discountAmount,
                    };
                    getIdStore(objStore.idWarehouse)
                      .then((rs) => {
                        if (rs) {
                          let objTicket = { ...objStore };
                          objTicket.idStore = rs.data.keys.idStore;
                          buyTicket(objTicket)
                            .then((rs) => {
                              if (rs) {
                                setLoading(false);
                                toast.success(
                                  "Kiểm tra đơn hàng hàng ở trang tài khoản !"
                                );
                                dispatch(removeItem(element));
                              }
                            })
                            .catch((err) => {
                              console.log(err.message);
                              setLoading(false);
                            });
                        }
                      })
                      .catch((err) => {
                        console.log(err.message);
                        setLoading(false);
                      });
                  }
                })
                .catch((err) => {
                  console.log(err.message);
                  setLoading(false);
                });
            }
          })
          .catch((err) => {
            console.log(err.message);
            setLoading(false);
          });
      }
    }
  };

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
                    {cart.length < 1 ? (
                      <></>
                    ) : (
                      <>
                        <div>
                          <h5>Thanh toán</h5>
                          <div>
                            Số dư:{" "}
                            {new Intl.NumberFormat("vi-VN", {
                              style: "currency",
                              currency: "VND",
                            }).format(balanceL ? balanceL.balance : 0)}
                          </div>
                          <div className="pt-2 pb-2">
                            <p>
                              Tổng giá trị phải thanh toán:{" "}
                              <b>
                                {new Intl.NumberFormat("vi-VN", {
                                  style: "currency",
                                  currency: "VND",
                                }).format(
                                  cart.reduce(
                                    (acc, item) =>
                                      (acc += item.price * item.quantity),
                                    0
                                  )
                                )}
                              </b>
                            </p>
                          </div>
                          <div>
                            {accessToken ? (
                              <>
                                <button
                                  className="btn btn-primary"
                                  onClick={() => handleBuyTicket()}
                                >
                                  {loading && (
                                    <>
                                      <Loading fileName={"Waiting"} />
                                    </>
                                  )}
                                  <span hidden={loading ? true : false}>
                                    Thanh toán
                                  </span>
                                </button>
                              </>
                            ) : (
                              <>
                                <button
                                  className="btn btn-primary"
                                  onClick={() => setIsShowModalLogin(true)}
                                >
                                  {loading && (
                                    <>
                                      <Loading fileName={"Waiting"} />
                                    </>
                                  )}
                                  <span hidden={loading ? true : false}>
                                    Đăng nhập để thanh toán
                                  </span>
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
