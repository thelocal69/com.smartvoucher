import React from "react";
import {
  getAllCategory,
  insertCategory,
  uploadBanner,
  editCategory,
  deleteCategory,
  searchAllByName,
  uploadLocalBanner
} from "../services/CategoryService";
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
import './Category.scss'

const Category = () => {
  const ref = React.useRef(null);

  const [isShowModalAddNew, setIsShowModalAddNew] = React.useState(false);
  const [isShowModalUpdate, setIsShowModalUpdate] = React.useState(false);
  const [isShowModalDelete, setIsShowModalDelete] = React.useState(false);
  const [smShow, setSmShow] = React.useState(false);
  const [loading, setLoading] = React.useState(false);
  const [imageShow, setImageShow] = React.useState(false);

  const [listCategory, setListCategory] = React.useState([]);
  const [categoryItem, setCategoryItem] = React.useState({});
  const [imageItem, setImageItem] = React.useState({});
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
  const [bannerUrl, setBannerUrl] = React.useState("");
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
    bannerUrl: bannerUrl,
    status: status,
  };

  React.useEffect(() => {
    getCategory(currentPage, limit, sortBy, sortField);
  }, []);

  const getCategory = async (currentPage, limit, sortBy, sortField) => {
    await getAllCategory(currentPage, limit, sortBy, sortField)
      .then((rs) => {
        if (rs) {
          setListCategory(rs.data);
          setTotalPage(rs.totalPage);
          setTotalItem(rs.totalItem);
          setCurrentPage(rs.page);
        }
      })
      .catch((err) => {
        toast.error(err.message);
      });
  };

  const handleSearchByName = debounce((event) =>{
    let value = event.target.value;
        if(value){
            searchCategoryByName(value);
            setKeyWord(value);
        }else{
            getCategory(currentPage, limit, sortBy, sortField);
        }
  }, 1000);

  const searchCategoryByName = async() => {
    await searchAllByName(keyWord)
    .then((rs) => {
        if(rs){
            setListCategory(rs.data);
        }
    })
    .catch((err) => toast.error(err.message));
  }

  const handleSaveCategory = async () => {
    if (!bannerUrl || !name || !status) {
      toast.error("Please input full field !");
      return;
    }
    await insertCategory(obj)
      .then((rs) => {
        if (rs) {
          toast.success("Insert row table category successfully !");
          getCategory(currentPage, limit, sortBy, sortField);
          handleClose();
          setBannerUrl("");
          setName("");
          setStatus(0);
        }
      })
      .catch((err) => {
        toast.error(err.message);
      });
  };

  const handleUpdateCategory = async() => {
    await editCategory(objEdit)
    .then((rs) => {
        if(rs){
            toast.success("Update row table category is successfully !");
            getCategory(currentPage, limit, sortBy, sortField);
            handleClose();
        }
    })
    .catch((err) => toast.error(err.message));
  }
  
  const handleDeleteCategory = async() => {
    await deleteCategory(objDelete)
    .then((rs) => {
      if(rs){
        toast.success("Delete row table category is successfully !");
        getCategory(currentPage, limit, sortBy, sortField);
        handleClose();
      }
    })
    .catch((err) => toast.error("Cannot delete parent row because FK !"));
  }

  const handleUploadCategory = async() => {
    if(file){
        const form = new FormData();
        form.append("fileName", file);
        setLoading(true);
        await uploadLocalBanner(form)
        .then((rs) => {
            if(rs){
                toast.success("Upload banner is successfully !");
                setBannerUrl(rs.data);
                setFile(null);
                setLoading(false);
            }
        })
        .catch((err) => {
            setLoading(false);
            toast.error(err.message);
        })
    }else {
        toast.error("Please choose a new banner !");
      }
  };

  const handleUploadEditCategory = async() => {
    if(file){
        const form = new FormData();
        form.append("fileName", file);
        setLoading(true);
        await uploadLocalBanner(form)
        .then((rs) => {
            if(rs){
                toast.success("Upload banner is successfully !");
                objEdit.bannerUrl = rs.data;
                setFile(null);
                setLoading(false);
            }
        })
        .catch((err) => {
            setLoading(false);
            toast.error(err.message);
        })
    }else {
        toast.error("Please choose a new banner !");
      }
  };

  const handleSortClick = (sortBy, sortField) => {
    getCategory(currentPage, limit, sortBy, sortField);
    setSortBy(sortBy);
  };

  const handleClickTable = (categoryItem) => {
    setCategoryItem(categoryItem);
    setSmShow(true);
  };

  const handleShowImage = (image) => {
    setImageItem(image);
    setImageShow(true);
  }

  const handClickEditCategory = (category) => {
    setObjEdit(category);
    setIsShowModalUpdate(true);
  };

  const handClickDeleteCategory = (category) => {
    setIsShowModalDelete(true);
    setObjDelete(category);
  };

  const handlePageClick = (event) => {
    getCategory(+event.selected + 1, limit, sortBy, sortField);
  };

  const handleClose = () => {
    setIsShowModalUpdate(false);
    setIsShowModalAddNew(false);
    setIsShowModalDelete(false);
    setSmShow(false);
    setImageShow(false);
  };

  return (
    <>
      <div className="my-3 d-sm-flex justify-content-between">
        <span>
          <b>List Category:</b>
        </span>
      </div>

      <div className="my-3 d-sm-flex justify-content-between">
        <div>
          <input
            className="form-control"
            placeholder="Search category by name..."
            onChange={(event) => handleSearchByName(event)}
          />
        </div>
        <button
          className="btn btn-success"
          onClick={() => setIsShowModalAddNew(true)}
        >
          <i class="fa-solid fa-circle-plus"></i>
          <span>Add new category</span>
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
              <th>Banner Url</th>
              <th>Name</th>
              <th>Category Code</th>
              <th>Status</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {listCategory
              ? listCategory?.map((item, key) => {
                  return (
                    <tr key={key}>
                      <td>{key + 1}</td>
                      <td>{item?.id}</td>
                      <td>
                        <label
                          className="formatLable"
                          onClick={() => handleShowImage(item?.bannerUrl)}
                        >
                          Banner {item?.id}
                        </label>
                      </td>
                      <td>{item?.name}</td>
                      <td>
                        <label
                          className="formatLable"
                          onClick={() => handleClickTable(item?.categoryCode)}
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
                            onClick={() => handClickEditCategory(item)}
                          >
                            <i class="fa-solid fa-pen-to-square"></i>
                            <span>Edit</span>
                          </button>
                          <button
                            className="btn btn-danger mx-2"
                            onClick={() => handClickDeleteCategory(item)}
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
              <Form.Label>Banner url</Form.Label>
              <Form.Control
                type="text"
                placeholder="Logo url"
                value={bannerUrl}
              />
              <div className="d-flex align-items-center">
                <Col xs={6} md={4} className="my-2">
                  <Image src={bannerUrl} thumbnail />
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
                    onClick={() => handleUploadCategory()}
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
            <Button variant="primary" onClick={() => handleSaveCategory()}>
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
              <Form.Label>Banner url</Form.Label>
              <Form.Control
                type="text"
                placeholder="Logo url"
                defaultValue={objEdit.bannerUrl}
              />
              <div className="d-flex align-items-center">
                <Col xs={6} md={4} className="d-flex my-2">
                  <Image src={objEdit?.bannerUrl} thumbnail />
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
                    onClick={() => handleUploadEditCategory()}
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
            <Button variant="primary" onClick={() => handleUpdateCategory()}>
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
          {categoryItem}
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
          <Modal.Title>Delete Category</Modal.Title>
        </Modal.Header>
        <Modal.Body>Do you want to delete !</Modal.Body>
        <Modal.Footer className="d-flex justify-content-between">
          <Button variant="secondary" onClick={handleClose}>
            <i class="fa-solid fa-circle-xmark"></i>
            Close
          </Button>
          <Button variant="primary" onClick={() => handleDeleteCategory()}>
            <i class="fa-solid fa-check"></i>
            Delete it
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default Category;
