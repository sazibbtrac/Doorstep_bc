package com.btracsolution.deliverysystem.Base;

public interface FoodItemClickListener {

    void onAddFoodItem(boolean isClick, int position, int extra);
    void onSubstractFoodItem(boolean isClick, int position, int extra);
    void onAddRemarks(boolean isClick, int position, String extra);

}
