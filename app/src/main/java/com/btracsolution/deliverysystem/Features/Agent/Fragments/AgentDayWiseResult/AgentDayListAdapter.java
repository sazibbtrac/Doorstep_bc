package com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentDayWiseResult;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.MainViewHolder;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;

import java.util.ArrayList;

/**
 * Created by mahmudul.hasan on 2/7/2018.
 */

public class AgentDayListAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_JOB = 1;

    Context context;
    ArrayList<OrderDetailsModel.orderBasicData> orderBasicDatas;
    BaseItemClickListener baseItemClickListener;

    public AgentDayListAdapter(Context context, ArrayList<OrderDetailsModel.orderBasicData> orderBasicDatas, BaseItemClickListener baseItemClickListener) {
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
                return new AgentDayViewHolder(LayoutInflater.from(context).inflate(R.layout.row_agent_my_day_item, parent, false));

            default:
                return new AgentDayViewHolder(LayoutInflater.from(context).inflate(R.layout.row_agent_my_day_item, parent, false));

        }

    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case TYPE_JOB:
                AgentDayViewHolder AgentDayViewHolder = (AgentDayViewHolder) holder;
                AgentDayViewHolder.setDataIntoView(context, orderBasicDatas.get(position), baseItemClickListener, position);
                break;
            case TYPE_HEADER:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return orderBasicDatas.size();
    }
}
