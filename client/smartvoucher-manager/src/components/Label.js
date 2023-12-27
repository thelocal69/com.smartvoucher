import React from 'react';
import { Table} from 'react-bootstrap';
import Paging from './Paging';
import { getAllLabel } from '../services/LabelService';
import { toast } from "react-toastify";
import './Label.scss';


const Label = () => {

    const [sortBy, setSortBy] = React.useState("desc");
    const [sortField, setSortField] = React.useState("id");
    const [totalPage, setTotalPage] = React.useState(0);
    const [currentPage, setCurrentPage] = React.useState(1);
    const [limit, setLimit] = React.useState(6);
    const [totalItem, setTotalItem] = React.useState(0);

    const [listLabel, setListLabel] = React.useState([]);

    React.useEffect(() => {
        getLabel(currentPage, limit, sortBy, sortField);
    }, []);

    const getLabel = async(currentPage, limit, sortBy, sortField) =>{
        await getAllLabel(currentPage, limit, sortBy, sortField)
        .then((rs) => {
            if(rs){
                setListLabel(rs.data);
                setCurrentPage(rs.page);
                setTotalItem(rs.totalItem);
                setTotalPage(rs.totalPage);
            }
        })
        .catch((err) => {
            toast.error(err.message);
        })
    }

    const handleSortClick = (sortBy, sortField) => {
        getLabel(currentPage, limit, sortBy, sortField);
        setSortBy(sortBy);
    }

    const handlePageClick = (event) => {
        getLabel(+event.selected + 1, limit, sortBy, sortField);
    }

  return (
    <>
        <div className="my-3 d-sm-flex justify-content-between">
        <span>
          <b>List Label:</b>
        </span>
      </div>

      {/* <div className="my-3 d-sm-flex justify-content-between">
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
              <th>Name</th>
            </tr>
          </thead>
          <tbody>
            {listLabel
              ? listLabel?.map((item, key) => {
                  return (
                    <tr key={key}>
                      <td>{key + 1}</td>
                      <td>{item?.id}</td>
                      <td>{item?.name}</td>
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
    </>
  )
}

export default Label