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
    avatar: (state, action) => {
      state.avatarUrl = action.payload;
    },
    username: (state, action) => {
      state.userName = action.payload;
    },
    userId: (state, action) => {
      state.id = action.payload;
    },
    balance: (state, action) => {
      state.balance = action.payload;
    },
    reset: (state) => {
      state.avatarUrl = "";
      state.userName = "";
      state.id = 0;
      state.balance = 0;
    },
  },
  extraReducers: (builder) => {},
});

export const { avatar, username, userId, balance, reset, infoLogin } =
  UserSlice.actions;

export const selectAvatar = (state) => state.user.avatarUrl;
export const selectUsername = (state) => state.user.userName;
export const selectUserId = (state) => state.user.id;
export const selectUserBalance = (state) => state.user.balance;

export default UserSlice.reducer;
