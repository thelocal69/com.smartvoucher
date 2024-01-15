import React from "react";
import { Link, Routes, Route } from "react-router-dom";
import "../User/User.scss";
import Profile from "./Profile";
import Order from "./Order";
import OrderInfor from "../Home/Detail/OrderInfor";

const User = () => {
  return (
    <>
      <div className="p-3 d-flex justify-content-between">
        <div className="list-l">
          <div className="d-flex flex-column p-3">
            <Link to="/User/Profile" className="sideBar oL">
              Profile
            </Link>
            <Link to="/User/Order" className="sideBar oL">
              Order
            </Link>
          </div>
        </div>
        <div>
          <Routes>
            <Route path="Profile" element={<Profile />} />
            <Route path="Order" element={<Order />} />
            <Route path="Infor/:id/*" element={<OrderInfor />}/>
          </Routes>
        </div>
      </div>
    </>
  );
};

export default User;
