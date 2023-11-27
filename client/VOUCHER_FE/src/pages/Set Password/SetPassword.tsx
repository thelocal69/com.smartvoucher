import React from "react";
import { useNavigate } from "react-router-dom";
import "./style.scss";
import { Wrapper } from "components/Wrapper/Wrapper";
import ArrowBackIosIcon from "@mui/icons-material/ArrowBackIos";
import { schemaSetPassword } from "validate";
import { setPassword as setPasswordAsync } from "queries/auth";
import { useForm } from "react-hook-form";
import { toast } from "react-toastify";
import { yupResolver } from "@hookform/resolvers/yup";
import { Loading } from "components/Loading/Loading";
export const SetPassword = () => {
  const [loading, setLoading] = React.useState(false);
  const navigate = useNavigate();
  const {
    register: register4,
    handleSubmit: handleSubmit4,
    formState: { errors: errors4 },
  } = useForm({
    resolver: yupResolver(schemaSetPassword),
  });

  const onSetPassword = async (data: any) => {
    setLoading(true);
    let obj = {
      email: data.mail,
      newPassword: data.newpass,
    };
    setPasswordAsync(obj)
      .then((rs: any) => {
        if (rs) {
          setLoading(false);
          toast.success(rs.message);
          navigate("/");
        }
      })
      .catch((er: any) => {
        toast.error("Something went wrong");
        setLoading(false);
      });
  };
  return (
    <div className="set-password">
      <Wrapper>
        <div className="content">
          <div
            className="path"
            onClick={() => {
              navigate("/");
            }}
          >
            <ArrowBackIosIcon className="ic" /> Home
          </div>
          <form onSubmit={handleSubmit4(onSetPassword)}>
            <div>
              <label>Enter your email</label>
              <input {...register4("mail")} />
              {errors4.mail && <p>{errors4.mail.message.toString()}</p>}
            </div>
            <div>
              <label>Enter valid new password</label>
              <input {...register4("newpass")} />
              {errors4.newpass && <p>{errors4.newpass.message.toString()}</p>}
            </div>
            <div>
              <label>Confirm new password</label>
              <input {...register4("confirmnewpass")} />
              {errors4.confirmnewpass && (
                <p>{errors4.confirmnewpass.message.toString()}</p>
              )}
            </div>
            <input type="submit" value="OK" />
            {loading && (
              <div className="loading">
                <Loading />
              </div>
            )}
          </form>
        </div>
      </Wrapper>
    </div>
  );
};
