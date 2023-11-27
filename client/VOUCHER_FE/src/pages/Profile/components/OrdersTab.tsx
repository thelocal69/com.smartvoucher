import { TextField } from "@mui/material";
import React, { useState } from "react";
import FilterAltOutlinedIcon from "@mui/icons-material/FilterAltOutlined";
import { DatePicker } from "@mui/x-date-pickers";
import moment from "moment";
import { useSelector } from "react-redux";
import { selectAccessToken } from "redux/features/auth/authSlice";
import { getOrderByUser } from "queries/order";
import { toast } from "react-toastify";
import { styled } from "@mui/material/styles";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell, { tableCellClasses } from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import { useNavigate } from "react-router-dom";
import { onUseTicket } from "queries/ticket";
const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
  },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  // hide last border
  "&:last-child td, &:last-child th": {
    border: 0,
  },
}));
export const OrdersTab = ({ info }: { info: any }) => {
  const token = useSelector(selectAccessToken);
  const [orders, setOrders] = useState<any>();
  const [ordersSearch, setOrdersSearch] = useState<any>();
  const navigate = useNavigate();

  React.useEffect(() => {
    if (token)
      getOrderByUser(info.id)
        .then((rs: any) => {
          if (rs) {
            setOrders(rs.data);
            setOrdersSearch(rs.data);
          }
        })
        .catch((err: any) => toast.error(err.message));
  }, [token, info]);
  console.log(orders);
  const handleUseTicket = (code: string) => {
    onUseTicket(code)
      .then((rs: any) => {
        if (rs) {
          toast.success(rs.message);
        }
      })
      .catch((err: any) => toast.error(err.message));
  };

  return (
    <>
      <div className="title">Lịch sử đơn hàng</div>
      <div className="label">
        Hiển thị thông tin các sản phẩm bạn đã mua tại Divine Shop
      </div>
      <div className="line"></div>
      <div className="filters">
        <TextField
          id="outlined-basic"
          variant="outlined"
          size="small"
          label="Mã đơn hàng"
          onChange={(e) => {
            if (e.target.value === "") {
              setOrdersSearch(orders);
            } else {
              let obj = orders.filter((item: any) =>
                item.orderNo
                  .toLowerCase()
                  .includes(e.target.value.toLowerCase())
              );
              setOrdersSearch(obj);
            }
          }}
        />
        {/* <TextField
          id="outlined-basic"
          variant="outlined"
          size="small"
          label="Số tiền từ"
        />{" "}
        <TextField
          id="outlined-basic"
          variant="outlined"
          size="small"
          label="Số tiền đến"
        /> */}
        <DatePicker
          slotProps={{ textField: { size: "small" } }}
          label="Từ ngày"
          value={moment()}
        />
        <DatePicker
          slotProps={{ textField: { size: "small" } }}
          label="Đến ngày"
          value={moment()}
        />
        <div className="btn-filter">
          <FilterAltOutlinedIcon /> Lọc
        </div>
      </div>
      {ordersSearch ? (
        <div className="table">
          <TableContainer component={Paper}>
            <Table sx={{ minWidth: 700 }} aria-label="customized table">
              <TableHead>
                <TableRow>
                  <StyledTableCell>orderNo</StyledTableCell>
                  <StyledTableCell align="right">Quantity</StyledTableCell>
                  <StyledTableCell align="right">Discount Name</StyledTableCell>
                  <StyledTableCell align="right">Warehouse</StyledTableCell>
                  <StyledTableCell align="right">Availabel To</StyledTableCell>
                  <StyledTableCell align="right">Status</StyledTableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {ordersSearch.map((order: any) => (
                  <>
                    <StyledTableRow
                      key={order.id}
                      id="row"
                      onClick={() =>
                        navigate(`/warehouse/${order.idWarehouseDTO.id}`)
                      }
                    >
                      <StyledTableCell component="th" scope="row">
                        {order.orderNo}
                      </StyledTableCell>
                      <StyledTableCell align="right">
                        {order.quantity}
                      </StyledTableCell>
                      <StyledTableCell align="right">
                        {order.discountName}
                      </StyledTableCell>
                      <StyledTableCell align="right">
                        {order.idWarehouseDTO.name}
                      </StyledTableCell>
                      <StyledTableCell align="right">
                        {moment(order.idWarehouseDTO.availableTo).format(
                          "llll"
                        )}
                      </StyledTableCell>
                      <StyledTableCell
                        align="right"
                        className={order.status === 1 ? "active" : " deactive"}
                      >
                        {order.status === 1 ? "Active" : "Deactive"}
                      </StyledTableCell>
                    </StyledTableRow>
                    {order.listTicketDTO && (
                      <TableRow id="tickets-head">
                        <StyledTableCell></StyledTableCell>
                        <StyledTableCell align="right"></StyledTableCell>
                        <StyledTableCell align="right">
                          Ticket ID
                        </StyledTableCell>
                        <StyledTableCell align="right">
                          Serial Code
                        </StyledTableCell>
                        <StyledTableCell align="right">
                          Availabel To
                        </StyledTableCell>
                        <StyledTableCell align="right">Action</StyledTableCell>
                      </TableRow>
                    )}
                    {order.listTicketDTO &&
                      order.listTicketDTO.map((item: any, key: any) => {
                        return (
                          <StyledTableRow
                            id="tickets-row"
                            key={key}
                            onClick={() => navigate(``)}
                          >
                            <StyledTableCell
                              component="th"
                              scope="row"
                            ></StyledTableCell>
                            <StyledTableCell align="right"></StyledTableCell>
                            <StyledTableCell align="right">
                              {item.id}
                            </StyledTableCell>
                            <StyledTableCell align="right">
                              {item.idSerialDTO.serialCode}
                            </StyledTableCell>
                            <StyledTableCell align="right">
                              {moment(order.idWarehouseDTO.availableTo).format(
                                "llll"
                              )}
                            </StyledTableCell>
                            <StyledTableCell
                              align="right"
                              className={
                                order.status === 1 ? "active" : " deactive"
                              }
                              onClick={() => {
                                handleUseTicket(item.idSerialDTO.serialCode);
                              }}
                            >
                              <div id="btn-use">
                                {order.status === 1 ? "Use" : "Used"}
                              </div>
                            </StyledTableCell>
                          </StyledTableRow>
                        );
                      })}
                  </>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </div>
      ) : (
        "No data"
      )}
    </>
  );
};
