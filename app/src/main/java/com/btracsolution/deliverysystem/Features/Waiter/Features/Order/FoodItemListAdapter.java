package com.btracsolution.deliverysystem.Features.Waiter.Features.Order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import com.btracsolution.deliverysystem.Base.FoodItemClickListener;
import com.btracsolution.deliverysystem.Base.MainViewHolder;
import com.btracsolution.deliverysystem.Model.CartItem;
import com.btracsolution.deliverysystem.Model.FoodMenuShowListModel;
import com.btracsolution.deliverysystem.Model.MenuFoodListModel;
import com.btracsolution.deliverysystem.Model.RiderListSingleModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.SharedData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FoodItemListAdapter extends RecyclerView.Adapter<MainViewHolder> implements Filterable {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_MENU = 1;

    Context context;
    SharedData sharedData;
    ArrayList<FoodMenuShowListModel> foodMenuFilter;
    ArrayList<FoodMenuShowListModel> foodMenuMainModel;
    ArrayList<CartItem> cartItems;
    FoodItemClickListener baseItemClickListener;

    public FoodItemListAdapter(Context context, ArrayList<FoodMenuShowListModel> orderBasicDatas
            , FoodItemClickListener baseItemClickListener, ArrayList<CartItem> cartItems) {
        this.context = context;
        this.foodMenuFilter = orderBasicDatas;
        this.foodMenuMainModel = orderBasicDatas;
        this.baseItemClickListener = baseItemClickListener;
        this.cartItems = cartItems;
        sharedData= new SharedData(context);
    }

    @Override
    public int getItemViewType(int position) {

        if (foodMenuMainModel.get(position).isHeader())
            return TYPE_HEADER;
        else
            return TYPE_MENU;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return new ViewHolderFoodItemListHeader(LayoutInflater.from(context).inflate(R.layout.activity_menu_deatils_header, parent, false));
            case TYPE_MENU:
                return new ViewHolderFoodItemList(LayoutInflater.from(context).inflate(R.layout.row_food_item, parent, false));
            default:
                return new ViewHolderFoodItemList(LayoutInflater.from(context).inflate(R.layout.row_food_item, parent, false));

        }

    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {

        holder.setIsRecyclable(false);
        switch (holder.getItemViewType()) {
            case TYPE_MENU:
                ViewHolderFoodItemList viewHolderFoodItemList = (ViewHolderFoodItemList) holder;
                viewHolderFoodItemList.setDataIntoView(context, foodMenuMainModel.get(position), baseItemClickListener, position, cartItems);

                break;
            case TYPE_HEADER:
                ViewHolderFoodItemListHeader viewHolderFoodItemListHeader = (ViewHolderFoodItemListHeader) holder;
                viewHolderFoodItemListHeader.setDataIntoView(context, foodMenuMainModel.get(position), baseItemClickListener, position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return foodMenuMainModel == null ? 0 : foodMenuMainModel.size();
    }

    public String getFoodID(int position) {
        return foodMenuMainModel.get(position).getFoodId();
    }

    public FoodMenuShowListModel getFoodDetails(int position) {
        return foodMenuMainModel.get(position);
    }





    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString=charSequence.toString().toLowerCase();
                final FilterResults oReturn = new FilterResults();
                 ArrayList<FoodMenuShowListModel> filteredList = new ArrayList<>();

                if (charString.isEmpty()) {
                    foodMenuMainModel = foodMenuFilter;
                    filteredList= foodMenuMainModel;
                } else {
                    for (FoodMenuShowListModel row : foodMenuFilter) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.isHeader()){
                            continue;
                        }
                        else if (row.getFoodName().toLowerCase().contains(charString)) {
                            filteredList.add(row);
                        }else if (charSequence.toString().contentEquals(""))
                            filteredList.add(row);
                    }

                }

                oReturn.values = filteredList;
                oReturn.count= filteredList.size();
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                foodMenuMainModel = (ArrayList<FoodMenuShowListModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
