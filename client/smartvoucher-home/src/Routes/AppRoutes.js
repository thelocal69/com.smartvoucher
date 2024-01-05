import { Routes, Route } from "react-router-dom";
import HomePage from "../components/Home/HomePage";
import NotFound from "../components/Error/NotFound";

const AppRoutes = () => {
  return (
    <>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </>
  );
};

export default AppRoutes;
