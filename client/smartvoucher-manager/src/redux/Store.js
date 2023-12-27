import { configureStore } from "@reduxjs/toolkit";import { combineReducers } from "redux";
import storage from "redux-persist/lib/storage";
import {persistReducer} from "redux-persist";
import AuthSlice from "./data/AuthSlice";

const reducers = combineReducers({
    auth: AuthSlice
});

const persistConfig = {
    key: "root",
    storage
};

const persistedReducer = persistReducer(persistConfig, reducers);

export const Store = configureStore({
    reducer: persistedReducer
});

export default Store;