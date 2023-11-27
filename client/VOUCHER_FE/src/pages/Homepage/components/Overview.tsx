/* Code generated with AutoHTML Plugin for Figma */
import React from "react";
import "./Overview.css";
import { Wrapper } from "components/Wrapper/Wrapper";
import OverviewImage from "assets/homepage/overview.png";
import ArrowForwardIcon from "@mui/icons-material/ArrowForward";
export interface IDivContainerProps {}

export const Overview = ({ ...props }: IDivContainerProps): JSX.Element => {
  return (
    <Wrapper>
      <div className="overview">
        <div className="left">
          <div className="title">
            Super Voucher - Mang đến cho bạn "cơn lốc" quà tặng mỗi ngày!
          </div>
          <div className="subtitle">
            Là tính năng trên ứng dụng di động VinID, VinID Voucher mang tới cho
            hội viên hàng ngàn ưu đãi độc quyền từ các thương hiệu hàng đầu,
            thuộc đa dạng lĩnh vực từ Thờ i trang, Ẩm thực tới Vui chơi - Giải
            trí. ”Săn” voucher ngay!
          </div>
          <div className="btn">
            Khám phá ngay
            <div className="arr">
              <ArrowForwardIcon />
            </div>
          </div>
        </div>
        <div className="right">
          <img
            className="_17-baf-63-c-im-216-onboarding-gami-monthly-end-3-1-png"
            src={OverviewImage}
            alt=""
          />
        </div>
      </div>
    </Wrapper>
  );
};
