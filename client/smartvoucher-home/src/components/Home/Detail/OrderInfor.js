import React from "react";
import { getOrder } from "../../../services/OrderServices";
import { selectAccessToken } from "../../../Redux/data/AuthSlice";
import { useSelector } from "react-redux";
import { Col, Container, Row } from "react-bootstrap";
import { useParams } from "react-router-dom";
import Moment from "moment";
import "../Detail/OrderInfor.scss";
import Ticket from '../../User/Ticket';

const OrderInfor = () => {
  const { id } = useParams();

  const accessToken = useSelector(selectAccessToken);
  const [listOrderInfor, setListOrderInfor] = React.useState({});

  React.useEffect(() => {
    getDetail();
  }, [id]);

  const getDetail = async () => {
    await getOrder(id)
      .then((rs) => {
        setListOrderInfor(rs.data);
      })
      .catch((err) => console.log(err.message));
  };

  return (
    <>
      {accessToken && (
        <div className="coLI xL">
          <Row xs={1} md='auto'>
            <Col>
              <div>
                <h4>Chi tiết đơn hàng #{listOrderInfor.orderNo}</h4>
                <p>
                  Hiển thị thông tin các sản phẩm bạn đã mua tại Smart voucher
                </p>
                <hr />
              </div>
            </Col>
            <Col>
              <Row xs={2} md='auto'>
                <Col>
                  <div className="pe-3">
                    <h6>
                      <b>Thông tin đơn hàng</b>
                    </h6>
                    <p>Mã đơn hàng: #{listOrderInfor.orderNo}</p>
                    <p>
                      Ngày tạo:{" "}
                      <span>
                        {Moment(listOrderInfor.createdAt).format(
                          "YYYY/DD/MM hh:mm:ss"
                        )}
                      </span>
                    </p>
                    <p>
                      Trạng thái đơn hàng:{" "}
                      <span
                        className={
                          listOrderInfor.status ? "ac active" : "ac deactive"
                        }
                      >
                        {listOrderInfor.status ? "Đã xử lý" : "Chưa xử lí"}
                      </span>
                    </p>
                    <p>Người nhận: {listOrderInfor.email}</p>
                  </div>
                </Col>
                <Col>
                  <div>
                    <h6>
                      <b>Giá trị đơn hàng</b>
                    </h6>
                    <p>
                      Tổng giá trị sản phẩm:{" "}
                      {listOrderInfor.price * listOrderInfor.quantity}đ
                    </p>
                  </div>
                </Col>
              </Row>
            </Col>
            <hr />
          </Row>
          <div>
            <Ticket id={id} />
          </div>
        </div>
      )}
    </>
  );
};

export default OrderInfor;
