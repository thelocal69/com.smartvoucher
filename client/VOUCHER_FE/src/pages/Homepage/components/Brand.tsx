import { Wrapper } from "components/Wrapper/Wrapper";
import React from "react";
import "./Brand.scss";
import { getMerchants } from "queries/merchant";
import { toast } from "react-toastify";
export const Brand = () => {
  const [data, setData] = React.useState<any>();
  React.useEffect(() => {
    getMerchants()
      .then((rs: any) => {
        if (rs) {
          setData(rs.data);
        }
      })
      .catch((err: any) => {
        toast.error(err.message);
      });
  }, []);
  const arr = [1, 2, 3, 4, 5, 6, 7, 8];
  console.log(arr.slice(0, arr.length / 2));
  console.log(arr.slice(-arr.length / 2));
  return (
    <Wrapper>
      <div className="brand">
        <div className="title">Thương hiệu nổi bật</div>
        <div className="list">
          {data &&
            data.slice(0, data.length / 2).map((item: any, key: any) => {
              return <img src={item.logoUrl} alt="" />;
            })}
        </div>
        <div className="title">Thương hiệu mới</div>
        <div className="list">
          {data &&
            data.slice(data.length / 2).map((item: any, key: any) => {
              return <img src={item.logoUrl} alt="" />;
            })}
        </div>
      </div>
    </Wrapper>
  );
};
