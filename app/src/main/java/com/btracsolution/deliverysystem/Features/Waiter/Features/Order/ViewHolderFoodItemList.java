package com.btracsolution.deliverysystem.Features.Waiter.Features.Order;

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
import com.btracsolution.deliverysystem.Features.Waiter.Features.Order.Cart.MyCartViewHolder;
import com.btracsolution.deliverysystem.Model.CartItem;
import com.btracsolution.deliverysystem.Model.FoodMenuShowListModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.databinding.RowAgentFoodListBinding;
import com.btracsolution.deliverysystem.databinding.RowFoodItemBinding;
import com.bumptech.glide.Glide;

import java.util.ArrayList;



public class ViewHolderFoodItemList extends MainViewHolder {

    public FoodItemClickListener foodItemClickListener;
    //row_food_item
    private RowFoodItemBinding binding;
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
*/

    int current_position;
    ArrayList<CartItem> cartItems;

    public ViewHolderFoodItemList(View itemView) {
        super(itemView);
        binding = RowFoodItemBinding.bind(itemView);
    }

    public void setDataIntoView(Context context, FoodMenuShowListModel foodName, FoodItemClickListener baseItemClickListener, int current_position, ArrayList<CartItem> cartItems) {
            this.foodItemClickListener = baseItemClickListener;
            this.current_position = current_position;
            this.cartItems = cartItems;
            binding.idFoodName.setText(foodName.getFoodName());
            binding.idFoodPrice.setText(context.getString(R.string.tk_sign) + " " + foodName.getFoodPrice()+"/Unit");

            for (int i = 0; i < cartItems.size(); i++) {
                if (cartItems.get(i).getFoodId().equals(foodName.getFoodId())) {
                    System.out.println("sdjhsdjsd "+cartItems.get(i).getQuantity());
                    binding.etQty.setText("" + cartItems.get(i).getQuantity());
                    //  etQty.setText(""+foodName.getQuantity());
                }
//                else{
//                    etQty.setText("0");
//                }
            }

            binding.llAddSubsProduct.setVisibility(View.VISIBLE);

            Glide.with(context)
                    .load(foodName.getFoodPicture())
                    .centerCrop()
                    .placeholder(R.drawable.dr_logo)
                    .error(R.drawable.ic_broken_image_black_36dp)
                    .into(binding.imFoodImage);

        binding.etQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable notes) {
                String text = binding.etQty.getText().toString();

                if (text.isEmpty() || text.equals("0")) {
                   // binding.etQty.removeTextChangedListener(this);
                   // binding.etQty.setText("0");
                   // binding.etQty.setSelection(binding.etQty.getText().length()); // Move cursor to end
                    binding.etQty.addTextChangedListener(this);
                    ViewHolderFoodItemList.this.foodItemClickListener.onAddFoodItem(true, current_position, 0);
                } else {
                    try {
                        int qty = Integer.parseInt(text);
                        ViewHolderFoodItemList.this.foodItemClickListener.onAddFoodItem(true, current_position, qty);
                    } catch (NumberFormatException e) {
                        System.out.println("checking item : "+e.toString());
                        binding.etQty.setText("0");
                    }
                }



              //  MyCartViewHolder.this.foodItemClickListener.onAddRemarks(true, current_position, notes.toString());
//                if(binding.etQty.getText().equals("")){
//                    ViewHolderFoodItemList.this.foodItemClickListener.onAddFoodItem(true, current_position, 0);
//
//                }
//                else {
//                    ViewHolderFoodItemList.this.foodItemClickListener.onAddFoodItem(true, current_position, Integer.parseInt(binding.etQty.getText().toString()));
//                }

            }
        });


            binding.ivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        int qty = Integer.parseInt(binding.etQty.getText().toString());
                        int newQty = qty + 1;
                        binding.etQty.setText("" + newQty);
                        ViewHolderFoodItemList.this.foodItemClickListener.onAddFoodItem(true, current_position, newQty);
                        foodName.setQuantity(newQty);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            });

            binding.ivMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int qty, newQty = 1;
                    qty = Integer.parseInt(binding.etQty.getText().toString());
                    if (qty > 0) {
                        newQty = qty - 1;
                        binding.etQty.setText("" + newQty);
                        ViewHolderFoodItemList.this.foodItemClickListener.onSubstractFoodItem(true, current_position, newQty);
                        foodName.setQuantity(newQty);

                    }
                }
            });


    }


//        @OnClick(R.id.sw_menu_off)
//        public void onClickOfSw() {
//            if (sw_menu_off.isChecked()) {
//                ViewHolderProductList.this.foodItemClickListener.onClickOfListItem(true, current_position, "checked");
//
//            } else
//                ViewHolderProductList.this.foodItemClickListener.onClickOfListItem(true, current_position, "unchecked");
//
//
//        }

//    @OnClick(R.id.ivAdd)
//    public void onClickOfivAdd() {
//
//    }
//    @OnClick(R.id.ivMinus)
//    public void onClickOfivMinus() {
//
//    }
}
