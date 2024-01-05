import React from "react";
import {
  Container,
  Navbar,
  Nav,
  Col,
  Image,
  Badge,
} from "react-bootstrap";
import "../Header/Header.scss";
import { NavLink } from "react-router-dom";

const Header = () => {
  return (
    <>
      <div className="custom-header">
        <div className="bg">
        <Container>
        <div className="p-2 bg d-flex justify-content-between sub-top">
          <div>
          <i class="fa-solid fa-chevron-left"></i>
          <i class="fa-solid fa-chevron-right"></i>
          <span>dsd</span>
          </div>
          <div className="d-flex">
            <div>
              <a>
                <i class="fa-solid fa-mobile-screen-button"></i>
                Hướng dẫn mua hàng
              </a>
            </div>
            <div>
              <a>
                <i class="fa-solid fa-gift"></i>
                Ưu đãi khách hàng
              </a>
            </div>
            <div>
              <a>
                <i class="fa-regular fa-handshake"></i>
                Thông tin liên hệ
              </a>
            </div>
          </div>
        </div>
        </Container>
        </div>
        <Navbar
          expand="lg"
          className="bg-body-tertiary custom-bg d-flex"
          data-bs-theme="dark"
        >
          <Container>
            <Nav className="me-auto">
              <NavLink to="/" className="custom-font">
                SMART VOUCHER
              </NavLink>
            </Nav>
            <Nav className="me-auto">
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
            <Nav className="me-auto">
              <Navbar>
                <Col xs={3} md={2}>
                  <NavLink to="/Profile">
                    <Image src="" roundedCircle className="avatar" />
                  </NavLink>
                </Col>
              </Navbar>
              <NavLink to="/Login" className="custom-font mx-2">
                <i class="fa-regular fa-circle-user"></i>
              </NavLink>
              <NavLink to="/Login" className="custom-font">
                Login
              </NavLink>
              <span className="custom-font mx-2">/</span>
              <NavLink to="/Login" className="custom-font">
                Register
              </NavLink>
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
          <div className="py-3 d-flex justify-content-between">
            <Nav className="me-auto">
              <NavLink to="/" className="sub-font">
                <i class="fa-regular fa-eye"></i>
                Sản phẩm bạn vừa xem
              </NavLink>
            </Nav>
            <Nav className="me-auto">
              <NavLink to="/" className="sub-font">
                <i class="fa-solid fa-fire"></i>
                Sản phẩm mua nhiều
              </NavLink>
            </Nav>
            <Nav className="me-auto">
              <NavLink to="/" className="sub-font">
                <i class="fa-solid fa-percent"></i>
                Sản phẩm khuyến mại
              </NavLink>
            </Nav>
            <Nav className="me-auto">
              <NavLink to="/" className="sub-font">
                <i class="fa-regular fa-map"></i>
                Đại lý giao dịch
              </NavLink>
            </Nav>
            <Nav className="me-auto">
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
            <a>
              <i class="fa-solid fa-bars"></i>
              Danh mục sản phẩm
            </a>
          </div>
          <div className="d-flex">
            <div>
              <a>
                <i class="fa-solid fa-mobile-screen-button"></i>
                Thủ thuật & Tin Tức
              </a>
            </div>
            <div>
              <a>
                <i class="fa-solid fa-gift"></i>
                Giới thiệu bạn bè
              </a>
            </div>
            <div>
              <a>
                <i class="fa-regular fa-handshake"></i>
                Liên hệ hợp tác
              </a>
            </div>
            <div>
              <a>
                <i class="fa-regular fa-gem"></i>
                Ưu đãi khách hàng VIP
              </a>
            </div>
          </div>
        </div>
      </Container>
    </>
  );
};

export default Header;
