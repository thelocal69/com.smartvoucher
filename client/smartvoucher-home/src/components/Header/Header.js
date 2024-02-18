import React from "react";
import {
  Container,
  Navbar,
  Nav, Badge,
  NavDropdown,
  Offcanvas,
  Form,
  Button,
  Row,
  Col
} from "react-bootstrap";
import "../Header/Header.scss";
import { NavLink, useNavigate } from "react-router-dom";
import Logo from "../../assets/logo/logo.png";
import { useSelector, useDispatch } from "react-redux";
import {
  selectIsAuthenticated,
  selectRefreshToken,
  logOut,
} from "../../Redux/data/AuthSlice";
import {
  selectAvatar,
  selectUsername,
  selectUserBalance,
} from "../../Redux/data/UserSlice";
import { logoutUser } from "../../services/AccountServices";
import { toast } from "react-toastify";
import Account from "../Security/Account";
import { selectIdCarts } from "../../Redux/data/CartSlice";
import Store from "../../Redux/Store";

const Header = (props) => {

  const {userName} = props;
  

  const [isShowModalLogin, setIsShowModalLogin] = React.useState(false);
  const [isShowMenu, setIsShowMenu] = React.useState(false);
  const [sizes, setSizes] = React.useState("");
  const isAuthenticated = useSelector(selectIsAuthenticated);
  const refreshToken = useSelector(selectRefreshToken);
  const avatarL = useSelector(selectAvatar);
  const usernameL = useSelector(selectUsername);
  const cart = useSelector(selectIdCarts);
  const balanceL = useSelector(selectUserBalance);

  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleClickLogin = () => {
    setIsShowModalLogin(true);
    setSizes("lg");
  };

  const handleClose = () => {
    setIsShowModalLogin(false);
    setIsShowMenu(false);
  };

  const handleLogout = async () => {
    dispatch(logOut());
    Store.dispatch({type: "RESET"});
    await logoutUser(refreshToken).then((rs) => {
      if (rs) {
        toast.success(rs.message);
      }
    });
  };


  return (
    <>
      <div className="bg oh">
        <Container>
          <div
            className="p-2 bg d-flex justify-content-between sub-top">
            <Nav>
              <NavLink to="/" className="form-auth">
                <i class="fa-solid fa-chevron-left"></i>
                <i class="fa-solid fa-chevron-right"></i>
                <span>dsd</span>
              </NavLink>
            </Nav>
            <div className="d-flex">
              <Nav>
                <NavLink to="/" className="form-auth pe-3">
                  <i class="fa-solid fa-mobile-screen-button"></i>
                  Hướng dẫn mua hàng
                </NavLink>
              </Nav>
              <Nav>
                <NavLink to="/" className="form-auth pe-3">
                  <i class="fa-solid fa-gift"></i>
                  Ưu đãi khách hàng
                </NavLink>
              </Nav>
              <Nav>
                <NavLink to="/" className="form-auth">
                  <i class="fa-regular fa-handshake"></i>
                  Thông tin liên hệ
                </NavLink>
              </Nav>
            </div>
          </div>
        </Container>
      </div>
      <Container>

      </Container>
      <Navbar expand="sm" className="bg-body-tertiary custom-header">
        <Container>
          <div className="LH">
            <Button
              onClick={() => setIsShowMenu(true)}
            ><i class="fa-solid fa-bars"></i></Button>
          </div>
          <Navbar.Collapse className="d-lg-flex justify-content-between">
            <Navbar.Brand>
              <div className="oh">
                <NavLink to="/" className="custom-font">
                  <img
                    src={Logo}
                    alt=""
                    style={{
                      width: 4 + "rem",
                      marginRight: 1 + "rem",
                    }}
                  />
                </NavLink>
                <NavLink
                  to="/"
                  className="custom-font"
                  style={{
                    lineHeight: 4 + "rem",
                  }}
                >
                  SMART VOUCHER
                </NavLink>
              </div>
            </Navbar.Brand>
            <Offcanvas
              show={isShowMenu}
              onHide={handleClose}
              id={`offcanvasNavbar-expand-sm`}
              aria-labelledby={`offcanvasNavbarLabel-expand-sm`}
              placement="start"
              responsive="sm"
            >
              <Offcanvas.Header className="custom-header">
                <Offcanvas.Title id={`offcanvasNavbarLabel-expand-sm`}>
                  {
                    isAuthenticated ? (
                      <>
                        <Container>
                          <Row xs='auto'>
                            <Col>
                              <div>
                                <img
                                  alt=""
                                  src={avatarL}
                                  className="EE" />
                              </div>
                            </Col>
                            <Col className="HH">
                              <div className="lG">
                                {usernameL}
                              </div>
                              <div className="lG">
                                <span>Số dư: </span>
                                <span>
                                  {new Intl.NumberFormat("vi-VN", {
                                    style: "currency",
                                    currency: "VND",
                                  }).format(balanceL)}
                                </span>
                              </div>
                            </Col>
                          </Row>
                        </Container>
                      </>
                    ) : (
                      <>
                        <NavLink className="custom-font mx-2">
                          <i
                            class="fa-regular fa-circle-user"
                            onClick={() => handleClickLogin()}
                          ></i>
                        </NavLink>
                        <NavLink
                          className="form-auth"
                          onClick={() => handleClickLogin()}
                        >
                          Đăng nhập
                        </NavLink>
                        <span className="form-auth mx-2">/</span>
                        <NavLink
                          className="form-auth"
                          onClick={() => handleClickLogin()}
                        >
                          Đăng ký
                        </NavLink>
                      </>
                    )
                  }
                </Offcanvas.Title>
              </Offcanvas.Header>
              <Offcanvas.Body>
                <Container>
                  <Row xs={1}>
                    <Col>
                      <Nav className="justify-content-end flex-grow-1 pe-3">
                        <Form className="d-flex">
                          <Form.Control
                            type="search"
                            placeholder="Search"
                            className="me-2"
                            aria-label="Search"
                          />
                          <Button
                            className="btn btn-primary"
                          >Search</Button>
                        </Form>
                      </Nav>
                    </Col>
                    <Col>
                      <div style={{
                        paddingTop: 0.5 + 'rem'
                      }}>
                        <div
                          className="KK"
                          onClick={
                            () => {
                              navigate("/");
                              handleClose();
                            }
                          }
                        >
                          <i class="fa-solid fa-house"></i>
                          Home
                        </div>
                        <hr />
                      </div>
                    </Col>
                    <Col>
                      <div style={{
                        paddingTop: 0.5 + 'rem'
                      }}>
                        <div
                          className="KK"
                          onClick={
                            () => {
                              navigate("/User/Profile");
                              handleClose();
                            }
                          }
                        >
                          <i class="fa-solid fa-user"></i>
                          Quản lí tài khoản
                        </div>
                      </div>

                      <div style={{
                        paddingTop: 0.5 + 'rem'
                      }}>
                        <div
                          className="KK"
                          onClick={
                            () => {
                              navigate("/User/Order");
                              handleClose();
                            }
                          }
                        >
                          <i class="fa-solid fa-cart-shopping"></i>
                          Lịch sử đơn hàng
                        </div>
                        <hr />
                      </div>
                    </Col>
                    <Col>
                      <div style={{
                        paddingTop: 0.5 + 'rem'
                      }}>
                        <div
                          className="KK"
                          onClick={
                            () => {
                              navigate("/Category/Du lịch");
                              handleClose();
                            }
                          }
                        >
                          <i class="fa-solid fa-plane"></i>
                          Du lịch
                        </div>
                      </div>
                      <div style={{
                        paddingTop: 0.5 + 'rem'
                      }}>
                        <div
                          className="KK"
                          onClick={
                            () => {
                              navigate("/Category/Điện tử");
                              handleClose();
                            }
                          }
                        >
                          <i class="fa-solid fa-lightbulb"></i>
                          Điện tử
                        </div>
                      </div>
                      <div style={{
                        paddingTop: 0.5 + 'rem'
                      }}>
                        <div
                          className="KK"
                          onClick={
                            () => {
                              navigate("/Category/Thời trang");
                              handleClose();
                            }
                          }
                        >
                          <i class="fa-solid fa-shirt"></i>
                          Thời trang
                        </div>
                      </div>
                      <div style={{
                        paddingTop: 0.5 + 'rem'
                      }}>
                        <div
                          className="KK"
                          onClick={
                            () => {
                              navigate("/Category/Ẩm thực");
                              handleClose();
                            }
                          }
                        >
                          <i class="fa-solid fa-bowl-food"></i>
                          Ẩm thực
                        </div>
                      </div>
                      <div style={{
                        paddingTop: 0.5 + 'rem'
                      }}>
                        <div
                          className="KK"
                          onClick={
                            () => {
                              navigate("/Category/Sách và Học nghệ thuật");
                              handleClose();
                            }
                          }
                        >
                          <i class="fa-solid fa-book"></i>
                          Sách hay
                        </div>
                      </div>
                      <div style={{
                        paddingTop: 0.5 + 'rem'
                      }}>
                        <div
                          className="KK"
                          onClick={
                            () => {
                              navigate("/Category/Sức khỏe và Làm đẹp");
                              handleClose();
                            }
                          }
                        >
                          <i class="fa-solid fa-notes-medical"></i>
                          Sức khỏe
                        </div>
                      </div>
                      <div style={{
                        paddingTop: 0.5 + 'rem'
                      }}>
                        <div
                          className="KK"
                          onClick={
                            () => {
                              navigate("/Category/Nội thất và Gia đình");
                              handleClose();
                            }
                          }
                        >
                          <i class="fa-solid fa-people-roof"></i>
                          Gia đình
                        </div>
                      </div>
                      <div style={{
                        paddingTop: 0.5 + 'rem'
                      }}>
                        <div
                          className="KK"
                          onClick={
                            () => {
                              navigate("/Category/Công nghệ");
                              handleClose();
                            }
                          }
                        >
                          <i class="fa-solid fa-microchip"></i>
                          Công nghệ
                        </div>
                      </div>
                      <div style={{
                        paddingTop: 0.5 + 'rem'
                      }}>
                        <div
                          className="KK"
                          onClick={
                            () => {
                              navigate("/Category/Thể thao và Hoạt động ngoại ô");
                              handleClose();
                            }
                          }
                        >
                          <i class="fa-solid fa-bicycle"></i>
                          Thể thao
                        </div>
                      </div>
                      <div style={{
                        paddingTop: 0.5 + 'rem'
                      }}>
                        <div
                          className="KK"
                          onClick={
                            () => {
                              navigate("/Category/Trò chơi");
                              handleClose();
                            }
                          }
                        >
                          <i class="fa-solid fa-gamepad"></i>
                          Trò chơi
                        </div>
                        <hr />
                      </div>
                    </Col>
                    <Col>
                      {
                        isAuthenticated ? (
                          <>
                            <div style={{
                              paddingTop: 0.5 + 'rem'
                            }}>
                              <div
                                className="KK"
                                onClick={
                                  () => {
                                    handleLogout();
                                    navigate("/");
                                    handleClose();
                                  }
                                }
                              >
                                <i class="fa-solid fa-right-from-bracket"></i>
                                Đăng xuất
                              </div>
                              <hr />
                            </div>
                          </>
                        ) : (
                          <>
                          </>
                        )
                      }
                    </Col>
                  </Row>
                </Container>
              </Offcanvas.Body>
            </Offcanvas>
            <Nav>
              <div className="oh">
                <div className="search-wrapper">
                  <input
                    type="search"
                    name=""
                    id=""
                    placeholder="Tìm kiếm sản phẩm theo tên"
                  />
                  <div className="ic-wrapper custom-btn">
                    <i class="fa-solid fa-magnifying-glass"></i>
                  </div>
                </div>
              </div>
            </Nav>
            <div className="oh d-flex">
              {isAuthenticated ? (
                <>
                  <Navbar>
                    <NavLink>
                      <img alt="" src={avatarL} className="aval" />
                    </NavLink>
                  </Navbar>
                  <NavDropdown
                    title={<span className="ft">
                      {usernameL}
                      </span>}
                    id="basic-nav-dropdown"
                    className="ft"
                  >
                    <NavDropdown.Item>
                      Số dư tài khoản
                      <br />
                      <b>
                        {new Intl.NumberFormat("vi-VN", {
                          style: "currency",
                          currency: "VND",
                        }).format(balanceL)}
                      </b>
                    </NavDropdown.Item>
                    <NavLink to="/User/Profile" className="dropdown-item">
                      Quản lí tài khoản
                    </NavLink>
                    <NavDropdown.Item onClick={() => {
                      handleLogout()
                    }}>
                      Logout
                    </NavDropdown.Item>
                  </NavDropdown>
                </>
              ) : (
                <>
                  <NavLink className="custom-font mx-2">
                    <i
                      class="fa-regular fa-circle-user"
                      onClick={() => handleClickLogin()}
                    ></i>
                  </NavLink>
                  <NavLink
                    className="form-auth"
                    onClick={() => handleClickLogin()}
                  >
                    Đăng nhập
                  </NavLink>
                  <span className="form-auth mx-2">/</span>
                  <NavLink
                    className="form-auth"
                    onClick={() => handleClickLogin()}
                  >
                    Đăng ký
                  </NavLink>
                </>
              )}
            </div>
            <div className="oh"><Button
              className="btn btn-light custom-cart"
              onClick={() => {
                navigate("/Cart");
              }}
            >
              <i class="fa-solid fa-cart-shopping"></i>
              Cart <Badge bg="danger">{cart.length}</Badge>
            </Button></div>
          </Navbar.Collapse>
          <div className="lm"><Button
            className="btn btn-light custom-cart btn-sm"
            onClick={() => {
              navigate("/Cart");
            }}
          >
            <i class="fa-solid fa-cart-shopping"></i>
            Cart <Badge bg="danger">{cart.length}</Badge>
          </Button></div>
        </Container>
      </Navbar>
      <div className="custom-header oh">
        <Container>
          <div className="p-3 d-flex justify-content-between">
            <Nav>
              <NavLink to="/" className="sub-font">
                <i class="fa-regular fa-eye"></i>
                Sản phẩm bạn vừa xem
              </NavLink>
            </Nav>
            <Nav>
              <NavLink to="/" className="sub-font">
                <i class="fa-solid fa-fire"></i>
                Sản phẩm mua nhiều
              </NavLink>
            </Nav>
            <Nav>
              <NavLink to="/" className="sub-font">
                <i class="fa-solid fa-percent"></i>
                Sản phẩm khuyến mại
              </NavLink>
            </Nav>
            <Nav>
              <NavLink to="/" className="sub-font">
                <i class="fa-regular fa-map"></i>
                Đại lý giao dịch
              </NavLink>
            </Nav>
            <Nav>
              <NavLink to="/" className="sub-font">
                <i class="fa-regular fa-credit-card"></i>
                Hình thức thanh toán
              </NavLink>
            </Nav>
          </div>
        </Container>
      </div>
      <div className="oh">
        <Container>
          <div className="p-2 d-flex justify-content-between sub-header">
            <div>
              <NavDropdown
                title={
                  <span className="form-sub">
                    <i class="fa-solid fa-bars"></i>
                    Danh mục sản phẩm
                  </span>
                }
                className="form-sub"
              >
                <NavDropdown.Item>
                  <NavLink to="/Category/Du lịch" className="dropItem">
                    <i class="fa-solid fa-plane"></i>
                    Du lịch
                  </NavLink>
                </NavDropdown.Item>
                <NavDropdown.Item>
                  <NavLink to="/Category/Điện tử" className="dropItem">
                    <i class="fa-solid fa-lightbulb"></i>
                    Điện tử
                  </NavLink>
                </NavDropdown.Item>
                <NavDropdown.Item>
                  <NavLink to="/Category/Thời trang" className="dropItem">
                    <i class="fa-solid fa-shirt"></i>
                    Thời trang
                  </NavLink>
                </NavDropdown.Item>
                <NavDropdown.Item>
                  <NavLink to="/Category/Ẩm thực" className="dropItem">
                    <i class="fa-solid fa-bowl-food"></i>
                    Ẩm thực
                  </NavLink>
                </NavDropdown.Item>
                <NavDropdown.Item>
                  <NavLink to="/Category/Sách và Học nghệ thuật" className="dropItem">
                    <i class="fa-solid fa-book"></i>
                    Sách hay
                  </NavLink>
                </NavDropdown.Item>
                <NavDropdown.Item>
                  <NavLink to="/Category/Sức khỏe và Làm đẹp" className="dropItem">
                    <i class="fa-solid fa-notes-medical"></i>
                    Sức khỏe
                  </NavLink>
                </NavDropdown.Item>
                <NavDropdown.Item>
                  <NavLink to="/Category/Nội thất và Gia đình" className="dropItem">
                    <i class="fa-solid fa-people-roof"></i>
                    Gia đình
                  </NavLink>
                </NavDropdown.Item>
                <NavDropdown.Item>
                  <NavLink to="/Category/Công nghệ" className="dropItem">
                    <i class="fa-solid fa-microchip"></i>
                    Công nghệ
                  </NavLink>
                </NavDropdown.Item>
                <NavDropdown.Item>
                  <NavLink to="/Category/Thể thao và Hoạt động ngoại ô" className="dropItem">
                    <i class="fa-solid fa-bicycle"></i>
                    Thể thao
                  </NavLink>
                </NavDropdown.Item>
                <NavDropdown.Item>
                  <NavLink to="/Category/Trò chơi" className="dropItem">
                    <i class="fa-solid fa-gamepad"></i>
                    Trò chơi
                  </NavLink>
                </NavDropdown.Item>
              </NavDropdown>
            </div>
            <div className="d-flex">
              <Nav>
                <NavLink to="/" className="form-sub pe-3">
                  <i class="fa-solid fa-mobile-screen-button"></i>
                  Checkin !
                </NavLink>
              </Nav>
              <Nav>
                <NavLink to="/" className="form-sub pe-3">
                  <i class="fa-regular fa-handshake"></i>
                  Liên hệ hợp tác
                </NavLink>
              </Nav>
              <Nav>
                <NavLink to="/" className="form-sub">
                  <i class="fa-regular fa-gem"></i>
                  Ưu đãi khách hàng VIP
                </NavLink>
              </Nav>
            </div>
          </div>
          <Account show={isShowModalLogin} handleClose={handleClose} sizes={sizes} />
        </Container>
        <div className="pm">
          <hr />
        </div>
      </div>
    </>
  );
};

export default Header;
