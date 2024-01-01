import { Routes, Route } from "react-router-dom";
import HomePage from "../components/Home/HomePage";
import Login from "../components/Security/Login";

const AppRoutes = () => {
  return (
    <>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/Login" element={<Login />} />
      </Routes>
    </>
  );
};

export default AppRoutes;
