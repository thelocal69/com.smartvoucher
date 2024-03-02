import React from "react";
import { Card, Col, Container, Row } from "react-bootstrap";
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
import { userInfor } from "../../Redux/data/UserSlice";
import { useNavigate } from "react-router-dom";
import { getUserInfor } from "../../services/UserServices";

const Cart = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const cart = useSelector(selectIdCarts);
  const [isShowModalLogin, setIsShowModalLogin] = React.useState(false);
  const [loading, setLoading] = React.useState(false);
  const accessToken = useSelector(selectAccessToken);
  const userL = useSelector(selectUserId);
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

  const getInfor = async () => {
    await getUserInfor()
      .then((rs) => {
        if (rs) {
          dispatch(
            userInfor(rs.data)
          );
        }
      })
      .catch((err) => console.log(err.message));
  }

  const handleBuyTicket = async () => {
    if (cart) {
      for (let index = 0; index < cart.length; index++) {
        const element = cart[index];
        const total = cart.reduce(
          (acc, item) => (acc += item.price * item.quantity),
          0
        );
        const objBuyVoucher = {
          balance: balanceL,
          total: total,
        };
        setLoading(true);
        await buyVoucher(objBuyVoucher)
          .then((rs) => {
            if (rs) {
              toast.success("Cập nhật số dư trong tài khoản !");
              dispatch(
                userInfor(rs.data)
              );
              getInfor();
              const objOrder = {
                idUser: userL,
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
                                dispatch(removeItem(element));
                                navigate("/User/Order");
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
        <Row xs={1} md='auto' className="my-4 justify-content-md-between">
          <Col md='auto'>
            <div className="sC">
              <Card>
                <Card.Body>
                  <Row xs={1} md='auto' className="justify-content-md-between">
                    <Col md='auto'>
                      <div
                        className={
                          cart.length < 1
                            ? "d-flex flex-column justify-content-center align-items-center"
                            : "pb-3"
                        }
                      >
                        <h2>{cart.length < 1 ? "Giỏ hàng trống!" : "Giỏ hàng"}</h2>
                      </div>
                      <div className="rolca">
                        {cart.length < 1 && (
                          <Row xs={1} md='auto' className="flex-column justify-content-md-center align-items-center">
                            <Col>
                              <p>
                                Thêm sản phẩm vào giỏ và quay lại trang này để thanh
                                toán nha bạn
                              </p>
                            </Col>
                            <Col>
                              <img
                                alt=""
                                src="https://cdn.divineshop.vn/static/4e0db8ffb1e9cac7c7bc91d497753a2c.svg"
                              />
                            </Col>
                          </Row>
                        )}
                      </div>
                      <div>
                        {cart.map((item, key) => {
                          return (
                            <Row xs={1} md='auto' className="mb-3 pb-3 justify-content-md-between" key={key}>
                              <Col>
                                <div className="pe-2 MhR">
                                  <img
                                    alt=""
                                    src={item.bannerUrl}
                                  />
                                </div>
                              </Col>
                              <Col >
                                <div className="d-flex flex-column justify-content-between IU">
                                  <div className="d-flex justify-content-between tranfer-display">
                                    <div className="mg">
                                      <h5>
                                        <b>{item.name}</b>
                                      </h5>
                                    </div>
                                    <div className="sub-o">
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
                              </Col>
                            </Row>
                          );
                        })}
                      </div>
                    </Col>
                    <Col md='auto'>
                      {cart.length < 1 ? (
                        <></>
                      ) : (
                        <>
                          <Row xs={1}>
                            <Col>
                              <div>
                                <h5>Thanh toán</h5>
                                <div>
                                  Số dư:{" "}
                                  <b>
                                    {new Intl.NumberFormat("vi-VN", {
                                      style: "currency",
                                      currency: "VND",
                                    }).format(balanceL ? balanceL : 0)}
                                  </b>
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
                                        className="btn btn-primary lP"
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
                            </Col>
                          </Row>
                        </>
                      )}
                    </Col>
                  </Row>
                </Card.Body>
              </Card>
            </div>
          </Col>
        </Row>
        <Account show={isShowModalLogin} handleClose={handleClose} />
      </Container>
    </>
  );
};

export default Cart;
