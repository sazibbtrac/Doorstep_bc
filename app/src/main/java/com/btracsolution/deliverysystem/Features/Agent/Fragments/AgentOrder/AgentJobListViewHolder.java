package com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentOrder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.MainViewHolder;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.databinding.RowAgentFoodListBinding;
import com.btracsolution.deliverysystem.databinding.RowJobCategoryHeaderBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by mahmudul.hasan on 2/7/2018.
 */

public class AgentJobListViewHolder extends MainViewHolder implements View.OnClickListener {

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
    @BindView(R.id.tvPaymentStatus)
    TextView tvPaymentStatus;
    @BindView(R.id.list_name_agent)
    LinearLayout list_name_agent;*/
    int current_position;

    public AgentJobListViewHolder(View itemView) {
        super(itemView);
        binding = RowAgentFoodListBinding.bind(itemView);
        binding.listNameAgent.setOnClickListener(this);
    }

    public void setDataIntoView(Context context, OrderDetailsModel.orderBasicData orderBasicData, BaseItemClickListener baseItemClickListener, int current_position) {
        this.baseItemClickListener = baseItemClickListener;
        this.current_position = current_position;
        binding.txName.setText(orderBasicData.getWaiter().getFullName());
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
                binding.tvPaymentStatus.setVisibility(View.VISIBLE);
                if (orderBasicData.getPaymentstatus().contentEquals("0")){
                    binding.tvPaymentStatus.setText(context.getResources().getString(R.string.unpaid));
                    binding.tvPaymentStatus.setBackground(context.getResources().getDrawable(R.drawable.dr_rectangle_red));
                }else{
                    binding.tvPaymentStatus.setText(context.getResources().getString(R.string.paid));
                    binding.tvPaymentStatus.setBackground(context.getResources().getDrawable(R.drawable.dr_rectangle_green));
                }

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
                case "12": // Canceled by Branch Agent
                    binding.txStatus.setText(context.getString(R.string.inpantry));

                break;
            case "13": // Canceled by Branch Agent
                binding.txStatus.setText(context.getString(R.string.p_ready_to_pickup));

                break;
            case "14": // Canceled by Branch Agent
                binding.txStatus.setText(context.getString(R.string.p_on_way));

                break;
            case "15": // Canceled by Branch Agent
                binding.txStatus.setText(context.getString(R.string.p_inpantry));

                break;
            case "16": // Canceled by Branch Agent
                binding.txStatus.setText(context.getString(R.string.p_delivered));
                break;
        }
        binding.txDate.setText(getDate(orderBasicData.getCreatedAt()));
        binding.txTime.setText(getTime(orderBasicData.getCreatedAt()));


    }

    //@OnClick(R.id.list_name_agent)
    public void ClickOnList() {
        baseItemClickListener.onClickOfListItem(true, current_position, "AgentJob");
    }

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
