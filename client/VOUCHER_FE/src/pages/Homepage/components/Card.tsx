import React from "react";
import "./Card.scss";
import { useNavigate } from "react-router-dom";

export interface ICardProps {
  name: String;
  price: String;
  discountAmount: String;
  img: any;
  isSoldout?: boolean;
  id: any;
}
export const Card = (props: ICardProps) => {
  const navigate = useNavigate();
  return (
    <div
      className="card"
      onClick={() => {
        navigate(`/warehouse/${props.id}`);
      }}
    >
      <div className="top">
        <img src={props.img} alt="" />
        {props.isSoldout && <div className="soldout">Hết hàng</div>}
      </div>
      <div className="bottom">
        <div className="name">{props.name}</div>
        <div className="prices">
          <div className="main-price">{props.price}đ</div>
          <div className="discount">{props.discountAmount}đ</div>
          <div className="discount-percent">
            -{(Number(props.discountAmount) * 100).toFixed(1)}%
          </div>
        </div>
      </div>
    </div>
  );
};
