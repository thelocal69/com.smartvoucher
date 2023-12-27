import { Route, Routes } from "react-router-dom";
import HomePage from "../components/HomePage";
import Merchant from "../components/Merchant";
import NotFound from "../components/NotFound";
import Chain from "../components/Chain";
import Login from "../components/Login";
import PrivateRoutes from "./PrivateRoutes";
import Label from "../components/Label";
import Profile from "../components/Profile";
import Category from "../components/Category";
import DiscountType from "../components/DiscountType";
import Store from "../components/Store";
import Warehouse from "../components/Warehouse";
import Serial from "../components/Serial";
import User from "../components/User";

const AppRoutes = () => {
  return (
    <>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/Home" element={<PrivateRoutes Component={HomePage}/>} />
        <Route path="/Merchant" element={<PrivateRoutes Component={Merchant}/>} />
        <Route path="/Chain" element={<PrivateRoutes Component={Chain}/>} />
        <Route path="/Label" element={<PrivateRoutes Component={Label}/>} />
        <Route path="/Category" element={<PrivateRoutes Component={Category}/>} />
        <Route path="/Discount type" element={<PrivateRoutes Component={DiscountType}/>} />
        <Route path="/Store" element={<PrivateRoutes Component={Store}/>} />
        <Route path="/Warehouse" element={<PrivateRoutes Component={Warehouse}/>} />
        <Route path="/Serial" element={<PrivateRoutes Component={Serial}/>} />
        <Route path="/User" element={<PrivateRoutes Component={User}/>} />
        <Route path="/Profile" element={<PrivateRoutes Component={Profile}/>} />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </>
  );
};

export default AppRoutes;
