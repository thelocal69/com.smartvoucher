import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  avatarUrl: "",
  userName: "",
  id: 0,
  balance: 0,
};

export const UserSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    userInfor: (state, action) => {
      state.avatarUrl = action.payload.avatarUrl;
      state.userName = action.payload.userName;
      state.id = action.payload.id;
      state.balance = action.payload.balance;
    },
  },
  extraReducers: (builder) => {},
});

export const { userInfor } =
  UserSlice.actions;

export const selectAvatar = (state) => state.user.avatarUrl;
export const selectUsername = (state) => state.user.userName;
export const selectUserId = (state) => state.user.id;
export const selectUserBalance = (state) => state.user.balance;

export default UserSlice.reducer;
