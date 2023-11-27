import "./App.scss";
import React from "react";
import { Link, Route, Routes, useLocation } from "react-router-dom";

import {
  Category,
  Chain,
  Discount,
  Merchant,
  Serial,
  Warehouse,
} from "./modules";
import { schemaSignIn } from "./validate";
import { signIn, signOut } from "./queries/auth.queries";
import { useDispatch, useSelector } from "react-redux";
import { toast } from "react-toastify";
import { useForm } from "react-hook-form";
import {
  logIn,
  logOut,
  selectAccessToken,
  selectRefreshToken,
} from "./redux/features/authSlice";
import { yupResolver } from "@hookform/resolvers/yup";
const list = [
  "Merchant",
  "Serial",
  "Chain",
  "Warehouse",
  "Category",
  "Discount",
];
function App() {
  const [activetab, setActivetab] = React.useState(0);
  const location = useLocation();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: yupResolver(schemaSignIn),
  });
  const dispatch = useDispatch();
  const token = useSelector(selectAccessToken);
  const refreshToken = useSelector(selectRefreshToken);

  const onSubmitSignIn = async (data) => {
    signIn(data)
      .then((rs) => {
        if (rs) {
          toast.success(rs.message);
          dispatch(logIn(rs.data));
        }
      })
      .catch((err) => toast.error(err.response.data.message));
  };
  React.useState(() => {
    let tab = 0;
    tab = list?.findIndex(
      (item) => location.pathname.replace("/", "") === item.toLowerCase()
    );
    setActivetab(tab);
  }, []);
  return (
    <>
      <div className="nav">
        <h2>VOUCHER ADMIN</h2>
        {list?.map((item, key) => {
          return (
            <Link to={`${item.toLowerCase()}`}>
              <div
                className={key === activetab ? "nav-item active" : "nav-item"}
                key={key}
                onClick={() => setActivetab(key)}
              >
                {item}
              </div>
            </Link>
          );
        })}
        {token ? (
          <div
            className="logout"
            onClick={() => {
              dispatch(logOut());
              signOut(refreshToken)
                .then((rs) => {
                  if (rs) {
                    toast.success(rs.message);
                  }
                })
                .catch((err) => toast.error(err.response.data.message));
            }}
          >
            Logout
          </div>
        ) : (
          <form onSubmit={handleSubmit(onSubmitSignIn)}>
            <div>
              <label>Email</label>
              <input {...register("email")} />
              {errors.email && <p>{errors.email.message.toString()}</p>}
            </div>
            <div>
              <label>Password</label>
              <input {...register("password")} type="password" />
              {errors.password && <p>{errors.password.message.toString()}</p>}
            </div>
            <input type="submit" value="Đăng nhập" />
          </form>
        )}
      </div>
      <Routes>
        <Route path="/merchant" element={<Merchant />}></Route>
        <Route path="/serial" element={<Serial />}></Route>
        <Route path="/chain" element={<Chain />}></Route>
        <Route path="/warehouse" element={<Warehouse />}></Route>
        <Route path="/category" element={<Category />}></Route>
        <Route path="/discount" element={<Discount />}></Route>
      </Routes>
    </>
  );
}

export default App;
