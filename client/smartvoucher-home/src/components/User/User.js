import React from "react";
import { Link, useLocation, Routes, Route } from "react-router-dom";
import "../User/User.scss";
import Profile from "./Profile";
import Order from "./Order";

const User = () => {
  const location = useLocation();
  const [tabs, setTabs] = React.useState(0);

  const list = ["Profile", "Order"];

  React.useState(() => {
    let tab = 0;
    tab = list?.findIndex(
      (item) => location.pathname.replace("/", "") === item
    );
    setTabs(tab);
  }, []);

  console.log(tabs);

  return (
    <>
      <div className="p-3 d-flex justify-content-between">
        <div className="list-l">
          {list?.map((item, key) => {
            return (
              <>
                <div className={key === tabs ? "p-3 bd active" : "p-3 bd"}>
                  <Link
                    to={`/User/${item}`}
                    className="sideBar"
                    key={key}
                    onClick={() => setTabs(key)}
                  >
                      <i class="fa-solid fa-user"></i>
                      {item}
                  </Link>
                </div>
              </>
            );
          })}
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
