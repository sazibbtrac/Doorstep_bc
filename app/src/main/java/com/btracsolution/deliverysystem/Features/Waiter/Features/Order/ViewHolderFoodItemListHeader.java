package com.btracsolution.deliverysystem.Features.Waiter.Features.Order;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.FoodItemClickListener;
import com.btracsolution.deliverysystem.Base.MainViewHolder;
import com.btracsolution.deliverysystem.Model.FoodMenuShowListModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.databinding.ActivityMenuDeatilsHeaderBinding;


public class ViewHolderFoodItemListHeader extends MainViewHolder {

        public FoodItemClickListener baseItemClickListener;
        private ActivityMenuDeatilsHeaderBinding binding;
        //activity_menu_deatils_header
/*
        @BindView(R.id.tx_header_title)
        TextView tx_header_title;*/


        int current_position;

        public ViewHolderFoodItemListHeader(View itemView) {
            super(itemView);
            //ButterKnife.bind(this, itemView);
            binding = ActivityMenuDeatilsHeaderBinding.bind(itemView);
        }

        public void setDataIntoView(Context context, FoodMenuShowListModel foodName, FoodItemClickListener baseItemClickListener, int current_position) {
            this.baseItemClickListener = baseItemClickListener;
            this.current_position = current_position;
            binding.txHeaderTitle.setText(foodName.getHeaderTitle());


        }


    }