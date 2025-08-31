package com.btracsolution.deliverysystem.Features.Waiter.Features.Order.Cart;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.btracsolution.deliverysystem.Base.FoodItemClickListener;
import com.btracsolution.deliverysystem.Base.MainViewHolder;
import com.btracsolution.deliverysystem.Model.CartItem;
import com.btracsolution.deliverysystem.Model.FoodMenuShowListModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.databinding.RowFoodItemBinding;
import com.btracsolution.deliverysystem.databinding.RowRiderMyDayItemBinding;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class MyCartViewHolder extends MainViewHolder {
    private RowFoodItemBinding binding;

    public FoodItemClickListener foodItemClickListener;
/*
    @BindView(R.id.id_food_name)
    TextView id_food_name;

    @BindView(R.id.id_food_price)
    TextView id_food_price;

    @BindView(R.id.im_food_image)
    ImageView im_food_image;


    @BindView(R.id.llAddSubsProduct)
    LinearLayout llAddSubsProduct;

    @BindView(R.id.ivMinus)
    ImageView ivMinus;

    @BindView(R.id.ivAdd)
    ImageView ivAdd;

    @BindView(R.id.etQty)
    TextView etQty;

    @BindView(R.id.tvAddNotes)
    TextView tvAddNotes;
    @BindView(R.id.etNotes)
    EditText etNotes;*/

    int current_position;
    ArrayList<CartItem> cartItems;

    public MyCartViewHolder(View itemView) {
        super(itemView);
        binding = RowFoodItemBinding.bind(itemView);
    }

    public void setDataIntoView(Context context, CartItem foodName, FoodItemClickListener baseItemClickListener, int current_position, ArrayList<CartItem> cartItems) {

        this.foodItemClickListener = baseItemClickListener;
        this.current_position = current_position;
        this.cartItems = cartItems;
        binding.idFoodName.setText(foodName.getFoodName());
        binding.idFoodPrice.setText(context.getString(R.string.tk_sign) + " " + foodName.getFoodPrice()+"/Unit");

        binding.tvAddNotes.setVisibility(View.VISIBLE);
        binding.tvAddNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.etNotes.setVisibility(View.VISIBLE);
            }
        });
        binding.etNotes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable notes) {
                MyCartViewHolder.this.foodItemClickListener.onAddRemarks(true, current_position, notes.toString());

            }
        });
        for (int i=0;i<cartItems.size();i++){
            if (cartItems.get(i).getFoodId().equals(foodName.getFoodId())){
                binding.etQty.setText(""+cartItems.get(i).getQuantity());
                //  etQty.setText(""+foodName.getQuantity());
            }
//                else{
//                    etQty.setText("0");
//                }
        }

        //llAddSubsProduct.setVisibility(View.VISIBLE);

        Glide.with(context)
                .load(foodName.getFoodPicture())
                .centerCrop()
                .placeholder(R.drawable.dr_logo)
                .error(R.drawable.ic_broken_image_black_36dp)
                .into(binding.imFoodImage);


        binding.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int qty= Integer.parseInt(binding.etQty.getText().toString());
                    int newQty= qty+1;
                    binding.etQty.setText(""+newQty);
                    MyCartViewHolder.this.foodItemClickListener.onAddFoodItem(true, current_position, newQty);
                    foodName.setQuantity(newQty);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });

        binding.ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty,newQty=1;
                qty= Integer.parseInt(binding.etQty.getText().toString());
                if (qty>0) {
                    newQty = qty - 1;
                    binding.etQty.setText("" + newQty);
                    MyCartViewHolder.this.foodItemClickListener.onSubstractFoodItem(true, current_position, newQty);
                    foodName.setQuantity(newQty);

                }
            }
        });

    }

}