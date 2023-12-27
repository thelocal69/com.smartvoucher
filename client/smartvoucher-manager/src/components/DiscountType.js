import React, { useRef } from "react";
import {
  getAllDiscountType,
  searchAllByName,
  insertDiscountType,
  editDiscountType,
  deleteDiscountType,
} from "../services/DiscountTypeService";
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
import Loading from "./Loading";
import './DiscountType.scss';

const DiscountType = () => {
  const ref = React.useRef(null);

  const [isShowModalAddNew, setIsShowModalAddNew] = React.useState(false);
  const [isShowModalUpdate, setIsShowModalUpdate] = React.useState(false);
  const [isShowModalDelete, setIsShowModalDelete] = React.useState(false);
  const [smShow, setSmShow] = React.useState(false);
  const [loading, setLoading] = React.useState(false);

  const [listDiscount, setListDiscount] = React.useState([]);
  const [discountItem, setDiscountItem] = React.useState({});
  const [objEdit, setObjEdit] = React.useState({});
  const [objDelete, setObjDelete] = React.useState({});

  const [sortBy, setSortBy] = React.useState("desc");
  const [sortField, setSortField] = React.useState("id");
  const [totalItem, setTotalItem] = React.useState(0);
  const [limit, setLimit] = React.useState(6);
  const [totalPage, setTotalPage] = React.useState(0);
  const [currentPage, setCurrentPage] = React.useState(1);
  const [file, setFile] = React.useState(null);
  const [keyWord, setKeyWord] = React.useState("");

  const [name, setName] = React.useState("");
  const [status, setStatus] = React.useState(0);

  const statusHardCode = [
    {
      label: "Active",
      value: 1,
    },
    {
      label: "Deactive",
      value: 0,
    },
  ];

  const obj = {
    name: name,
    status: status,
  };

  React.useEffect(() => {
    getDiscountType(currentPage, limit, sortBy, sortField);
  }, []);

  const getDiscountType = async (currentPage, limit, sortBy, sortField) => {
    await getAllDiscountType(currentPage, limit, sortBy, sortField)
      .then((rs) => {
        if (rs) {
          setListDiscount(rs.data);
          setTotalItem(rs.totalItem);
          setTotalPage(rs.totalPage);
          setCurrentPage(rs.page);
        }
      })
      .catch((err) => toast.error(err.message));
  };

  const handleSaveDiscount = async () => {
    if (!name || !status) {
      toast.error("Please input full field !");
      return;
    }
    await insertDiscountType(obj)
      .then((rs) => {
        if (rs) {
          toast.success("Insert row table is successfully !");
          getDiscountType(currentPage, limit, sortBy, sortField);
          handleClose();
          setName("");
          setStatus(0);
        }
      })
      .catch((err) => toast.error(err.message));
  };

  const handleUpdateDiscount = async () => {
    await editDiscountType(objEdit)
      .then((rs) => {
        if (rs) {
          toast.success("Update row table is successfully !");
          getDiscountType(currentPage, limit, sortBy, sortField);
          handleClose();
        }
      })
      .catch((err) => toast.error(err.message));
  };

  const handleDeleteDiscount = async () => {
    await deleteDiscountType(objDelete)
      .then((rs) => {
        if (rs) {
          toast.success("Delete row table is successfully !");
          getDiscountType(currentPage, limit, sortBy, sortField);
          handleClose();
        }
      })
      .catch((err) => toast.error("Cannot delete parent row because FK !"));
  };

  const handClickEditDiscount = (discount) => {
    setIsShowModalUpdate(true);
    setObjEdit(discount);
  };

  const handClickDeleteDiscount = (discount) => {
    setIsShowModalDelete(true);
    setObjDelete(discount);
  };

  const handleSearchByName = debounce((event) => {
    const value = event.target.value;
    if (value) {
      setKeyWord(value);
      searchByDiscountName(value);
    } else {
      getDiscountType(currentPage, limit, sortBy, sortField);
    }
  }, 1000);

  const searchByDiscountName = async () => {
    await searchAllByName(keyWord)
      .then((rs) => {
        if (rs) {
          setListDiscount(rs.data);
        }
      })
      .catch((err) => toast.error(err.message));
  };

  const handleSortClick = (sortBy, sortField) => {
    getDiscountType(currentPage, limit, sortBy, sortField);
    setSortBy(sortBy);
  };

  const handleClickTable = (discountItem) => {
    setDiscountItem(discountItem);
    setSmShow(true);
  };

  const handlePageClick = (event) => {
    getDiscountType(+event.selected + 1, limit, sortBy, sortField);
  };

  const handleClose = () => {
    setIsShowModalAddNew(false);
    setIsShowModalUpdate(false);
    setIsShowModalDelete(false);
    setSmShow(false);
  };

  return (
    <>
      <div className="my-3 d-sm-flex justify-content-between">
        <span>
          <b>List Discount Type:</b>
        </span>
      </div>
      <div className="my-3 d-sm-flex justify-content-between">
        <div>
          <input
            className="form-control"
            placeholder="Search discount by name..."
            onChange={(event) => handleSearchByName(event)}
          />
        </div>
        <button
          className="btn btn-success"
          onClick={() => setIsShowModalAddNew(true)}
        >
          <i class="fa-solid fa-circle-plus"></i>
          <span>Add new discount type</span>
        </button>
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
              <th>Name</th>
              <th>Discount Type Code</th>
              <th>Status</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {listDiscount
              ? listDiscount?.map((item, key) => {
                  return (
                    <tr key={key}>
                      <td>{key + 1}</td>
                      <td>{item?.id}</td>
                      <td>{item?.name}</td>
                      <td>
                        <label
                          className="formatLable"
                          onClick={() => handleClickTable(item?.code)}
                        >
                          Code {item?.id}
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
                        <div className="d-flex">
                          <button
                            className="btn btn-warning mx-2"
                            onClick={() => handClickEditDiscount(item)}
                          >
                            <i class="fa-solid fa-pen-to-square"></i>
                            <span>Edit</span>
                          </button>
                          <button
                            className="btn btn-danger mx-2"
                            onClick={() => handClickDeleteDiscount(item)}
                          >
                            <i class="fa-solid fa-trash"></i>
                            <span>Delete</span>
                          </button>
                        </div>
                      </td>
                    </tr>
                  );
                })
              : "No Data"}
          </tbody>
        </Table>
      </div>
      <Paging handlePageClick={handlePageClick} totalPages={totalPage} />

      <Offcanvas
        show={isShowModalAddNew}
        onHide={() => handleClose()}
        placement="end"
        backdrop="static"
      >
        <Offcanvas.Header closeButton>
          <Offcanvas.Title>Add new Merchant</Offcanvas.Title>
        </Offcanvas.Header>
        <Offcanvas.Body>
          <Form>
            <Form.Group className="mb-3">
              <Form.Label>Name</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter name"
                value={name}
                onChange={(event) => setName(event.target.value)}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Status</Form.Label>
              <div
                className="d-flex justify-content-between"
                onChange={(event) => setStatus(event.target.value)}
              >
                {statusHardCode?.map((item) => {
                  return (
                    <label>
                      <input
                        checked={Number(status) === Number(item.value)}
                        type="radio"
                        value={item.value}
                      />
                      {item?.label}
                    </label>
                  );
                })}
              </div>
            </Form.Group>
          </Form>
          <div className="d-flex justify-content-between">
            <Button variant="secondary" onClick={handleClose}>
              <i class="fa-solid fa-circle-xmark"></i>
              Close
            </Button>
            <Button variant="primary" onClick={() => handleSaveDiscount()}>
              <i class="fa-solid fa-floppy-disk"></i>
              Save Changes
            </Button>
          </div>
        </Offcanvas.Body>
      </Offcanvas>

      <Offcanvas
        show={isShowModalUpdate}
        onHide={handleClose}
        placement="end"
        backdrop="static"
      >
        <Offcanvas.Header closeButton>
          <Offcanvas.Title>Update Merchant</Offcanvas.Title>
        </Offcanvas.Header>
        <Offcanvas.Body>
          <Form>
            <Form.Group className="mb-3">
              <Form.Label>Name</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter name"
                onChange={(event) => {
                  let element = { ...objEdit };
                  element.name = event.target.value;
                  setObjEdit(element);
                }}
                defaultValue={objEdit?.name}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Status</Form.Label>
              <div
                className="d-flex justify-content-between"
                onChange={(event) => {
                  let obj = { ...objEdit };
                  obj.status = event.target.value;
                  setObjEdit(obj);
                }}
              >
                {statusHardCode?.map((item) => {
                  return (
                    <label>
                      <input
                        checked={Number(objEdit?.status) === Number(item.value)}
                        type="radio"
                        value={item.value}
                      />
                      {item?.label}
                    </label>
                  );
                })}
              </div>
            </Form.Group>
          </Form>
          <div className="d-flex justify-content-between">
            <Button variant="secondary" onClick={handleClose}>
              <i class="fa-solid fa-circle-xmark"></i>
              Close
            </Button>
            <Button variant="primary" onClick={() => handleUpdateDiscount()}>
              <i class="fa-solid fa-floppy-disk"></i>
              Update Changes
            </Button>
          </div>
        </Offcanvas.Body>
      </Offcanvas>

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
          {discountItem}
        </Modal.Body>
      </Modal>

      <Modal show={isShowModalDelete} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Delete Discount</Modal.Title>
        </Modal.Header>
        <Modal.Body>Do you want to delete !</Modal.Body>
        <Modal.Footer className="d-flex justify-content-between">
          <Button variant="secondary" onClick={handleClose}>
            <i class="fa-solid fa-circle-xmark"></i>
            Close
          </Button>
          <Button variant="primary" onClick={() => handleDeleteDiscount()}>
            <i class="fa-solid fa-check"></i>
            Delete it
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default DiscountType;
