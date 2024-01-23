import React from 'react';
import { Container } from "react-bootstrap";
import { googleLogout } from '@react-oauth/google';



const OAuth2Logout = () => {
  googleLogout();
  return (
   <>
    <Container>
        
    </Container>
   </>
  )
}

export default OAuth2Logout