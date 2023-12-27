import React from "react";
import { getAllSerial } from "../services/SerialService";
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
import Moment from "moment";
import './Serial.scss';


const Serial = () => {
  const [smShow, setSmShow] = React.useState(false);

  const [listSerial, setListSerial] = React.useState([]);
  const [serialItem, setSerialItem] = React.useState({});
  const [totalItem, setTotalItem] = React.useState(0);
  const [totalPage, setTotalPage] = React.useState(0);
  const [currentPage, setCurrentPage] = React.useState(1);
  const [limit, setLimit] = React.useState(6);
  const [sortBy, setSortBy] = React.useState("desc");
  const [sortField, setSortField] = React.useState("id");

    React.useEffect(() =>{
        getSerial(currentPage, limit, sortBy, sortField);
    }, []);

  const getSerial = async(currentPage, limit, sortBy, sortField) => {
    await getAllSerial(currentPage, limit, sortBy, sortField)
    .then((rs) => {
        if(rs){
            setListSerial(rs.data);
            setCurrentPage(rs.page);
            setTotalItem(rs.totalItem);
            setTotalPage(rs.totalPage);
        }
    })
    .catch((err) => toast.error(err.message));
  }

  const handleSortClick = (sortBy, sortField) => {
    getSerial(currentPage, limit, sortBy, sortField);
    setSortBy(sortBy);
  };

  const handleClickTable = (serialItem) => {
    setSerialItem(serialItem);
    setSmShow(true);
  };

  const handlePageClick = (event) => {
    getSerial(+event.selected + 1, limit, sortBy, sortField);
  };

  const handleClose = () => {
    setSmShow(false);
  };
  return (
    <>
      <div className="my-3 d-sm-flex justify-content-between">
        <span>
          <b>List Serial:</b>
        </span>
      </div>

      {/* <div className="my-3 d-sm-flex justify-content-between">
        <div>
          <input
            className="form-control"
            placeholder="Search serial by name..."
            onChange={(event) => handleSearchByName(event)}
          />
        </div>
        <button
          className="btn btn-success"
          onClick={() => setIsShowModalAddNew(true)}
        >
          <i class="fa-solid fa-circle-plus"></i>
          <span>Add new merchant</span>
        </button>
      </div> */}

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
              <th>Batch Code</th>
              <th>Number Of Serial</th>
              <th>Serial Code</th>
              <th>Status</th>
              <th>Created By</th>
              <th>Created At</th>
              <th>Update By</th>
              <th>Update At</th>
            </tr>
          </thead>
          <tbody>
            {listSerial
              ? listSerial?.map((item, key) => {
                  return (
                    <tr key={key}>
                      <td>{key + 1}</td>
                      <td>{item?.id}</td>
                      <td>
                        <label>
                          {item?.batchCode}
                        </label>
                      </td>
                      <td>{item?.numberOfSerial}</td>
                      <td>
                        <label>
                         {item?.serialCode}
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
                        <label>
                         {item?.createdBy}
                        </label>
                      </td>
                      <td>
                        <label
                          className="formatLable"
                          onClick={() => handleClickTable(
                            Moment(item?.createdAt).format(
                                "YYYY/DD/MM hh:mm:ss"
                            )                        
                            )}
                        >
                          Created At {item?.id}
                        </label>
                      </td>
                      <td>{item?.updatedBy}</td>
                      <td>
                        <label
                          className="formatLable"
                          onClick={() => handleClickTable(
                            Moment(item?.updateAt).format(
                                "YYYY/DD/MM hh:mm:ss"
                            )                        
                            )}
                        >
                          Update At {item?.id}
                        </label>
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
          {serialItem}
        </Modal.Body>
      </Modal>
    </>
  );
};

export default Serial;
