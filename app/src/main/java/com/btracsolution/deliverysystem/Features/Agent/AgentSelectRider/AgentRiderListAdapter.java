package com.btracsolution.deliverysystem.Features.Agent.AgentSelectRider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.BaseItemClickListenerRiderSelect;
import com.btracsolution.deliverysystem.Base.MainViewHolder;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.Model.RiderListSingleModel;
import com.btracsolution.deliverysystem.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahmudul.hasan on 2/7/2018.
 */

public class AgentRiderListAdapter extends RecyclerView.Adapter<MainViewHolder> implements Filterable {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_MENU = 1;

    Context context;
    ArrayList<RiderListSingleModel.riderInfo> meunuFoodListModel;
    BaseItemClickListenerRiderSelect baseItemClickListener;
    private List<RiderListSingleModel.riderInfo> meunuFoodListModelSearch;

    public AgentRiderListAdapter(Context context, ArrayList<RiderListSingleModel.riderInfo> orderBasicDatas, BaseItemClickListenerRiderSelect baseItemClickListener) {
        this.context = context;
        this.meunuFoodListModel = orderBasicDatas;
        this.baseItemClickListener = baseItemClickListener;
        this.meunuFoodListModelSearch = orderBasicDatas;
    }


    @Override
    public int getItemViewType(int position) {
        return TYPE_MENU;
    }


    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return new AgentRiderViewHolder(LayoutInflater.from(context).inflate(R.layout.row_agent_rider_list, parent, false));
            case TYPE_MENU:
                return new AgentRiderViewHolder(LayoutInflater.from(context).inflate(R.layout.row_agent_rider_list, parent, false));
            default:
                return new AgentRiderViewHolder(LayoutInflater.from(context).inflate(R.layout.row_agent_rider_list, parent, false));

        }

    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {


        switch (holder.getItemViewType()) {
            case TYPE_MENU:
                AgentRiderViewHolder agentJobListViewHolderHeader = (AgentRiderViewHolder) holder;
                System.out.println("position " + holder.getAdapterPosition() + " " + position + " " + meunuFoodListModelSearch.get(holder.getAdapterPosition()).getFullname());
                agentJobListViewHolderHeader.setDataIntoView(context, meunuFoodListModelSearch.get(holder.getAdapterPosition()), baseItemClickListener);
                break;

        }
    }

    @Override
    public int getItemCount() {
        return meunuFoodListModelSearch.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    meunuFoodListModelSearch = meunuFoodListModel;
                } else {
                    List<RiderListSingleModel.riderInfo> filteredList = new ArrayList<>();
                    for (RiderListSingleModel.riderInfo row : meunuFoodListModel) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getFullname().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    meunuFoodListModelSearch = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = meunuFoodListModelSearch;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                meunuFoodListModelSearch = (ArrayList<RiderListSingleModel.riderInfo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
