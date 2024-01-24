import React from "react";
import "../Footer/Footer.scss";
import { Image, Container } from "react-bootstrap";
import Account from "../Security/Account";
import { useSelector } from "react-redux";
import { selectIsAuthenticated } from "../../Redux/data/AuthSlice";

const Footer = () => {
  const [isShowModalLogin, setIsShowModalLogin] = React.useState(false);
  const authenticated = useSelector(selectIsAuthenticated);

  const handleClose = () => {
    setIsShowModalLogin(false);
  };

  return (
    <>
      {authenticated === false && (
        <div className="bg-footer mt-3">
          <Container>
            <div className="d-flex align-items-center p-3">
              <div>
                <Image src="https://cdn.divineshop.vn/static/528b91cfa29c7ffd85418f4b1e8cc8ce.svg" />
              </div>
              <div className="font-color p-3">
                <h3>Bạn chưa có tài khoản?</h3>
                <div className="py-3">
                  Hãy tạo ngay một tài khoản để sử dụng đầy đủ các tính năng,
                  tích lũy ưu đãi khi thanh toán các sản phẩm và tham gia vào
                  chương trình Giới thiệu bạn bè nhận hoa hồng vĩnh viễn tại
                  Divine Shop.
                </div>
                <div className="d-flex">
                  <button
                    className="btn btn-primary"
                    onClick={() => setIsShowModalLogin(true)}
                  >
                    Đăng ký ngay
                  </button>
                  <div className="p-2">
                    Hoặc bạn đã có tài khoản?{" "}
                    <button
                      className="btn-footer"
                      onClick={() => setIsShowModalLogin(true)}
                    >
                      Đăng nhập
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </Container>
        </div>
      )}
      <Container>
        <div className="p-3">
          <Image
            loading="lazy"
            src="https://cdn.divineshop.vn/static/b77a2122717d76696bd2b87d7125fd47.svg"
            className="img-footer"
          />
          <Image
            loading="lazy"
            src="https://cdn.divineshop.vn/static/72a3a36fc7c66085b3f442940ba45fde.svg"
            className="img-footer"
          />
          <Image
            loading="lazy"
            src="https://cdn.divineshop.vn/static/464c7c79044dea88e86adf0e1b9c064c.svg"
            className="img-footer"
          />
          <Image
            loading="lazy"
            src="https://cdn.divineshop.vn/static/ddb866eb1214c914ea62417879046b99.svg"
            className="img-footer"
          />
          <span className="mx-3">và nhiều hình thức thanh toán khác</span>
        </div>
      </Container>
      <div className="bg-gray">
        <Container>
          <div className="p-3">
            <Image
              loading="lazy"
              src="https://cdn.divineshop.vn/static/4ba68c7a47305b454732e1a9e9beb8a1.svg"
              className="img-footer"
            />
            <Image
              loading="lazy"
              src="https://cdn.divineshop.vn/static/20334129395885adefc2e5217043f670.svg"
              className="img-footer"
            />
            <Image
              loading="lazy"
              src="https://cdn.divineshop.vn/static/4ae438165f9d5ea0fc6ff3da6051f938.svg"
              className="img-footer"
            />
          </div>
          <hr />
          <div className="d-flex justify-content-between">
            <div className="d-flex">
              <div className="p-3">
                <span className="font-end">GIỚI THIỆU</span>
                <br />
                <a>Game bản quyền là gì?</a>
                <br />
                <a>Giới thiệu Smart Voucher</a>
                <br />
                <a>Điều khoản dịch vụ</a>
                <br />
                <a>Chính sách bảo mật</a>
              </div>

              <div className="p-3">
                <span className="font-end">TÀI KHOẢN</span>
                <br />
                <a>Đăng nhập</a>
                <br />
                <a>Đăng ký</a>
              </div>

              <div className="p-3">
                <span className="font-end">LIÊN HỆ</span>
                <br />
                <a>Hotline tự động 1900 633 305</a>
                <br />
                <a>Liên hệ Hỗ trợ</a>
                <br />
                <a>Chat với CSKH</a>
              </div>
            </div>
            <div>
              <Image
                loading="lazy"
                src="https://images.dmca.com/Badges/_dmca_premi_badge_2.png?ID=18522dc6-fdd6-4b49-ab40-395a79849050"
              />
            </div>
          </div>
        </Container>
        <Account show={isShowModalLogin} handleClose={handleClose} />
      </div>
    </>
  );
};

export default Footer;
