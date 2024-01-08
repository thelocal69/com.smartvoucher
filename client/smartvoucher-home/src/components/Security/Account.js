import React from "react";
import { Container, Modal } from "react-bootstrap";
import "../Security/Account.scss";
import { loginUser, registerUser } from "../../services/AccountServices";
import { toast } from "react-toastify";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { logIn } from "../../Redux/data/AuthSlice";
import Loading from "../Util/Loading";

const Account = (props) => {
  const { show, handleClose } = props;

  const [isShowPassword, setIsShowPassword] = React.useState(false);
  const [authMode, setAuthMode] = React.useState("signin");

  const [loading, setLoading] = React.useState(false);
  const [email, setEmail] = React.useState("");
  const [emailRegister, setEmailRegister] = React.useState("");
  const [password, setPassword] = React.useState("");
  const [passwordRegister, setPasswordRegister] = React.useState("");
  const [confirmPassword, setConfirmPassword] = React.useState("");
  const [phone, setPhone] = React.useState("");

  const obj = {
    email: email,
    password: password,
  };

  const objRegister = {
    email: emailRegister,
    password: passwordRegister,
    phone: phone,
  };

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
          handleClose();
          setEmail("");
          setPassword("");
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
          handleClose();
          setEmailRegister("");
          setPasswordRegister("");
          setConfirmPassword("");
          setPhone("");
        }
      })
      .catch((err) => {
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
              <button onClick={() => changeAuthMode()}>Sign In</button>
              <button onClick={() => changeAuthModeV2()}>Sign Up</button>
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
                  <div className="forgot-password f" action="">
                    <input id="forgot-password-toggle" type="checkbox" />
                    <label for="forgot-password-toggle">forgot password?</label>
                    <div className="forgot-password-content">
                      <input
                        type="email"
                        placeholder="enter your email"
                        required
                      />
                      <input type="submit" value="go" />
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
                        type={isShowPassword ? "text" : "password"}
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
                          isShowPassword
                            ? "fa-solid fa-eye"
                            : "fa-solid fa-eye-slash"
                        }
                        hidden={confirmPassword ? false : true}
                        onClick={() => setIsShowPassword(!isShowPassword)}
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
          </Modal.Body>
        </Modal>
      </Container>
    </>
  );
};

export default Account;
