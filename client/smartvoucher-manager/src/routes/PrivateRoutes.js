import React from 'react'
import { Navigate } from 'react-router-dom';
import { selectAccessToken } from '../redux/data/AuthSlice';
import {useSelector} from 'react-redux';

const PrivateRoutes = ({Component}) => {
    const accessToken = useSelector(selectAccessToken);
    return accessToken ? <Component /> : <Navigate to="/" />
}

export default PrivateRoutes