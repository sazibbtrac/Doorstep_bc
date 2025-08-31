package com.btracsolution.deliverysystem.Features.Agent.AgentMenuList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.MainViewHolder;
import com.btracsolution.deliverysystem.Model.FoodMenuShowListModel;
import com.btracsolution.deliverysystem.R;

import java.util.ArrayList;

/**
 * Created by mahmudul.hasan on 2/7/2018.
 */

public class AgentMenuDetailsAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_MENU = 1;

    Context context;
    ArrayList<FoodMenuShowListModel> meunuFoodListModel;
    BaseItemClickListener baseItemClickListener;

    public AgentMenuDetailsAdapter(Context context, ArrayList<FoodMenuShowListModel> orderBasicDatas, BaseItemClickListener baseItemClickListener) {
        this.context = context;
        this.meunuFoodListModel = orderBasicDatas;
        this.baseItemClickListener = baseItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {

        if (meunuFoodListModel.get(position).isHeader())
            return TYPE_HEADER;
        else
            return TYPE_MENU;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return new AgentMenuDetailViewHolderHeader(LayoutInflater.from(context).inflate(R.layout.activity_menu_deatils_header, parent, false));
            case TYPE_MENU:
                return new AgentMenuDetailViewHolder(LayoutInflater.from(context).inflate(R.layout.row_menu_agent, parent, false));
            default:
                return new AgentMenuDetailViewHolder(LayoutInflater.from(context).inflate(R.layout.row_menu_agent, parent, false));

        }

    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case TYPE_MENU:
                AgentMenuDetailViewHolder agentJobListViewHolderHeader = (AgentMenuDetailViewHolder) holder;
                agentJobListViewHolderHeader.setDataIntoView(context, meunuFoodListModel.get(position), baseItemClickListener, position);
                break;
            case TYPE_HEADER:
                AgentMenuDetailViewHolderHeader agentJobListViewHolder = (AgentMenuDetailViewHolderHeader) holder;
                agentJobListViewHolder.setDataIntoView(context, meunuFoodListModel.get(position), baseItemClickListener, position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return meunuFoodListModel.size();
    }

    public String getFoodID(int position) {
        return meunuFoodListModel.get(position).getFoodId();
    }

}
