import React, { useState } from "react";
import "./Header.scss";
import { Wrapper } from "../Wrapper/Wrapper";
import SearchIcon from "@mui/icons-material/Search";
import { selectAccessToken } from "redux/features/auth/authSlice";
import Logo from "assets/logo.png";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import { AuthPopUp } from "components/AuthPopUp/AuthPopUp";
import { useDispatch, useSelector } from "react-redux";
import { toast } from "react-toastify";
import { logOut } from "redux/features/auth/authSlice";
import { logOutAsync } from "queries/auth";
import { selectRefreshToken } from "redux/features/auth/authSlice";
import { getUserInfo } from "queries/auth";
import { useNavigate } from "react-router-dom";
import { getAllWarehouse } from "queries/warehouse";
export const Header = () => {
  const [isShowAuthPopup, setIsShowAuthPopup] = React.useState(false);
  const dispatch = useDispatch();
  const token = useSelector(selectAccessToken);
  const refreshToken = useSelector(selectRefreshToken);
  const [info, setInfo] = useState<any>();
  const [resultSearch, setResultSearch] = useState<any>();
  const [allWarehouse, setAllWarehouse] = React.useState([]);
  const navigate = useNavigate();
  React.useEffect(() => {
    if (token) {
      getUserInfo()
        .then((rs: any) => {
          if (rs) {
            setInfo(rs.data);
          }
        })
        .catch((err: any) => toast.error(err.message));
    }
  }, [token]);
  React.useEffect(() => {
    getAllWarehouse()
      .then((rs: any) => {
        if (rs) {
          setAllWarehouse(rs.data);
        }
      })
      .catch((err: any) => {
        toast.error(err.message);
      });
  }, []);
  return (
    <div className="header-container">
      <Wrapper>
        {isShowAuthPopup && <AuthPopUp setVisibility={setIsShowAuthPopup} />}
        <div className="header-content">
          <div className="logo-wrapper" onClick={() => navigate("/")}>
            <img src={Logo} alt="" />
            <div className="logo">Smart Voucher</div>
          </div>
          <div className="search-wrapper">
            <input
              type="search"
              name=""
              id=""
              placeholder="Tìm kiếm sản phẩm theo tên"
              onChange={(e) => {
                if (e.target.value) {
                  let dt = allWarehouse.filter((item) =>
                    item.name.toLowerCase().includes(e.target.value)
                  );
                  setResultSearch(dt);
                } else {
                  setResultSearch(null);
                }
              }}
            />
            <div className="ic-wrapper">
              <SearchIcon />
            </div>
            {resultSearch && (
              <div className="search-result">
                <div className="label">Kết quả</div>
                {resultSearch.length > 0 ? (
                  <div className="rs">
                    {resultSearch.map((item: any, key: any) => {
                      return (
                        <div
                          className="row"
                          key={key}
                          onClick={() => {
                            navigate(`/warehouse/${item.id}`);
                            setResultSearch(null);
                          }}
                        >
                          {item.name}
                        </div>
                      );
                    })}
                  </div>
                ) : (
                  "No data"
                )}
              </div>
            )}
          </div>
          {token && info ? (
            <div
              className="profile"
              onClick={() => {
                navigate("/profile");
              }}
            >
              <div className="wr">
                <div>Hello {info.fullName || "..."}</div>
                <div className="avatar">
                  {info.avatarUrl ? (
                    // <img
                    //   src={`https://drive.google.com/uc?export=view&id=${info.avatarUrl.slice(
                    //     32,
                    //     info.avatarUrl.length - 18
                    //   )}`}
                    //   alt=""
                    // />
                    <img src={info.avatarUrl} alt="" />
                  ) : (
                    <AccountCircleIcon className="ic" />
                  )}
                </div>
              </div>
              <div
                className="btn"
                onClick={() => {
                  dispatch(logOut());

                  logOutAsync(refreshToken)
                    .then((rs: any) => {
                      if (rs) {
                        toast.success(rs.message);
                      }
                    })
                    .catch((err: any) => toast.error(err.response.data.error));
                }}
              >
                Đăng Xuất
              </div>
            </div>
          ) : (
            <div className="btns" onClick={() => setIsShowAuthPopup(true)}>
              <AccountCircleIcon className="ic" />
              <div className="btn">Đăng nhập</div>/
              <div className="btn">Đăng ký</div>
            </div>
          )}
          {/* <div className="cart">
            <ShoppingCartOutlinedIcon />
            <span>Giỏ hàng</span>
            <div className="num">4</div>
          </div> */}
        </div>
      </Wrapper>
    </div>
  );
};
