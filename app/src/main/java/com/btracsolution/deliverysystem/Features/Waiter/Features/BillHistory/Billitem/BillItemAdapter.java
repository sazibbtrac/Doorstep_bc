package com.btracsolution.deliverysystem.Features.Waiter.Features.BillHistory.Billitem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.MainViewHolder;
import com.btracsolution.deliverysystem.Features.Waiter.Features.BillHistory.BillHistoryActivity;
import com.btracsolution.deliverysystem.Features.Waiter.Features.BillHistory.BillHistoryAdapter;
import com.btracsolution.deliverysystem.Model.BillHistoryResponse;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.databinding.RowAgentFoodListBinding;
import com.btracsolution.deliverysystem.databinding.RowBillHistoryBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class BillItemAdapter extends RecyclerView.Adapter<MainViewHolder>{


    Context context;
    BillHistoryResponse.Datum billData;
    BaseItemClickListener baseItemClickListener;


    public BillItemAdapter(Context context, BillHistoryResponse.Datum data, BaseItemClickListener baseItemClickListener) {
        this.context=context;
        this.billData=data;
        this.baseItemClickListener=baseItemClickListener;
    }


    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new BillItemHolder(LayoutInflater.from(context).inflate(R.layout.row_agent_food_list, parent, false));

    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        BillItemHolder billHistoryViewHolder = (BillItemHolder) holder;
        billHistoryViewHolder.setDataIntoView(context, billData.getBillitem().get(position), baseItemClickListener, position);
    }

    @Override
    public int getItemCount() {
        return billData.getBillitem().size();
    }



    public class BillItemHolder  extends MainViewHolder implements View.OnClickListener{

        public BaseItemClickListener baseItemClickListener;
        //row_agent_food_list
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
        LinearLayout list_name_agent;*/
        int current_position;

        public BillItemHolder(View itemView) {
            super(itemView);
            binding = RowAgentFoodListBinding.bind(itemView);
            binding.listNameAgent.setOnClickListener(this);
        }

        public void setDataIntoView(Context context, BillHistoryResponse.Billitem billitem, BaseItemClickListener baseItemClickListener, int current_position) {
            this.baseItemClickListener = baseItemClickListener;
            this.current_position = current_position;
            binding.txName.setText(""+billitem.getBillItemId());
            binding.txBdtAmount.setText(context.getString(R.string.bdt) + " " + billitem.getBillItemId());
            binding.txStatus.setText(context.getString(R.string.Delivered));
            binding.txStatus.setBackground(context.getResources().getDrawable(R.drawable.dr_rectangle_green));
            // tx_total_item.setText(context.getString(R.string.items) + " " + billData.getOrderitem().size());
//            switch (billData.getOrderstatus()) {
//                case "0": // Pending
//                    tx_status.setText(context.getString(R.string.Pending));
//
//                    break;
//                case "1": // Ordered
//                    tx_status.setText(context.getString(R.string.Ordered));
//
//                    break;
//                case "2": // Processing
//                    tx_status.setText(context.getString(R.string.Processing));
//
//                    break;
//                case "3": // Ready for Deliver
//                    tx_status.setText(context.getString(R.string.Deliver));
//
//                    break;
//                case "4": // On Way
//                    tx_status.setText(context.getString(R.string.way));
//
//                    break;
//                case "5": // Delivered
//                    tx_status.setText(context.getString(R.string.Delivered));
//                    tx_status.setBackground(context.getResources().getDrawable(R.drawable.dr_rectangle_green));
//                    break;
//                case "6": // Canceled by Customer
//                    tx_status.setText(context.getString(R.string.returned));
//
//                    break;
//                case "7": // Canceled by Customer
//                    tx_status.setText(context.getString(R.string.damaged));
//
//                    break;
//                case "8": // Canceled by Call Center Agent
//                    tx_status.setText(context.getString(R.string.completed_job));
//
//                    break;
//                case "9": // Canceled by Branch Agent
//                    tx_status.setText(context.getString(R.string.cancel_customer));
//
//                    break;
//                case "10": // Canceled by Branch Agent
//                    tx_status.setText(context.getString(R.string.cancel_call_agent));
//
//                    break;
//                case "11": // Canceled by Branch Agent
//                    tx_status.setText(context.getString(R.string.cancel_by_agent));
//
//                    break;
//            }
            binding.txDate.setText(getDate(billitem.getCreatedAt()));
            binding.txTime.setText(getTime(billitem.getCreatedAt()));

           // tx_status.setVisibility(View.GONE);
        }

      //  @OnClick(R.id.list_name_agent)
        public void ClickOnList() {
            baseItemClickListener.onClickOfListItem(true, current_position, "billhistory");
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
}
