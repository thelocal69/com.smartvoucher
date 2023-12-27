import React from "react";
import { getAllUser, searchAllByUserEmail } from "../services/UserService";
import {
  Table,
  Modal,
  Button,
  Offcanvas,
  Form,
  Image,
  Col,
} from "react-bootstrap";
import Paging from "./Paging";
import { toast } from "react-toastify";
import debounce from "lodash.debounce";
import './User.scss';

const User = () => {
  const [smShow, setSmShow] = React.useState(false);
  const [imageShow, setImageShow] = React.useState(false);

  const [listUser, setListUser] = React.useState([]);
  const [userItem, setUserItem] = React.useState({});
  const [imageItem, setImageItem] = React.useState({});
  const [totalItem, setTotalItem] = React.useState(0);
  const [totalPage, setTotalPage] = React.useState(0);
  const [currentPage, setCurrentPage] = React.useState(1);
  const [limit, setLimit] = React.useState(6);
  const [sortBy, setSortBy] = React.useState("desc");
  const [sortField, setSortField] = React.useState("id");
  const [keyWord, setKeyWord] = React.useState("");

  React.useEffect(() => {
    getUser(currentPage, limit, sortBy, sortField);
  }, []);

  const getUser = async (currentPage, limit, sortBy, sortField) => {
    await getAllUser(currentPage, limit, sortBy, sortField)
      .then((rs) => {
        if (rs) {
          setListUser(rs.data);
          setCurrentPage(rs.page);
          setTotalItem(rs.totalItem);
          setTotalPage(rs.totalPage);
        }
      })
      .catch((err) => toast.error(err.message));
  };

  const handleSearchByEmail = debounce((event) => {
    let value = event.target.value;
    if (value) {
      setKeyWord(value);
      searchByUserEmail(value);
    } else {
      getUser(currentPage, limit, sortBy, sortField);
    }
  }, 1000);

  const searchByUserEmail = async (keyWord) => {
    await searchAllByUserEmail(keyWord)
      .then((rs) => {
        if (rs) {
          setListUser(rs.data);
        }
      })
      .catch((err) => toast.error(err.message));
  };

  const handleSortClick = (sortBy, sortField) => {
    getUser(currentPage, limit, sortBy, sortField);
    setSortBy(sortBy);
  };

  const handleClickTable = (serialItem) => {
    setUserItem(serialItem);
    setSmShow(true);
  };

  const handleShowImage = (imageItem) => {
    setImageItem(imageItem);
    setImageShow(true);
  }

  const handlePageClick = (event) => {
    getUser(+event.selected + 1, limit, sortBy, sortField);
  };

  const handleClose = () => {
    setSmShow(false);
    setImageShow(false);
  };

  return (
    <>
      <div className="my-3 d-sm-flex justify-content-between">
        <span>
          <b>List User:</b>
        </span>
      </div>

      <div className="my-3 d-sm-flex justify-content-between">
        <div>
          <input
            className="form-control"
            placeholder="Search user by email..."
            onChange={(event) => handleSearchByEmail(event)}
          />
        </div>
        {/* <button
          className="btn btn-success"
          onClick={() => setIsShowModalAddNew(true)}
        >
          <i class="fa-solid fa-circle-plus"></i>
          <span>Add new merchant</span>
        </button> */}
      </div>

      <div className="customize-table">
        <Table striped bordered hover size="sm" className="table-condensed">
          <thead>
            <tr>
              <th>NO</th>
              <th>
                <div className="sort-header">
                  <span>ID</span>
                  <span>
                    {
                      <i
                        class="fa-solid fa-sort-down"
                        onClick={() => handleSortClick("desc", sortField)}
                      ></i>
                    }
                    <i
                      class="fa-solid fa-sort-up"
                      onClick={() => handleSortClick("asc", sortField)}
                    ></i>
                  </span>
                </div>
              </th>
              <th>Avatar</th>
              <th>Full Name</th>
              <th>First Name</th>
              <th>Last Name</th>
              <th>User Name</th>
              <th>Member Code</th>
              <th>Phone</th>
              <th>Email</th>
              <th>Address</th>
              <th>Status</th>
              <th>Enable</th>
              <th>Provider</th>
            </tr>
          </thead>
          <tbody>
            {listUser
              ? listUser?.map((item, key) => {
                  return (
                    <tr key={key}>
                      <td>{key + 1}</td>
                      <td>{item?.id}</td>
                      <td>
                        <label
                          className="formatLable"
                          onClick={() => handleShowImage(item?.avatarUrl)}
                        >
                          Avatar {item?.id}
                        </label>
                      </td>
                      <td>
                        <label
                          className="formatLable"
                          onClick={() => handleClickTable(item?.fullName)}
                        >
                          Full Name {item?.id}
                        </label>
                      </td>
                      <td>
                        {item?.firstName}
                      </td>
                      <td>
                        {item?.lastName}
                      </td>
                      <td>
                        <label
                          className="formatLable"
                          onClick={() => handleClickTable(item?.userName)}
                        >
                          User Name {item?.id}
                        </label>
                      </td>
                      <td>
                        <label
                          className="formatLable"
                          onClick={() => handleClickTable(item?.memberCode)}
                        >
                          Member Code {item?.id}
                        </label>
                      </td>
                      <td>
                        <label
                          className="formatLable"
                          onClick={() => handleClickTable(item?.phone)}
                        >
                          Phone {item?.id}
                        </label>
                      </td>
                      <td>
                        <label  
                          className="formatLable"
                          onClick={() => handleClickTable(item?.email)}
                        >
                          Email {item?.id}
                        </label>
                      </td>
                      <td>
                        <label
                          className="formatLable"
                          onClick={() => handleClickTable(item?.address)}
                        >
                          Address {item?.id}
                        </label>
                      </td>
                      <td>
                        <label
                        className={
                          item?.status ? "ac active" : "ac deactive"
                        }
                        >{item?.status ? "Active" : "Deactive"}</label>
                      </td>
                      <td>
                        <label
                        className={
                          item?.enable ? "ac active" : "ac deactive"
                        }
                        >{item?.enable ? "Enable" : "Disable"}</label>
                      </td>
                      <td>
                        <label>{item?.provider}</label>
                      </td>
                      {/* <td>
                        <div className="d-flex">
                          <button
                            className="btn btn-warning mx-2"
                            onClick={() => handClickEditMerchant(item)}
                          >
                            <i class="fa-solid fa-pen-to-square"></i>
                            <span>Edit</span>
                          </button>
                          <button
                            className="btn btn-danger mx-2"
                            onClick={() => handClickDeleteMerchant(item)}
                          >
                            <i class="fa-solid fa-trash"></i>
                            <span>Delete</span>
                          </button>
                        </div>
                      </td> */}
                    </tr>
                  );
                })
              : "No Data"}
          </tbody>
        </Table>
      </div>
      <Paging handlePageClick={handlePageClick} totalPages={totalPage} />

      <Modal
        size="md"
        show={smShow}
        onHide={() => handleClose()}
        aria-labelledby="example-modal-sizes-title-sm"
      >
        <Modal.Header closeButton>
          <Modal.Title id="example-modal-sizes-title-sm">
            Small info
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {userItem}
        </Modal.Body>
      </Modal>

      <Modal
        size="md"
        show={imageShow}
        onHide={() => handleClose()}
        aria-labelledby="example-modal-sizes-title-sm"
      >
        <Modal.Header closeButton>
          <Modal.Title id="example-modal-sizes-title-sm">
            Show image
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
              <Col xs={10} md={8}>
                <Image src={imageItem}  thumbnail/>
              </Col>
        </Modal.Body>
      </Modal>
    </>
  );
};

export default User;
