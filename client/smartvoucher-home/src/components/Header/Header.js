import React from "react";
import { Container, Navbar, Nav, Badge, NavDropdown } from "react-bootstrap";
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
import { reset } from "../../Redux/data/UserSlice";
import DropdownItem from "react-bootstrap/esm/DropdownItem";

const Header = () => {
  const [isShowModalLogin, setIsShowModalLogin] = React.useState(false);
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
  };

  const handleClose = () => {
    setIsShowModalLogin(false);
  };

  const handleLogout = async () => {
    dispatch(logOut());
    dispatch(reset());
    await logoutUser(refreshToken).then((rs) => {
      if (rs) {
        toast.success(rs.message);
      }
    });
  };

  return (
    <>
      <div className="custom-header">
        <div className="bg">
          <Container>
            <div className="p-2 bg d-flex justify-content-between sub-top">
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
        <Navbar
          expand="lg"
          className=" bg-body-tertiary custom-bg d-flex justify-content-between"
          data-bs-theme="dark"
          style={{
            paddingTop: 2 + "rem",
            paddingLeft: 7.5 + "rem",
            paddingRight: 7.5 + "rem",
          }}
        >
          <Nav>
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
          </Nav>
          <Nav>
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
          </Nav>
          <Nav>
            <Navbar
              expand="lg"
              className=" bg-body-tertiary custom-bg d-flex justify-content-center"
              data-bs-theme="dark"
            >
              {isAuthenticated ? (
                <>
                  <Navbar>
                    <NavLink>
                      <img alt="" src={avatarL.avatar} className="aval" />
                    </NavLink>
                  </Navbar>
                  <NavDropdown
                    title={<span className="ft">{usernameL.username}</span>}
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
                        }).format(balanceL.balance)}
                      </b>
                    </NavDropdown.Item>
                    <NavLink to="/User/Profile" className="dropdown-item">
                      Quản lí tài khoản
                    </NavLink>
                    <NavDropdown.Item onClick={() => handleLogout()}>
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
            </Navbar>
          </Nav>
          <Nav>
            <button
              className="btn btn-light custom-cart"
              onClick={() => {
                navigate("/Cart");
              }}
            >
              <i class="fa-solid fa-cart-shopping"></i>
              Cart <Badge bg="danger">{cart.length}</Badge>
            </button>
          </Nav>
        </Navbar>
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
              <DropdownItem>
                <NavLink className="dropItem">
                  <i class="fa-solid fa-sack-dollar"></i>
                  Ưu đãi đặc biệt
                </NavLink>
              </DropdownItem>
              <DropdownItem>
                <NavLink className="dropItem">
                  <i class="fa-regular fa-snowflake"></i>
                  Cuối tuần
                </NavLink>
              </DropdownItem>
              <DropdownItem>
                <NavLink className="dropItem">
                  <i class="fa-solid fa-trademark"></i>
                  Chỉ có tại SmartVoucher
                </NavLink>
              </DropdownItem>
              <DropdownItem>
                <NavLink className="dropItem">
                  <i class="fa-solid fa-house-medical"></i>
                  Khỏe đẹp
                </NavLink>
              </DropdownItem>
              <DropdownItem>
                <NavLink className="dropItem">
                  <i class="fa-solid fa-microchip"></i>
                  Công nghệ và Đời sống
                </NavLink>
              </DropdownItem>
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
        <Account show={isShowModalLogin} handleClose={handleClose} />
      </Container>
      <div className="pm">
        <hr />
      </div>
    </>
  );
};

export default Header;
