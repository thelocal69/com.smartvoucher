import React from "react";
import { NavLink } from "react-router-dom";
import '../Error/NotFound.scss'

const NotFound = () => {
  return (
    <>
      <div className="d-flex flex-column m-3">
        <div className="">
          <NavLink to="/" className="f p-3">
            <i class="fa-solid fa-chevron-left"></i>
            Quay về Trang chủ
          </NavLink>
        </div>
        <div className="ts">
            <h4>Đường dẫn không tồn tại hoặc đã bị xóa!</h4>
            <p>Vui lòng liên hệ với hỗ trợ để biết thêm chi tiết.</p>
        </div>
        <div className="imf">
          <img
          loading="lazy"
            alt=""
            src="https://cdn.divineshop.vn/static/5d4c5cd16ffe8af838e5bc08bb7930c8.svg"
          />
        </div>
      </div>
    </>
  );
};

export default NotFound;
