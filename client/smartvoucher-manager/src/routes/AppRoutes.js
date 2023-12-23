import { Route, Routes } from "react-router-dom";
import HomePage from "../components/HomePage";
import Merchant from "../components/Merchant";
import NotFound from "../components/NotFound";
import Chain from "../components/Chain";

const AppRoutes = () => {
  return (
    <>
    <Routes>
        <Route path="/" element={<HomePage />} />
        <Route
          path="/Merchant"
          element={<Merchant />}
          />
          <Route path="/Chain" element={<Chain />} />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </>
  )
}

export default AppRoutes