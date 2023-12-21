import React from "react";
import { getAllMerchant,
          insertMerchant,
          updateMerchant,
          deleteMerchant
} from "../services/MechantService";
import { Table, Modal, Button, Offcanvas, Form } from "react-bootstrap";
import Paging from "./Paging";
import "./Merchant.scss";
import { toast } from "react-toastify";

const Merchant = () => {
  const [listMerchant, setListMerchant] = React.useState([]);
  const [totalMerchant, setTotalMerchant] = React.useState(0);
  const [totalPage, setTotalPage] = React.useState(0);
  const [currentPage, setCurrentPage] = React.useState(1);
  const [limit, setLimit] = React.useState(4);
  const [sortBy, setSortBy] = React.useState("desc");
  const [sortField, setSortField] = React.useState("id");

  const [smShow, setSmShow] = React.useState(false);
  const [isShowModalAddNew, setIsShowModalAddNew] = React.useState(false);
  const [isShowModalUpdate, setIsShowModalUpdate] = React.useState(false);
  const [isShowModalDelete, setIsShowModalDelete] = React.useState(false);

  const [legalName, setLegalName] = React.useState("");
  const [logoUrl, setLogoUrl] = React.useState("");
  const [name, setName] = React.useState("");
  const [email, setEmail] = React.useState("");
  const [phone, setPhone] = React.useState("");
  const [description, setDescription] = React.useState("");
  const [address, setAddress] = React.useState("");
  const [status, setStatus] = React.useState(0);
  const [merchantItem, setMerchantItem] = React.useState({});
  const [objEdit, setObjEdit] = React.useState({});
  const [objDelete, setObjDelete] = React.useState({});

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
    legalName: legalName,
    logoUrl: logoUrl,
    address: address,
    phone: phone,
    email: email,
    description: description,
    status: status
  }

  React.useEffect(() => {
    getMerchant(currentPage, limit, sortBy, sortField);
  }, []);

  const getMerchant = async (page, limit, sortBy, sortField) => {
    let res = await getAllMerchant(page, limit, sortBy, sortField);
    if (res && res.data) {
      setListMerchant(res.data);
      setCurrentPage(res.page);
      setTotalMerchant(res.totalItem);
      setTotalPage(res.totalPage);
    }
  };

  const handleSearchByName = () => {};

  const handleSaveProduct = async() => {
    if(!legalName || !logoUrl ||
       !name || !email || !phone ||
       !description || !address ||
       !status){
        toast.error("Please input full field !");
        return;
       }
       await insertMerchant(obj)
       .then((rs) => {
        if(rs){
          toast.success("Insert table merchant successfully !");
          getMerchant(currentPage, limit, sortBy, sortField);
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

  const handleUpdateMerchant = async() => {
      await updateMerchant(objEdit)
      .then((rs) => {
        if(rs){
          toast.success("Update table merchant successfully !");
          getMerchant(currentPage, limit, sortBy, sortField);
          handleClose();
        }
      })
      .catch((err) => toast.error(err.message));
  }

  const handleDeleteProduct = async() => {
    await deleteMerchant(objDelete)
    .then((rs) =>{
      if(rs){
        toast.success("Delete item merchant is successfully !");
        getMerchant(currentPage, limit, sortBy, sortField);;
        handleClose();
      }
    })
    .catch((err) => toast.error(err.message));
  };

  const handClickEditMerchant = (merchant) => {
    setIsShowModalUpdate(true);
    setObjEdit(merchant);
  };

  const handClickDeleteMerchant = (merchant) => {
      setIsShowModalDelete(true);
      setObjDelete(merchant);
  };

  const handleSortClick = (sortBy, sortField) => {
    getMerchant(currentPage, limit, sortBy, sortField);
  };
  const handlePageClick = (event) => {
    getMerchant(+event.selected + 1, limit, sortBy, sortField);
  };

  const handleClickTable = (merchant) => {
    setSmShow(true);
    setMerchantItem(merchant);
  };
  const handleClose = () => {
    setSmShow(false);
    setIsShowModalAddNew(false);
    setIsShowModalUpdate(false);
    setIsShowModalDelete(false);
  };

  return (
    <>
      <div className="my-3 d-sm-flex justify-content-between">
        <span>
          <b>List Merchant:</b>
        </span>
      </div>

      <div className="my-3 d-sm-flex justify-content-between">
        <div>
          <input
            className="form-control"
            placeholder="Search product by name..."
            onChange={(event) => handleSearchByName(event)}
          />
        </div>
        <button
          className="btn btn-success"
          onClick={() => setIsShowModalAddNew(true)}
        >
          <i class="fa-solid fa-circle-plus"></i>
          <span>Add new product</span>
        </button>
      </div>

      <div className="customize-table">
        <Table striped bordered hover className="table-condensed">
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
                        onClick={() => handleSortClick("desc", "id")}
                      ></i>
                    }
                    <i
                      class="fa-solid fa-sort-up"
                      onClick={() => handleSortClick("asc", "id")}
                    ></i>
                  </span>
                </div>
              </th>
              <th>Legal Name</th>
              <th>Logo Url</th>
              <th>Name</th>
              <th>Merchant Code</th>
              <th>Email</th>
              <th>Phone</th>
              <th>Description</th>
              <th>Address</th>
              <th>Status</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {listMerchant
              ? listMerchant?.map((item, key) => {
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
                          onClick={() => handleClickTable(item?.logoUrl)}
                        >
                          Logo {item?.id}
                        </label>
                      </td>
                      <td>{item?.name}</td>
                      <td>
                        <label
                          className="formatLable"
                          onClick={() => handleClickTable(item?.merchantCode)}
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
                      <td>{item?.phone}</td>
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
                        <label>
                          {
                            item?.status ? "Active" : "Deactive"
                          }
                        </label>
                        </td>
                      <td>
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
          <Offcanvas.Title>Add new product</Offcanvas.Title>
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
                placeholder="Enter logo url"
                value={logoUrl}
                onChange={(event) => setLogoUrl(event.target.value)}
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
          </Form>
          <div className="d-flex justify-content-between">
            <Button variant="secondary" onClick={handleClose}>
              <i class="fa-solid fa-circle-xmark"></i>
              Close
            </Button>
            <Button variant="primary" onClick={() => handleSaveProduct()}>
              <i class="fa-solid fa-floppy-disk"></i>
              Save Changes
            </Button>
          </div>
        </Offcanvas.Body>
      </Offcanvas>


      <Offcanvas show={isShowModalUpdate} onHide={handleClose} placement='end' backdrop="static">
            <Offcanvas.Header closeButton>
                <Offcanvas.Title>Update product</Offcanvas.Title>
            </Offcanvas.Header>
            <Offcanvas.Body>
                <Form>
                    <Form.Group className="mb-3">
                        <Form.Label>Legal name</Form.Label>
                        <Form.Control type="text" placeholder="Enter legal name"
                            onChange={(event) => {
                                let element = { ...objEdit }
                                element.legalName = event.target.value;
                                setObjEdit(element);
                            }}
                            defaultValue={objEdit?.legalName}
                        />
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>Logo url</Form.Label>
                        <Form.Control type="text" placeholder="Enter logo url"
                            onChange={(event) => {
                                let element = { ...objEdit }
                                element.logoUrl = event.target.value;
                                setObjEdit(element);
                            }}
                            defaultValue={objEdit?.logoUrl}
                        />
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>Name</Form.Label>
                        <Form.Control type="text" placeholder="Enter name"
                            onChange={(event) => {
                                let element = { ...objEdit }
                                element.name = event.target.value;
                                setObjEdit(element);
                            }}
                            defaultValue={objEdit?.name}
                        />
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>Email</Form.Label>
                        <Form.Control type="text" placeholder="Enter email"
                            onChange={(event) => {
                                let element = { ...objEdit }
                                element.email = event.target.value;
                                setObjEdit(element);
                            }}
                            defaultValue={objEdit?.email}
                        />
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>Phone</Form.Label>
                        <Form.Control type="text" placeholder="Enter phone"
                            onChange={(event) => {
                                let element = { ...objEdit }
                                element.phone = event.target.value;
                                setObjEdit(element);
                            }}
                            defaultValue={objEdit?.phone}
                        />
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>Description</Form.Label>
                        <Form.Control type="text" placeholder="Enter description"
                            onChange={(event) => {
                                let element = { ...objEdit }
                                element.description = event.target.value;
                                setObjEdit(element);
                            }}
                            defaultValue={objEdit?.description}
                        />
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>Address</Form.Label>
                        <Form.Control type="text" placeholder="Enter address"
                            onChange={(event) => {
                                let element = { ...objEdit }
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
                </Form>
                <div className='d-flex justify-content-between'>
                    <Button variant="secondary" onClick={handleClose}>
                        <i class="fa-solid fa-circle-xmark"></i>
                        Close
                    </Button>
                    <Button variant="primary" onClick={() => handleUpdateMerchant()}>
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
        <Modal.Body>{merchantItem}</Modal.Body>
      </Modal>

      <Modal show={isShowModalDelete} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Delete product</Modal.Title>
            </Modal.Header>
            <Modal.Body>Do you want to delete !</Modal.Body>
            <Modal.Footer className='d-flex justify-content-between'>
                <Button variant="secondary" onClick={handleClose}>
                    <i class="fa-solid fa-circle-xmark"></i>
                    Close
                </Button>
                <Button variant="primary" onClick={() => handleDeleteProduct()}>
                    <i class="fa-solid fa-check"></i>
                    Delete it
                </Button>
            </Modal.Footer>
        </Modal>
    </>
  );
};

export default Merchant;
