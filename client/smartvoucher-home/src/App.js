import "./App.scss";
import React from "react";
import AppRoutes from "./Routes/AppRoutes";
import { Container } from "react-bootstrap";
import Header from "./components/Header/Header";
import Footer from "./components/Footer/Footer";

const App = () => {
  return (
    <>
      <Header />
      <Container>
        <AppRoutes />
      </Container>
      <Footer />
    </>
  );
};

export default App;
