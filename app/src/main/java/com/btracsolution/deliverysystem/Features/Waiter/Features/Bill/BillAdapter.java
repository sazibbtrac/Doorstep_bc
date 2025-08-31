package com.btracsolution.deliverysystem.Features.Waiter.Features.Bill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.MainViewHolder;
import com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterJobList.WaiterJobListViewHolder;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;

import java.util.ArrayList;

public class BillAdapter extends RecyclerView.Adapter<MainViewHolder>{
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_JOB = 1;

    Context context;
    ArrayList<OrderDetailsModel.orderBasicData> orderBasicDatas;
    BaseItemClickListener baseItemClickListener;

    public BillAdapter(Context context, ArrayList<OrderDetailsModel.orderBasicData> orderBasicDatas, BaseItemClickListener baseItemClickListener) {
        this.context = context;
        this.orderBasicDatas = orderBasicDatas;
        this.baseItemClickListener = baseItemClickListener;
    }


    @Override
    public int getItemViewType(int position) {
        return TYPE_JOB;
    }


    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_JOB:
                return new WaiterJobListViewHolder(LayoutInflater.from(context).inflate(R.layout.row_agent_food_list, parent, false));

            default:
                return new WaiterJobListViewHolder(LayoutInflater.from(context).inflate(R.layout.row_agent_food_list, parent, false));

        }

    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case TYPE_JOB:
                WaiterJobListViewHolder waiterJobListViewHolder = (WaiterJobListViewHolder) holder;
                waiterJobListViewHolder.setDataIntoView(context, orderBasicDatas.get(position), baseItemClickListener, position);
                break;
            case TYPE_HEADER:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return orderBasicDatas.size();
    }

    public OrderDetailsModel.orderBasicData getItemFromThePosition(int position) {
        return orderBasicDatas.get(position);
    }
}
