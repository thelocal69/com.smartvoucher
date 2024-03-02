import React from "react";
import { useSelector } from "react-redux";
import { selectAccessToken } from "../../Redux/data/AuthSlice";
import { Row, Col } from "react-bootstrap";
import { getAllTicket, userUseVoucher } from "../../services/TicketServices";
import { toast } from "react-toastify";
import Paginate from "../Util/Paginate";

const Ticket = (props) => {
  const { id } = props;
  const accessToken = useSelector(selectAccessToken);
  const [listTicket, setListTicket] = React.useState([]);
  const [currentPage, setCurrentPage] = React.useState(1);
  const [limit, setLimit] = React.useState(3);
  const [totalItem, setTotalItem] = React.useState(0);
  const [totalPage, setTotalPage] = React.useState(0);

  React.useEffect(() => {
    getTicket(id, currentPage, limit);
  }, []);

  const getTicket = async (id, page, limit) => {
    await getAllTicket(id, page, limit)
      .then((rs) => {
        if (rs) {
          setCurrentPage(rs.page);
          setTotalItem(rs.totalItem);
          setTotalPage(rs.totalPage);
          setListTicket(rs.data);
        }
      })
      .catch((err) => console.log(err.message));
  };

  const handleUseVoucher = async (serialCode) => {
    await userUseVoucher(serialCode)
      .then((rs) => {
        if (rs) {
          toast.success("Use voucher is successfully !");
          getTicket(id, currentPage, limit);
        }
      })
      .catch((err) => console.log(err.message));
  };

  const handlePageClick = (event) => {
    getTicket(id, +event.selected + 1, limit);
  };

  return (
    <>
      {accessToken && (
        <div>
          {listTicket
            ? listTicket.map((item, key) => {
              return (
                <Row xs={1} md='auto' key={key} className="justify-content-md-between">
                  <Col>
                    <div>
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
                    <hr />
                  </Col>
                  <Col md={7}>
                    <div>
                      <div>
                        <h4>{item.warehouseName}</h4>
                        <p>{item.categoryName}</p>
                      </div>
                      <div>
                        <button
                          className={item.status === 2 ? "btn btn-danger" : "btn btn-primary"}
                          disabled={item.status === 2}
                          onClick={() => handleUseVoucher(item.serialCode)}
                        >
                          {item.status === 2 ? 'Used' : 'Redeem'}
                          <i class={item.status === 2 ? "fa-solid fa-check" : "fa-solid fa-hand-point-down"}></i>
                        </button>
                        <label
                          className="ps-3"
                        >{
                            item.status === 2 ? item.serialCode : ""
                          }</label>
                      </div>
                    </div>
                    <hr />
                  </Col>
                </Row>
              );
            })
            : "No data"}
          <Paginate totalPages={totalPage} handlePageClick={handlePageClick} />
        </div>
      )}
    </>
  );
};

export default Ticket;
