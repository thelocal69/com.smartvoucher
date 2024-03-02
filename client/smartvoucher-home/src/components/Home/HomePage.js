import React from "react";
import "../Home/HomePage.scss";
import Product from "./Product";
import Category from "./Category";
import Merchant from "./Merchant";

const HomePage = () => {
  return (
    <>
      <Category />
      <Merchant />
      <Product />
    </>
  );
};

export default HomePage;