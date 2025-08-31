package com.btracsolution.deliverysystem.Features.Agent.AgentOrderDetails;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.MainViewHolder;
import com.btracsolution.deliverysystem.Features.Agent.AgentMenuList.AgentMenuDetailViewHolder;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.databinding.RowInsideJobTwoGeneric2Binding;
import com.btracsolution.deliverysystem.databinding.RowMenuAgentBinding;

/*import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;*/

/**
 * Created by mahmudul.hasan on 2/7/2018.
 */

public class AgentRowInside extends MainViewHolder implements View.OnClickListener {

    public BaseItemClickListener baseItemClickListener;
    //private row_inside_job_two_generic_2
    private RowInsideJobTwoGeneric2Binding binding;

/*    @BindView(R.id.llMain)
    LinearLayout llMain;
    @BindView(R.id.tx_food_name)
    TextView tx_food_name;
    @BindView(R.id.tx_food_price)
    TextView tx_food_price;
    @BindView(R.id.tx_wuantity)
    TextView tx_wuantity;
    @BindView(R.id.tx_item_amount)
    TextView tx_item_amount;
    @BindView(R.id.tx_topping)
    TextView tx_topping;*/


/*    @BindView(R.id.tvItemStatus)
    TextView tvItemStatus;
    @BindView(R.id.cbItem)
    CheckBox cbItem;*/

    int current_position;

    public AgentRowInside(View itemView) {


        super(itemView);
        binding = RowInsideJobTwoGeneric2Binding.bind(itemView);
        binding.llMain.setOnClickListener(this);
        binding.cbItem.setOnClickListener(this);
    }

    String orderStatus;
    public void setDataIntoView(Context context, OrderDetailsModel.OrderItem OrderItem, BaseItemClickListener baseItemClickListener, int current_position) {
        this.baseItemClickListener = baseItemClickListener;
        this.current_position = current_position;
        orderStatus=OrderItem.getItemstatus();
        binding.txFoodName.setText(OrderItem.getFoodinfo().getFoodname());
        binding.txFoodPrice.setText(/*context.getString(R.string.bdt) + " " + */OrderItem.getFoodinfo().getPrice()); // Change by safayet vi
        binding.txWuantity.setText(OrderItem.getQuantity());
        binding.txItemAmount.setText(getTotalPrice(OrderItem.getPrice(), OrderItem.getQuantity()));

        String ToppingText = "";
        binding.txTopping.setVisibility(View.GONE);

//        if (OrderItem.getOrderitemaddon().size() > 0)
//            tx_topping.setVisibility(View.VISIBLE);
//        else
//            tx_topping.setVisibility(View.GONE);
//
//        for (int i = 0; i < OrderItem.getOrderitemaddon().size(); i++) {
//           // ToppingText += "+(x"+OrderItem.getOrderitemaddon().get(i).getQuantity()+") " + OrderItem.getOrderitemaddon().get(i).getAddon_info().getFoodname() + " - ৳ " + OrderItem.getOrderitemaddon().get(i).getAddon_info().getPrice() + "\n";
//            ToppingText +=OrderItem.getOrderitemaddon().get(i).getAddon_info().getFoodname() + " - ৳ " + OrderItem.getOrderitemaddon().get(i).getAddon_info().getPrice() + " x"+OrderItem.getOrderitemaddon().get(i).getQuantity()+" = "+getTotalPrice(OrderItem.getOrderitemaddon().get(i).getAddon_info().getPrice(), OrderItem.getOrderitemaddon().get(i).getQuantity())+"\n";
//        }
        binding.txTopping.setText(ToppingText);
        if (OrderItem.getItemstatus().contentEquals("0")){
            binding.tvItemStatus.setText("N/R");
            binding.cbItem.setEnabled(true);
            binding.cbItem.setChecked(false);
        }else if(OrderItem.getItemstatus().contentEquals("1")){
            binding.tvItemStatus.setText("R");
            binding.cbItem.setEnabled(false);
            binding.cbItem.setChecked(true);
        }else{
            binding.tvItemStatus.setText("D");
            binding.cbItem.setEnabled(false);
            binding.cbItem.setChecked(true);
        }


    }

   // @OnClick(R.id.llMain)
    public void onClickOfSw() {
        if (orderStatus.contentEquals("0")) {
            if (!binding.cbItem.isChecked()) {
                binding.cbItem.setChecked(true);
                AgentRowInside.this.baseItemClickListener.onClickOfListItem(true, current_position, "checked");

            } else {
                binding.cbItem.setChecked(false);
                AgentRowInside.this.baseItemClickListener.onClickOfListItem(true, current_position, "unchecked");
            }
        }

    }
  //  @OnClick(R.id.cbItem)
    public void onClickOfCheckBox() {
        if (orderStatus.contentEquals("0")) {
            if (binding.cbItem.isChecked()) {
                AgentRowInside.this.baseItemClickListener.onClickOfListItem(true, current_position, "checked");

            } else {
                AgentRowInside.this.baseItemClickListener.onClickOfListItem(true, current_position, "unchecked");
            }
        }

    }

    String getTotalPrice(String UnitPrice, String quantity) {
        int totalPrice = getStringIntoInt(UnitPrice) * getStringIntoInt(quantity);
        return Integer.toString(totalPrice);
    }

    public int getStringIntoInt(String number) {
        return Integer.parseInt(number);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llMain:
                onClickOfSw();
                break;

            case R.id.cbItem:
                onClickOfCheckBox();
                break;
        }
    }
}
