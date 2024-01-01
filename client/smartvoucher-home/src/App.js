import "./App.scss";
import AppRoutes from "./Routes/AppRoutes";
import Header from "./components/Header/Header";
import { Container } from "react-bootstrap";

const App = () => {
  return (
    <>
      <Header />
      <Container>
        <AppRoutes />
      </Container>
    </>
  );
};

export default App;
