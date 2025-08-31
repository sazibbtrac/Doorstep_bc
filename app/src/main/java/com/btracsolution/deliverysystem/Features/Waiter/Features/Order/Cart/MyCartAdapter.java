package com.btracsolution.deliverysystem.Features.Waiter.Features.Order.Cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btracsolution.deliverysystem.Base.FoodItemClickListener;
import com.btracsolution.deliverysystem.Base.MainViewHolder;
import com.btracsolution.deliverysystem.Features.Waiter.Features.Order.ViewHolderFoodItemList;
import com.btracsolution.deliverysystem.Features.Waiter.Features.Order.ViewHolderFoodItemListHeader;
import com.btracsolution.deliverysystem.Model.CartItem;
import com.btracsolution.deliverysystem.Model.FoodMenuShowListModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.SharedData;

import java.util.ArrayList;

public class MyCartAdapter extends RecyclerView.Adapter<MainViewHolder>{

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_MENU = 1;

    Context context;
    ArrayList<CartItem> cartItems;
    FoodItemClickListener baseItemClickListener;


    public MyCartAdapter(Context context,FoodItemClickListener baseItemClickListener, ArrayList<CartItem> cartItems) {
        this.context = context;
//        this.foodMenuFilter = orderBasicDatas;
//        this.foodMenuMainModel = orderBasicDatas;
        this.baseItemClickListener = baseItemClickListener;
        this.cartItems = cartItems;
   //     sharedData= new SharedData(context);
    }

    @Override
    public int getItemViewType(int position) {

//        if (foodMenuMainModel.get(position).isHeader())
//            return TYPE_HEADER;
//        else
//            return TYPE_MENU;
        return TYPE_MENU;
    }



    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyCartViewHolder(LayoutInflater.from(context).inflate(R.layout.row_food_item, parent, false));

//        switch (viewType) {
//            case TYPE_HEADER:
//                return new ViewHolderFoodItemListHeader(LayoutInflater.from(context).inflate(R.layout.activity_menu_deatils_header, parent, false));
//            case TYPE_MENU:
//                return new ViewHolderFoodItemList(LayoutInflater.from(context).inflate(R.layout.row_menu_agent, parent, false));
//            default:
//                return new ViewHolderFoodItemList(LayoutInflater.from(context).inflate(R.layout.row_menu_agent, parent, false));
//
//        }

    }

    public CartItem getFoodDetails(int position) {
        return cartItems.get(position);
    }


    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {

        //holder.setIsRecyclable(false);
        MyCartViewHolder myCartViewHolder = (MyCartViewHolder) holder;
        myCartViewHolder.setDataIntoView(context, cartItems.get(position), baseItemClickListener, position, cartItems);


//        switch (holder.getItemViewType()) {
//            case TYPE_MENU:
//                MyCartViewHolder viewHolderFoodItemList = (MyCartViewHolder) holder;
//                viewHolderFoodItemList.setDataIntoView(context, foodMenuMainModel.get(position), baseItemClickListener, position, cartItems);
//                break;
//            case TYPE_HEADER:
//                ViewHolderFoodItemListHeader viewHolderFoodItemListHeader = (ViewHolderFoodItemListHeader) holder;
//                viewHolderFoodItemListHeader.setDataIntoView(context, foodMenuMainModel.get(position), baseItemClickListener, position);
//                break;
//        }

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }




}
