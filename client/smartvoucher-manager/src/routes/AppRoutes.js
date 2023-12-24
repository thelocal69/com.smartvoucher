import { Route, Routes } from "react-router-dom";
import HomePage from "../components/HomePage";
import Merchant from "../components/Merchant";
import NotFound from "../components/NotFound";
import Chain from "../components/Chain";
import Login from "../components/Login";
import PrivateRoutes from "./PrivateRoutes";

const AppRoutes = () => {
  return (
    <>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/Home" element={<PrivateRoutes Component={HomePage}/>} />
        <Route path="/Merchant" element={<PrivateRoutes Component={Merchant}/>} />
        <Route path="/Chain" element={<PrivateRoutes Component={Chain}/>} />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </>
  );
};

export default AppRoutes;
