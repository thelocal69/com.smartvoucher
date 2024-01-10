import { useSelector } from "react-redux";
import { selectAccessToken } from "../Redux/data/AuthSlice";
import { Navigate } from "react-router-dom";

const PrivateRoutes = ({ Component }) => {
  const auth = useSelector(selectAccessToken);
  return auth ? <Component /> : <Navigate to="/" />;
};

export default PrivateRoutes;
