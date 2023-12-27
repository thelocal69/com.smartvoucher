import React from 'react';
import Offcanvas from 'react-bootstrap/Offcanvas';
import { Link, useLocation } from 'react-router-dom';
import './SideMenu.scss';



const SideMenu = (props) => {

    const list = [
        "Merchant",
        "Chain",
        "Label",
        "Category",
        "Discount type",
        "Store",
        "Warehouse",
        "Serial",
        "User"
    ];

    const [activeTab, setActiveTab] = React.useState(0);
    const location = useLocation();

    const { show, handleClose } = props;

    React.useState(() => {
        let tab = 0;
        tab = list?.findIndex(
            (item) => location.pathname.replace("/", "") === item.toLowerCase()
        );
        setActiveTab(tab);
    }, []);

    const handleClick = (key) => {
        setActiveTab(key);
        handleClose();
    };

    return (
        <>
            <Offcanvas show={show} onHide={handleClose} backdrop="static">
                <Offcanvas.Header closeButton>
                    <Offcanvas.Title>SMARTVOUCHER MANAGER</Offcanvas.Title>
                </Offcanvas.Header>
                <Offcanvas.Body className='nav'>
                    {
                        list?.map((item, key) => {
                            return (
                                <>
                                    <Link to={`${item.toLowerCase()}`}>
                                        <Offcanvas.Title
                                            className='nav-item'
                                            key={key}
                                            onClick={() => handleClick(key)}
                                        >
                                            {item}
                                        </Offcanvas.Title>
                                    </Link>
                                </>
                            )
                        })
                    }
                </Offcanvas.Body>
            </Offcanvas>
        </>
    );
}


export default SideMenu;