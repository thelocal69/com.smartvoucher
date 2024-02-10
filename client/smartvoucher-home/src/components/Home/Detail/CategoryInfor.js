import React from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getAllWarehouseByCategory } from '../../../services/WarehouseServices';
import { Card, Image, Badge } from 'react-bootstrap';
import Paginate from '../../Util/Paginate';

const CategoryInfor = () => {

  const { name } = useParams();
  const navigate = useNavigate();
  const [listWarehouse, setListWarehouse] = React.useState([]);
  const [currentPage, setCurrentPage] = React.useState(1);
  const [limit, setLimit] = React.useState(6);
  const [totalPage, setTotalPage] = React.useState(0);
  const [totalItem, setTotalItem] = React.useState(0);

  React.useEffect(() => {
    getWarehouseByCategory(name, currentPage, limit);
    setListWarehouse([]);
  }, [name]);

  const handlePageClick = (event) => {
    getWarehouseByCategory(name, +event.selected + 1, limit);
  }

  const getWarehouseByCategory = async (name, currentPage, limit) => {
    await getAllWarehouseByCategory(name, currentPage, limit)
      .then((rs) => {
        if (rs) {
          setCurrentPage(rs.page);
          setTotalPage(rs.totalPage);
          setTotalItem(rs.totalItem);
          setListWarehouse(rs.data);
        }
      })
      .catch((err) => console.log(err.message));
  }
  return (
    <>
      <div className="app se">
        <div className="p-4 label-font ta gc Pe">
          <h4>{name}</h4>
        </div>
        <div className="d-flex align-content-center justify-content-center flex-wrap">
          {listWarehouse &&
            listWarehouse.map((item, key) => {
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
        <Paginate totalPages={totalPage} handlePageClick={handlePageClick} />
        <hr />
      </div>
    </>
  )
}

export default CategoryInfor