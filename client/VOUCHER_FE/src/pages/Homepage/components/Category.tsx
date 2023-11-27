/* Code generated with AutoHTML Plugin for Figma */
import React from "react";
import "./Category.css";
import { Wrapper } from "components/Wrapper/Wrapper";
import Slider from "react-slick";

import KeyboardArrowRightIcon from "@mui/icons-material/KeyboardArrowRight";
import KeyboardArrowLeftIcon from "@mui/icons-material/KeyboardArrowLeft";
import { getAllCategory } from "queries/category";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
export interface IDivContainerProps {}

export const Category = ({ ...props }: IDivContainerProps): JSX.Element => {
  const settings = {
    dots: false,
    infinite: true,
    speed: 500,
    slidesToShow: 4,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 1000,
  };
  const [categories, setCategories] = React.useState([]);
  const navigate = useNavigate();
  React.useEffect(() => {
    getAllCategory()
      .then((rs: any) => {
        if (rs) {
          setCategories(rs.data);
        }
      })
      .catch((err: any) => toast.error(err.message));
  }, []);
  console.log("categories", categories);

  return (
    <Wrapper>
      <div className="category">
        <div className="title">Danh mục</div>
        <div
          className="prev"
          onClick={() => {
            let el = document.querySelector(
              ".category .slick-slider .slick-prev"
            ) as HTMLElement;
            el.click();
          }}
        >
          <KeyboardArrowLeftIcon className="ic" />
        </div>
        <div
          className="next"
          onClick={() => {
            let el = document.querySelector(
              ".category .slick-slider .slick-next"
            ) as HTMLElement;
            el.click();
          }}
        >
          <KeyboardArrowRightIcon className="ic" />
        </div>
        <Slider {...settings}>
          {categories
            ? categories.map((cat: any, key) => {
                return (
                  <div
                    className="card-cate"
                    key={key}
                    onClick={() => {
                      navigate(`/category/${cat.id}`);
                    }}
                  >
                    <div className="bg">
                      <img src={cat.bannerUrl} alt="" className="imagee" />
                    </div>
                    <span className="txt">{cat.name}</span>
                  </div>
                );
              })
            : null}
        </Slider>
      </div>
    </Wrapper>
  );
};
