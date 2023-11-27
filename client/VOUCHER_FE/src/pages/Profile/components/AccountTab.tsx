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
interface Profile {
  data: any;
}
export const AccountTab = ({ data }: Profile) => {
  const ref = useRef<any>(null);
  const [obj, setObj] = React.useState(data);
  const [file, setFile] = React.useState<any>();
  const [loading, setLoading] = React.useState(false);
  console.log(obj);
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
  return (
    <>
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
