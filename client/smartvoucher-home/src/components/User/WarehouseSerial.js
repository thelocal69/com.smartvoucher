import React from "react";
import { useSelector } from "react-redux";
import { selectAccessToken } from "../../Redux/data/AuthSlice";
import { Container } from "react-bootstrap";
import Paginate from "../Util/Paginate";
import { getAllWarehouseSerial } from '../../services/WarehouseSerialService';

const WarehouseSerial = (props) => {
  const { id } = props;
  const accessToken = useSelector(selectAccessToken);
  const [listWarehouseSerial, setListWarehouseSerial] = React.useState([]);
  const [currentPage, setCurrentPage] = React.useState(1);
  const [limit, setLimit] = React.useState(3);
  const [totalItem, setTotalItem] = React.useState(0);
  const [totalPage, setTotalPage] = React.useState(0);

//   React.useEffect(() => {
//     getWarehouseSerial(id, currentPage, limit);
//   }, []);

  const getWarehouseSerial = async (id, page, limit) => {
    await getAllWarehouseSerial(id, page, limit)
      .then((rs) => {
        if (rs) {
          setCurrentPage(rs.page);
          setTotalItem(rs.totalItem);
          setTotalPage(rs.totalPage);
          setListWarehouseSerial(rs.data);
        }
      })
      .catch((err) => console.log(err.message));
  };

  const handlePageClick = (event) => {
    getWarehouseSerial(id, +event.selected + 1, limit);
  };

  return (
    <>
      {accessToken && (
        <Container>
          {listWarehouseSerial
            ? listWarehouseSerial.map((item, key) => {
                return (
                  <div key={key} className="d-flex p-3">
                    <div className="pe-3">
                      <img
                        alt=""
                        src={item.bannerUrl}
                        style={{
                          width: 20 + "rem",
                          height: 10 + "rem",
                          borderRadius: 10,
                        }}
                      />
                    </div>
                    <div>
                      <div>
                        <h4>{item.warehouseName}</h4>
                        <p>{item.categoryName}</p>
                      </div>
                      <div>
                        <label htmlFor="clipboard" className="btn btn-primary">
                          <i class="fa-solid fa-copy"></i>
                        </label>
                        <input id="clipboard" value={item.serialCode} />
                      </div>
                    </div>
                  </div>
                );
              })
            : "No data"}
          <Paginate totalPages={totalPage} handlePageClick={handlePageClick} />
        </Container>
      )}
    </>
  );
};

export default WarehouseSerial;
