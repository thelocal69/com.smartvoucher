import React from "react";

import {
  getAllChain,
  searchAllChainByName,
  insertChain,
  uploadLogoChain,
  editChain,
  deleteChain
} from "../services/ChainService";
import { getAllName } from "../services/MechantService";
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
import "./Chain.scss";
import { toast } from "react-toastify";
import debounce from "lodash.debounce";
import Loading from "./Loading";

const Chain = () => {
  const ref = React.useRef(null);

  const [isShowModalAddNew, setIsShowModalAddNew] = React.useState(false);
  const [isShowModalUpdate, setIsShowModalUpdate] = React.useState(false);
  const [isShowModalDelete, setIsShowModalDelete] = React.useState(false);
  const [smShow, setSmShow] = React.useState(false);
  const [imageShow, setImageShow] = React.useState(false);

  const [currentPage, setCurrentPage] = React.useState(1);
  const [totalPage, setTotalPage] = React.useState(0);
  const [totalItem, setTotalItem] = React.useState(0);
  const [limit, setLimit] = React.useState(6);
  const [sortBy, setSortBy] = React.useState("desc");
  const [sortField, setSortField] = React.useState("id");
  const [keyWord, setKeyWord] = React.useState("");
  const [loading, setLoading] = React.useState(false);
  const [file, setFile] = React.useState(null);

  const [listChain, setListChain] = React.useState([]);
  const [merchantNames, setMerchantNames] = React.useState([]);
  const [chainItem, setChainItem] = React.useState({});
  const [imageItem, setImageItem] = React.useState({});
  const [objEdit, setObjEdit] = React.useState({});
  const [objDelete, setObjDelete] = React.useState({});
  const [legalName, setLegalName] = React.useState("");
  const [logoUrl, setLogoUrl] = React.useState("");
  const [name, setName] = React.useState("");
  const [email, setEmail] = React.useState("");
  const [phone, setPhone] = React.useState("");
  const [description, setDescription] = React.useState("");
  const [address, setAddress] = React.useState("");
  const [merchantName, setMerchantName] = React.useState("");
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
    legalName: legalName,
    logoUrl: logoUrl,
    name: name,
    email: email,
    phone: phone,
    description: description,
    address: address,
    status: status,
    merchantName: merchantName,
  };

  React.useEffect(() => {
    getChain(currentPage, limit, sortBy, sortField).catch((err) =>
      toast.error(err.message)
    );
    getMerchantNameList();
  }, []);

  React.useEffect(() => {
    merchantNames && setMerchantName(merchantNames[0]);
  }, [merchantNames]);

  const getMerchantNameList = async () => {
    await getAllName()
      .then((rs) => {
        setMerchantNames(rs.data);
      })
      .catch((err) => toast.error(err.message));
  };

  const getChain = async (currentPage, limit, sortBy, sortField) => {
    let res = await getAllChain(currentPage, limit, sortBy, sortField);
    if (res && res.data) {
      setCurrentPage(res.page);
      setTotalItem(res.totalItem);
      setTotalPage(res.totalPage);
      setListChain(res.data);
    }
  };

  const handleSearchByName = debounce((event) => {
    let value = event.target.value;
    if (value) {
      setKeyWord(value);
      searchByChainName(value);
    } else {
      getChain(currentPage, limit, sortBy, sortField);
    }
  }, 1000);

  const searchByChainName = async (keyWord) => {
    await searchAllChainByName(keyWord)
      .then((rs) => {
        if (rs) {
          setListChain(rs.data);
        }
      })
      .catch((err) => toast.error(err.message));
  };

  const handleSaveChain = async () => {
    if (
      !legalName ||
      !logoUrl ||
      !name ||
      !email ||
      !phone ||
      !description ||
      !address ||
      !status
    ) {
      toast.error("Please input full field !");
      return;
    }
    await insertChain(obj)
      .then((rs) => {
        if (rs) {
          toast.success("Insert row chain is successfully !");
          getChain(currentPage, limit, sortBy, sortField);
          handleClose();
          setLegalName("");
          setLogoUrl("");
          setName("");
          setEmail("");
          setPhone("");
          setDescription("");
          setAddress("");
          setStatus(0);
        }
      })
      .catch((err) => toast.error(err.message));
  };

  const handleUpdateChain = async() => {
    await editChain(objEdit)
    .then((rs) => {
      if(rs){
        toast.success("Update chain is successfully !");
        getChain(currentPage, limit, sortBy, sortField);
        handleClose();
      }
    })
    .catch((err) => {
      toast.error(err.message);
    })
  }

  const handDeleteChain = async() => {
    await deleteChain(objDelete)
    .then((rs) => {
      if(rs){
        toast.success("Delete item chain is successfully !");
        getChain(currentPage, limit, sortBy, sortField);
        handleClose();
      }
    })
    .catch((err) => toast.error("Cannot delete parent row because FK !"));
  }

  const handClickEditChain = (chain) => {
    setIsShowModalUpdate(true);
    setObjEdit(chain);
  };

  const handClickDeleteChain = (chain) => {
    setIsShowModalDelete(true);
    setObjDelete(chain);
  };

  const handleUploadChain = async() => {
    if (file) {
      const form = new FormData();
      form.append("fileName", file);
      setLoading(true);
      await uploadLogoChain(form)
      .then((rs) => {
        if(rs){
          setLoading(false);
          setFile(null);
          setLogoUrl(rs.data);
          toast.success(rs.message);
        }
      })
      .catch((err) => {
        setLoading(false);
        toast.error(err.message);
      });
    }else{
      toast.error("Please choose an logo !");
    }
  };

  const handleUploadEditChain = async() => {
    if (file) {
      const form = new FormData();
      form.append("fileName", file);
      setLoading(true);
      await uploadLogoChain(form)
      .then((rs) => {
        if(rs){
          setLoading(false);
          setFile(null);
          objEdit.logoUrl = rs.data;;
          toast.success(rs.message);
        }
      })
      .catch((err) => {
        setLoading(false);
        toast.error(err.message);
      });
    }else{
      toast.error("Please choose an logo !");
    }
  };

  const handleSortClick = (sortBy, sortField) => {
    getChain(currentPage, limit, sortBy, sortField);
    setSortBy(sortBy);
  };

  const handleClickTable = (chain) => {
    setSmShow(true);
    setChainItem(chain);
  };

  const handleShowImage = (image) => {
    setImageItem(image);
    setImageShow(true);
  }

  const handlePageClick = (event) => {
    getChain(+event.selected + 1, limit, sortBy, sortField);
  };

  const handleClose = () => {
    setIsShowModalAddNew(false);
    setIsShowModalUpdate(false);
    setIsShowModalDelete(false);
    setSmShow(false);
    setImageShow(false);
  };


  return (
    <>
      <div className="my-3 d-sm-flex justify-content-between">
        <span>
          <b>List Chain:</b>
        </span>
      </div>

      <div className="my-3 d-sm-flex justify-content-between">
        <div>
          <input
            className="form-control"
            placeholder="Search chain by name..."
            onChange={(event) => handleSearchByName(event)}
          />
        </div>
        <button
          className="btn btn-success"
          onClick={() => setIsShowModalAddNew(true)}
        >
          <i class="fa-solid fa-circle-plus"></i>
          <span>Add new chain</span>
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
              <th>Legal Name</th>
              <th>Logo Url</th>
              <th>Name</th>
              <th>Chain Code</th>
              <th>Email</th>
              <th>Phone</th>
              <th>Description</th>
              <th>Address</th>
              <th>Status</th>
              <th>Merchant Name</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {listChain
              ? listChain?.map((item, key) => {
                  return (
                    <tr key={key}>
                      <td>{key + 1}</td>
                      <td>{item?.id}</td>
                      <td>
                        <label
                          className="formatLable"
                          onClick={() => handleClickTable(item?.legalName)}
                        >
                          Legal {item?.id}
                        </label>
                      </td>
                      <td>
                        <label
                          className="formatLable"
                          onClick={() => handleShowImage(item?.logoUrl)}
                        >
                          Logo {item?.id}
                        </label>
                      </td>
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
                          onClick={() => handleClickTable(item?.chainCode)}
                        >
                          Code {item?.id}
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
                        <div className="d-flex">
                          <button
                            className="btn btn-warning mx-2"
                            onClick={() => handClickEditChain(item)}
                          >
                            <i class="fa-solid fa-pen-to-square"></i>
                            <span>Edit</span>
                          </button>
                          <button
                            className="btn btn-danger mx-2"
                            onClick={() => handClickDeleteChain(item)}
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

      <Offcanvas
        show={isShowModalAddNew}
        onHide={() => handleClose()}
        placement="end"
        backdrop="static"
      >
        <Offcanvas.Header closeButton>
          <Offcanvas.Title>Add new Chain</Offcanvas.Title>
        </Offcanvas.Header>
        <Offcanvas.Body>
          <Form>
            <Form.Group className="mb-3">
              <Form.Label>Legal name</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter legal name"
                value={legalName}
                onChange={(event) => setLegalName(event.target.value)}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Logo url</Form.Label>
              <Form.Control
                type="text"
                placeholder="Logo url"
                value={logoUrl}
              />
              <div className="d-flex align-items-center">
                <Col xs={6} md={4} className="my-2">
                  <Image src={logoUrl} thumbnail />
                </Col>
                <div className="m-3">
                  <span>Vui lòng chọn ảnh nhỏ hơn 5MB</span>
                  <br />
                  <span>Chọn hình ảnh phù hợp, không phản cảm</span>
                </div>
              </div>
              {file ? (
                <>
                  <Form.Label
                    className="btn btn-success my-3"
                    onClick={() => handleUploadChain()}
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
                    Upload logo
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
              <Form.Label>Email</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter email"
                value={email}
                onChange={(event) => setEmail(event.target.value)}
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
          </Form>
          <div className="d-flex justify-content-between">
            <Button variant="secondary" onClick={handleClose}>
              <i class="fa-solid fa-circle-xmark"></i>
              Close
            </Button>
            <Button variant="primary" onClick={() => handleSaveChain()}>
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
          <Offcanvas.Title>Update Chain</Offcanvas.Title>
        </Offcanvas.Header>
        <Offcanvas.Body>
          <Form>
            <Form.Group className="mb-3">
              <Form.Label>Legal name</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter legal name"
                onChange={(event) => {
                  let element = { ...objEdit };
                  element.legalName = event.target.value;
                  setObjEdit(element);
                }}
                defaultValue={objEdit?.legalName}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Logo url</Form.Label>
              <Form.Control
                type="text"
                placeholder="Logo url"
                defaultValue={objEdit.logoUrl}
              />
              <div className="d-flex align-items-center">
                <Col xs={6} md={4} className="d-flex my-2">
                  <Image src={objEdit?.logoUrl} thumbnail />
                </Col>
                <div className="m-3">
                  <span>Vui lòng chọn ảnh nhỏ hơn 5MB</span>
                  <br />
                  <span>Chọn hình ảnh phù hợp, không phản cảm</span>
                </div>
              </div>
              {file ? (
                <>
                  <Form.Label
                    className="btn btn-success my-3"
                    onClick={() => handleUploadEditChain()}
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
                    Upload logo
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
              <Form.Label>Email</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter email"
                onChange={(event) => {
                  let element = { ...objEdit };
                  element.email = event.target.value;
                  setObjEdit(element);
                }}
                defaultValue={objEdit?.email}
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
                  let element = {...objEdit};
                  element.merchantName = event.target.value;
                  setObjEdit(element);
                }}
              >
                {
                  merchantNames?.map((item) => {
                    return (
                      <>
                      <option
                      value={item}
                      selected={item === objEdit?.merchantName ? true : false}
                      >
                          {item}
                      </option>
                      </>
                    )
                  })
                }
              </Form.Select>
            </Form.Group>
          </Form>
          <div className="d-flex justify-content-between">
            <Button variant="secondary" onClick={handleClose}>
              <i class="fa-solid fa-circle-xmark"></i>
              Close
            </Button>
            <Button variant="primary" onClick={() => handleUpdateChain()}>
              <i class="fa-solid fa-floppy-disk"></i>
              Update Changes
            </Button>
          </div>
        </Offcanvas.Body>
      </Offcanvas>

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
          {chainItem}
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

      <Modal show={isShowModalDelete} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Delete Chain</Modal.Title>
        </Modal.Header>
        <Modal.Body>Do you want to delete !</Modal.Body>
        <Modal.Footer className="d-flex justify-content-between">
          <Button variant="secondary" onClick={handleClose}>
            <i class="fa-solid fa-circle-xmark"></i>
            Close
          </Button>
          <Button variant="primary" onClick={() => handDeleteChain()}>
            <i class="fa-solid fa-check"></i>
            Delete it
          </Button>
        </Modal.Footer>
      </Modal>

    </>
  );
};

export default Chain;
