import React from 'react';
import { selectAccessToken } from '../../Redux/data/AuthSlice';
import { selectUserId } from '../../Redux/data/UserSlice';
import { useSelector, useDispatch } from 'react-redux';
import { getAllWishList, deleteWishList } from '../../services/WishlistServices';
import { Col, Row, Badge } from 'react-bootstrap';
import './Wishlist.scss';
import Paginate from '../Util/Paginate';
import { addToCart } from '../../Redux/data/CartSlice';
import { toast } from 'react-toastify';

const Wishlist = () => {

    const accessToken = useSelector(selectAccessToken);
    const idCurrentUser = useSelector(selectUserId);
    const dispatch = useDispatch();
    const [listWishlist, setListWishlist] = React.useState([]);
    const [currentPage, setCurrentPage] = React.useState(1);
    const [limit, setLimit] = React.useState(4);
    const [totalItem, setTotalItem] = React.useState(0);
    const [totalPage, setTotalPage] = React.useState(0);
    const [objDelete, setObjDelete] = React.useState({});


    React.useEffect(() => {
        getWishList(idCurrentUser, currentPage, limit);
    }, []);

    const getWishList = async (idCurrentUser, currentPage, limit) => {
        await getAllWishList(idCurrentUser, currentPage, limit)
            .then((rs) => {
                if (rs) {
                    setCurrentPage(rs.page);
                    setTotalItem(rs.totalItem);
                    setTotalPage(rs.totalPage);
                    setListWishlist(rs.data);
                }
            })
            .catch((err) => console.log(err.message));
    }

    const handleDeleteWishlist = async (item) => {
        setObjDelete(item);
        await deleteWishList(objDelete)
            .then((rs) => {
                if (rs) {
                    toast.success("Remove product from wishlist successfully !");
                    getWishList(idCurrentUser, currentPage, limit);
                }
            })
            .catch((err) => console.log(err));
    }

    const handleClickTable = (event) => {
        getWishList(idCurrentUser, +event.selected + 1, limit);
    }

    console.log(objDelete)

    return (
        <>
            {
                accessToken && (
                    <div>
                        {
                            listWishlist ? listWishlist.map((item, key) => {
                                return (
                                    <Row xs={1} md='auto' key={key} className="justify-content-md-between">
                                        <Col className='pb-3'>
                                            <Row xs={1} md={3}>
                                                <Col className='OU'>
                                                    <img alt='' src={item.bannerUrl} />
                                                </Col>
                                                <Col>
                                                    <Row xs={1} md={1}>
                                                        <Col>
                                                            <h6>{item.name}</h6>
                                                        </Col>
                                                        <Col>
                                                            <p>{item.categoryName}</p>
                                                        </Col>
                                                        <Col>
                                                            <Row xs={3} md='auto'>
                                                                <Col>
                                                                    <span
                                                                        className="btn-heart">
                                                                        <i class={
                                                                            item.status ? "fa-solid fa-heart ol active" : "fa-solid fa-heart ol deactive"
                                                                        }
                                                                        ></i>
                                                                    </span>
                                                                </Col>
                                                                <Col>
                                                                    <span
                                                                        className="ps-3 btn-cart"
                                                                        onClick={() => {
                                                                            let element = null;
                                                                            element = { ...item, quantity: 1 };
                                                                            dispatch(addToCart(element));
                                                                        }}
                                                                    >

                                                                        <i class="fa-solid fa-cart-shopping"></i>
                                                                        Thêm vào giỏ
                                                                    </span>
                                                                </Col>
                                                                <Col className='mk'>
                                                                    <i
                                                                        class="fa-solid fa-trash-can text-danger"
                                                                        onClick={() => {
                                                                            handleDeleteWishlist(item);
                                                                        }}
                                                                        style={{
                                                                            cursor: "pointer",
                                                                        }}
                                                                    ></i>
                                                                </Col>
                                                            </Row>
                                                        </Col>
                                                    </Row>
                                                </Col>
                                                <Col>
                                                    <Row xs={1} md={1}>
                                                        <Col>
                                                            <h6 className="fW">
                                                                {new Intl.NumberFormat("vi-VN", {
                                                                    style: "currency",
                                                                    currency: "VND",
                                                                }).format(item.price)}
                                                            </h6>
                                                        </Col>
                                                        <Col>
                                                            <h6 className="dp fW">
                                                                {item.maxDiscountAmount > 0 && (
                                                                    <>
                                                                        <span className='pe-3'>
                                                                            <Badge bg="danger">-{item.maxDiscountAmount}%</Badge>
                                                                        </span>
                                                                        {new Intl.NumberFormat("vi-VN", {
                                                                            style: "currency",
                                                                            currency: "VND",
                                                                        }).format(item.originalPrice)}
                                                                    </>
                                                                )}
                                                            </h6>
                                                        </Col>
                                                    </Row>
                                                </Col>
                                            </Row>
                                        </Col>
                                    </Row>
                                )
                            })
                                : "No data"
                        }
                        <Paginate totalPages={totalPage} handlePageClick={handleClickTable} />
                        {
                            listWishlist < 1 && (
                                <div>
                                    <Row xs={1} md='auto' className="flex-column justify-content-md-center align-items-center">
                                        <Col>
                                            <p>
                                                Không có sản phẩm yêu thích ! Hãy thêm vào bạn nhé !
                                            </p>
                                        </Col>
                                        <Col>
                                            <img
                                                alt=""
                                                src="https://cdn.divineshop.vn/static/4e0db8ffb1e9cac7c7bc91d497753a2c.svg"
                                            />
                                        </Col>
                                    </Row>
                                </div>
                            )
                        }
                    </div>
                )
            }
        </>
    )
}

export default Wishlist