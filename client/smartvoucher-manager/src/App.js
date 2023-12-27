import "./App.scss";
import {Container} from 'react-bootstrap';
import Header from './components/Header'
import AppRoutes from "./routes/AppRoutes";


function App() {
  return (
    <>
      <div className="app-container">
          <Header />
          <Container>
            <AppRoutes />
          </Container>
      </div>
    </>
  );
}

export default App;
