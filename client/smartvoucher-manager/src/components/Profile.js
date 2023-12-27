import React from "react";
import {
  Container,
  Image,
  Button,
  Offcanvas,
  Form,
  Col,
} from "react-bootstrap";
import "./Profile.scss";
import {
  getUserInforLogin,
  editUserInfor,
  editAvatarUser,
} from "../services/ProfileService";
import { selectAccessToken } from "../redux/data/AuthSlice";
import { useSelector } from "react-redux";
import Loading from "./Loading";
import { toast } from "react-toastify";

const Profile = () => {
  const [isShowModalUpdate, setIsShowModalUpdate] = React.useState(false);
  const [loading, setLoading] = React.useState(false);

  const [objInfor, setObjInfor] = React.useState({});
  const [objEdit, setObjEdit] = React.useState({});
  const [file, setFile] = React.useState(null);

  const ref = React.useRef(null);
  const accessToken = useSelector(selectAccessToken);

  React.useEffect(() => {
    if (accessToken) {
      getProfileAdmin();
    }
  }, []);

  const getProfileAdmin = async () => {
    await getUserInforLogin().then((rs) => {
      if (rs) {
        setObjInfor(rs.data);
      }
    });
  };

  const handleUpdateProfile = async () => {
    await editUserInfor(objEdit)
      .then((rs) => {
        if (rs) {
          toast.success("Update profile is successfully !");
          getProfileAdmin();
          handleClose();
        }
      })
      .catch((err) => {
        toast.error(err.message);
      });
  };

  const handleUploadAvatar = async () => {
    if (file) {
      const form = new FormData();
      form.append("fileName", file);
      setLoading(true);
      await editAvatarUser(form)
        .then((rs) => {
          setLoading(false);
          setFile(null);
          toast.success("Change avatar successfully !");
          getProfileAdmin();
        })
        .catch((err) => {
          setLoading(false);
          toast.error(err.message);
        });
    } else {
      toast.error("Please choose an avatar !");
    }
  };

  const handClickEditMerchant = () => {
    setIsShowModalUpdate(true);
    setObjEdit(objInfor);
  };

  const handleClose = () => {
    setIsShowModalUpdate(false);
  };

  return (
    <>
      <Container>
        {accessToken && (
          <>
            <div class="row custom">
              <div class="col-lg-12 mb-4 mb-sm-5">
                <div class="card card-style1 border-0">
                  <div class="card-body p-1-9 p-sm-2-3 p-md-6 p-lg-7">
                    <div class="row align-items-center">
                      <div class="col-lg-6 mb-4 mb-lg-0">
                        <Col xs={10} md={10} className="my-2">
                          <Image src={objInfor.avatarUrl} thumbnail />
                        </Col>
                        <Form.Group className="mb-3">
                          <div className="m-3">
                            <span>Vui lòng chọn ảnh nhỏ hơn 5MB</span>
                            <br />
                            <span>Chọn hình ảnh phù hợp, không phản cảm</span>
                          </div>
                          {file ? (
                            <>
                              <Form.Label
                                className="btn btn-success my-3"
                                onClick={() => handleUploadAvatar()}
                              >
                                <i class="fa-solid fa-check"></i>
                                Accept
                              </Form.Label>
                              {loading && (
                                <div className="loading">
                                  <Loading fileName={file.name} />
                                </div>
                              )}
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
                        </Form.Group>
                      </div>
                      <div class="col-lg-6 px-xl-10">
                        <div className="bg-secondary d-lg-inline-block py-1-9 px-1-9 px-sm-6 mb-1-9 rounded">
                          <h3 class="h2 text-white mb-0">
                            {objInfor.fullName}
                          </h3>
                          <span class="text-primary">Admin</span>
                        </div>
                        <ul class="list-unstyled mb-1-9">
                          <li class="mb-2 mb-xl-3 display-28">
                            <span class="display-26 text-secondary me-2 font-weight-600">
                              Position:
                            </span>{" "}
                            Admin
                          </li>
                          <li class="mb-2 mb-xl-3 display-28">
                            <span class="display-26 text-secondary me-2 font-weight-600">
                              First Name:
                            </span>{" "}
                            {objInfor.firstName}
                          </li>
                          <li class="mb-2 mb-xl-3 display-28">
                            <span class="display-26 text-secondary me-2 font-weight-600">
                              Last Name:
                            </span>{" "}
                            {objInfor?.lastName}
                          </li>
                          <li class="mb-2 mb-xl-3 display-28">
                            <span class="display-26 text-secondary me-2 font-weight-600">
                              User Name:
                            </span>{" "}
                            {objInfor?.userName}
                          </li>
                          <li class="mb-2 mb-xl-3 display-28">
                            <span class="display-26 text-secondary me-2 font-weight-600">
                              Email:
                            </span>{" "}
                            {objInfor?.email}
                          </li>
                          <li class="display-28">
                            <span class="display-26 text-secondary me-2 font-weight-600">
                              Phone:
                            </span>{" "}
                            {objInfor?.phone}
                          </li>
                          <li class="display-28">
                            <span class="display-26 text-secondary me-2 font-weight-600">
                              Address:
                            </span>{" "}
                            {objInfor?.address}
                          </li>
                        </ul>
                        <button
                          className="btn btn-warning mx-2"
                          onClick={() => handClickEditMerchant()}
                        >
                          <i class="fa-solid fa-pen-to-square"></i>
                          <span>Update information</span>
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </>
        )}
      </Container>

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
            <Button variant="primary" onClick={() => handleUpdateProfile()}>
              <i class="fa-solid fa-floppy-disk"></i>
              Update Changes
            </Button>
          </div>
        </Offcanvas.Body>
      </Offcanvas>
    </>
  );
};

export default Profile;
