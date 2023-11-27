import React from "react";
import "./style.scss";
import CloseIcon from "@mui/icons-material/Close";
import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import { signUp } from "queries/auth";
import { toast } from "react-toastify";
import { signIn } from "queries/auth";
import { schemaSignIn } from "validate";
import { schemaSignUp } from "validate";
import { logIn } from "redux/features/auth/authSlice";
import { useDispatch } from "react-redux";
import { Loading } from "components/Loading/Loading";
import { forgotPassword } from "queries/auth";
import { schemaForgotPassword } from "validate";
import { Link } from "react-router-dom";
// import { getUserGoogleInfo } from "queries/auth";

export const AuthPopUp = ({
  setVisibility,
}: {
  setVisibility: (is: boolean) => void;
}) => {
  const [activeTab, setActiveTab] = React.useState(0);

  const dispatch = useDispatch();
  const [loading, setLoading] = React.useState(false);
  const [isForgotPassword, setIsForgotPassword] = React.useState(false);
  const [validEmail, setValidEmail] = React.useState("");
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<any>({
    resolver: yupResolver(schemaSignUp),
  });
  const {
    register: register2,
    handleSubmit: handleSubmit2,
    formState: { errors: errors2 },
  } = useForm<any>({
    resolver: yupResolver(schemaSignIn),
  });
  const {
    register: register3,
    handleSubmit: handleSubmit3,
    formState: { errors: errors3 },
  } = useForm({
    resolver: yupResolver(schemaForgotPassword),
  });

  const onSubmitSignIn = async (data: any) => {
    let obj = {
      email: data.emailSignin,
      password: data.passwordSignin,
    };
    setLoading(true);
    signIn(obj)
      .then((rs: any) => {
        if (rs) {
          setLoading(false);
          toast.success(rs.message);
          dispatch(logIn(rs.data));
          setVisibility(false);
        }
      })
      .catch((err: any) => {
        toast.error("Something went wrong");
        setLoading(false);
      });
  };
  const onSubmit = async (data: any) => {
    let obj = { ...data };
    delete obj.confirmPassword;
    setLoading(true);
    signUp(obj)
      .then((rs: any) => {
        if (rs) {
          toast.success(rs.message);
          setVisibility(false);
          setLoading(false);
        }
      })
      .catch((er: any) => {
        toast.error("Something went wrong");
        setLoading(false);
      });
  };
  const onForGotPassword = async (data: any) => {
    setLoading(true);
    forgotPassword(data.emailForgot)
      .then((rs: any) => {
        if (rs) {
          setLoading(false);
          toast.success(rs.message);
          setValidEmail(data.emailForgot);
        }
      })
      .catch((er: any) => {
        setLoading(false);
        toast.error("Something went wrong");
      });
  };

  return (
    <div className="auth-pop-up">
      <div className="bg" onClick={() => setVisibility(false)}></div>
      {isForgotPassword ? (
        validEmail === "" ? (
          <div className="content">
            <form onSubmit={handleSubmit3(onForGotPassword)}>
              <div>
                <label>Enter valid email</label>
                <input {...register3("emailForgot")} />
                {errors3.emailForgot && (
                  <p>{errors3.emailForgot.message.toString()}</p>
                )}
              </div>
              {loading ? (
                <div className="loading">
                  <Loading />
                </div>
              ) : (
                <input type="submit" value="Check" />
              )}
              <div
                className="cancel"
                onClick={() => setIsForgotPassword(false)}
              >
                Cancel
              </div>
            </form>
          </div>
        ) : (
          <div className="content">
            <span>Please check your email for reset password link !</span>
          </div>
        )
      ) : (
        <div className="content">
          <div className="ic" onClick={() => setVisibility(false)}>
            <CloseIcon />
          </div>
          <div className="left">
            <div className="tabs">
              {["Đăng nhập", "Đăng ký"].map((item, key) => {
                return (
                  <div
                    className={activeTab === key ? "tab active" : "tab"}
                    key={key}
                    onClick={() => setActiveTab(key)}
                  >
                    {item}
                  </div>
                );
              })}
            </div>
            {activeTab === 0 && (
              <>
                <div className="lb">
                  Đăng nhập để theo dõi đơn hàng, lưu danh sách sản phẩm yêu
                  thích và nhận nhiều ưu đãi hấp dẫn
                </div>
                <form onSubmit={handleSubmit2(onSubmitSignIn)}>
                  <div>
                    <label>Email</label>
                    <input {...register2("emailSignin")} />
                    {errors2.emailSignin && (
                      <p>{errors2.emailSignin.message.toString()}</p>
                    )}
                  </div>
                  <div>
                    <label>Password</label>
                    <input {...register2("passwordSignin")} type="password" />
                    {errors2.passwordSignin && (
                      <p>{errors2.passwordSignin.message.toString()}</p>
                    )}
                  </div>
                  {loading ? (
                    <div className="loading">
                      <Loading />
                    </div>
                  ) : (
                    <input type="submit" value="Đăng nhập" />
                  )}
                </form>
                <Link to="http://localhost:8082/login/oauth2/code/google">
                  <div className="btn-gg" onClick={() => {}}>
                    <img src={require(`assets/gg.png`)} alt="" />
                    Sign in with google
                  </div>
                </Link>
                <div
                  className="lb2"
                  onClick={() => {
                    setIsForgotPassword(true);
                  }}
                >
                  Bạn quên mật khẩu?
                </div>
              </>
            )}
            {activeTab === 1 && (
              <form onSubmit={handleSubmit(onSubmit)}>
                <div>
                  <label>Email</label>
                  <input {...register("email")} />
                  {errors.email && <p>{errors.email.message.toString()}</p>}
                </div>{" "}
                <div>
                  <label>Phone</label>
                  <input {...register("phone")} />
                  {errors.phone && <p>{errors.phone.message.toString()}</p>}
                </div>
                <div>
                  <label>Password</label>
                  <input
                    {...register("password")}
                    type="password"
                    id="passSignUp"
                  />
                  {errors.password && (
                    <p>{errors.password.message.toString()}</p>
                  )}
                </div>
                <div>
                  <label>Confirm Password</label>
                  <input {...register("confirmPassword")} type="password" />
                  {errors.confirmPassword && (
                    <p>{errors.confirmPassword.message.toString()}</p>
                  )}
                </div>
                {loading ? (
                  <div className="loading">
                    <Loading />
                  </div>
                ) : (
                  <input type="submit" />
                )}
              </form>
            )}
          </div>
          <div className="right">
            <img src={require(`assets/homepage/auth.png`)} alt="" />
          </div>
        </div>
      )}
    </div>
  );
};
