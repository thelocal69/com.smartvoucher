import { configureStore } from "@reduxjs/toolkit";
import { combineReducers } from "redux";
import storage from "redux-persist/lib/storage";
import { persistReducer } from "redux-persist";
import AuthSlice from "./data/AuthSlice";
import UserSlice from "./data/UserSlice";
import CartSlice from "./data/CartSlice";

const reducers = combineReducers({
  auth: AuthSlice,
  user: UserSlice,
  cart: CartSlice,
});

const initialState = reducers({}, {}, {});

const persistConfig = {
  key: "root",
  storage,
};

const rootReducer = (state, action) => {
  if (action.type === 'LOG_OUT') {
    state = initialState
  }

  return reducers(state, action)
}

const persistedReducer = persistReducer(persistConfig, rootReducer);

export const Store = configureStore({
  reducer: persistedReducer
});

export default Store;
