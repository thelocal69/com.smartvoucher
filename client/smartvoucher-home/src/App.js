import "./App.scss";
import AppRoutes from "./Routes/AppRoutes";
import Footer from "./components/Footer/Footer";
import Header from "./components/Header/Header";
import { Container } from "react-bootstrap";

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
