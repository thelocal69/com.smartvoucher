import React from "react";
import { Container, Modal, Button, Col, Row } from "react-bootstrap";
import { changePassword } from "../../services/UserServices";
import "../User/ChangePassword.scss";
import { toast } from "react-toastify";
import Loading from "../Util/Loading";
import { useDispatch, useSelector } from "react-redux";
import { logOut, selectRefreshToken } from "../../Redux/data/AuthSlice";
import { logoutUser } from "../../services/AccountServices";

const ChangePassword = () => {
  const [show, setShow] = React.useState(false);
  const [isShowPassword, setIsShowPassword] = React.useState(false);
  const [isShowPasswordConfirm, setIsShowPasswordConfirm] =
    React.useState(false);
  const [isShowCurrentPassword, setIsShowCurrentPassword] =
    React.useState(false);
  const [loading, setLoading] = React.useState(false);

  const [currentPassword, setCurrentPassword] = React.useState("");
  const [newPassword, setNewPassword] = React.useState("");
  const [confirmNewPassword, setConfirmNewPassword] = React.useState("");

  const dispatch = useDispatch();
  const refreshToken = useSelector(selectRefreshToken);

  const objChangePassword = {
    currentPassword: currentPassword,
    newPassword: newPassword,
    confirmPassword: newPassword,
  };

  const handleChangePassword = async () => {
    if (!newPassword || !confirmNewPassword) {
      toast.error("Please fill all !");
      return;
    }
    if (confirmNewPassword !== newPassword) {
      toast.error("New password won't match confirm password !");
      return;
    }
    if (newPassword && confirmNewPassword === currentPassword) {
      toast.error("New password match current password !");
      return;
    }
    setLoading(false);
    await changePassword(objChangePassword)
      .then((rs) => {
        if (rs) {
          toast.success(rs.data);
          setLoading(false);
          dispatch(logOut());
          handleLogout();
          handleClose();
        }
      })
      .catch((err) => {
        console.log(err.message);
        setLoading(false);
      });
  };

  const handleLogout = async () => {
    await logoutUser(refreshToken);
  };

  const handleClose = () => {
    setShow(false);
    setNewPassword("");
    setConfirmNewPassword("");
    setCurrentPassword("");
  };

  return (
    <>
      <div className="oo list-lol">
        <Row xs={1} md='auto'>
          <Col>
            <div>
              <h3>Mật khẩu & Bảo mật</h3>
              <p>
                Vì sự an toàn, Smart voucher khuyến khích khách hàng sử dụng mật
                khẩu mạnh
              </p>
              <hr />
            </div>
          </Col>
          <Col>
            <Row xs={1} md='auto'>
              <Col>
                <div className="pe-5">
                  <h4>Đổi mật khẩu</h4>
                  <button className="btn btn-warning" onClick={() => setShow(true)}>
                    Đổi
                  </button>
                </div>
              </Col>
              <Col>
                <div>
                  <h4>Mật khẩu của bạn</h4>
                  <p>
                    Phải từ 8 ký tự trở lên
                    <br /> Nên có ít nhất 1 số hoặc 1 ký tự đặc biệt
                    <br /> Không nên giống với mật khẩu được sử dụng gần đây
                  </p>
                </div>
              </Col>
            </Row>
          </Col>
          <Modal
            show={show}
            onHide={handleClose}
            animation={false}
            size="sm"
            aria-labelledby="contained-modal-title-vcenter"
            centered
            backdrop="static"
          >
            <Modal.Header closeButton>
              <Modal.Title>Đổi mật khẩu</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <div>
                <div class="form-group p-2 input-pass">
                  <input
                    type={isShowCurrentPassword ? "text" : "password"}
                    class="form-control form-control-user p-2"
                    id="exampleInputPassword"
                    placeholder="Current password"
                    required
                    value={currentPassword}
                    onChange={(event) => setCurrentPassword(event.target.value)}
                    tabIndex={0}
                  />
                  <i
                    className={
                      isShowCurrentPassword
                        ? "fa-solid fa-eye"
                        : "fa-solid fa-eye-slash"
                    }
                    hidden={currentPassword ? false : true}
                    onClick={() =>
                      setIsShowCurrentPassword(!isShowCurrentPassword)
                    }
                  ></i>
                </div>
                <div class="form-group p-2 input-pass">
                  <input
                    type={isShowPassword ? "text" : "password"}
                    class="form-control form-control-user p-2"
                    id="exampleInputPassword"
                    placeholder="New password"
                    required
                    value={newPassword}
                    onChange={(event) => setNewPassword(event.target.value)}
                    tabIndex={0}
                  />
                  <i
                    className={
                      isShowPassword ? "fa-solid fa-eye" : "fa-solid fa-eye-slash"
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
                    placeholder="Confirm new password"
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
                    onClick={() =>
                      setIsShowPasswordConfirm(!isShowPasswordConfirm)
                    }
                  ></i>
                </div>
              </div>
            </Modal.Body>
            <Modal.Footer>
              <Button variant="primary" onClick={() => handleChangePassword()}>
                {loading && (
                  <>
                    <Loading fileName={"Save changes"} />
                  </>
                )}
                <span className="text-center" hidden={loading ? true : false}>
                  Lưu thay đổi
                </span>
              </Button>
            </Modal.Footer>
          </Modal>
        </Row>
      </div>
    </>
  );
};

export default ChangePassword;
