import { 
  Nav, 
  Navbar, 
  Container, 
  NavDropdown,
  Modal,
  Button } from "react-bootstrap";
import React from "react";
import SideMenu from "./SideMenu";
import "./Header.scss";
import { useLocation, useNavigate, NavLink } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import {
  logOut,
  selectRefreshToken,
  selectAccessToken,
  selectIsAuthenticated,
} from "../redux/data/AuthSlice";
import { removeUserPayload, selectEmail } from "../redux/data/UserSlice";
import { logOutAsync } from "../services/AccountService";
import { toast } from "react-toastify";

const Header = (props) => {
  const [isShowMenu, setIsShowMenu] = React.useState(false);
  const [isShowHeader, setIsShowHeader] = React.useState(false);
  const [isShowLogOut, setIsShowLogOut] = React.useState(false);

  const location = useLocation();
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const isAuthenticated = useSelector(selectIsAuthenticated);
  const accessToken = useSelector(selectAccessToken);
  const refreshToken = useSelector(selectRefreshToken);
  const email = useSelector(selectEmail);
  let index = email.indexOf("@");
  const username = email.substring(0, index);

  React.useEffect(() => {
    if (isAuthenticated) {
      setIsShowHeader(true);
    } else {
      setIsShowHeader(false);
    }
  }, [isAuthenticated]);

  const handleClose = () => {
    setIsShowMenu(false);
    setIsShowLogOut(false);
  };

  const handleLogout = async () => {
    dispatch(logOut());
    dispatch(removeUserPayload());
    navigate("/");
    setIsShowLogOut(false);
    await logOutAsync(refreshToken)
      .then((rs) => {
        if (rs) {
          toast.success(rs.message);
        }
      })
      .catch((err) => {
        toast.error(err.message);
      });
  };

  return (
    <>
      {isAuthenticated && (
        <>
          <Navbar expand="lg" className="bg-white">
            <Container>
              <Navbar.Brand>
                <NavLink to="/Home" className="color-black">
                  SMARTVOUCHER MANAGER
                </NavLink>
              </Navbar.Brand>
              <Navbar.Toggle aria-controls="basic-navbar-nav" />
              <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="me-auto" activeKey={location.pathname}>
                  <NavLink to="/Home" className="nav-link">
                    Home
                  </NavLink>
                  <NavLink
                    onClick={() => setIsShowMenu(true)}
                    className="nav-link"
                  >
                    Menu
                  </NavLink>
                </Nav>
                <Nav>
                  <NavDropdown title={username} id="basic-nav-dropdown">
                    <NavLink
                    to="/profile"
                    className="dropdown-item"
                    >
                      Profile
                    </NavLink>
                    <NavLink
                      to="/"
                      className="dropdown-item"
                      hidden={accessToken ? true : false}
                    >
                      Login
                    </NavLink>
                    {accessToken && (
                      <>
                        <NavDropdown.Item
                          hidden={accessToken ? false : true}
                          onClick={() => setIsShowLogOut(true)}
                        >
                          Logout
                        </NavDropdown.Item>
                      </>
                    )}
                  </NavDropdown>
                </Nav>
              </Navbar.Collapse>
            </Container>
          </Navbar>
        </>
      )}
      <Container>
        <SideMenu show={isShowMenu} handleClose={handleClose} />
      </Container>
      
      <Modal show={isShowLogOut} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Logout SMARTVOUCHER MANAGER</Modal.Title>
        </Modal.Header>
        <Modal.Body>Do you want to log-out !</Modal.Body>
        <Modal.Footer className="d-flex justify-content-between">
          <Button variant="secondary" onClick={handleClose}>
            <i class="fa-solid fa-circle-xmark"></i>
            Close
          </Button>
          <Button variant="primary" onClick={handleLogout}>
            <i class="fa-solid fa-check"></i>
            Accept
          </Button>
        </Modal.Footer>
      </Modal>

    </>
  );
};

export default Header;
