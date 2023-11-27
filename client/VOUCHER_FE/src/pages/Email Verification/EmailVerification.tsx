import { Wrapper } from "components/Wrapper/Wrapper";
import React from "react";
import "./style.scss";
import CloudDoneIcon from "@mui/icons-material/CloudDone";
import { Link, useLocation, useNavigate, useParams } from "react-router-dom";
import { verifyEmail } from "queries/auth";
import { toast } from "react-toastify";
export const EmailVerification = () => {
  const navigate = useNavigate();
  const [count, setCount] = React.useState(10);
  const [data, setData] = React.useState();
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);

  const token = searchParams.get("token");
  React.useEffect(() => {
    if (data) {
      var countdownTimer = setInterval(() => {
        setCount((c) => c - 1);
      }, 1000);
      if (count <= 0) {
        clearInterval(countdownTimer);
        navigate("/");
      }
    }
    return () => {
      clearInterval(countdownTimer);
    };
  }, [count, data]);
  React.useEffect(() => {
    if (token) {
      verifyEmail(token)
        .then((rs: any) => {
          if (rs) {
            setData(rs);
          }
        })
        .catch((err: any) => toast.error(err.message));
    }
  }, [token]);
  return (
    <div className="email-verification">
      <Wrapper>
        <div className="content">
          {data ? (
            <div className="card-verify-email">
              <CloudDoneIcon className="ic" />
              <span>Email verification successfully </span>
              <div className="navs">
                <p>Redirecting to home... ({count}s)</p>
                <span>
                  Or click <Link to="/">here</Link> to direct to home imediately
                </span>
              </div>
            </div>
          ) : (
            "Oops! Something went wrong"
          )}
        </div>
      </Wrapper>
    </div>
  );
};
