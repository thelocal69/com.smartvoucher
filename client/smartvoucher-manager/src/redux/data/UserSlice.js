import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    email: ""
}

export const UserData = createSlice({
    name: 'user',
    initialState,
    reducers: {
        userPayload:(state, action) => {
            state.email = action.payload;
        },
        removeUserPayload: (state) =>{
            state.email = "";
        }
    },
    extraReducers: (builder) =>{}
});

export const {userPayload, removeUserPayload} = UserData.actions;
export const selectEmail = (state) => state.user.email;

export default UserData.reducer;