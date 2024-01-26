import React from "react";
import {
  getAllWarehouse,
  searchAllByName,
  insertWarehouse,
  editWarehouse,
  deleteWarehouse,
  uploadLocalWarehouseBanner,
  uploadLocalWarehouseThumbnail,
} from "../services/WarehouseService";
import {
  Table,
  Modal,
  Button,
  Offcanvas,
  Form,
  Col,
  Image,
} from "react-bootstrap";
import Paging from "./Paging";
import { toast } from "react-toastify";
import debounce from "lodash.debounce";
import { getAllCategoryName } from "../services/CategoryService";
import { getAllDiscountName } from "../services/DiscountTypeService";
import { getAllLabelName } from "../services/LabelService";
import Moment from "moment";
import Loading from "./Loading";
import { DateTimePicker } from "@mui/x-date-pickers";
import "./Warehouse.scss";

const Warehouse = () => {
  const ref = React.useRef(null);
  const refClone = React.useRef(null);

  const [isShowModalAddNew, setIsShowModalAddNew] = React.useState(false);
  const [isShowModalUpdate, setIsShowModalUpdate] = React.useState(false);
  const [isShowModalDelete, setIsShowModalDelete] = React.useState(false);
  const [smShow, setSmShow] = React.useState(false);
  const [imageShow, setImageShow] = React.useState(false);
  const [loading, setLoading] = React.useState(false);

  const [currentPage, setCurrentPage] = React.useState(1);
  const [totalPage, setTotalPage] = React.useState(0);
  const [totalItem, setTotalItem] = React.useState(0);
  const [limit, setLimit] = React.useState(6);
  const [sortBy, setSortBy] = React.useState("desc");
  const [sortField, setSortField] = React.useState("id");
  const [keyWord, setKeyWord] = React.useState("");
  const [file, setFile] = React.useState(null);
  const [fileClone, setFileClone] = React.useState(null);

  const [listWarehouse, setListWarehouse] = React.useState([]);
  const [categoryNames, setCategoryNames] = React.useState([]);
  const [discountNames, setDiscountNames] = React.useState([]);
  const [labelNames, setLabelNames] = React.useState([]);
  const [warehouseItem, setWareHouseItem] = React.useState({});
  const [imageItem, setImageItem] = React.useState({});
  const [objEdit, setObjEdit] = React.useState({});
  const [objDelete, setObjDelete] = React.useState({});
  const [name, setName] = React.useState("");
  const [description, setDescription] = React.useState("");
  const [termOfUse, setTermOfUse] = React.useState("");
  const [bannerUrl, setBannerUrl] = React.useState("");
  const [thumbnailUrl, setThumbnailUrl] = React.useState("");
  const [price, setPrice] = React.useState(0);
  const [discountAmount, setDiscountAmount] = React.useState(0);
  const [maxDiscountAmount, setMaxDiscountAmount] = React.useState(0);
  const [availableFrom, setAvailableFrom] = React.useState(
    Moment(new Date()).format()
  );
  const [availableTo, setAvailableTo] = React.useState(
    Moment(new Date().setDate(new Date().getDate() + 1)).format()
  );
  const [status, setStatus] = React.useState(0);
  const [showOnWeb, setShowOnWeb] = React.useState(0);
  const [capacity, setCapacity] = React.useState(0);
  const [voucherChannel, setVoucherChannel] = React.useState(0);
  const [categoryName, setCategoryName] = React.useState("");
  const [discountName, setDiscountName] = React.useState("");
  const [labelName, setLabelName] = React.useState("");

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

  const showOnWebHardCode = [
    {
      label: "Show",
      value: 1,
    },
    {
      label: "Nope",
      value: 0,
    },
  ];

  const obj = {
    name: name,
    description: description,
    termOfUse: termOfUse,
    bannerUrl: bannerUrl,
    thumbnailUrl: thumbnailUrl,
    price: Number(price),
    discountAmount: Number(discountAmount),
    maxDiscountAmount: Number(maxDiscountAmount),
    availableFrom: availableFrom,
    availableTo: availableTo,
    showOnWeb: Number(showOnWeb),
    capacity: Number(capacity),
    voucherChannel: Number(voucherChannel),
    status: status,
    categoryName: categoryName,
    discountTypeName: discountName,
    labelName: labelName,
  };

  React.useEffect(() => {
    if (Moment(availableTo).isBefore(Moment(availableFrom))) {
      let d = Moment(availableFrom).date();
      setAvailableTo(
        Moment(availableFrom)
          .set("date", d + 1)
          .format()
      );
    }
  }, [availableFrom, availableTo]);
  React.useEffect(() => {
    if (
      objEdit &&
      Moment(objEdit.availableTo).isBefore(Moment(objEdit.availableFrom))
    ) {
      let d = Moment(objEdit.availableFrom).date();
      let ob = { ...objEdit };
      ob.availableTo = Moment(objEdit.availableFrom)
        .set("date", d + 1)
        .format();

      setObjEdit(ob);
    }
  }, [objEdit]);

  React.useEffect(() => {
    getWarehouse(currentPage, limit, sortBy, sortField);
    getCategoryName();
    getDiscountName();
    getLabelName();
  }, []);

  React.useEffect(() => {
    categoryNames && setCategoryName(categoryNames[0]);
  }, [categoryNames]);

  React.useEffect(() => {
    discountNames && setDiscountName(discountNames[0]);
  }, [discountNames]);

  React.useEffect(() => {
    labelNames && setLabelName(labelNames[0]);
  }, [labelNames]);

  const getWarehouse = async (currentPage, limit, sortBy, sortField) => {
    await getAllWarehouse(currentPage, limit, sortBy, sortField)
      .then((rs) => {
        if (rs) {
          setListWarehouse(rs.data);
          setCurrentPage(rs.page);
          setTotalItem(rs.totalItem);
          setTotalPage(rs.totalPage);
        }
      })
      .catch((err) => toast.error(err.message));
  };

  const handleSearchByName = debounce((event) => {
    const value = event.target.value;
    if (value) {
      setKeyWord(value);
      searchAllWarehouseByName(value);
    } else {
      getWarehouse(currentPage, limit, sortBy, sortField);
    }
  }, 1000);

  const searchAllWarehouseByName = async (keyWord) => {
    await searchAllByName(keyWord)
      .then((rs) => {
        if (rs) {
          setListWarehouse(rs.data);
        }
      })
      .catch((err) => toast.error(err.message));
  };

  const getCategoryName = async () => {
    await getAllCategoryName()
      .then((rs) => {
        if (rs) {
          setCategoryNames(rs.data);
        }
      })
      .catch((err) => toast.error(err.message));
  };

  const getDiscountName = async () => {
    await getAllDiscountName()
      .then((rs) => {
        if (rs) {
          setDiscountNames(rs.data);
        }
      })
      .catch((err) => toast.error(err.message));
  };

  const getLabelName = async () => {
    await getAllLabelName()
      .then((rs) => {
        if (rs) {
          setLabelNames(rs.data);
        }
      })
      .catch((err) => toast.error(err.message));
  };

  const handleSaveWarehouse = async () => {
    if (
      !bannerUrl ||
      !thumbnailUrl ||
      !name ||
      !price ||
      !termOfUse ||
      !description ||
      !discountAmount ||
      !maxDiscountAmount ||
      !availableFrom ||
      !availableTo ||
      !showOnWeb ||
      !capacity ||
      !voucherChannel ||
      !categoryName ||
      !discountName ||
      !labelName ||
      !status
    ) {
      toast.error("Please input full field !");
      return;
    }
    await insertWarehouse(obj)
      .then((rs) => {
        if (rs) {
          toast.success("Insert row Warehouse is successfully !");
          getWarehouse(currentPage, limit, sortBy, sortField);
          handleClose();
          setBannerUrl("");
          setThumbnailUrl("");
          setName("");
          setTermOfUse(0);
          setAvailableFrom(Moment(new Date()).format());
          setAvailableTo(
            Moment(new Date().setDate(new Date().getDate() + 1)).format()
          );
          setDescription("");
          setCapacity(0);
          setDiscountAmount(0);
          setMaxDiscountAmount(0);
          setPrice(0);
          setShowOnWeb(0);
          setVoucherChannel(0);
          setStatus(0);
        }
      })
      .catch((err) => toast.error(err.message));
  };

  const handleUpdateWarehouse = async () => {
    await editWarehouse(objEdit)
      .then((rs) => {
        if (rs) {
          toast.success("Update row table Warehouse is successfully !");
          getWarehouse(currentPage, limit, sortBy, sortField);
          handleClose();
        }
      })
      .catch((err) => toast.error(err.message));
  };

  const handDeleteWarehouse = async () => {
    await deleteWarehouse(objDelete)
      .then((rs) => {
        if (rs) {
          toast.success("Delete row table Warehouse is successfully !");
          getWarehouse(currentPage, limit, sortBy, sortField);
          handleClose();
        }
      })
      .catch((err) => toast.error("Cannot delete parent row because FK !"));
  };

  const handleUploadBannerWarehouse = async () => {
    if (file) {
      const form = new FormData();
      form.append("fileName", file);
      setLoading(true);
      await uploadLocalWarehouseBanner(form)
        .then((rs) => {
          if (rs) {
            setLoading(false);
            setFile(null);
            setBannerUrl(rs.data);
            toast.success(rs.message);
          }
        })
        .catch((err) => {
          setLoading(false);
          toast.error(err.message);
        });
    } else {
      toast.error("Please choose an banner !");
    }
  };

  const handleUploadEditBannerWarehouse = async () => {
    if (file) {
      const form = new FormData();
      form.append("fileName", file);
      setLoading(true);
      await uploadLocalWarehouseBanner(form)
        .then((rs) => {
          if (rs) {
            setLoading(false);
            setFile(null);
            objEdit.bannerUrl = rs.data;
            toast.success(rs.message);
          }
        })
        .catch((err) => {
          setLoading(false);
          toast.error(err.message);
        });
    } else {
      toast.error("Please choose an banner !");
    }
  };

  const handleUploadThumbnailWarehouse = async () => {
    if (fileClone) {
      const form = new FormData();
      form.append("fileName", fileClone);
      setLoading(true);
      await uploadLocalWarehouseThumbnail(form)
        .then((rs) => {
          if (rs) {
            setLoading(false);
            setFileClone(null);
            setThumbnailUrl(rs.data);
            toast.success(rs.message);
          }
        })
        .catch((err) => {
          setLoading(false);
          toast.error(err.message);
        });
    } else {
      toast.error("Please choose an thumbnail !");
    }
  };

  const handleUploadEditThumbnailWarehouse = async () => {
    if (fileClone) {
      const form = new FormData();
      form.append("fileName", fileClone);
      setLoading(true);
      await uploadLocalWarehouseThumbnail(form)
        .then((rs) => {
          if (rs) {
            setLoading(false);
            setFileClone(null);
            objEdit.thumbnailUrl = rs.data;
            toast.success(rs.message);
          }
        })
        .catch((err) => {
          setLoading(false);
          toast.error(err.message);
        });
    } else {
      toast.error("Please choose an thumbnail !");
    }
  };

  const handleSortClick = (sortBy, sortField) => {
    getWarehouse(currentPage, limit, sortBy, sortField);
    setSortBy(sortBy);
  };

  const handleClickTable = (warehouseItem) => {
    setWareHouseItem(warehouseItem);
    setSmShow(true);
  };

  const handleShowImage = (image) => {
    setImageItem(image);
    setImageShow(true);
  };

  const handClickEditWarehouse = (warehouse) => {
    setObjEdit(warehouse);
    setIsShowModalUpdate(true);
  };

  const handClickDeleteWarehouse = (warehouse) => {
    setObjDelete(warehouse);
    setIsShowModalDelete(true);
  };

  const handlePageClick = (event) => {
    getWarehouse(+event.selected + 1, limit, sortBy, sortField);
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
          <b>List Warehouse:</b>
        </span>
      </div>

      <div className="my-3 d-sm-flex justify-content-between">
        <div>
          <input
            className="form-control"
            placeholder="Search warehouse by name..."
            onChange={(event) => handleSearchByName(event)}
          />
        </div>
        <button
          className="btn btn-success"
          onClick={() => setIsShowModalAddNew(true)}
        >
          <i class="fa-solid fa-circle-plus"></i>
          <span>Add new warehouse</span>
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
              <th>Warehouse Code</th>
              <th>Banner Url</th>
              <th>Thumbnail Url</th>
              <th>Price</th>
              <th>Discount Amount</th>
              <th>Max Discount Amount</th>
              <th>Available From</th>
              <th>Available To</th>
              <th>Term Of Use</th>
              <th>Show On Web</th>
              <th>Capacity</th>
              <th>Voucher Channel</th>
              <th>Description</th>
              <th>Status</th>
              <th>Category Name</th>
              <th>Discount Name</th>
              <th>Label Name</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {listWarehouse
              ? listWarehouse?.map((item, key) => {
                  return (
                    <tr key={key}>
                      <td>{key + 1}</td>
                      <td>{item?.id}</td>
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
                          onClick={() => handleClickTable(item?.warehouseCode)}
                        >
                          Code {item?.id}
                        </label>
                      </td>
                      <td>
                        <label
                          className="formatLable"
                          onClick={() => handleShowImage(item?.bannerUrl)}
                        >
                          Banner {item?.id}
                        </label>
                      </td>
                      <td>
                        <label
                          className="formatLable"
                          onClick={() => handleShowImage(item?.thumbnailUrl)}
                        >
                          Thumbnail {item?.id}
                        </label>
                      </td>
                      <td>
                        <label
                          className="formatLable"
                          onClick={() => handleClickTable(item?.price)}
                        >
                          Price {item?.id}
                        </label>
                      </td>
                      <td>
                        <label
                          className="formatLable"
                          onClick={() => handleClickTable(item?.discountAmount)}
                        >
                          Discount {item?.id}
                        </label>
                      </td>
                      <td>
                        <label
                          className="formatLable"
                          onClick={() =>
                            handleClickTable(item?.maxDiscountAmount)
                          }
                        >
                          Max Discount {item?.id}
                        </label>
                      </td>
                      <td>
                        <label
                          className="formatLable"
                          onClick={() =>
                            handleClickTable(
                              Moment(item?.availableFrom).format(
                                "YYYY/DD/MM hh:mm:ss"
                              )
                            )
                          }
                        >
                          From {item?.id}
                        </label>
                      </td>
                      <td>
                        <label
                          className="formatLable"
                          onClick={() =>
                            handleClickTable(
                              Moment(item?.availableTo).format(
                                "YYYY/DD/MM hh:mm:ss"
                              )
                            )
                          }
                        >
                          To {item?.id}
                        </label>
                      </td>
                      <td>
                        <label
                          className="formatLable"
                          onClick={() => handleClickTable(item?.termOfUse)}
                        >
                          Use {item?.id}
                        </label>
                      </td>
                      <td>
                        <label
                          className={
                            item?.showOnWeb ? "ac active" : "ac deactive"
                          }
                        >
                          {item?.showOnWeb ? "Show" : "Nope"}
                        </label>
                      </td>
                      <td>
                        <label
                          className="formatLable"
                          onClick={() => handleClickTable(item?.capacity)}
                        >
                          Capacity {item?.id}
                        </label>
                      </td>
                      <td>
                        <label
                          className="formatLable"
                          onClick={() => handleClickTable(item?.voucherChannel)}
                        >
                          Channel {item?.id}
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
                          className={item?.status ? "ac active" : "ac deactive"}
                        >
                          {item?.status ? "Active" : "Deactive"}
                        </label>
                      </td>
                      <td>
                        <label
                          className="formatLable"
                          onClick={() => handleClickTable(item?.categoryName)}
                        >
                          Category {item?.id}
                        </label>
                      </td>
                      <td>
                        <label
                          className="formatLable"
                          onClick={() =>
                            handleClickTable(item?.discountTypeName)
                          }
                        >
                          Discount Type {item?.id}
                        </label>
                      </td>
                      <td>
                        <label
                          className="formatLable"
                          onClick={() => handleClickTable(item?.labelName)}
                        >
                          Label {item?.id}
                        </label>
                      </td>
                      <td>
                        <div className="d-flex">
                          <button
                            className="btn btn-warning mx-2"
                            onClick={() => handClickEditWarehouse(item)}
                          >
                            <i class="fa-solid fa-pen-to-square"></i>
                            <span>Edit</span>
                          </button>
                          <button
                            className="btn btn-danger mx-2"
                            onClick={() => handClickDeleteWarehouse(item)}
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
          <Offcanvas.Title>Add new Warehouse</Offcanvas.Title>
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
                    onClick={() => handleUploadBannerWarehouse()}
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
                    Upload banner
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
              <Form.Label>Thumbnail url</Form.Label>
              <Form.Control
                type="text"
                placeholder="Logo url"
                value={thumbnailUrl}
              />
              <div className="d-flex align-items-center">
                <Col xs={6} md={4} className="my-2">
                  <Image src={thumbnailUrl} thumbnail />
                </Col>
                <div className="m-3">
                  <span>Vui lòng chọn ảnh nhỏ hơn 5MB</span>
                  <br />
                  <span>Chọn hình ảnh phù hợp, không phản cảm</span>
                </div>
              </div>
              {fileClone ? (
                <>
                  <Form.Label
                    className="btn btn-success my-3"
                    onClick={() => handleUploadThumbnailWarehouse()}
                  >
                    <i class="fa-solid fa-check"></i>
                    Accept
                  </Form.Label>
                  {loading && (
                    <div className="loading">
                      <Loading fileName={fileClone.name} />
                    </div>
                  )}
                </>
              ) : (
                <>
                  <Form.Label
                    className="btn btn-primary my-3"
                    onClick={() => {
                      refClone.current.click();
                    }}
                  >
                    <i class="fa-solid fa-upload"></i>
                    Upload thumbnail
                  </Form.Label>
                </>
              )}
              <Form.Control
                type="file"
                ref={refClone}
                accept="image/png, image/jpeg"
                onChange={(event) => setFileClone(event.target.files[0])}
                hidden
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Price</Form.Label>
              <Form.Control
                type="number"
                placeholder="Enter price"
                value={price}
                onChange={(event) => setPrice(event.target.value)}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Discount Amount</Form.Label>
              <Form.Control
                type="number"
                placeholder="Enter Discount Amount"
                value={discountAmount}
                onChange={(event) => setDiscountAmount(event.target.value)}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Max Discount Amount</Form.Label>
              <Form.Control
                type="number"
                placeholder="Enter Max Discount Amount"
                value={maxDiscountAmount}
                onChange={(event) => setMaxDiscountAmount(event.target.value)}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Available From</Form.Label>
              <DateTimePicker
                value={Moment(availableFrom)}
                onChange={(newValue) => setAvailableFrom(newValue.format())}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Available To</Form.Label>
              <DateTimePicker
                value={Moment(availableTo)}
                onChange={(newValue) => setAvailableTo(newValue.format())}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Term Of Use</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter Term Of Use"
                value={termOfUse}
                onChange={(event) => setTermOfUse(event.target.value)}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Show On Web</Form.Label>
              <div
                className="d-flex justify-content-between"
                onChange={(event) => setShowOnWeb(event.target.value)}
              >
                {showOnWebHardCode?.map((item) => {
                  return (
                    <label>
                      <input
                        checked={Number(showOnWeb) === Number(item.value)}
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
              <Form.Label>Capacity</Form.Label>
              <Form.Control
                type="number"
                placeholder="Enter Capacity"
                value={capacity}
                onChange={(event) => setCapacity(event.target.value)}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Voucher Channel</Form.Label>
              <Form.Control
                type="number"
                placeholder="Enter Voucher Channel"
                value={voucherChannel}
                onChange={(event) => setVoucherChannel(event.target.value)}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Description</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter Description"
                value={description}
                onChange={(event) => setDescription(event.target.value)}
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
              <Form.Label>Category Name</Form.Label>
              <Form.Select
                type="text"
                placeholder="Enter Category"
                onChange={(event) => setCategoryName(event.target.value)}
              >
                {categoryNames?.map((item) => {
                  return <option value={item}>{item}</option>;
                })}
              </Form.Select>
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Discount Name</Form.Label>
              <Form.Select
                type="text"
                placeholder="Enter Discount"
                onChange={(event) => setDiscountName(event.target.value)}
              >
                {discountNames?.map((item) => {
                  return <option value={item}>{item}</option>;
                })}
              </Form.Select>
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Label Name</Form.Label>
              <Form.Select
                type="text"
                placeholder="Enter Label"
                onChange={(event) => setLabelName(event.target.value)}
              >
                {labelNames?.map((item) => {
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
            <Button variant="primary" onClick={() => handleSaveWarehouse()}>
              <i class="fa-solid fa-floppy-disk"></i>
              Save Changes
            </Button>
          </div>
        </Offcanvas.Body>
      </Offcanvas>

      <Offcanvas
        show={isShowModalUpdate}
        onHide={() => handleClose()}
        placement="end"
        backdrop="static"
      >
        <Offcanvas.Header closeButton>
          <Offcanvas.Title>Update Warehouse</Offcanvas.Title>
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
              <Form.Label>Banner url</Form.Label>
              <Form.Control
                type="text"
                placeholder="Logo url"
                defaultValue={objEdit.bannerUrl}
              />
              <div className="d-flex align-items-center">
                <Col xs={6} md={4} className="my-2">
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
                    onClick={() => handleUploadEditBannerWarehouse()}
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
                    Upload banner
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
              <Form.Label>Thumbnail url</Form.Label>
              <Form.Control
                type="text"
                placeholder="Logo url"
                defaultValue={objEdit.thumbnailUrl}
              />
              <div className="d-flex align-items-center">
                <Col xs={6} md={4} className="my-2">
                  <Image src={objEdit?.thumbnailUrl} thumbnail />
                </Col>
                <div className="m-3">
                  <span>Vui lòng chọn ảnh nhỏ hơn 5MB</span>
                  <br />
                  <span>Chọn hình ảnh phù hợp, không phản cảm</span>
                </div>
              </div>
              {fileClone ? (
                <>
                  <Form.Label
                    className="btn btn-success my-3"
                    onClick={() => handleUploadEditThumbnailWarehouse()}
                  >
                    <i class="fa-solid fa-check"></i>
                    Accept
                  </Form.Label>
                  {loading && (
                    <div className="loading">
                      <Loading fileName={fileClone.name} />
                    </div>
                  )}
                </>
              ) : (
                <>
                  <Form.Label
                    className="btn btn-primary my-3"
                    onClick={() => {
                      refClone.current.click();
                    }}
                  >
                    <i class="fa-solid fa-upload"></i>
                    Upload thumbnail
                  </Form.Label>
                </>
              )}
              <Form.Control
                type="file"
                ref={refClone}
                accept="image/png, image/jpeg"
                onChange={(event) => setFileClone(event.target.files[0])}
                hidden
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Price</Form.Label>
              <Form.Control
                type="number"
                placeholder="Enter price"
                onChange={(event) => {
                  let element = { ...objEdit };
                  element.price = event.target.value;
                  setObjEdit(element);
                }}
                defaultValue={objEdit?.price}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Discount Amount</Form.Label>
              <Form.Control
                type="number"
                placeholder="Enter Discount Amount"
                onChange={(event) => {
                  let element = { ...objEdit };
                  element.discountAmount = event.target.value;
                  setObjEdit(element);
                }}
                defaultValue={objEdit?.discountAmount}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Max Discount Amount</Form.Label>
              <Form.Control
                type="number"
                placeholder="Enter Max Discount Amount"
                onChange={(event) => {
                  let element = { ...objEdit };
                  element.maxDiscountAmount = event.target.value;
                  setObjEdit(element);
                }}
                defaultValue={objEdit?.maxDiscountAmount}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              {objEdit && objEdit.availableFrom && (
                <>
                  <Form.Label>Available From</Form.Label>
                  <DateTimePicker
                    value={Moment(objEdit.availableFrom)}
                    onChange={(newValue) => {
                      let element = { ...objEdit };
                      element.availableFrom = newValue.format();
                      setObjEdit(element);
                    }}
                  />
                </>
              )}
            </Form.Group>
            <Form.Group className="mb-3">
              {objEdit && objEdit.availableTo && (
                <>
                  <Form.Label>Available To</Form.Label>
                  <DateTimePicker
                    value={Moment(objEdit.availableTo)}
                    onChange={(newValue) => {
                      let element = { ...objEdit };
                      element.availableTo = newValue.format();
                      setObjEdit(element);
                    }}
                  />
                </>
              )}
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Term Of Use</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter Term Of Use"
                onChange={(event) => {
                  let element = { ...objEdit };
                  element.termOfUse = event.target.value;
                  setObjEdit(element);
                }}
                defaultValue={objEdit?.termOfUse}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Show On Web</Form.Label>
              <div
                className="d-flex justify-content-between"
                onChange={(event) => {
                  let element = { ...objEdit };
                  element.showOnWeb = event.target.value;
                  setObjEdit(element);
                }}
              >
                {showOnWebHardCode?.map((item) => {
                  return (
                    <label>
                      <input
                        checked={
                          Number(objEdit?.showOnWeb) === Number(item.value)
                        }
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
              <Form.Label>Capacity</Form.Label>
              <Form.Control
                type="number"
                placeholder="Enter Capacity"
                onChange={(event) => {
                  let element = { ...objEdit };
                  element.capacity = event.target.value;
                  setObjEdit(element);
                }}
                defaultValue={objEdit?.capacity}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Voucher Channel</Form.Label>
              <Form.Control
                type="number"
                placeholder="Enter Voucher Channel"
                onChange={(event) => {
                  let element = { ...objEdit };
                  element.voucherChannel = event.target.value;
                  setObjEdit(element);
                }}
                defaultValue={objEdit?.voucherChannel}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Description</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter Description"
                onChange={(event) => {
                  let element = { ...objEdit };
                  element.description = event.target.value;
                  setObjEdit(element);
                }}
                defaultValue={objEdit?.description}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Status</Form.Label>
              <div
                className="d-flex justify-content-between"
                onChange={(event) => {
                  let element = { ...objEdit };
                  element.status = event.target.value;
                  setObjEdit(element);
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
              <Form.Label>Category Name</Form.Label>
              <Form.Select
                type="text"
                placeholder="Enter Category"
                onChange={(event) => {
                  let element = { ...objEdit };
                  element.categoryName = event.target.value;
                  setObjEdit(element);
                }}
              >
                {categoryNames?.map((item) => {
                  return (
                    <option
                      value={item}
                      selected={item === objEdit?.merchantName ? true : false}
                    >
                      {item}
                    </option>
                  );
                })}
              </Form.Select>
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Discount Name</Form.Label>
              <Form.Select
                type="text"
                placeholder="Enter Discount"
                onChange={(event) => {
                  let element = { ...objEdit };
                  element.discountTypeName = event.target.value;
                  setObjEdit(element);
                }}
              >
                {discountNames?.map((item) => {
                  return (
                    <option
                      value={item}
                      selected={
                        item === objEdit?.discountTypeName ? true : false
                      }
                    >
                      {item}
                    </option>
                  );
                })}
              </Form.Select>
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Label Name</Form.Label>
              <Form.Select
                type="text"
                placeholder="Enter Label"
                onChange={(event) => {
                  let element = { ...objEdit };
                  element.labelName = event.target.value;
                  setObjEdit(element);
                }}
              >
                {labelNames?.map((item) => {
                  return (
                    <option
                      value={item}
                      selected={item === objEdit?.labelName ? true : false}
                    >
                      {item}
                    </option>
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
            <Button variant="primary" onClick={() => handleUpdateWarehouse()}>
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
        <Modal.Body>{warehouseItem}</Modal.Body>
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
            <Image src={imageItem} thumbnail />
          </Col>
        </Modal.Body>
      </Modal>

      <Modal show={isShowModalDelete} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Delete Warehouse</Modal.Title>
        </Modal.Header>
        <Modal.Body>Do you want to delete !</Modal.Body>
        <Modal.Footer className="d-flex justify-content-between">
          <Button variant="secondary" onClick={handleClose}>
            <i class="fa-solid fa-circle-xmark"></i>
            Close
          </Button>
          <Button variant="primary" onClick={() => handDeleteWarehouse()}>
            <i class="fa-solid fa-check"></i>
            Delete it
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default Warehouse;
