import { Route, Routes } from "react-router-dom";
import HomePage from "../components/HomePage";
import Merchant from "../components/Merchant";
import NotFound from "../components/NotFound";

const AppRoutes = () => {
  return (
    <>
    <Routes>
        <Route path="/" element={<HomePage />} />
        <Route
          path="/Merchant"
          element={<Merchant />}
          />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </>
  )
}

export default AppRoutes