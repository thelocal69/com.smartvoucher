import React from "react";
import { Container, Navbar, Nav, Col, Image, Badge } from "react-bootstrap";
import "../Header/Header.scss";
import { NavLink } from "react-router-dom";
import Logo from "../../assets/logo/logo.png";
import ModalLogin from "../Util/ModalLogin";

const Header = () => {
  const [isShowModalLogin, setIsShowModalLogin] = React.useState(false);

  const handleClickLogin = () => {
    setIsShowModalLogin(true);
  };

  const handleClose = () => {
    setIsShowModalLogin(false);
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
                  <NavLink to="/" className="form-auth">
                    <i class="fa-solid fa-mobile-screen-button"></i>
                    Hướng dẫn mua hàng
                  </NavLink>
                </Nav>
                <Nav>
                  <NavLink to="/" className="form-auth">
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
          className=" bg-body-tertiary custom-bg d-flex"
          data-bs-theme="dark"
          style={{
            paddingTop: 2 + "rem",
          }}
        >
          <Container>
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
                style={{
                  width: 16 + "rem",
                }}
              >
                <Col xs={3} md={2}>
                  <NavLink to="/Profile">
                    <Image src="" roundedCircle className="avatar" />
                  </NavLink>
                </Col>
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
              </Navbar>
            </Nav>
            <Nav>
              <button className="btn btn-light custom-cart">
                <i class="fa-solid fa-cart-shopping"></i>
                Cart <Badge bg="secondary">9</Badge>
                <span className="visually-hidden">unread messages</span>
              </button>
            </Nav>
          </Container>
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
          <Nav>
            <NavLink to="/" className="form-sub">
              <i class="fa-solid fa-bars"></i>
              Danh mục sản phẩm
            </NavLink>
          </Nav>
          <Nav>
            <NavLink to="/" className="form-sub">
              <i class="fa-solid fa-mobile-screen-button"></i>
              Thủ thuật & Tin Tức
            </NavLink>
          </Nav>
          <Nav>
            <NavLink to="/" className="form-sub">
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

        <ModalLogin show={isShowModalLogin} handleClose={handleClose} />
      </Container>
    </>
  );
};

export default Header;
