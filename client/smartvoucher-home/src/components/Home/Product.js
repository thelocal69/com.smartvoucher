import React from "react";
import { Card, Badge, Image, Row, Col, Container } from "react-bootstrap";
import "../Home/HomePage.scss";
import { getAllWarehouseByLabel } from "../../services/WarehouseServices";
import { getAllLabelName } from "../../services/LabelServices";
import { useNavigate } from "react-router-dom";

const Product = () => {
  const [pageSpecial, setPageSpecial] = React.useState(1);
  const [pageFestival, setPageFestival] = React.useState(1);
  const [pageOnlySmartvoucher, setPageOnlySmartvoucher] = React.useState(1);
  const [pageFood, setPageFood] = React.useState(1);
  const [pageTechnologiesLife, setPageTechnologiesLife] = React.useState(1);
  const [pageHealthy, setPageHealthy] = React.useState(1);
  const [totalPageSpecial, setTotalPageSpecial] = React.useState(0);
  const [totalPageFestival, setTotalPageFestival] = React.useState(0);
  const [totalPageOnlySmartvoucher, setTotalPageOnlySmartvoucher] =
    React.useState(0);
  const [totalPageFood, setTotalPageFood] = React.useState(0);
  const [totalPageTechnologiesLife, setTotalPageTechnologiesLife] =
    React.useState(0);
  const [totalPageHealthy, setTotalPageHealthy] = React.useState(0);
  const [limit, setLimit] = React.useState(8);
  const [lableName, setLabelName] = React.useState([]);
  const [listWarehouseSpecial, setListWarehouseSpecial] = React.useState([]);
  const [listWarehouseFestival, setListWarehouseFestival] = React.useState([]);
  const [listWarehouseOnlySmartvoucher, setListWarehouseOnlySmartvoucher] =
    React.useState([]);
  const [listWarehouseFood, setListWarehouseFood] = React.useState([]);
  const [listWarehouseTechnologiesLife, setListWarehouseTechnologiesLife] =
    React.useState([]);
  const [listWarehouseHealthy, setListWarehouseHealthy] = React.useState([]);
  const [special, setSpecial] = React.useState("special");
  const [festival, setFestival] = React.useState("festival");
  const [onlySmartvoucher, setOnlySmartvoucher] =
    React.useState("only-smartvoucher");
  const [food, setFoodl] = React.useState("food");
  const [technologiesLife, setTechnologiesLife] =
    React.useState("technologies-life");
  const [healthy, setHealthy] = React.useState("healthy");
  // const [bigSale, setBigSale] = React.useState("big-sale");
  // const [weekend, setWeekend] = React.useState("weekend");
  // const [saleMonth, setSaleMonth] = React.useState("sale-month");
  // const [moment, setMoment] = React.useState("moment");
  const navigate = useNavigate();

  React.useEffect(() => {
    getLable();
  }, []);

  React.useEffect(() => {
    const getWarehouseBySpecial = async () => {
      await getAllWarehouseByLabel(special, pageSpecial, limit)
        .then((rs) => {
          if (rs) {
            setPageSpecial(rs.page);
            setTotalPageSpecial(rs.totalPage);
            setListWarehouseSpecial([...listWarehouseSpecial, ...rs.data]);
          }
        })
        .catch((err) => console.log(err.message));
    };

    getWarehouseBySpecial();
  }, [pageSpecial]);

  React.useEffect(() => {
    const getWarehouseByFestival = async () => {
      await getAllWarehouseByLabel(festival, pageFestival, limit)
        .then((rs) => {
          if (rs) {
            setPageFestival(rs.page);
            setTotalPageFestival(rs.totalPage);
            setListWarehouseFestival([...listWarehouseFestival, ...rs.data]);
          }
        })
        .catch((err) => console.log(err.message));
    };

    getWarehouseByFestival();
  }, [pageFestival]);

  React.useEffect(() => {
    const getWarehouseByOnlySmartvoucher = async () => {
      await getAllWarehouseByLabel(
        onlySmartvoucher,
        pageOnlySmartvoucher,
        limit
      )
        .then((rs) => {
          if (rs) {
            setPageOnlySmartvoucher(rs.page);
            setTotalPageOnlySmartvoucher(rs.totalPage);
            setListWarehouseOnlySmartvoucher([
              ...listWarehouseOnlySmartvoucher,
              ...rs.data,
            ]);
          }
        })
        .catch((err) => console.log(err.message));
    };

    getWarehouseByOnlySmartvoucher();
  }, [pageOnlySmartvoucher]);

  React.useEffect(() => {
    const getWarehouseByFood = async () => {
      await getAllWarehouseByLabel(food, pageFood, limit)
        .then((rs) => {
          if (rs) {
            setPageFood(rs.page);
            setTotalPageFood(rs.totalPage);
            setListWarehouseFood([...listWarehouseFood, ...rs.data]);
          }
        })
        .catch((err) => console.log(err.message));
    };

    getWarehouseByFood();
  }, [pageFood]);

  React.useEffect(() => {
    const getWarehouseByTechnologiesLife = async () => {
      await getAllWarehouseByLabel(
        technologiesLife,
        pageTechnologiesLife,
        limit
      )
        .then((rs) => {
          if (rs) {
            setPageTechnologiesLife(rs.page);
            setTotalPageTechnologiesLife(rs.totalPage);
            setListWarehouseTechnologiesLife([
              ...listWarehouseTechnologiesLife,
              ...rs.data,
            ]);
          }
        })
        .catch((err) => console.log(err.message));
    };

    getWarehouseByTechnologiesLife();
  }, [pageTechnologiesLife]);

  React.useEffect(() => {
    const getWarehouseByHealthy = async () => {
      await getAllWarehouseByLabel(healthy, pageHealthy, limit)
        .then((rs) => {
          if (rs) {
            setPageHealthy(rs.page);
            setTotalPageHealthy(rs.totalPage);
            setListWarehouseHealthy([...listWarehouseHealthy, ...rs.data]);
          }
        })
        .catch((err) => console.log(err.message));
    };

    getWarehouseByHealthy();
  }, [pageHealthy]);

  const getLable = async () => {
    await getAllLabelName()
      .then((rs) => {
        if (rs) {
          const name = rs.data?.map((item) => item.name);
          setLabelName(name);
        }
      })
      .catch((err) => console.log(err.message));
  };

  return (
    <>
        <Row xs={1} md={1}>
          <Col>
            <div>
              <div className="p-4 label-font">
                <h4>{lableName[0]}</h4>
              </div>
              <div className="d-flex align-content-center justify-content-center flex-wrap">
                {listWarehouseSpecial &&
                  listWarehouseSpecial.map((item, key) => {
                    return (
                      <>
                        <div className="ta gc Pe p-4 bO" key={key}>
                          <Card >
                            <Image
                              loading="lazy"
                              src={item.bannerUrl}
                              className="img"
                            />
                            <Card.Body>
                              <div
                                onClick={() => {
                                  navigate(`/Product/Detail/${item.id}`);
                                }}
                                className="a"
                              >
                                {item.name}
                              </div>
                              <Card.Text className="d-flex justify-content-between my-2">
                                <span className="price">
                                  {new Intl.NumberFormat("vi-VN", {
                                    style: "currency",
                                    currency: "VND",
                                  }).format(item.price)}
                                </span>
                                <span className="discount-price">
                                  {item.maxDiscountAmount > 0 && (
                                    <>
                                      {new Intl.NumberFormat("vi-VN", {
                                        style: "currency",
                                        currency: "VND",
                                      }).format(item.originalPrice)}
                                      <span className="ps-3">
                                        <Badge bg="danger">
                                          -{item.maxDiscountAmount}%
                                        </Badge>
                                      </span>
                                    </>
                                  )}
                                </span>
                              </Card.Text>
                            </Card.Body>
                          </Card>
                        </div>
                      </>
                    );
                  })}
              </div>
              <div className="d-flex justify-content-center">
                {totalPageSpecial !== pageSpecial && (
                  <>
                    <button
                      className="btn-more my-md-3"
                      onClick={() => setPageSpecial(pageSpecial + 1)}
                    >
                      Load more
                    </button>
                  </>
                )}
              </div>
              <hr />
            </div>
          </Col>
          <Col>
            <div>
              <div className="p-4 label-font">
                <h4>{lableName[1]}</h4>
              </div>
              <div className="d-flex align-content-center justify-content-center flex-wrap">
                {listWarehouseFestival &&
                  listWarehouseFestival.map((item, key) => {
                    return (
                      <>
                        <div className="ta gc Pe p-4" key={key}>
                          <Card>
                            <Image
                              loading="lazy"
                              src={item.bannerUrl}
                              className="img"
                            />
                            <Card.Body>
                              <div
                                onClick={() => {
                                  navigate(`/Product/Detail/${item.id}`);
                                }}
                                className="a"
                              >
                                {item.name}
                              </div>
                              <Card.Text className="d-flex justify-content-between my-2">
                                <span className="price">
                                  {new Intl.NumberFormat("vi-VN", {
                                    style: "currency",
                                    currency: "VND",
                                  }).format(item.price)}
                                </span>
                                <span className="discount-price">
                                  {item.maxDiscountAmount > 0 && (
                                    <>
                                      {new Intl.NumberFormat("vi-VN", {
                                        style: "currency",
                                        currency: "VND",
                                      }).format(item.originalPrice)}
                                      <span className="ps-3">
                                        <Badge bg="danger">
                                          -{item.maxDiscountAmount}%
                                        </Badge>
                                      </span>
                                    </>
                                  )}
                                </span>
                              </Card.Text>
                            </Card.Body>
                          </Card>
                        </div>
                      </>
                    );
                  })}
              </div>
              <div className="d-flex justify-content-center">
                {totalPageFestival !== pageFestival && (
                  <>
                    <button
                      className="btn-more my-md-3"
                      onClick={() => setPageFestival(pageFestival + 1)}
                    >
                      Load more
                    </button>
                  </>
                )}
              </div>
              <hr />
            </div>
          </Col>
          <Col>
            <div>
              <div className="p-4 label-font">
                <h4>{lableName[2]}</h4>
              </div>
              <div className="d-flex align-content-center justify-content-center flex-wrap">
                {listWarehouseOnlySmartvoucher &&
                  listWarehouseOnlySmartvoucher.map((item, key) => {
                    return (
                      <>
                        <div className="ta gc Pe p-4" key={key}>
                          <Card>
                            <Image
                              loading="lazy"
                              src={item.bannerUrl}
                              className="img"
                            />
                            <Card.Body>
                              <div
                                onClick={() => {
                                  navigate(`/Product/Detail/${item.id}`);
                                }}
                                className="a"
                              >
                                {item.name}
                              </div>
                              <Card.Text className="d-flex justify-content-between my-2">
                                <span className="price">
                                  {new Intl.NumberFormat("vi-VN", {
                                    style: "currency",
                                    currency: "VND",
                                  }).format(item.price)}
                                </span>
                                <span className="discount-price">
                                  {item.maxDiscountAmount > 0 && (
                                    <>
                                      {new Intl.NumberFormat("vi-VN", {
                                        style: "currency",
                                        currency: "VND",
                                      }).format(item.originalPrice)}
                                      <span className="ps-3">
                                        <Badge bg="danger">
                                          -{item.maxDiscountAmount}%
                                        </Badge>
                                      </span>
                                    </>
                                  )}
                                </span>
                              </Card.Text>
                            </Card.Body>
                          </Card>
                        </div>
                      </>
                    );
                  })}
              </div>
              <div className="d-flex justify-content-center">
                {totalPageOnlySmartvoucher !== pageOnlySmartvoucher && (
                  <>
                    <button
                      className="btn-more my-md-3"
                      onClick={() =>
                        setPageOnlySmartvoucher(pageOnlySmartvoucher + 1)
                      }
                    >
                      Load more
                    </button>
                  </>
                )}
              </div>
              <hr />
            </div>
          </Col>
          <Col>
            <div>
              <div className="p-4 label-font">
                <h4>{lableName[3]}</h4>
              </div>
              <div className="d-flex align-content-center justify-content-center flex-wrap">
                {listWarehouseFood &&
                  listWarehouseFood.map((item, key) => {
                    return (
                      <>
                        <div className="ta gc Pe p-4" key={key}>
                          <Card>
                            <Image
                              loading="lazy"
                              src={item.bannerUrl}
                              className="img"
                            />
                            <Card.Body>
                              <div
                                onClick={() => {
                                  navigate(`/Product/Detail/${item.id}`);
                                }}
                                className="a"
                              >
                                {item.name}
                              </div>
                              <Card.Text className="d-flex justify-content-between my-2">
                                <span className="price">
                                  {new Intl.NumberFormat("vi-VN", {
                                    style: "currency",
                                    currency: "VND",
                                  }).format(item.price)}
                                </span>
                                <span className="discount-price">
                                  {item.maxDiscountAmount > 0 && (
                                    <>
                                      {new Intl.NumberFormat("vi-VN", {
                                        style: "currency",
                                        currency: "VND",
                                      }).format(item.originalPrice)}
                                      <span className="ps-3">
                                        <Badge bg="danger">
                                          -{item.maxDiscountAmount}%
                                        </Badge>
                                      </span>
                                    </>
                                  )}
                                </span>
                              </Card.Text>
                            </Card.Body>
                          </Card>
                        </div>
                      </>
                    );
                  })}
              </div>
              <div className="d-flex justify-content-center">
                {totalPageFood !== pageFood && (
                  <>
                    <button
                      className="btn-more my-md-3"
                      onClick={() => setPageFood(pageFood + 1)}
                    >
                      Load more
                    </button>
                  </>
                )}
              </div>
              <hr />
            </div>
          </Col>
          <Col>
            <div>
              <div className="p-4 label-font">
                <h4>{lableName[4]}</h4>
              </div>
              <div className="d-flex align-content-center justify-content-center flex-wrap">
                {listWarehouseTechnologiesLife &&
                  listWarehouseTechnologiesLife.map((item, key) => {
                    return (
                      <>
                        <div className="ta gc Pe p-4" key={key}>
                          <Card>
                            <Image
                              loading="lazy"
                              src={item.bannerUrl}
                              className="img"
                            />
                            <Card.Body>
                              <div
                                onClick={() => {
                                  navigate(`/Product/Detail/${item.id}`);
                                }}
                                className="a"
                              >
                                {item.name}
                              </div>
                              <Card.Text className="d-flex justify-content-between my-2">
                                <span className="price">
                                  {new Intl.NumberFormat("vi-VN", {
                                    style: "currency",
                                    currency: "VND",
                                  }).format(item.price)}
                                </span>
                                <span className="discount-price">
                                  {item.maxDiscountAmount > 0 && (
                                    <>
                                      {new Intl.NumberFormat("vi-VN", {
                                        style: "currency",
                                        currency: "VND",
                                      }).format(item.originalPrice)}
                                      <span className="ps-3">
                                        <Badge bg="danger">
                                          -{item.maxDiscountAmount}%
                                        </Badge>
                                      </span>
                                    </>
                                  )}
                                </span>
                              </Card.Text>
                            </Card.Body>
                          </Card>
                        </div>
                      </>
                    );
                  })}
              </div>
              <div className="d-flex justify-content-center">
                {totalPageTechnologiesLife !== pageTechnologiesLife && (
                  <>
                    <button
                      className="btn-more my-md-3"
                      onClick={() =>
                        setPageTechnologiesLife(pageTechnologiesLife + 1)
                      }
                    >
                      Load more
                    </button>
                  </>
                )}
              </div>
              <hr />
            </div>
          </Col>
        </Row>
      {/* <div className="app se">
        <div className="p-4 label-font ta gc Pe">
          <h4>{lableName[8]}</h4>
        </div>
        <div className="d-flex align-content-center justify-content-center flex-wrap">
          {listWarehouseHealthy &&
            listWarehouseHealthy.map((item, key) => {
              return (
                <>
                  <div className="ta gc Pe p-4" key={key}>
                    <Card
                      className="custom-card"
                      style={{ width: "18rem", border: "none" }}
                    >
                      <Image
                        loading="lazy"
                        src={item.bannerUrl}
                        className="img"
                      />
                      <Card.Body>
                        <div
                          onClick={() => {
                            navigate(`/Product/Detail/${item.id}`);
                          }}
                          className="a"
                        >
                          {item.name}
                        </div>
                        <Card.Text className="d-flex justify-content-between my-2">
                          <span className="price">
                            {new Intl.NumberFormat("vi-VN", {
                              style: "currency",
                              currency: "VND",
                            }).format(item.price)}
                          </span>
                          <span className="discount-price">
                            {item.maxDiscountAmount > 0 && (
                              <>
                                {new Intl.NumberFormat("vi-VN", {
                                  style: "currency",
                                  currency: "VND",
                                }).format(item.originalPrice)}
                                <span className="ps-3">
                                  <Badge bg="danger">
                                    -{item.maxDiscountAmount}%
                                  </Badge>
                                </span>
                              </>
                            )}
                          </span>
                        </Card.Text>
                      </Card.Body>
                    </Card>
                  </div>
                </>
              );
            })}
        </div>
        <div className="d-flex justify-content-center">
          {totalPageHealthy !== pageHealthy && (
            <>
              <button
                className="btn-more my-3"
                onClick={() => setPageHealthy(pageHealthy + 1)}
              >
                Load more
              </button>
            </>
          )}
        </div>
        <hr />
      </div> */}
      <br />
    </>
  );
};

export default Product;
