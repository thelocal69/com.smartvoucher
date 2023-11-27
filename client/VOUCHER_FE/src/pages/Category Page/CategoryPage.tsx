import { Wrapper } from "components/Wrapper/Wrapper";
import React from "react";
import { useNavigate, useParams } from "react-router-dom";
import "./CategoryPage.scss";
import { toast } from "react-toastify";
import ArrowBackIosIcon from "@mui/icons-material/ArrowBackIos";
// import { IBodyBuyTicket } from "queries/ticket";
import { getWarehouseByCategoryId } from "queries/warehouse";
export const CategoryPage = () => {
  let { id } = useParams();
  const [data, setData] = React.useState<any>();
  const navigate = useNavigate();
  React.useEffect(() => {
    getWarehouseByCategoryId(id)
      .then((rs: any) => {
        if (rs) {
          setData(rs.data);
        }
      })
      .catch((err: any) => {
        toast.error(err.message);
      });
  }, [id]);

  return (
    <div className="category-page">
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
          <div className="list">
            {data &&
              data.map((item: any, key: any) => {
                return (
                  <div
                    className="card"
                    onClick={() => {
                      navigate(`/warehouse/${item.id}`);
                    }}
                    key={key}
                  >
                    <div className="top">
                      <img src={item.bannerUrl} alt="" />
                      {item.isSoldout && (
                        <div className="soldout">Hết hàng</div>
                      )}
                    </div>
                    <div className="bottom">
                      <div className="name">{item.name}</div>
                      <div className="prices">
                        <div className="main-price">{item.price}đ</div>
                        <div className="discount">{item.discountAmount}đ</div>
                        <div className="discount-percent">
                          -{(Number(item.discountAmount) * 100).toFixed(1)}%
                        </div>
                      </div>
                    </div>
                  </div>
                );
              })}
          </div>
        </Wrapper>
      ) : (
        "No data"
      )}
    </div>
  );
};
