import React from "react";
import { Container, Modal } from "react-bootstrap";
import "../Security/Account.scss";
import {
  loginUser,
  registerUser,
  forgotPassword,
  resetPassword,
  registerVerify,
} from "../../services/AccountServices";
import { toast } from "react-toastify";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { logIn } from "../../Redux/data/AuthSlice";
import Loading from "../Util/Loading";

const Account = (props) => {
  const { show, handleClose } = props;

  const [isShowPassword, setIsShowPassword] = React.useState(false);
  const [isShowPasswordConfirm, setIsShowPasswordConfirm] = React.useState(false);
  const [authMode, setAuthMode] = React.useState("signin");

  const [loading, setLoading] = React.useState(false);
  const [email, setEmail] = React.useState("");
  const [emailRegister, setEmailRegister] = React.useState("");
  const [forgotEmail, setForgotEmail] = React.useState("");
  const [newPassword, setNewPassword] = React.useState("");
  const [confirmNewPassword, setConfirmNewPassword] = React.useState("");
  const [password, setPassword] = React.useState("");
  const [passwordRegister, setPasswordRegister] = React.useState("");
  const [confirmPassword, setConfirmPassword] = React.useState("");
  const [phone, setPhone] = React.useState("");
  const [token, setToken] = React.useState("");

  const obj = {
    email: email,
    password: password,
  };

  const objRegister = {
    email: emailRegister,
    password: passwordRegister,
    phone: phone,
  };

  const objResetPassword = {
    token: token,
    email: forgotEmail,
    newPassword: newPassword,
  };

  const objVerifyAccount = {
    token: token,
  }

  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleLogin = async () => {
    if (!email || !password) {
      toast.error("Email or password is required !");
      return;
    }
    setLoading(true);
    await loginUser(obj)
      .then((rs) => {
        if (rs) {
          dispatch(logIn(rs.data));
          toast.success(rs.message);
          navigate("/");
          setLoading(false);
          handleCloseReset();
        }
      })
      .catch((err) => {
        setLoading(false);
      });
  };

  const handleRegister = async () => {
    if (!emailRegister || !passwordRegister || !confirmPassword || !phone) {
      toast.error("Please fill all !");
      return;
    }
    if (passwordRegister !== confirmPassword) {
      toast.error("Confirm password is not correct !");
      return;
    }
    setLoading(true);
    await registerUser(objRegister)
      .then((rs) => {
        if (rs) {
          toast.success(rs.data);
          setLoading(false);
          setAuthMode("verifyEmail");
        }
      })
      .catch((err) => {
        toast.error(err.message);
        setLoading(false);
      });
  };

  const handleVerifyAccount = async () => {
    if (!token) {
      toast.error("Please fill token !");
      return;
    }
    setLoading(true);
    await registerVerify(objVerifyAccount)
      .then((rs) => {
        if (rs) {
          toast.success(rs.data);
          handleCloseReset();
          setLoading(false);
          navigate("/");
        }
      })
      .catch((err) => {
        toast.error(err.message);
        setLoading(false);
      });
  };

  const handleForgotPassword = async () => {
    if (!forgotEmail || !newPassword || !confirmNewPassword) {
      toast.error("Please fill all !");
      return;
    }
    if (confirmNewPassword !== newPassword) {
      toast.error("Confirm password and new password won't match !");
      return;
    }
    setLoading(true);
    await forgotPassword(forgotEmail)
      .then((rs) => {
        if (rs) {
          toast.success(rs.data);
          setAuthMode("verifyToken");
          setLoading(false);
        }
      })
      .catch((err) => {
        toast.error(err.message);
        setLoading(false);
      });
  };

  const handleSetPassword = async () => {
    if (!token) {
      toast.error("Please fill token !");
      return;
    }
    setLoading(true);
    await resetPassword(objResetPassword)
      .then((rs) => {
        if (rs) {
          toast.success(rs.data);
          setLoading(false);
          handleCloseReset();
          navigate("/");
        }
      })
      .catch((err) => {
        toast.error(err.message);
        setLoading(false);
      });
  };

  const handleKeyPress = (event) => {
    if (event.code === "Enter") {
      handleLogin();
      handleRegister();
    }
  };

  const changeAuthMode = () => {
    setAuthMode("signin");
  };

  const changeAuthModeV2 = () => {
    setAuthMode("signup");
  };

  const handleCloseReset = () => {
    handleClose();
    setAuthMode("signin");
    setEmail("");
    setPassword("");
    setEmailRegister("");
    setPasswordRegister("");
    setConfirmPassword("");
    setPhone("");
    setForgotEmail("");
    setNewPassword("");
    setConfirmNewPassword("");
    setToken("");
    setIsShowPassword(false);
  };

  return (
    <>
      <Container>
        <Modal
          show={show}
          onHide={handleCloseReset}
          size="lg"
          aria-labelledby="contained-modal-title-vcenter"
          centered
          backdrop="static"
        >
          <Modal.Header closeButton>
            <div>
              <button onClick={() => changeAuthMode()} className="vK">
                Sign In
              </button>
              <button onClick={() => changeAuthModeV2()} className="vK">
                Sign Up
              </button>
            </div>
          </Modal.Header>
          <Modal.Body>
            {authMode === "signin" && (
              <>
                <div className="tabs-content">
                  <div className="login_socnet"></div>
                  <div className="f">
                    <div class="form-group p-2">
                      <input
                        type="email"
                        class="form-control form-control-user p-2"
                        id="exampleInputEmail"
                        aria-describedby="emailHelp"
                        placeholder="Enter Email Address..."
                        required
                        value={email}
                        onChange={(event) => setEmail(event.target.value)}
                      />
                    </div>
                    <div class="form-group p-2 input-pass">
                      <input
                        type={isShowPassword ? "text" : "password"}
                        class="form-control form-control-user p-2"
                        id="exampleInputPassword"
                        placeholder="Password"
                        required
                        value={password}
                        onChange={(event) => setPassword(event.target.value)}
                        onKeyDown={(event) => handleKeyPress(event)}
                        tabIndex={0}
                      />
                      <i
                        className={
                          isShowPassword
                            ? "fa-solid fa-eye"
                            : "fa-solid fa-eye-slash"
                        }
                        hidden={password ? false : true}
                        onClick={() => setIsShowPassword(!isShowPassword)}
                      ></i>
                    </div>
                    <div className="form-group btn-login p-2 w-100 mt-3">
                      <button
                        className={
                          email && password && loading === false ? "active" : ""
                        }
                        disabled={email && password ? false : true}
                        hidden={email && password ? false : true}
                        onClick={() => handleLogin()}
                      >
                        {loading && (
                          <>
                            <Loading fileName={"Login"} />
                          </>
                        )}
                        <span hidden={loading ? true : false}>login</span>
                      </button>
                    </div>
                  </div>
                  <div className="forgot-password f text-center" action="">
                    <input id="forgot-password-toggle" type="checkbox" />
                    <label for="forgot-password-toggle">forgot password?</label>
                    <div className="forgot-password-content flex-column">
                      <div class="form-group p-2">
                        <input
                          type="email"
                          class="form-control form-control-user p-2"
                          id="exampleInputEmail"
                          aria-describedby="emailHelp"
                          placeholder="Enter Email..."
                          required
                          value={forgotEmail}
                          onChange={(event) =>
                            setForgotEmail(event.target.value)
                          }
                        />
                      </div>
                      <div class="form-group p-2 input-pass">
                        <input
                          type={isShowPassword ? "text" : "password"}
                          class="form-control form-control-user p-2"
                          id="exampleInputPassword"
                          placeholder="New password"
                          required
                          value={newPassword}
                          onChange={(event) =>
                            setNewPassword(event.target.value)
                          }
                          tabIndex={0}
                        />
                        <i
                          className={
                            isShowPassword
                              ? "fa-solid fa-eye"
                              : "fa-solid fa-eye-slash"
                          }
                          hidden={newPassword ? false : true}
                          onClick={() => setIsShowPassword(!isShowPassword)}
                        ></i>
                      </div>
                      <div class="form-group p-2 input-pass">
                        <input
                          type={isShowPasswordConfirm ? "text" : "password"}
                          class="form-control form-control-user p-2"
                          id="exampleInputPassword"
                          placeholder="New password"
                          required
                          value={confirmNewPassword}
                          onChange={(event) =>
                            setConfirmNewPassword(event.target.value)
                          }
                          tabIndex={0}
                        />
                        <i
                          className={
                            isShowPasswordConfirm
                              ? "fa-solid fa-eye"
                              : "fa-solid fa-eye-slash"
                          }
                          hidden={confirmNewPassword ? false : true}
                          onClick={() => setIsShowPasswordConfirm(!isShowPasswordConfirm)}
                        ></i>
                      </div>
                      <div className="form-group btn-primary p-2 w-100 mt-3">
                        <button
                          className="Lj"
                          onClick={() => handleForgotPassword()}
                        >
                          {loading && (
                            <>
                              <Loading fileName={"Reset"} />
                            </>
                          )}
                          <span hidden={loading ? true : false}>Reset</span>
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </>
            )}
            {authMode === "signup" && (
              <>
                <div className="tabs-content">
                  <div className="login_socnet"></div>
                  <div className="f">
                    <div class="form-group p-2">
                      <input
                        type="email"
                        class="form-control form-control-user p-2"
                        placeholder="Enter Email Address..."
                        value={emailRegister}
                        onChange={(event) =>
                          setEmailRegister(event.target.value)
                        }
                      />
                    </div>
                    <div class="form-group p-2 input-pass">
                      <input
                        type={isShowPassword ? "text" : "password"}
                        class="form-control form-control-user p-2"
                        placeholder="Password"
                        required
                        value={passwordRegister}
                        onChange={(event) =>
                          setPasswordRegister(event.target.value)
                        }
                      />
                      <i
                        className={
                          isShowPassword
                            ? "fa-solid fa-eye"
                            : "fa-solid fa-eye-slash"
                        }
                        hidden={passwordRegister ? false : true}
                        onClick={() => setIsShowPassword(!isShowPassword)}
                      ></i>
                    </div>
                    <div class="form-group p-2 input-pass">
                      <input
                        type={isShowPasswordConfirm ? "text" : "password"}
                        class="form-control form-control-user p-2"
                        placeholder="Confirm password"
                        required
                        value={confirmPassword}
                        onChange={(event) =>
                          setConfirmPassword(event.target.value)
                        }
                      />
                      <i
                        className={
                          isShowPasswordConfirm
                            ? "fa-solid fa-eye"
                            : "fa-solid fa-eye-slash"
                        }
                        hidden={confirmPassword ? false : true}
                        onClick={() => setIsShowPasswordConfirm(!isShowPasswordConfirm)}
                      ></i>
                    </div>
                    <div class="form-group p-2">
                      <input
                        type="text"
                        class="form-control form-control-user p-2"
                        placeholder="Enter phone number..."
                        required
                        value={phone}
                        onChange={(event) => setPhone(event.target.value)}
                        onKeyDown={(event) => handleKeyPress(event)}
                      />
                    </div>
                    <div className="form-group btn-login p-2 w-100 mt-3">
                      <button onClick={() => handleRegister()}>
                        {loading && (
                          <>
                            <Loading fileName={"Sign up"} />
                          </>
                        )}
                        <span hidden={loading ? true : false}>Sign Up</span>
                      </button>
                    </div>
                  </div>
                </div>
              </>
            )}
            {authMode === "verifyToken" && (
              <>
                <div>
                  <div class="form-group p-2">
                    <input
                      type="text"
                      class="form-control form-control-user p-2"
                      placeholder="Enter token..."
                      required
                      value={token}
                      onChange={(event) => setToken(event.target.value)}
                    />
                  </div>
                  <div className="form-group btn-primary p-2 w-100 mt-3">
                    <button className="Lj" onClick={() => handleSetPassword()}>
                      {loading && (
                        <>
                          <Loading fileName={"Verify"} />
                        </>
                      )}
                      <span
                        className="text-center"
                        hidden={loading ? true : false}
                      >
                        Xác nhận
                      </span>
                    </button>
                  </div>
                </div>
              </>
            )}
            {authMode === "verifyEmail" && (
              <>
                <div>
                  <div class="form-group p-2">
                    <input
                      type="text"
                      class="form-control form-control-user p-2"
                      placeholder="Enter token..."
                      required
                      value={token}
                      onChange={(event) => setToken(event.target.value)}
                    />
                  </div>
                  <div className="form-group btn-primary p-2 w-100 mt-3">
                    <button
                      className="Lj"
                      onClick={() => handleVerifyAccount()}
                    >
                      {loading && (
                        <>
                          <Loading fileName={"Verify"} />
                        </>
                      )}
                      <span
                        className="text-center"
                        hidden={loading ? true : false}
                      >
                        Xác nhận
                      </span>
                    </button>
                  </div>
                </div>
              </>
            )}
          </Modal.Body>
        </Modal>
      </Container>
    </>
  );
};

export default Account;