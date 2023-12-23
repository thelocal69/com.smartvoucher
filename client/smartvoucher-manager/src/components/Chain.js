import React from "react";
import {getAllChain} from '../services/ChainService';
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

    const [isShowModalAddNew, setIsShowModalAddNew] = React.useState(false);
    const [smShow, setSmShow] = React.useState(false);
    
    const [currentPage, setCurrentPage] = React.useState(1);
    const [totalPage, setTotalPage] = React.useState(0);
    const [totalItem, setTotalItem] = React.useState(0);
    const [limit, setLimit] = React.useState(6);
    const [sortBy, setSortBy] = React.useState("desc");
    const [sortField, setSortField] = React.useState("id");

    const [listChain, setListChain] = React.useState([]);
    const [chainItem, setChainItem] = React.useState({});

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

    React.useEffect(() =>{
        getChain(currentPage, limit, sortBy, sortField)
        .catch((err) => toast.error(err.message));
    },[]);

    const getChain = async(currentPage, limit, sortBy, sortField) => {
        let res = await getAllChain(currentPage, limit, sortBy, sortField);
        if(res && res.data){
            setCurrentPage(res.page);
            setTotalItem(res.totalItem);
            setTotalPage(res.totalPage);
            setListChain(res.data);
        }
    }

    const handleSearchByName = () => {

    }

    const handClickEditMerchant = () => {

    }

    const handClickDeleteMerchant = () => {

    }

    const handleSortClick = (sortBy, sortField) => {
        getChain(currentPage, limit, sortBy, sortField);
        setSortBy(sortBy);
    }

    const handleClickTable = (chain) => {
        setSmShow(true);
        setChainItem(chain);
    }

    const handlePageClick = (event) => {
        getChain(+event.selected + 1, limit, sortBy, sortField);
   }

    const handleClose = () => {
        setIsShowModalAddNew(false);
        setSmShow(false);
    }

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
            placeholder="Search merchant by name..."
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
                        <label>{item?.status ? "Active" : "Deactive"}</label>
                      </td>
                      <td>
                        <label className="formatLable"
                          onClick={() => handleClickTable(item?.merchantName)}
                          >
                            Merchant {item?.id}
                          </label>
                      </td>
                      <td>
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
                      </td>
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
          {chainItem}
          <Col xs={6} md={4}>
            <Image src={chainItem} thumbnail />
          </Col>
        </Modal.Body>
      </Modal>


      </>
    )
  }
  
  export default Chain