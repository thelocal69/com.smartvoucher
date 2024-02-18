import React from "react";
import { Container } from "react-bootstrap";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { GoogleLogin } from "@react-oauth/google";
import { oauth2GoogleLogin } from "../../services/AccountServices";
import { toast } from "react-toastify";
import { logIn } from "../../Redux/data/AuthSlice";
import { getUserInfor } from "../../services/UserServices";
import { userInfor } from "../../Redux/data/UserSlice";

const OAuth2Login = (props) => {
  const { handleClose } = props;

  const dispatch = useDispatch();
  const navigate = useNavigate();

  const getInfor = async () => {
    await getUserInfor()
      .then((rs) => {
        if (rs) {
          dispatch(
            userInfor(rs.data)
          );
        }
      })
      .catch((err) => console.log(err.message));
  }

  const onSuccess = async (credentialResponse) => {
    const token = credentialResponse.credential;
    const objGoogle = {
      token: token,
    };
    await oauth2GoogleLogin(objGoogle)
      .then((rs) => {
        toast.success(rs.message);
        dispatch(logIn(rs.data));
        getInfor();
        navigate("/");
        handleClose();
      })
      .catch((err) => console.log(err));
  };

  return (
    <>
      <Container>
        <div className="pb-3">
          <GoogleLogin onSuccess={onSuccess} useOneTap />
        </div>
      </Container>
    </>
  );
};

export default OAuth2Login;
