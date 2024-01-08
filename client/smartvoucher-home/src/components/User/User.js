import React from "react";
import { Link, Routes, Route } from "react-router-dom";
import "../User/User.scss";
import Profile from "./Profile";
import Order from "./Order";
import { Tab, Nav } from "react-bootstrap";

const User = () => {

  return (
    <>
      <div className="p-3 d-flex justify-content-between">
        <div className="list-l">
          <Tab.Container id="left-tabs-example" defaultActiveKey="first">
                <Nav variant="pills" className="flex-column">
                    <Nav.Link eventKey="first" className="bd">
                      <Link to="/User/Profile" className="sideBar">Profile</Link>
                    </Nav.Link>
                    <Nav.Link eventKey="second" className="bd">
                      <Link to="/User/Order" className="sideBar">Order</Link>
                    </Nav.Link>
                </Nav>
          </Tab.Container>
        </div>
        <div>
          <Routes>
            <Route path="Profile" element={<Profile />} />
            <Route path="Order" element={<Order />} />
          </Routes>
        </div>
      </div>
    </>
  );
};

export default User;
