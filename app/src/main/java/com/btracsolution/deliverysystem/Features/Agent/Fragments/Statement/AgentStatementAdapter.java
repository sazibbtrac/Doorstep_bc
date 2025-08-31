package com.btracsolution.deliverysystem.Features.Agent.Fragments.Statement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.MainViewHolder;
import com.btracsolution.deliverysystem.Model.StatementModel;
import com.btracsolution.deliverysystem.R;

import java.util.ArrayList;

/**
 * Created by mahmudul.hasan on 2/7/2018.
 */

public class AgentStatementAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_REPORT = 1;

    Context context;
    ArrayList<StatementModel.IndividualData> statementOrderDetails;
    BaseItemClickListener baseItemClickListener;

    public AgentStatementAdapter(Context context, ArrayList<StatementModel.IndividualData> orderBasicDatas, BaseItemClickListener baseItemClickListener) {
        this.context = context;
        this.statementOrderDetails = orderBasicDatas;
        this.baseItemClickListener = baseItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {

       /* if (statementOrderDetails.get(position).isHeader())
            return TYPE_HEADER;
        else*/
        return TYPE_REPORT;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return new AgentStatementViewHolder(LayoutInflater.from(context).inflate(R.layout.row_daily_statement_generic, parent, false));
            case TYPE_REPORT:
                return new AgentStatementViewHolder(LayoutInflater.from(context).inflate(R.layout.row_daily_statement_generic, parent, false));
            default:
                return new AgentStatementViewHolder(LayoutInflater.from(context).inflate(R.layout.row_daily_statement_generic, parent, false));

        }

    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case TYPE_REPORT:
                AgentStatementViewHolder agentJobListViewHolderHeader = (AgentStatementViewHolder) holder;
                agentJobListViewHolderHeader.setDataIntoView(context, statementOrderDetails.get(position), baseItemClickListener, position);
                break;
            case TYPE_HEADER:
                AgentStatementViewHolder agentJobListViewHolder = (AgentStatementViewHolder) holder;
                agentJobListViewHolder.setDataIntoView(context, statementOrderDetails.get(position), baseItemClickListener, position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return statementOrderDetails.size();
    }


}
