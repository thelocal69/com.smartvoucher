import React from "react";
import { Overview } from "./components/Overview";
import { Brand } from "./components/Brand";
import { Category } from "./components/Category";
import { Products } from "./components/Products";
import { Wrapper } from "components/Wrapper/Wrapper";
import "./Homepage.scss";
import { toast } from "react-toastify";
import { getAllWarehouse } from "queries/warehouse";
import { getLabel } from "queries/label";
export const Homepage = () => {
  const [labels, setLabels] = React.useState([]);
  const [allWarehouse, setAllWarehouse] = React.useState([]);
  React.useEffect(() => {
    getAllWarehouse()
      .then((rs: any) => {
        if (rs) {
          setAllWarehouse(rs.data);
        }
      })
      .catch((err: any) => {
        toast.error(err.message);
      });
    getLabel()
      .then((rs: any) => {
        if (rs) {
          setLabels(rs.data);
        }
      })
      .catch((err: any) => {
        toast.error(err.message);
      });
  }, []);
  console.log(labels);
  console.log(allWarehouse);
  return (
    <div className="homepage">
      <Overview />
      <Brand />
      <Category />
      {labels &&
        labels.map((lb, key) => {
          return (
            <Products
              title={lb.name}
              data={allWarehouse.filter((item) => item.labelName === lb.name)}
              id={lb.id}
              key={key}
            />
          );
        })}
      <Wrapper>
        <div className="faqs-wrapper">
          <div className="faqs">
            <div className="question">
              Bạn có <span> câu hỏi?</span> SmartVoucher có{" "}
              <span>câu trả lời </span>
            </div>
            <div className="more">Xem thêm</div>
          </div>
        </div>
      </Wrapper>
      <div className="not-have-account">
        <Wrapper>
          <div className="content">
            <img src={require(`assets/homepage/register.png`)} alt="" />
            <div className="txt">
              <div className="title">Bạn chưa có tài khoản?</div>
              <div className="subtitle">
                Hãy tạo ngay một tài khoản để sử dụng đầy đủ các tính năng, tích
                lũy ưu đãi khi thanh toán các sản phẩm và tham gia vào chương
                trình Giới thiệu bạn bè nhận hoa hồng vĩnh viễn tại Super
                Voucher.
              </div>
              <div className="btns">
                <div className="register">Đăng ký ngay</div>
                <div className="login">
                  Hoặc bạn đã có tài khoản? <span>Đăng nhập</span>
                </div>
              </div>
            </div>
          </div>
        </Wrapper>
      </div>
    </div>
  );
};
