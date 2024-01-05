import React from "react";
import { Container } from "react-bootstrap";
import Loading from "../Util/Loading";
import "../Security/Account.scss";

const Account = () => {
  const [isShowPassword, setIsShowPassword] = React.useState(false);

  const [loading, setLoading] = React.useState(false);
  const [email, setEmail] = React.useState("");
  const [password, setPassword] = React.useState("");

  const handleLogin = async () => {};

  const handleKeyPress = (event) => {
    if (event.code === "Enter") {
      //handleLogin();
    }
  };

  return (
    <>
      <Container>
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
            <form action="">
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
            </form>
            <form className="forgot-password" action="">
              <input id="forgot-password-toggle" type="checkbox" />
              <label for="forgot-password-toggle">forgot password?</label>
              <div className="forgot-password-content">
                <input type="email" placeholder="enter your email" required />
                <input type="submit" value="go" />
              </div>
            </form>
          </div>

          <input className="radio" id="tab-2" name="tabs-name" type="radio" />
          <label for="tab-2" class="table">
            <span>Sign up</span>
          </label>
          <div className="tabs-content">
            <div className="login_socnet"></div>
            <form action="">
              <input type="email" placeholder="Email" required />
              <input type="password" placeholder="Password" required />
              <input type="password" placeholder="Confirm password" required />
              <input type="text" placeholder="Phone number" required />
              <input type="submit" value="Sign Up" />
            </form>
          </div>
        </div>
      </Container>
    </>
  );
};
export default Account;
