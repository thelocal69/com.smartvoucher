import React, { useRef } from "react";
import { TextField } from "@mui/material";
import MapOutlinedIcon from "@mui/icons-material/MapOutlined";
import FormGroup from "@mui/material/FormGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import { editUser } from "queries/user";
import { toast } from "react-toastify";
import { userUpload } from "queries/user";
import { Loading } from "components/Loading/Loading";
import VpnKeyIcon from "@mui/icons-material/VpnKey";
import ArrowBackIosIcon from "@mui/icons-material/ArrowBackIos";
import { yupResolver } from "@hookform/resolvers/yup";
import { schemaChangePassword } from "validate";
import { useForm } from "react-hook-form";
import { editPassword } from "queries/user";
import { logOut } from "redux/features/auth/authSlice";
import { useDispatch, useSelector } from "react-redux";
import { logOutAsync } from "queries/auth";
import { selectRefreshToken } from "redux/features/auth/authSlice";
import { useNavigate } from "react-router-dom";
interface Profile {
  data: any;
}
export const AccountTab = ({ data }: Profile) => {
  const ref = useRef<any>(null);
  const [obj, setObj] = React.useState(data);
  const [file, setFile] = React.useState<any>();
  const [loading, setLoading] = React.useState(false);
  const [signingOut, setSigningOut] = React.useState(false);
  const [isShowPopUp, setIsShowPopUp] = React.useState(false);
  const dispatch = useDispatch();
  const refreshToken = useSelector(selectRefreshToken);
  console.log(obj);
  const navigate = useNavigate();
  const handleEdit = () => {
    setLoading(true);
    delete obj.avatarUrl;
    editUser(obj)
      .then((rs: any) => {
        if (rs) {
          toast.success(rs.message);
          setLoading(false);
        }
      })
      .catch((err: any) => {
        setLoading(false);
        toast.error(err.message);
      });
  };
  const handleUpload = () => {
    if (file) {
      const form = new FormData();
      form.append("fileName", file);
      setLoading(true);
      userUpload(form)
        .then((rs: any) => {
          if (rs) {
            setLoading(false);
            setFile(null);
            toast.success(rs.message);
          }
        })
        .catch((err: any) => {
          setLoading(false);
          toast.error(err.message);
        });
    } else {
      toast.error("Please choose an image");
    }
  };
  const handleChangePass = (data: any) => {
    setLoading(true);
    let obj = {
      currentPassword: data.currentPassword,
      newPassword: data.newpassChange,
      confirmPassword: data.confirmnewpassChange,
    };
    editPassword(obj)
      .then((rs: any) => {
        if (rs) {
          setLoading(false);
          toast.success(rs.message);
          setSigningOut(true);
          var timer = setTimeout(() => {
            dispatch(logOut());
            logOutAsync(refreshToken)
              .then((rs: any) => {
                if (rs) {
                  toast.success(rs.message);
                  setSigningOut(false);
                  clearTimeout(timer);
                  navigate("/");
                }
              })
              .catch((err: any) => {
                setSigningOut(false);
                toast.error(err.message);
                clearTimeout(timer);
              });
          }, 3000);
        }
      })
      .catch((err: any) => {
        setLoading(false);
        toast.error(err.message);
      });
  };
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<any>({
    resolver: yupResolver(schemaChangePassword),
  });
  return (
    <>
      {isShowPopUp && (
        <div className="popup-changepass">
          <div className="bg" onClick={() => setIsShowPopUp(false)}></div>
          <div className="content-popup">
            <ArrowBackIosIcon
              className="ic"
              onClick={() => {
                setIsShowPopUp(false);
              }}
            />
            {!signingOut ? (
              <form onSubmit={handleSubmit(handleChangePass)}>
                <div>
                  <div className="label">Enter current password</div>
                  <input type="password" {...register("currentPassword")} />
                  <p>
                    {errors.currentPassword &&
                      errors.currentPassword.message.toString()}
                  </p>
                </div>
                <div>
                  <div className="label">Enter new password</div>
                  <input type="password" {...register("newpassChange")} />
                  <p>
                    {errors.newpassChange &&
                      errors.newpassChange.message.toString()}
                  </p>
                </div>
                <div>
                  <div className="label">Confirm new password</div>
                  <input
                    type="password"
                    {...register("confirmnewpassChange")}
                  />
                  <p>
                    {errors.confirmnewpassChange &&
                      errors.confirmnewpassChange.message.toString()}
                  </p>
                </div>
                <input type="submit" value={"OK"} />
                <div className="cancel" onClick={() => setIsShowPopUp(false)}>
                  Cancel
                </div>
                {loading && (
                  <div className="loading">
                    <Loading />
                    <p>Requesting ...</p>
                  </div>
                )}
              </form>
            ) : (
              <div className="loading">
                <Loading />
                <p>Signing out for login again...</p>
              </div>
            )}
          </div>
        </div>
      )}
      <div className="title">Tổng quan</div>
      <div className="stats">
        <div className="stat">
          <div className="label">Tên đăng nhập</div>
          <div className="val">{data.userName ? data.userName : "..."}</div>
        </div>{" "}
        <div className="stat">
          <div className="label">Email</div>
          <div className="val">{data.email}</div>
        </div>{" "}
        <div className="stat">
          <div className="label">Họ và tên</div>
          <div className="val">{data.fullName ? data.fullName : "..."}</div>
        </div>{" "}
        <div className="stat">
          <div className="change-pass" onClick={() => setIsShowPopUp(true)}>
            Đổi mật khẩu
            <VpnKeyIcon color="white" />
          </div>
        </div>
      </div>
      <div className="line"></div>
      <div className="avatar">
        <img src={data.avatarUrl} alt="" />
        {file ? (
          <>
            <div className="btn" onClick={() => handleUpload()}>
              {"OK"}
            </div>
            {loading && (
              <div className="loading">
                <Loading />
              </div>
            )}
            <span>{file.name}</span>
          </>
        ) : (
          <div
            className="btn"
            onClick={() => {
              ref.current.click();
            }}
          >
            Sửa ảnh đại diện
          </div>
        )}
        <input
          type="file"
          ref={ref}
          name="avatar"
          accept="image/png, image/jpeg"
          style={{ display: "none" }}
          onChange={(e: any) => setFile(e.target.files[0])}
        />
        <div className="straight"></div>
        <div className="txts">
          <span>Vui lòng chọn ảnh nhỏ hơn 5MB</span>
          <span>Chọn hình ảnh phù hợp, không phản cảm</span>
        </div>
      </div>
      <div className="title">Cá nhân</div>
      <div className="inputs">
        <TextField
          id="outlined-basic"
          label="Username"
          variant="outlined"
          size="small"
          defaultValue={data.userName}
          onChange={(e) => {
            let o = { ...obj };
            o.userName = e.target.value;
            setObj(o);
          }}
        />
        <TextField
          id="outlined-basic"
          label="First Name"
          variant="outlined"
          size="small"
          defaultValue={data.firstName}
          onChange={(e) => {
            let o = { ...obj };
            o.firstName = e.target.value;
            setObj(o);
          }}
        />
        <TextField
          id="outlined-basic"
          label="Last Name"
          variant="outlined"
          size="small"
          defaultValue={data.lastName}
          onChange={(e) => {
            let o = { ...obj };
            o.lastName = e.target.value;
            setObj(o);
          }}
        />
        <TextField
          id="outlined-basic"
          label="Full Name"
          variant="outlined"
          size="small"
          defaultValue={data.fullName}
          onChange={(e) => {
            let o = { ...obj };
            o.fullName = e.target.value;
            setObj(o);
          }}
        />{" "}
        <TextField
          id="outlined-basic"
          label="Phone"
          variant="outlined"
          size="small"
          type="number"
          defaultValue={data.phone}
          onChange={(e) => {
            let o = { ...obj };
            o.phone = e.target.value;
            setObj(o);
          }}
        />{" "}
        <TextField
          id="outlined-basic"
          label="Email"
          variant="outlined"
          size="small"
          defaultValue={data.email}
          onChange={(e) => {
            let o = { ...obj };
            o.email = e.target.value;
            setObj(o);
          }}
        />{" "}
        <TextField
          id="outlined-basic"
          label="Address"
          variant="outlined"
          size="small"
          defaultValue={data.address}
          onChange={(e) => {
            let o = { ...obj };
            o.address = e.target.value;
            setObj(o);
          }}
        />{" "}
        {/* <TextField
          id="outlined-basic"
          label="Giới tính"
          variant="outlined"
          size="small"
          select
        >
          {[
            { label: "Nam", val: "nam" },
            { label: "Nữ", val: "nu" },
          ].map((item, key) => {
            return (
              <MenuItem key={item.val} value={item.val}>
                {item.label}
              </MenuItem>
            );
          })}
        </TextField> */}
        <div className="address">
          <MapOutlinedIcon /> Quản lý địa chỉ mua hàng
        </div>
      </div>
      <div className="allow">
        <FormGroup>
          <FormControlLabel
            control={<Checkbox />}
            label="Cho phép hiển thị tên của bạn trên các hoạt động"
          />
        </FormGroup>
      </div>
      <div
        className={obj !== data ? "save active" : "save"}
        onClick={() => {
          obj !== data && handleEdit();
        }}
      >
        Lưu thay đổi
      </div>{" "}
      {loading && (
        <div className="loading">
          <Loading />
        </div>
      )}
    </>
  );
};
