import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  avatarUrl: "",
  userName: "",
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
  },
  extraReducers: (builder) => {},
});

export const { avatar, username } = UserSlice.actions;

export const selectAvatar = (state) => state.user.avatarUrl;
export const selectUsername = (state) => state.user.userName;

export default UserSlice.reducer;
