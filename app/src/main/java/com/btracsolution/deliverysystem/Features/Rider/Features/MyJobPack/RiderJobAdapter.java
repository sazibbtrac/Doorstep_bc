package com.btracsolution.deliverysystem.Features.Rider.Features.MyJobPack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.MainViewHolder;
import com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentOrder.AgentJobListViewHolder;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;

import java.util.ArrayList;

/**
 * Created by mahmudul.hasan on 2/7/2018.
 */

public class RiderJobAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_JOB = 1;

    Context context;
    ArrayList<OrderDetailsModel.orderBasicData> orderBasicDatas;
    BaseItemClickListener baseItemClickListener;

    public RiderJobAdapter(Context context, ArrayList<OrderDetailsModel.orderBasicData> orderBasicDatas, BaseItemClickListener baseItemClickListener) {
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
                return new RiderJobViewHolder(LayoutInflater.from(context).inflate(R.layout.row_my_order, parent, false));

            default:
                return new AgentJobListViewHolder(LayoutInflater.from(context).inflate(R.layout.row_my_order, parent, false));

        }

    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case TYPE_JOB:
                RiderJobViewHolder agentJobListViewHolder = (RiderJobViewHolder) holder;
                agentJobListViewHolder.setDataIntoView(context, orderBasicDatas.get(position), baseItemClickListener, position);
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
