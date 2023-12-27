import React from "react";
import {
  getAllStore,
  searchAllByName,
  insertStore,
  editStore,
  deleteStore,
} from "../services/StoreService";
import { getAllChainName } from "../services/ChainService";
import { getAllName } from "../services/MechantService";
import { Table, Modal, Button, Offcanvas, Form } from "react-bootstrap";
import Paging from "./Paging";
import { toast } from "react-toastify";
import debounce from "lodash.debounce";
import './Store.scss';

const Store = () => {
  const [isShowModalAddNew, setIsShowModalAddNew] = React.useState(false);
  const [isShowModalUpdate, setIsShowModalUpdate] = React.useState(false);
  const [isShowModalDelete, setIsShowModalDelete] = React.useState(false);
  const [smShow, setSmShow] = React.useState(false);

  const [currentPage, setCurrentPage] = React.useState(1);
  const [totalPage, setTotalPage] = React.useState(0);
  const [totalItem, setTotalItem] = React.useState(0);
  const [limit, setLimit] = React.useState(6);
  const [sortBy, setSortBy] = React.useState("desc");
  const [sortField, setSortField] = React.useState("id");
  const [keyWord, setKeyWord] = React.useState("");

  const [listStore, setListStore] = React.useState([]);
  const [merchantNames, setMerchantNames] = React.useState([]);
  const [chainNames, setChainNames] = React.useState([]);
  const [storeItem, setStoreItem] = React.useState({});
  const [objEdit, setObjEdit] = React.useState({});
  const [objDelete, setObjDelete] = React.useState({});
  const [name, setName] = React.useState("");
  const [phone, setPhone] = React.useState("");
  const [address, setAddress] = React.useState("");
  const [description, setDescription] = React.useState("");
  const [merchantName, setMerchantName] = React.useState("");
  const [chainName, setChainName] = React.useState("");
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
    phone: phone,
    description: description,
    address: address,
    status: status,
    merchantName: merchantName,
    chainName: chainName,
  };

  React.useEffect(() => {
    getStore(currentPage, limit, sortBy, sortField);
    getMerchantNameList();
    getChainNameList();
  }, []);

  React.useEffect(() => {
    merchantNames && setMerchantName(merchantNames[0]);
  }, [merchantNames]);

  React.useEffect(() => {
    chainNames && setChainName(chainNames[0]);
  }, [chainNames]);

  const getStore = async (currentPage, limit, sortBy, sortField) => {
    await getAllStore(currentPage, limit, sortBy, sortField)
      .then((rs) => {
        if (rs) {
          setListStore(rs.data);
          setCurrentPage(rs.page);
          setTotalItem(rs.totalItem);
          setTotalPage(rs.totalPage);
        }
      })
      .catch((err) => toast.error(err.message));
  };

  const getMerchantNameList = async () => {
    await getAllName()
      .then((rs) => {
        if (rs) {
          setMerchantNames(rs.data);
        }
      })
      .catch((err) => toast.error(err.message));
  };

  const getChainNameList = async () => {
    await getAllChainName()
      .then((rs) => {
        if (rs) {
          setChainNames(rs.data);
        }
      })
      .catch((err) => toast.error(err.message));
  };

  const handleSaveStore = async () => {
    if (!name || !phone || !description || !address || !status) {
      toast.error("Please input full field !");
      return;
    }
    await insertStore(obj)
      .then((rs) => {
        if (rs) {
          toast.success("Insert row store is successfully !");
          getStore(currentPage, limit, sortBy, sortField);
          handleClose();
          setName("");
          setPhone("");
          setDescription("");
          setAddress("");
          setStatus(0);
        }
      })
      .catch((err) => toast.error(err.message));
  };

  const handleUpdateStore = async () => {
    await editStore(objEdit)
      .then((rs) => {
        if (rs) {
          toast.success("Update row store is successfully !");
          getStore(currentPage, limit, sortBy, sortField);
          handleClose();
        }
      })
      .catch((err) => toast.error(err.message));
  };

  const handDeleteStore = async () => {
    await deleteStore(objDelete)
      .then((rs) => {
        if (rs) {
          toast.success("Delete row store is successfully !");
          getStore(currentPage, limit, sortBy, sortField);
          handleClose();
        }
      })
      .catch((err) => toast.error("Cannot delete parent row because FK !"));
  };

  const handleSearchByName = debounce((event) => {
    const value = event.target.value;
    if (value) {
      setKeyWord(value);
      searchAllStoreByName(value);
    } else {
      getStore(currentPage, limit, sortBy, sortField);
    }
  }, 1000);

  const searchAllStoreByName = async (keyWord) => {
    await searchAllByName(keyWord)
      .then((rs) => {
        if (rs) {
          setListStore(rs.data);
        }
      })
      .catch((err) => toast.error(err.message));
  };

  const handleSortClick = (sortBy, sortField) => {
    getStore(currentPage, limit, sortBy, sortField);
    setSortBy(sortBy);
  };

  const handleClickTable = (storeItem) => {
    setStoreItem(storeItem);
    setSmShow(true);
  };

  const handClickEditStore = (store) => {
    setObjEdit(store);
    setIsShowModalUpdate(true);
  };

  const handClickDeleteStore = (store) => {
    setObjDelete(store);
    setIsShowModalDelete(true);
  };

  const handlePageClick = (event) => {
    getStore(+event.selected + 1, limit, sortBy, sortField);
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
          <b>List Store:</b>
        </span>
      </div>

      <div className="my-3 d-sm-flex justify-content-between">
        <div>
          <input
            className="form-control"
            placeholder="Search store by name..."
            onChange={(event) => handleSearchByName(event)}
          />
        </div>
        <button
          className="btn btn-success"
          onClick={() => setIsShowModalAddNew(true)}
        >
          <i class="fa-solid fa-circle-plus"></i>
          <span>Add new store</span>
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
              <th>Store Code</th>
              <th>Phone</th>
              <th>Description</th>
              <th>Address</th>
              <th>Status</th>
              <th>Merchant Name</th>
              <th>Chain Name</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {listStore
              ? listStore?.map((item, key) => {
                  return (
                    <tr key={key}>
                      <td>{key + 1}</td>
                      <td>{item?.id}</td>{" "}
                      <td>
                        <label
                          className="formatLable"
                          onClick={() => handleClickTable(item?.name)}
                        >
                          Name {item?.id}
                        </label>
                      </td>
                      <td>
                        <label
                          className="formatLable"
                          onClick={() => handleClickTable(item?.storeCode)}
                        >
                          Code {item?.id}
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
                          onClick={() => handleClickTable(item?.description)}
                        >
                          Description {item?.id}
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
                          className="formatLable"
                          onClick={() => handleClickTable(item?.merchantName)}
                        >
                          Merchant {item?.id}
                        </label>
                      </td>
                      <td>
                        <label
                          className="formatLable"
                          onClick={() => handleClickTable(item?.chainName)}
                        >
                          Chain {item?.id}
                        </label>
                      </td>
                      <td>
                        <div className="d-flex">
                          <button
                            className="btn btn-warning mx-2"
                            onClick={() => handClickEditStore(item)}
                          >
                            <i class="fa-solid fa-pen-to-square"></i>
                            <span>Edit</span>
                          </button>
                          <button
                            className="btn btn-danger mx-2"
                            onClick={() => handClickDeleteStore(item)}
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
              <Form.Label>Phone</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter phone"
                value={phone}
                onChange={(event) => setPhone(event.target.value)}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Description</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter description"
                value={description}
                onChange={(event) => setDescription(event.target.value)}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Address</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter address"
                value={address}
                onChange={(event) => setAddress(event.target.value)}
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
            <Form.Group className="mb-3">
              <Form.Label>Merchant Name</Form.Label>
              <Form.Select
                type="text"
                placeholder="Enter merchant"
                onChange={(event) => setMerchantName(event.target.value)}
              >
                {merchantNames?.map((item) => {
                  return <option value={item}>{item}</option>;
                })}
              </Form.Select>
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Chain Name</Form.Label>
              <Form.Select
                type="text"
                placeholder="Enter chain"
                onChange={(event) => setChainName(event.target.value)}
              >
                {chainNames?.map((item) => {
                  return <option value={item}>{item}</option>;
                })}
              </Form.Select>
            </Form.Group>
          </Form>
          <div className="d-flex justify-content-between">
            <Button variant="secondary" onClick={handleClose}>
              <i class="fa-solid fa-circle-xmark"></i>
              Close
            </Button>
            <Button variant="primary" onClick={() => handleSaveStore()}>
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
              <Form.Label>Description</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter description"
                onChange={(event) => {
                  let element = { ...objEdit };
                  element.description = event.target.value;
                  setObjEdit(element);
                }}
                defaultValue={objEdit?.description}
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
            <Form.Group className="mb-3">
              <Form.Label>Merchant Name</Form.Label>
              <Form.Select
                type="text"
                placeholder="Enter merchant"
                onChange={(event) => {
                  let element = { ...objEdit };
                  element.merchantName = event.target.value;
                  setObjEdit(element);
                }}
              >
                {merchantNames?.map((item) => {
                  return (
                    <>
                      <option
                        value={item}
                        selected={item === objEdit?.merchantName ? true : false}
                      >
                        {item}
                      </option>
                    </>
                  );
                })}
              </Form.Select>
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Chain Name</Form.Label>
              <Form.Select
                type="text"
                placeholder="Enter chain"
                onChange={(event) => {
                  let element = { ...objEdit };
                  element.chainName = event.target.value;
                  setObjEdit(element);
                }}
              >
                {chainNames?.map((item) => {
                  return (
                    <>
                      <option
                        value={item}
                        selected={item === objEdit?.chainName ? true : false}
                      >
                        {item}
                      </option>
                    </>
                  );
                })}
              </Form.Select>
            </Form.Group>
          </Form>
          <div className="d-flex justify-content-between">
            <Button variant="secondary" onClick={handleClose}>
              <i class="fa-solid fa-circle-xmark"></i>
              Close
            </Button>
            <Button variant="primary" onClick={() => handleUpdateStore()}>
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
        <Modal.Body>{storeItem}</Modal.Body>
      </Modal>

      <Modal show={isShowModalDelete} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Delete Store</Modal.Title>
        </Modal.Header>
        <Modal.Body>Do you want to delete !</Modal.Body>
        <Modal.Footer className="d-flex justify-content-between">
          <Button variant="secondary" onClick={handleClose}>
            <i class="fa-solid fa-circle-xmark"></i>
            Close
          </Button>
          <Button variant="primary" onClick={() => handDeleteStore()}>
            <i class="fa-solid fa-check"></i>
            Delete it
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default Store;
