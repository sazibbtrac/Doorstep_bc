package com.btracsolution.deliverysystem.Features.Waiter.Features.Order;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.FoodItemClickListener;
import com.btracsolution.deliverysystem.Model.FoodMenuShowListModel;
import com.btracsolution.deliverysystem.Model.MenuFoodListModel;
import com.btracsolution.deliverysystem.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    ArrayList<MenuFoodListModel.FoodGroups> subCategoryList;
    private Context mContext;
    BaseItemClickListener baseItemClickListener;


    public SubCategoryAdapter(Context context, ArrayList<MenuFoodListModel.FoodGroups> subCategoryList,
                              BaseItemClickListener baseItemClickListener) {
        this.baseItemClickListener = baseItemClickListener;
        this.subCategoryList = subCategoryList;
        mContext = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_food_sub_category, parent, false);
        return new ViewHolder(view);
    }

    int index=-1;
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.tvName.setText(subCategoryList.get(position).getFoodgroupName());
        holder.rlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = position;
                notifyDataSetChanged();
                baseItemClickListener.onClickOfListItem(true,position,"");

               // Toast.makeText(mContext, subCategoryList.get(position).getFoodgroupName()+" RL", Toast.LENGTH_SHORT).show();

            }
        });

        if (index == position){
            holder.rlMain.setBackground(mContext.getResources().getDrawable(R.drawable.shape_pink));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.tvName.setTextColor(mContext.getColor(R.color.white));
            }
        }
        else{
            holder.rlMain.setBackground(mContext.getResources().getDrawable(R.drawable.shape_silver));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.tvName.setTextColor(mContext.getColor(R.color.black));
                // holder.tvName.setTextSize(15);
            }
        }
    }

    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }

    public MenuFoodListModel.FoodGroups getFoodGroupsDetails(int position) {
        return subCategoryList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatImageView image;
        AppCompatTextView tvName;
        RelativeLayout rlMain;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.ivCategory);
            rlMain = itemView.findViewById(R.id.rlMain);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
