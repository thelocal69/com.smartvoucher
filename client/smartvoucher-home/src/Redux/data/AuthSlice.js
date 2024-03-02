import { createSlice } from "@reduxjs/toolkit";
import { toast } from "react-toastify";

const initialState = {
  accessToken: "",
  refreshToken: "",
  isAuthenticated: false,
};

export const AuthSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    logIn: (state, action) => {
      state.accessToken = action.payload.accessToken;
      state.refreshToken = action.payload.refreshToken;
      state.isAuthenticated = true;
      localStorage.setItem("token", action.payload.accessToken);
      localStorage.setItem('isReloaded', true);
    },
    logOut: (state) => {
      state.accessToken = null;
      state.refreshToken = null;
      state.isAuthenticated = false;
      toast.success("Logout Successfully");
      localStorage.removeItem("token");
    },
  },
  extraReducers: (builder) => {},
});

export const { logOut, logIn } = AuthSlice.actions;

export const selectAccessToken = (state) => state.auth.accessToken;
export const selectRefreshToken = (state) => state.auth.refreshToken;
export const selectIsAuthenticated = (state) => state.auth.isAuthenticated;

export default AuthSlice.reducer;
