import React from "react";
import { useSelector } from "react-redux";
import { selectAccessToken } from "../../Redux/data/AuthSlice";
import { Container } from "react-bootstrap";
import { getAllTicket } from "../../services/TicketServices";
import { toast } from "react-toastify";
import Paginate from "../Util/Paginate";

const Ticket = (props) => {
  const { id } = props;
  const textAreaRef = React.useRef(null);
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
      .catch((err) => toast.error(err.message));
  };

  const handlePageClick = (event) => {
    getTicket(id, +event.selected + 1, limit);
  };

  return (
    <>
      {accessToken && (
        <Container>
          {listTicket
            ? listTicket.map((item, key) => {
                return (
                  <div key={key} className="d-flex p-3">
                    <div className="pe-3">
                      <img
                        alt=""
                        src={item.bannerUrl}
                        style={{
                          width: 20 + "rem",
                          height: 10 + "rem",
                          borderRadius: 10
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

export default Ticket;
