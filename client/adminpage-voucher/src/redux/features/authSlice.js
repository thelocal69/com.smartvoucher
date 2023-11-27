import { createSlice } from "@reduxjs/toolkit";
import { toast } from "react-toastify";

const initialState = {
  accessToken: "",
  refreshToken: "",
};

export const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    logIn: (state, action) => {
      state.accessToken = action.payload.accessToken;
      state.refreshToken = action.payload.refreshToken;
      localStorage.setItem("token", action.payload.accessToken);
    },
    logOut: (state) => {
      state.accessToken = null;
      state.refreshToken = null;
      localStorage.removeItem("token");
      toast.success("Logout Successfully");
    },
  },
  extraReducers: (builder) => {},
});

export const { logOut, logIn } = authSlice.actions;

// Other code such as selectors can use the imported `RootState` type
export const selectAccessToken = (state) => state.auth.accessToken;
export const selectRefreshToken = (state) => state.auth.refreshToken;

export default authSlice.reducer;
