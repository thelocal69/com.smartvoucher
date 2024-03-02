import React from "react";
import { Col, Container, Row, Table } from "react-bootstrap";
import { selectAccessToken } from "../../Redux/data/AuthSlice";
import { useSelector } from "react-redux";
import { getAllOrder } from "../../services/OrderServices";
import { toast } from "react-toastify";
import Paging from "../Util/Paginate";
import "../User/Order.scss";
import { useNavigate, useParam } from "react-router-dom";
import Moment from "moment";

const Order = () => {
  const accessToken = useSelector(selectAccessToken);
  const navigate = useNavigate();

  const [listOrder, setListOrder] = React.useState([]);
  const [currentPage, setCurrentPage] = React.useState(1);
  const [limit, setLimit] = React.useState(6);
  const [totalPage, setTotalPage] = React.useState(0);
  const [totalItem, setTotalItem] = React.useState(0);
  const [sortBy, setSortBy] = React.useState("desc");
  const [sortField, setSortField] = React.useState("id");

  React.useEffect(() => {
    getOrder(currentPage, limit, sortBy, sortField);
  }, []);

  const getOrder = async (page, limit, sortBy, sortField) => {
    await getAllOrder(page, limit, sortBy, sortField).then((rs) => {
      if (rs) {
        setCurrentPage(rs.page);
        setTotalItem(rs.totalItem);
        setTotalPage(rs.totalPage);
        setListOrder(rs.data);
      }
    })
      .catch((err) => console.log(err.message));
  };

  const handlePageClick = (event) => {
    getOrder(+event.selected + 1, limit, sortBy, sortField);
  };

  return (
    <>
      {accessToken && (
        <>
          <div>
            <div className="LN list-profile">
              <Row xs={1} md='auto'>
                <Col>
                  <div>
                    <h3>Lịch sử đơn hàng</h3>
                    <p className="fD">
                      Hiển thị thông tin các sản phẩm bạn đã mua tại Smart voucher
                    </p>
                  </div>
                </Col>
                <Col>
                  <div className="customize-table">
                    <Table bordered hover>
                      <thead>
                        <tr>
                          <th>Thời gian</th>
                          <th>Mã đơn hàng</th>
                          <th>Sản phẩm</th>
                          <th>Tổng tiền</th>
                          <th>Trạng thái</th>
                          <th>Chi tiết</th>
                        </tr>
                      </thead>
                      <tbody>
                        {listOrder ? (
                          listOrder?.map((item, key) => {
                            return (
                              <tr key={key} className="fD">
                                <td>
                                  {Moment(item?.createdAt).format(
                                    "YYYY/DD/MM hh:mm:ss"
                                  )}
                                </td>
                                <td>{item?.orderNo}</td>
                                <td className="d-flex justify-content-between">
                                  <span>{item?.warehouseName}</span>
                                  <span> x{item?.quantity}</span>
                                </td>
                                <td className="">
                                  {item?.price * item?.quantity}đ
                                </td>
                                <td
                                  className={
                                    item?.status ? "ac active" : "ac deactive"
                                  }
                                >
                                  {item?.status ? "Đã xử lí" : "Chưa xử lí"}
                                </td>
                                <td
                                  onClick={() => {
                                    navigate(`/User/Infor/${item.id}`);
                                  }}
                                  className="Ul"
                                >
                                  Chi tiết
                                </td>
                              </tr>
                            );
                          })
                        ) : <></>}
                      </tbody>
                    </Table>
                  </div>
                </Col>
              </Row>
              <div>
              </div>

              <Paging
                totalPages={totalPage}
                handlePageClick={handlePageClick}
              />
            </div>
          </div>
        </>
      )}
    </>
  );
};

export default Order;
