import React from "react";
import { getAllMerchant } from "../services/MechantService";
import { Table } from "react-bootstrap";
import Paging from "./Paging";
import "./Merchant.scss";
import { toast } from "react-toastify";

const Merchant = () => {
  const [listMerchant, setListMerchant] = React.useState([]);
  const [totalMerchant, setTotalMerchant] = React.useState(0);
  const [totalPage, setTotalPage] = React.useState(0);
  const [currentPage, setCurrentPage] = React.useState(1);
  const [limit, setLimit] = React.useState(6);
  const [sortBy, setSortBy] = React.useState("desc");
  const [sortField, setSortField] = React.useState("id");

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
  
  const handleSortClick = (sortBy, sortField) => {
      getMerchant(currentPage, limit, sortBy, sortField);
  };
  const handlePageClick = (event) => {
    getMerchant(+event.selected + 1, limit, sortBy, sortField);
  };
  return (
    <>
      <div className="customize-table">
        <Table striped bordered hover responsive="sm">
          <thead>
            <tr>
              <th>NO</th>
              <th>
                <div className="sort-header">
                  <span>ID</span>
                  <span>
                    <i
                      class="fa-solid fa-sort-down"
                      onClick={() => handleSortClick("desc", "id")}
                    ></i>
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
          <tbody className="table-condensed">
            {listMerchant
              ? listMerchant?.map((item, key) => {
                  return (
                    <tr key={key}>
                      <td>{key + 1}</td>
                      <td>{item?.id}</td>
                      <td>{item?.legalName}</td>
                      <td>{item?.logoUrl}</td>
                      <td>{item?.name}</td>
                      <td>{item?.merchantCode}</td>
                      <td>{item?.email}</td>
                      <td>{item?.phone}</td>
                      <td>{item?.descripton}</td>
                      <td>{item?.address}</td>
                      <td>{item?.status}</td>
                    
              </tr>
                  );
                })
              : "No Data"}
          </tbody>
        </Table>
      </div>
      <Paging handlePageClick={handlePageClick} 
      totalPages={totalPage} />
    </>
  );
};

export default Merchant;
