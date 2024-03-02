import React from "react";
import { Routes, Route, useNavigate } from "react-router-dom";
import "../User/User.scss";
import Profile from "./Profile";
import Order from "./Order";
import OrderInfor from "../Home/Detail/OrderInfor";
import ChangePassword from "./ChangePassword";
import { Col, Container, Row } from "react-bootstrap";
import Wishlist from "./Wishlist";

const User = () => {

  const navigate = useNavigate();

  return (
    <>
      <Container>
        <Row xs={1} md={2} className="xx justify-content-md-between">
          <Col md={3}>
            <div className="list-l">
              <div className="padluon papa">
                <div className="sideBar oL" onClick={() => {
                  navigate("/User/Profile");
                }}>
                  <i class="fa-solid fa-user"></i>
                  <span>Profile</span>
                </div>
                <div className="sideBar oL" onClick={() => {
                  navigate("/User/Order");
                }}>
                  <i class="fa-solid fa-cart-shopping"></i>
                  <span>Order</span>
                </div>
                <div className="sideBar oL" onClick={() => {
                  navigate("/User/Security");
                }}>
                  <i class="fa-solid fa-user-lock"></i>
                  <span>Change password</span>
                </div>
                <div className="sideBar oL" onClick={() => {
                  navigate("/User/Wishlist");
                }}>
                  <i class="fa-solid fa-heart"></i>
                  <span>Wish list</span>
                </div>
              </div>
            </div>
          </Col>
          <Col md={9}>
            <div className="">
              <Routes>
                <Route path="Profile" element={<Profile />} />
                <Route path="Order" element={<Order />} />
                <Route path="Security" element={<ChangePassword />} />
                <Route path="Wishlist" element={<Wishlist />} />
                <Route path="Infor/:id/*" element={<OrderInfor />} />
              </Routes>
            </div>
          </Col>
        </Row>
      </Container>
    </>
  );
};

export default User;
