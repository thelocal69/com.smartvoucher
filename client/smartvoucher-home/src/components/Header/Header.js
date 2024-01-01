import React from "react";
import { Container, Navbar, Nav, Form, Col, Image, Badge } from "react-bootstrap";
import "../Header/Header.scss";
import { NavLink } from "react-router-dom";

const Header = () => {
  return (
    <>
      <div className="custom-header">
        <Navbar
          expand="lg"
          className="bg-body-tertiary custom-bg dd-flex"
          data-bs-theme="dark"
        >
          <Container>
            <Nav className="me-auto">
              <NavLink to="/" className="custom-font">
                SMART VOUCHER
              </NavLink>
            </Nav>
            <Nav className="me-auto">
              <Form>
                <Form.Group className="d-flex bg-light custom-form">
                  <Form.Control
                    type="search"
                    placeholder="Search"
                    aria-label="Search"
                    className="custom-search"
                  />
                  <div className="custom-btn-bg">
                    <button className="custom-btn my-1" type="submit">
                      <i class="fa-solid fa-magnifying-glass"></i>
                    </button>
                  </div>
                </Form.Group>
              </Form>
            </Nav>
            <Nav className="me-auto">
              <Navbar>
                <Col xs={3} md={2}>
                  <NavLink to="/Profile">
                    <Image
                      src=""
                      roundedCircle
                      className="avatar"
                    />
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
            <Nav >
                <button className="btn btn-light custom-cart">
                <i class="fa-solid fa-cart-shopping"></i>
                    Cart <Badge bg="secondary">9</Badge>
                    <span className="visually-hidden">unread messages</span>
                    </button>
            </Nav>
          </Container>
        </Navbar>
      </div>
    </>
  );
};

export default Header;
