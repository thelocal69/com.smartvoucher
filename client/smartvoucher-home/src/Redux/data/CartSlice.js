import { createSlice } from "@reduxjs/toolkit";
import { toast } from 'react-toastify';

const initialState = {
  carts: [],
};

export const CartSlice = createSlice({
  name: "cart",
  initialState,
  reducers: {
    addToCart: (state, action) => {
      const itemInCart = action.payload;
      let productItems   = state.carts.find(
        product => product.id === itemInCart.id
      );
      if (productItems) {
        productItems.quantity+=1;
      } else {
        state.carts = [itemInCart, ...state.carts];
      }
      toast.success("Add item into cart success !");  
    },
    incrementQuantity: (state, action) => {
      const item = action.payload;
      let productItems   = state.carts.find(
        product => product.id === item.id
      );
      if(productItems){
        productItems.quantity += 1;
      }
    },
    decrementQuantity: (state, action) => {
      const item = action.payload;
      let productItems   = state.carts.find(
        product => product.id === item.id
      );
      if(productItems){
        productItems.quantity -=1;
      }
      if(productItems.quantity === 0){
        state.carts = state.carts.filter(product => product.id !== item.id);
    }
  },
    removeItem: (state, action) => {
      const removeItem = action.payload;
       state.carts = state.carts.filter(
        product => product.id !== removeItem.id
      );
    },
  },
  extraReducers: (builder) => {},
});

export const { addToCart, incrementQuantity, decrementQuantity, removeItem } =
  CartSlice.actions;

export const selectIdCarts = (state) => state.cart.carts;

export default CartSlice.reducer;
