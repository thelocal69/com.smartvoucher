import React from "react";
import { Carousel, Container, Image } from "react-bootstrap";
import { getAllCategory } from "../../services/CategoryServices";
import { toast } from "react-toastify";
import "../Home/Category.scss";

const Category = () => {
  const [listCategory, setListCategory] = React.useState([]);

  React.useEffect(() => {
    getCategory();
  }, []);

  const getCategory = async () => {
    await getAllCategory()
      .then((rs) => {
        if (rs) {
          setListCategory(rs.data);
        }
      })
      .catch((err) => toast.error(err.message));
  };

  return (
    <>
      <div className="m-2 p-2 d-flex justify-content-between">
        <div className="p-3 list-box">
          <div className="py-2">
            <a>
              <i class="fa-solid fa-sack-dollar"></i>
              Ưu đãi đặc biệt
            </a>
          </div>
          <div className="pb-2">
            <a>
              <i class="fa-regular fa-snowflake"></i>
              Ngày lễ
            </a>
          </div>
          <div className="pb-2">
            <a>
              <i class="fa-solid fa-trademark"></i>
              Chỉ có tại SmartVoucher
            </a>
          </div>
          <div className="pb-2">
            <a>
              <i class="fa-solid fa-bowl-food"></i>
              Ăn ngon
            </a>
          </div>
          <div className="pb-2">
            <a>
              <i class="fa-solid fa-microchip"></i>
              Công nghệ và Đời sống
            </a>
          </div>
          <div className="pb-2">
            <a>
              <i class="fa-solid fa-house-medical"></i>
              Khỏe đẹp
            </a>
          </div>
        </div>
        <Carousel
          dynamicHeight={false}
          style={{
            borderRadius: "10px 10px 10px 10px",
            overflow: "hidden",
          }}
        >
          {listCategory.map((item, key) => {
            return (
              <>
                <Carousel.Item key={key}>
                  <img
                    src={item.bannerUrl}
                    alt={item.name}
                    style={{
                      width: 40 + "rem",
                      height: 20 + "rem",
                    }}
                  />
                  <Carousel.Caption>
                    <p>{item.name}</p>
                  </Carousel.Caption>
                </Carousel.Item>
              </>
            );
          })}
        </Carousel>
        <div>
          <div>
            <Image
              src="https://giadinh.mediacdn.vn/296230595582509056/2022/12/21/an-gi-102-16715878746102005998080.jpg"
              style={{
                width: 17 + "rem",
                height: 9 + "rem",
                borderRadius: "10px",
              }}
            />
          </div>
          <br />
          <div>
            <Image
              src="https://file.hstatic.net/200000472237/file/tong-quan-flash-sale_e1c32736c1ce4449b845bc1fb8dc98aa_grande.png"
              style={{
                width: 17 + "rem",
                height: 9 + "rem",
                borderRadius: "10px",
              }}
            />
          </div>
        </div>
      </div>
      <div className="m-2 p-2 d-flex justify-content-between">
        <div>
          <Image
            src="https://img.freepik.com/free-vector/special-offer-creative-sale-banner-design_1017-16284.jpg?1"
            style={{
              width: 17 + "rem",
              height: 9 + "rem",
              borderRadius: "10px",
            }}
          />
        </div>
        <div>
          <Image
            src="https://www.anarapublishing.com/wp-content/uploads/elementor/thumbs/photo-1506157786151-b8491531f063-o67khcr8g8y3egfjh6eh010ougiroekqaq5cd8ly88.jpeg"
            style={{
              width: 17 + "rem",
              height: 9 + "rem",
              borderRadius: "10px",
            }}
          />
        </div>
        <div>
          <Image
            src="https://webstar.ug/wp-content/uploads/2023/06/Unveiling-Modern-Trends-in-Technology.jpeg"
            style={{
              width: 17 + "rem",
              height: 9 + "rem",
              borderRadius: "10px",
            }}
          />
        </div>
        <div>
          <Image
            src="https://media.defense.gov/2020/Apr/30/2002291608/1920/1080/0/200501-F-PO640-0034.JPG"
            style={{
              width: 17 + "rem",
              height: 9 + "rem",
              borderRadius: "10px",
            }}
          />
        </div>
      </div>
    </>
  );
};

export default Category;
