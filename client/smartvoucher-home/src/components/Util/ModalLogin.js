import React from "react";
import { Button, Modal, Nav } from "react-bootstrap";
import Account from "../Security/Account";

const ModalLogin = (props) => {
  const { show, handleClose } = props;

  return (
    <>
      <Modal
        show={show}
        onHide={handleClose}
        size="lg"
        aria-labelledby="contained-modal-title-vcenter"
        centered
        backdrop="static"
      >
        <Modal.Header closeButton>
        </Modal.Header>
        <Modal.Body>
          <Account />
        </Modal.Body>
      </Modal>
    </>
  );
};

export default ModalLogin;
