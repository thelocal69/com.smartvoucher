import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { toast } from "react-toastify";
import { RootState } from "redux/store/store";
// import { RootState } from "redux/store/store";

interface IAuthState {
  accessToken: string;
  refreshToken: string;
}

// Define the initial state using that type
const initialState: IAuthState = {
  accessToken: null,
  refreshToken: null,
};

export const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    logIn: (state, action: PayloadAction<IAuthState>) => {
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
export const selectAccessToken = (state: RootState) => state.auth.accessToken;
export const selectRefreshToken = (state: RootState) => state.auth.refreshToken;

export default authSlice.reducer;
