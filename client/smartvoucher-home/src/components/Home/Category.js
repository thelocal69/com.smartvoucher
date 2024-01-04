import React from "react";
import { Carousel, Container, Image } from "react-bootstrap";
import { getAllCategory } from "../../services/CategoryServices";
import { toast } from "react-toastify";
import "../Home/Category.scss";

const Category = () => {
  const [listCategoryBanner, setListCategoryBanner] = React.useState([]);
  const [listCategoryTitle, setListCategoryTitle] = React.useState([]);

  React.useEffect(() => {
    getCategory();
  }, []);

  const getCategory = async () => {
    await getAllCategory()
      .then((rs) => {
        if (rs) {
          const bannerUrl = rs.data?.map((item) => item.bannerUrl);
          const title = rs.data?.map((item) => item.name);
          setListCategoryBanner(bannerUrl);
          setListCategoryTitle(title);
        }
      })
      .catch((err) => toast.error(err.message));
  };

  return (
    <>
      <div className="m-3 d-flex justify-content-center">
        <div>sda</div>
        <Carousel width="600px" dynamicHeight={false}>
          <Carousel.Item>
            <div className="h-128">
              <img src={listCategoryBanner[0]} alt={listCategoryTitle[0]} />
            </div>
            <Carousel.Caption>
              <div className="fo">{listCategoryTitle[0]}</div>
            </Carousel.Caption>
          </Carousel.Item>
          <Carousel.Item>
            <div className="h-128">
              <img src={listCategoryBanner[1]} alt={listCategoryTitle[1]} />
            </div>
            <Carousel.Caption>
              <div className="fo">{listCategoryTitle[1]}</div>
            </Carousel.Caption>
          </Carousel.Item>
          <Carousel.Item>
            <div className="h-128">
              <img src={listCategoryBanner[2]} alt={listCategoryTitle[2]} />
            </div>
            <Carousel.Caption>
              <div className="fo">{listCategoryTitle[2]}</div>
            </Carousel.Caption>s
          </Carousel.Item>
          <Carousel.Item>
            <div className="h-128">
              <img src={listCategoryBanner[3]} alt={listCategoryTitle[3]} />
            </div>
            <Carousel.Caption>
              <div className="fo">{listCategoryTitle[3]}</div>
            </Carousel.Caption>s
          </Carousel.Item>
          <Carousel.Item>
            <div className="h-128">
              <img src={listCategoryBanner[4]} alt={listCategoryTitle[4]} />
            </div>
            <Carousel.Caption>
              <div className="fo">{listCategoryTitle[4]}</div>
            </Carousel.Caption>s
          </Carousel.Item>
          <Carousel.Item>
            <div className="h-128">
              <img src={listCategoryBanner[5]} alt={listCategoryTitle[5]} />
            </div>
            <Carousel.Caption>
              <div className="fo">{listCategoryTitle[5]}</div>
            </Carousel.Caption>s
          </Carousel.Item>
          <Carousel.Item>
            <div className="h-128">
              <img src={listCategoryBanner[6]} alt={listCategoryTitle[6]} />
            </div>
            <Carousel.Caption>
              <div className="fo">{listCategoryTitle[6]}</div>
            </Carousel.Caption>s
          </Carousel.Item>
          <Carousel.Item>
            <div className="h-128">
              <img src={listCategoryBanner[7]} alt={listCategoryTitle[7]} />
            </div>
            <Carousel.Caption>
              <div className="fo">{listCategoryTitle[7]}</div>
            </Carousel.Caption>s
          </Carousel.Item>
          <Carousel.Item>
            <div className="h-128">
              <img src={listCategoryBanner[8]} alt={listCategoryTitle[8]} />
            </div>
            <Carousel.Caption>
              <div className="fo">{listCategoryTitle[8]}</div>
            </Carousel.Caption>s
          </Carousel.Item>
          <Carousel.Item>
            <div className="h-128">
              <img src={listCategoryBanner[9]} alt={listCategoryTitle[9]} />
            </div>
            <Carousel.Caption>
              <div className="fo">{listCategoryTitle[9]}</div>
            </Carousel.Caption>s
          </Carousel.Item>
        </Carousel>
        <div>sda</div>
      </div>
    </>
  );
};

export default Category;
