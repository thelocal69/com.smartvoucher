import { Wrapper } from "components/Wrapper/Wrapper";
import React from "react";
import { useNavigate, useParams } from "react-router-dom";
import "./Detail.scss";
import { toast } from "react-toastify";
import { getWarehouseDetail } from "queries/warehouse";
import moment from "moment";
import ArrowBackIosIcon from "@mui/icons-material/ArrowBackIos";
// import { IBodyBuyTicket } from "queries/ticket";
import { addOrderAsync } from "queries/order";
import { IOrder } from "queries/order";
import { getUserInfo } from "queries/auth";
import { IBuyTicket } from "queries/ticket";
import { buyTicket } from "queries/ticket";
import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import { schemaBuyTicket } from "validate";
import { getAllWarehouseStore } from "queries/warehouse-store";
import { Loading } from "components/Loading/Loading";
import { useSelector } from "react-redux";
import { selectAccessToken } from "redux/features/auth/authSlice";
export const Detail = () => {
  let { id } = useParams();
  const [data, setData] = React.useState<any>();
  const [userData, setUserData] = React.useState<any>();
  const [isShowPopUp, setIsShowPopUp] = React.useState(false);
  const [loading, setLoading] = React.useState(false);
  const navigate = useNavigate();
  const token = useSelector(selectAccessToken);
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<any>({
    resolver: yupResolver(schemaBuyTicket),
  });
  React.useEffect(() => {
    if (token) {
      getUserInfo()
        .then((rs: any) => {
          if (rs) {
            setUserData(rs.data);
          }
        })
        .catch((err: any) => {
          toast.error(err.message);
        });
    }
  }, [token]);
  React.useEffect(() => {
    getWarehouseDetail(id)
      .then((rs: any) => {
        if (rs) {
          setData(rs.data);
        }
      })
      .catch((err: any) => {
        toast.error(err.message);
      });
  }, [id]);

  const addOrder = (amount: Number) => {
    let obj: IOrder = {
      status: 1,
      idUserDTO: { id: userData.id },
      quantity: amount,
      idWarehouseDTO: { id: data.id },
    };
    return addOrderAsync(obj);
  };
  const handelBuyTicket = (dt: any) => {
    if (data && userData) {
      setLoading(true);
      addOrder(dt.amount)
        .then((rs: any) => {
          if (rs) {
            let payload: IBuyTicket = {
              obj: {
                idWarehouseDTO: { id: rs.data.idWarehouseDTO.id },
                idCategoryDTO: { id: rs.data.idWarehouseDTO.idCategory },
                idOrderDTO: { id: rs.data.id },
                status: 1,
                discountType: rs.data.discountName,
                discountAmount: rs.data.idWarehouseDTO.discountAmount,
                idUserDTO: { id: rs.data.idUserDTO.id },
                // idStoreDTO: { id: idStore },
              },
              email: rs.data.idUserDTO.email,
              numberOfSerial: dt.amount,
            };
            getAllWarehouseStore()
              .then((rs: any) => {
                if (rs) {
                  let _id = rs.data.find(
                    (item: any) => item.idWarehouse === data.id
                  ).idStore;
                  let ob = { ...payload };
                  ob.obj.idStoreDTO = { id: _id };
                  buyTicket(ob)
                    .then((rs: any) => {
                      if (rs) {
                        setLoading(false);
                        setIsShowPopUp(false);
                        toast.success(rs.message);
                        navigate("/profile");
                      }
                    })
                    .catch((err: any) => {
                      toast.error(err.message);
                      setLoading(false);
                    });
                }
              })
              .catch((err: any) => {
                toast.error(err.message);
                setLoading(false);
              });
          }
        })
        .catch((err: any) => {
          toast.error(err.message);
          setLoading(false);
        });
    } else {
      toast.error("Please login for purchasing");
    }
  };
  return (
    <div className="detail">
      {data ? (
        <Wrapper>
          <div
            className="path"
            onClick={() => {
              navigate("/");
            }}
          >
            <ArrowBackIosIcon className="ic" /> Home
          </div>
          <div className="header">
            <h1>{data.name}</h1>
            <div className="label">
              <div className="time">
                {moment(data.availableTo).format("DD/MM/YYYY")}
              </div>
              <span>Tin Khuyến Mại</span>
            </div>
          </div>
          <div className="content">
            <div className="banner">
              {data.bannerUrl !== null ? (
                // <img
                //   src={`https://drive.google.com/uc?export=view&id=${data.thumbnailUrl.slice(
                //     32,
                //     data.bannerUrl.length - 18
                //   )}`}
                //   alt=""
                //   className="imagee"
                // />
                <img src={data.bannerUrl} alt="" className="imagee" />
              ) : (
                <img src={require(`assets/detail/default.jpg`)} alt="" />
              )}
            </div>
            <h3>Thông tin voucher</h3>
            <p className="note">
              ** LƯU Ý: Voucher có hiệu lực đến{" "}
              {moment(data.availableTo).format("DD/MM/YYYY")}
            </p>
            <h3>Điều kiện sử dụng</h3>
            <p>{data.termOfUse}</p>
            <h3>Hướng dẫn sử dụng voucher</h3>
            <p>
              <b>Bước 1: </b>Khách hàng đổi điểm lấy voucher và nhấn sử dụng
              voucher nhận được từ SmartVoucher
            </p>
            <p>
              <b>Bước 2: </b>Khách hàng đặt hàng hóa theo mong muốn
            </p>
            <p>
              <b> Bước 3: </b>Tại cửa hàng chấp nhận ưu đãi, khách hàng cung 
              cấp voucher để được giảm giá hoặc nhập mã giảm giá vào mục ưu 
              đãi khi thanh toán trực tuyến
            </p>
            <p>
              <b>Bước 4: </b>Mệnh giá voucher sẽ được trừ tiền vào hóa đơn
            </p>
            <p>Đến với SmartVoucher nhận ưu đãi ngay!</p>
          </div>
          <div
            className="btnBuy"
            onClick={() => {
              if (!token) {
                toast.error("Please login for purchasing");
                return;
              }
              setIsShowPopUp(true);
            }}
          >
            MUA NGAY
          </div>
          {isShowPopUp && (
            <div className="pop-up-buy-ticket">
              <div className="bg" onClick={() => setIsShowPopUp(false)}></div>
              <div className="content-popup">
                <ArrowBackIosIcon
                  className="ic"
                  onClick={() => {
                    setIsShowPopUp(false);
                  }}
                />
                <form onSubmit={handleSubmit(handelBuyTicket)}>
                  <div>
                    <div className="label">Enter amount want to buy :</div>
                    <input
                      type="number"
                      min={1}
                      max={3}
                      {...register("amount")}
                    />
                    <p>{errors.amount && errors.amount.message.toString()}</p>{" "}
                  </div>{" "}
                  <input type="submit" value={"OK"} />
                  <div className="cancel" onClick={() => setIsShowPopUp(false)}>
                    Cancel
                  </div>
                  {loading && (
                    <div className="loading">
                      <Loading />
                      <p>Purchasing ...</p>
                    </div>
                  )}
                </form>
              </div>
            </div>
          )}
        </Wrapper>
      ) : (
        "No data"
      )}
    </div>
  );
};

// const PopUpBuyTicket = () => {
//   return (
//
//   );
// };
