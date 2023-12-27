import React from "react";
import { Container, Alert } from "react-bootstrap";
import { NavLink } from "react-router-dom";

const NotFound = () => {
  return (
    <>
      <Container>
        <Alert variant={"danger"} className="m-5 p-4">
          <div className="p-0 mt-2">
            <div className="text-center card-body p-1">
              <Alert.Heading>
                <h1>404</h1>
              </Alert.Heading>
              <h2>Page Not Found</h2>
              <p class="text-gray-500 mb-0">
                It looks like you found a glitch in the matrix...
              </p>
              <NavLink to="/" className="btn btn-primary mt-4">
                Back to home
              </NavLink>
            </div>
          </div>
        </Alert>
      </Container>
    </>
  );
};

export default NotFound;
