package com.btracsolution.deliverysystem.Features.Waiter.Features.Bill;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.MainViewHolder;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.databinding.RowAgentFoodListBinding;
import com.btracsolution.deliverysystem.databinding.RowRiderMyDayItemBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;*/

public class BillViewHolder  extends MainViewHolder implements View.OnClickListener {

    public BaseItemClickListener baseItemClickListener;
    private RowAgentFoodListBinding binding;
/*
    @BindView(R.id.tx_name)
    TextView tx_name;
    @BindView(R.id.tx_bdt_amount)
    TextView tx_bdt_amount;
    @BindView(R.id.tx_total_item)
    TextView tx_total_item;
    @BindView(R.id.tx_status)
    TextView tx_status;
    @BindView(R.id.tx_date)
    TextView tx_date;
    @BindView(R.id.tx_time)
    TextView tx_time;
    @BindView(R.id.list_name_agent)
    LinearLayout list_name_agent;
    @BindView(R.id.cbOrder)
    CheckBox cbOrder;*/
    int current_position;

    public BillViewHolder(View itemView) {
        super(itemView);
        binding = RowAgentFoodListBinding.bind(itemView);
        binding.listNameAgent.setOnClickListener(this);
    }

    public void setDataIntoView(Context context, OrderDetailsModel.orderBasicData orderBasicData, BaseItemClickListener baseItemClickListener, int current_position) {
        this.baseItemClickListener = baseItemClickListener;
        this.current_position = current_position;
        binding.txName.setText(orderBasicData.getMember().getName());
        binding.txBdtAmount.setText(context.getString(R.string.bdt) + " " + orderBasicData.getTotalamount());
        binding.txTotalItem.setText(context.getString(R.string.items) + " " + orderBasicData.getOrderitem().size());
        switch (orderBasicData.getOrderstatus()) {
            case "0": // Pending
                binding.txStatus.setText(context.getString(R.string.Pending));

                break;
            case "1": // Ordered
                binding.txStatus.setText(context.getString(R.string.Ordered));

                break;
            case "2": // Processing
                binding.txStatus.setText(context.getString(R.string.Processing));

                break;
            case "3": // Ready for Deliver
                binding.txStatus.setText(context.getString(R.string.Deliver));

                break;
            case "4": // On Way
                binding.txStatus.setText(context.getString(R.string.way));

                break;
            case "5": // Delivered
                binding.txStatus.setText(context.getString(R.string.Delivered));
                binding.txStatus.setBackground(context.getResources().getDrawable(R.drawable.dr_rectangle_green));
                break;
            case "6": // Canceled by Customer
                binding.txStatus.setText(context.getString(R.string.returned));

                break;
            case "7": // Canceled by Customer
                binding.txStatus.setText(context.getString(R.string.damaged));

                break;
            case "8": // Canceled by Call Center Agent
                binding.txStatus.setText(context.getString(R.string.completed_job));

                break;
            case "9": // Canceled by Branch Agent
                binding.txStatus.setText(context.getString(R.string.cancel_customer));

                break;
            case "10": // Canceled by Branch Agent
                binding.txStatus.setText(context.getString(R.string.cancel_call_agent));

                break;
            case "11": // Canceled by Branch Agent
                binding.txStatus.setText(context.getString(R.string.cancel_by_agent));

                break;
        }
        binding.txDate.setText(getDate(orderBasicData.getCreatedAt()));
        binding.txTime.setText(getTime(orderBasicData.getCreatedAt()));

        binding.cbOrder.setVisibility(View.VISIBLE);
        binding.cbOrder.setChecked(orderBasicData.isOrderChecked());
        binding.cbOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderBasicData.isOrderChecked()) {
                    orderBasicData.setOrderChecked(false);
                    baseItemClickListener.onClickOfListItem(false, current_position, "unchecked");
                }else{
                    orderBasicData.setOrderChecked(true);
                    baseItemClickListener.onClickOfListItem(true, current_position, "checked");

                }
            }
        });


    }

    //@OnClick(R.id.list_name_agent)
    public void ClickOnList() {
        baseItemClickListener.onClickOfListItem(true, current_position, "AgentJob");
    }

//    @OnClick(R.id.cbOrder)
//    public void onCheckboxClicked() {
//        if (cbOrder.isChecked()) {
//            baseItemClickListener.onClickOfListItem(true, current_position, "checked");
//        }else{
//            baseItemClickListener.onClickOfListItem(true, current_position, "unchecked");
//        }
//    }

    public String getDate(String dateString) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateString);

            SimpleDateFormat fmtOut = new SimpleDateFormat("dd MMM, yyyy");
            return fmtOut.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateString;
        }

    }

    public String getTime(String dateString) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateString);

            SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm a");
            return fmtOut.format(date).toUpperCase();
        } catch (ParseException e) {
            e.printStackTrace();
            return dateString;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.list_name_agent:
                ClickOnList();
                break;
        }
    }
}