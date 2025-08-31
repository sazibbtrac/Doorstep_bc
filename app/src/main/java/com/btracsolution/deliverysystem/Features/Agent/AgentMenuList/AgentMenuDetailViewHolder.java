package com.btracsolution.deliverysystem.Features.Agent.AgentMenuList;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.MainViewHolder;
import com.btracsolution.deliverysystem.Model.FoodMenuShowListModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.databinding.RowAgentFoodListBinding;
import com.btracsolution.deliverysystem.databinding.RowMenuAgentBinding;
import com.bumptech.glide.Glide;

/*import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;*/

/**
 * Created by mahmudul.hasan on 2/7/2018.
 */

public class AgentMenuDetailViewHolder extends MainViewHolder implements View.OnClickListener {

    public BaseItemClickListener baseItemClickListener;
    private RowMenuAgentBinding binding;

/*    @BindView(R.id.id_food_name)
    TextView id_food_name;

    @BindView(R.id.id_food_price)
    TextView id_food_price;

    @BindView(R.id.im_food_image)
    ImageView im_food_image;

    @BindView(R.id.sw_menu_off)
    Switch sw_menu_off;*/


    int current_position;

    public AgentMenuDetailViewHolder(View itemView) {
        super(itemView);
        binding = RowMenuAgentBinding.bind(itemView);
        binding.swMenuOff.setOnClickListener(this);
    }



    public void setDataIntoView(Context context, FoodMenuShowListModel foodName, BaseItemClickListener baseItemClickListener, int current_position) {
        this.baseItemClickListener = baseItemClickListener;
        this.current_position = current_position;
        binding.idFoodName.setText(foodName.getFoodName());
        binding.idFoodPrice.setText(context.getString(R.string.tk_sign) + " " + foodName.getFoodPrice());
        switch (foodName.getFoodStatus()) {
            case "0":
                binding.swMenuOff.setChecked(false);
                break;
            case "1":
                binding.swMenuOff.setChecked(true);
                break;
        }


        Glide.with(context)
                .load(foodName.getFoodPicture())
                .centerCrop()
                .placeholder(R.drawable.dr_logo)
                .error(R.drawable.ic_broken_image_black_36dp)
                .into(binding.imFoodImage);

    }

/*
    @OnCheckedChanged({R.id.sw_menu_off})
    public void onRadioButtonCheckChanged(CompoundButton button, boolean checked) {
        if (checked) {
            switch (button.getId()) {
                case R.id.sw_menu_off:
                    AgentMenuDetailViewHolder.this.baseItemClickListener.onClickOfListItem(true, current_position, "checked");
                    // do stuff
                    break;
            }
        } else {
            switch (button.getId()) {
                case R.id.sw_menu_off:
                    AgentMenuDetailViewHolder.this.baseItemClickListener.onClickOfListItem(true, current_position, "unchecked");
                    // do stuff
                    break;
            }
        }
    }
*/

   // @OnClick(R.id.sw_menu_off)
    public void onClickOfSw() {
        if (binding.swMenuOff.isChecked()) {
            AgentMenuDetailViewHolder.this.baseItemClickListener.onClickOfListItem(true, current_position, "checked");

        } else
            AgentMenuDetailViewHolder.this.baseItemClickListener.onClickOfListItem(true, current_position, "unchecked");


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sw_menu_off:
                onClickOfSw();
                break;

        }
    }
}
