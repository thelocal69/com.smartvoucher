import React from "react";
import { Header } from "./components/Header/Header";
import { Footer } from "./components/Footer/Footer";
import { Homepage } from "./pages/Homepage/Homepage.tsx";
import { Route, Routes } from "react-router-dom";
import { Profile } from "pages/Profile/Profile.tsx";
import { Detail } from "pages/Detail/Detail.tsx";
import { CategoryPage } from "pages/Category Page/CategoryPage.tsx";
import { SetPassword } from "pages/Set Password/SetPassword.tsx";
import { EmailVerification } from "pages/Email Verification/EmailVerification.tsx";
function App() {
  return (
    <div className="app">
      <Header />
      <Routes>
        <Route path="/" element={<Homepage />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/warehouse/:id" element={<Detail />} />
        <Route path="/category/:id" element={<CategoryPage />} />
        <Route path="/set-password" element={<SetPassword />} />
        <Route path="/email-verification" element={<EmailVerification />} />
      </Routes>
      <Footer />
    </div>
  );
}

export default App;
