import { Routes, Route } from "react-router-dom";
import HomePage from "../components/Home/HomePage";
import NotFound from "../components/Error/NotFound";
import PrivateRoutes from "../Routes/PrivateRoutes";
import User from "../components/User/User";
import ProductInfor from "../components/Home/Detail/ProductInfor";
import Cart from "../components/Home/Cart";
import CategoryInfor from "../components/Home/Detail/CategoryInfor";

const AppRoutes = () => {
  return (
    <>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/Product/Detail/:id" element={<ProductInfor />} />
        <Route path="/User/*" element={<PrivateRoutes Component={User} />} />
        <Route path="/Cart" element={<Cart />} />
        <Route path="/Category/:name" element={<CategoryInfor />} />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </>
  );
};

export default AppRoutes;
