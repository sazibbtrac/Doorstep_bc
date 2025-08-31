package com.btracsolution.deliverysystem.Features.Waiter.Features.Bill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.MainViewHolder;
import com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterJobList.WaiterJobListViewHolder;
import com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterJobList.WaiterJobListViewHolderWithHeader;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;

import java.util.ArrayList;

public class BillAdapterWithHeader extends RecyclerView.Adapter<MainViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_JOB = 1;

    Context context;
    ArrayList<OrderDetailsModel.orderBasicData> orderBasicDatas;
    BaseItemClickListener baseItemClickListener;
    String isSelectedAll = "init";

    public BillAdapterWithHeader(Context context, ArrayList<OrderDetailsModel.orderBasicData> orderBasicDatas, BaseItemClickListener baseItemClickListener) {
        this.context = context;
        this.orderBasicDatas = orderBasicDatas;
        this.baseItemClickListener = baseItemClickListener;
    }


    @Override
    public int getItemViewType(int position) {
        if (orderBasicDatas.get(position).isHeader()) {
            return TYPE_HEADER;

        } else
            return TYPE_JOB;
    }


    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_JOB:
                return new BillViewHolder(LayoutInflater.from(context).inflate(R.layout.row_agent_food_list, parent, false));

            default:
                return new BillViewHolderWithHeader(LayoutInflater.from(context).inflate(R.layout.row_job_category_header, parent, false));

        }

    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case TYPE_JOB:
                BillViewHolder billViewHolder = (BillViewHolder) holder;
                billViewHolder.setDataIntoView(context, orderBasicDatas.get(position), baseItemClickListener, position);
                break;
            case TYPE_HEADER:
                BillViewHolderWithHeader billViewHolderWithHeader = (BillViewHolderWithHeader) holder;
                billViewHolderWithHeader.setDataIntoView(context, orderBasicDatas.get(position), baseItemClickListener, position);
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

    public void selectAll() {
        isSelectedAll = "check";
        notifyDataSetChanged();
        for(int i=0;i<orderBasicDatas.size();i++){
            orderBasicDatas.get(i).setOrderChecked(true);
        }
    }

    public void unselectall() {
        isSelectedAll = "uncheck";
        notifyDataSetChanged();
        for(int i=0;i<orderBasicDatas.size();i++){
            orderBasicDatas.get(i).setOrderChecked(false);
        }
    }


}