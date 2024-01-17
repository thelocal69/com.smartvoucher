import React from "react";
import { Container, Form, Offcanvas, Button } from "react-bootstrap";
import { useSelector, useDispatch } from "react-redux";
import Loading from "../Util/Loading";
import { toast } from "react-toastify";
import "../User/Profile.scss";
import { selectAccessToken } from "../../Redux/data/AuthSlice";
import { avatar, username, userId } from "../../Redux/data/UserSlice";
import {
  getUserInfor,
  updateImage,
  editProfile,
} from "../../services/UserServices";

const Profile = () => {
  const ref = React.useRef(null);

  const [objEdit, setObjEdit] = React.useState({});
  const [objInfor, setObjInfor] = React.useState({});

  const [file, setFile] = React.useState(null);
  const [loading, setLoading] = React.useState(false);
  const [isShowModalUpdate, setIsShowModalUpdate] = React.useState(false);

  const accessToken = useSelector(selectAccessToken);
  const dispatch = useDispatch();

  React.useEffect(() => {
    getUserInformation();
  }, []);

  const getUserInformation = async () => {
    await getUserInfor()
      .then((rs) => {
        if (rs) {
          setObjInfor(rs.data);
          dispatch(
            avatar({
              avatarUrl: rs.data.avatarUrl,
            })
          );
          dispatch(
            username({
              username: rs.data.userName,
            })
          );
          dispatch(
            userId({
              id: rs.data.id,
            })
          );
        }
      })
      .catch((err) => toast.error(err.message));
  };

  const handleUploadAvatar = async () => {
    if (file) {
      const form = new FormData();
      form.append("fileName", file);
      setLoading(true);
      await updateImage(form)
        .then((rs) => {
          if (rs) {
            setLoading(false);
            setFile(null);
            toast.success("Change avatar successfully !");
            getUserInformation();
            dispatch(
              avatar({
                avatarUrl: rs.data,
              })
            );
          }
        })
        .catch((err) => {
          setLoading(false);
          toast.error(err.message);
        });
    } else {
      toast.error("Please choose an avatar !");
    }
  };

  const handleUpdateProfile = async () => {
    setLoading(true);
    await editProfile(objEdit)
      .then((rs) => {
        if (rs) {
          setLoading(false);
          toast.success("Update information is successfully !");
          getUserInformation();
          handleClose();
          dispatch(
            username({
              username: rs.data.userName,
            })
          );
        }
      })
      .catch((err) => {
        setLoading(false);
        toast.error(err.message);
      });
  };

  const handClickEditProfile = () => {
    setObjEdit(objInfor);
    setIsShowModalUpdate(true);
  };

  const handleClose = () => {
    setIsShowModalUpdate(false);
  };

  return (
    <>
      <Container>
        {accessToken && (
          <>
            <div className="list-profile">
              <div className="p-3">
                <div>
                  <h3 className="ps-3">Tổng quan</h3>
                  <div className="p-3 d-flex flex-wrap">
                    <div className="pe-5">
                      <span>Tên đăng nhập</span>
                      <p>
                        <b>{objInfor.userName}</b>
                      </p>
                    </div>
                    <div className="pe-5">
                      <span>Email</span>
                      <p>
                        <b>{objInfor.email}</b>
                      </p>
                    </div>
                    <div className="pe-5">
                      <span>Họ và tên</span>
                      <p>
                        <b>{objInfor.fullName}</b>
                      </p>
                    </div>
                    <div className="pe-5">
                      <span>Nhóm khách hàng</span>
                      <p>
                        <b>{}</b>
                      </p>
                    </div>
                    <div className="pe-5">
                      <span>Số dư</span>
                      <p>
                        <b>{}đ</b>
                      </p>
                    </div>
                    <div className="pe-5">
                      <span>Đã tích lũy</span>
                      <p>
                        <b>{}đ</b>
                      </p>
                    </div>
                    <div className="pe-5">
                      <span>Ngày tham gia</span>
                      <p>
                        <b>2020-03-31 23:14:47</b>
                      </p>
                    </div>
                  </div>
                </div>
                <div className="d-flex ka lu">
                  <div className="yi">
                    <img alt="" src={objInfor.avatarUrl} className="Ava" />
                  </div>
                  <div className="d-flex justify-content-center">
                    <Form.Group className="d-flex justify-content-center flex-column yi">
                      {file ? (
                        <>
                          <Form.Label
                            className="btn btn-success my-3"
                            onClick={() => handleUploadAvatar()}
                          >
                            {loading ? (
                              <>
                                <div className="loading">
                                  <Loading fileName={file.name} />
                                </div>
                              </>
                            ) : (
                              <>
                                <i class="fa-solid fa-check"></i>
                                Accept
                              </>
                            )}
                          </Form.Label>
                        </>
                      ) : (
                        <>
                          <Form.Label
                            className="btn btn-primary my-3"
                            onClick={() => {
                              ref.current.click();
                            }}
                          >
                            <i class="fa-solid fa-upload"></i>
                            Upload avatar
                          </Form.Label>
                        </>
                      )}
                      <Form.Control
                        type="file"
                        ref={ref}
                        accept="image/png, image/jpeg"
                        onChange={(event) => setFile(event.target.files[0])}
                        hidden
                      />
                      <button
                        className="btn btn-warning"
                        onClick={() => handClickEditProfile()}
                      >
                        <i class="fa-solid fa-pen-to-square"></i>
                        Edit Profile
                      </button>
                    </Form.Group>
                    <div className="lu"></div>
                    <div className="d-flex justify-content-center flex-column yi">
                      <span>Vui lòng chọn ảnh nhỏ hơn 5MB</span>
                      <br />
                      <span>Chọn hình ảnh phù hợp, không phản cảm</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <Offcanvas
              show={isShowModalUpdate}
              onHide={handleClose}
              placement="end"
              backdrop="static"
            >
              <Offcanvas.Header closeButton>
                <Offcanvas.Title>Update Profile</Offcanvas.Title>
              </Offcanvas.Header>
              <Offcanvas.Body>
                <Form>
                  <Form.Group className="mb-3">
                    <Form.Label>full name</Form.Label>
                    <Form.Control
                      type="text"
                      placeholder="Enter full name"
                      onChange={(event) => {
                        let element = { ...objEdit };
                        element.fullName = event.target.value;
                        setObjEdit(element);
                      }}
                      defaultValue={objEdit?.fullName}
                    />
                  </Form.Group>
                  <Form.Group className="mb-3">
                    <Form.Label>First Name</Form.Label>
                    <Form.Control
                      type="text"
                      placeholder="Enter first name"
                      onChange={(event) => {
                        let element = { ...objEdit };
                        element.firstName = event.target.value;
                        setObjEdit(element);
                      }}
                      defaultValue={objEdit?.firstName}
                    />
                  </Form.Group>
                  <Form.Group className="mb-3">
                    <Form.Label>Last Name</Form.Label>
                    <Form.Control
                      type="text"
                      placeholder="Enter last name"
                      onChange={(event) => {
                        let element = { ...objEdit };
                        element.lastName = event.target.value;
                        setObjEdit(element);
                      }}
                      defaultValue={objEdit?.lastName}
                    />
                  </Form.Group>
                  <Form.Group className="mb-3">
                    <Form.Label>User Name</Form.Label>
                    <Form.Control
                      type="text"
                      placeholder="Enter user name"
                      onChange={(event) => {
                        let element = { ...objEdit };
                        element.userName = event.target.value;
                        setObjEdit(element);
                      }}
                      defaultValue={objEdit?.userName}
                    />
                  </Form.Group>
                  <Form.Group className="mb-3">
                    <Form.Label>Phone</Form.Label>
                    <Form.Control
                      type="text"
                      placeholder="Enter phone"
                      onChange={(event) => {
                        let element = { ...objEdit };
                        element.phone = event.target.value;
                        setObjEdit(element);
                      }}
                      defaultValue={objEdit?.phone}
                    />
                  </Form.Group>
                  <Form.Group className="mb-3">
                    <Form.Label>Address</Form.Label>
                    <Form.Control
                      type="text"
                      placeholder="Enter address"
                      onChange={(event) => {
                        let element = { ...objEdit };
                        element.address = event.target.value;
                        setObjEdit(element);
                      }}
                      defaultValue={objEdit?.address}
                    />
                  </Form.Group>
                </Form>
                <div className="d-flex justify-content-between">
                  <Button variant="secondary" onClick={handleClose}>
                    <i class="fa-solid fa-circle-xmark"></i>
                    Close
                  </Button>
                  <Button
                    variant="primary"
                    onClick={() => handleUpdateProfile()}
                  >
                    <i class="fa-solid fa-floppy-disk"></i>
                    Update Changes
                  </Button>
                </div>
              </Offcanvas.Body>
            </Offcanvas>
          </>
        )}
      </Container>
    </>
  );
};

export default Profile;
