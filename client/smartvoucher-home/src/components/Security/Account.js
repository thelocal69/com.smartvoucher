import React from "react";
import { Container, Modal } from "react-bootstrap";
import Loading from "../Util/Loading";
import "../Security/Account.scss";
import { loginUser } from "../../services/AccountServices";
import { toast } from "react-toastify";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { logIn } from "../../Redux/data/AuthSlice";

const Account = (props) => {
  const { show, handleClose } = props;

  const [isShowPassword, setIsShowPassword] = React.useState(false);

  const [loading, setLoading] = React.useState(false);
  const [email, setEmail] = React.useState("");
  const [password, setPassword] = React.useState("");

  const obj = {
    email: email,
    password: password,
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
        }
      })
      .catch((err) => {
        setLoading(false);
      });
  };

  const handleKeyPress = (event) => {
    if (event.code === "Enter") {
      handleLogin();
    }
  };

  return (
    <>
      <Container>
        <Modal
          show={show}
          onHide={handleClose}
          size="lg"
          aria-labelledby="contained-modal-title-vcenter"
          centered
          backdrop="static"
        >
          <Modal.Header closeButton></Modal.Header>
          <Modal.Body>
            <div className="tabs">
              <input
                className="radio"
                id="tab-1"
                name="tabs-name"
                type="radio"
                checked
              />
              <label for="tab-1" class="table">
                <span>Login</span>
              </label>
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

              <input
                className="radio"
                id="tab-2"
                name="tabs-name"
                type="radio"
              />
              <label for="tab-2" class="table">
                <span>Sign up</span>
              </label>
              <div className="tabs-content">
                <div className="login_socnet"></div>
                <div className="f">
                  <input type="email" placeholder="Email" required />
                  <input type="password" placeholder="Password" required />
                  <input
                    type="password"
                    placeholder="Confirm password"
                    required
                  />
                  <input type="text" placeholder="Phone number" required />
                  <input type="submit" value="Sign Up" />
                </div>
              </div>
            </div>
          </Modal.Body>
        </Modal>
      </Container>
    </>
  );
};
export default Account;
