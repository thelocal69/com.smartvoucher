import { Wrapper } from "components/Wrapper/Wrapper";
import "./Profile.scss";
import React, { useState } from "react";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import ShoppingCartIcon from "@mui/icons-material/ShoppingCart";
import { AccountTab } from "./components/AccountTab";
import { OrdersTab } from "./components/OrdersTab";
import { useSelector } from "react-redux";
import { selectAccessToken } from "redux/features/auth/authSlice";
import { getUserInfo } from "queries/auth";
import { toast } from "react-toastify";

const TABS = [
  {
    tab: "Tài khoản",
    ic: <AccountCircleIcon />,
  },
  {
    tab: "Lịch sử đơn hàng",
    ic: <ShoppingCartIcon />,
  },
];
export const Profile = () => {
  const [activeTab, setActiveTab] = React.useState(0);
  const token = useSelector(selectAccessToken);
  const [info, setInfo] = useState<any>();
  React.useEffect(() => {
    if (token)
      getUserInfo()
        .then((rs: any) => {
          if (rs) {
            setInfo(rs.data);
          }
        })
        .catch((err: any) => toast.error(err.message));
  }, [token]);
  return (
    <Wrapper>
      {token ? (
        <div className="profile">
          <div className="left">
            <div className="tabs">
              {TABS.map((t, key) => {
                return (
                  <div
                    className={activeTab === key ? "tab active" : "tab"}
                    onClick={() => setActiveTab(key)}
                  >
                    {t.ic} <span>{t.tab}</span>
                  </div>
                );
              })}
            </div>
          </div>
          <div className="right">
            {activeTab === 0 && info && <AccountTab data={info} />}
            {activeTab === 1 && <OrdersTab info={info} />}
          </div>
        </div>
      ) : (
        <div className="warn"> Please sign in !</div>
      )}
    </Wrapper>
  );
};
