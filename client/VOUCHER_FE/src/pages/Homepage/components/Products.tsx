import { Wrapper } from "components/Wrapper/Wrapper";
import React from "react";
import { Card } from "./Card";
import "./Products.scss";
export interface IProductsProps {
  title?: String;
  label?: String;
  subtitle?: String;
  id: any;
  data: any;
}
export const Products = ({
  title,
  label,
  subtitle,
  data,
  id,
}: IProductsProps) => {
  const [isExpand, setIsExpand] = React.useState(false);
  console.log(data);
  return (
    <Wrapper>
      <div className="title">
        {title} {label && <div className="label">{label}</div>}
      </div>
      {subtitle && <div className="subtitle">{subtitle} </div>}
      {data && data.length > 0 ? (
        <div className={isExpand ? "lst expand" : "lst"}>
          {data.map((item: any, key: any) => {
            return (
              <Card
                name={item.name}
                price={item.price}
                discountAmount={item.discountAmount}
                img={item.bannerUrl}
                isSoldout={item.status === 1 ? false : true}
                key={key}
                id={item.id}
              />
            );
          })}
        </div>
      ) : (
        "no data"
      )}
      {data && data.length > 8 && (
        <div
          className="more"
          onClick={() => {
            // const ele = document.querySelector(".lst");
            setIsExpand(!isExpand);
          }}
        >
          {isExpand ? "Thu gọn" : " Xem thêm"}
        </div>
      )}
      <div className="line"></div>
    </Wrapper>
  );
};
