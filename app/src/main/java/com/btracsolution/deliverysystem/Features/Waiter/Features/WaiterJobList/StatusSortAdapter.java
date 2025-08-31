package com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterJobList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Features.Waiter.Features.Order.SubCategoryAdapter;
import com.btracsolution.deliverysystem.Model.MenuFoodListModel;
import com.btracsolution.deliverysystem.R;

import java.util.ArrayList;

public class StatusSortAdapter extends RecyclerView.Adapter<StatusSortAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    ArrayList<String> statusList;
    private Context mContext;
    BaseItemClickListener baseItemClickListener;


    public StatusSortAdapter(Context context, ArrayList<String> statusList,
                             BaseItemClickListener baseItemClickListener) {
        this.baseItemClickListener = baseItemClickListener;
        this.statusList = statusList;
        mContext = context;
    }


    @NonNull
    @Override
    public StatusSortAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_job_list_sort, parent, false);
        return new StatusSortAdapter.ViewHolder(view);
    }

    int index = -1;

    @Override
    public void onBindViewHolder(@NonNull StatusSortAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        switch (statusList.get(position)) {
            case "0": // Pending
                holder.tx_status.setText(mContext.getResources().getString(R.string.Pending));

                break;
            case "1": // Ordered
                holder.tx_status.setText(mContext.getResources().getString(R.string.Ordered));

                break;
            case "2": // Processing
                holder.tx_status.setText(mContext.getResources().getString(R.string.Processing));

                break;
            case "3": // Ready for Deliver
                holder.tx_status.setText(mContext.getResources().getString(R.string.Deliver));

                break;
            case "4": // On Way
                holder.tx_status.setText(mContext.getResources().getString(R.string.way));

                break;
            case "5": // Delivered
                holder.tx_status.setText(mContext.getResources().getString(R.string.Delivered));

                break;
            case "6": // Canceled by Customer
                holder.tx_status.setText(mContext.getResources().getString(R.string.returned));

                break;
            case "7": // Canceled by Customer
                holder.tx_status.setText(mContext.getResources().getString(R.string.damaged));

                break;
            case "8": // Canceled by Call Center Agent
                holder.tx_status.setText(mContext.getResources().getString(R.string.completed_job));

                break;
            case "9": // Canceled by Branch Agent
                holder.tx_status.setText(mContext.getResources().getString(R.string.cancel_customer));

                break;
            case "10": // Canceled by Branch Agent
                holder.tx_status.setText(mContext.getResources().getString(R.string.cancel_call_agent));

                break;
            case "11": // Canceled by Branch Agent
                holder.tx_status.setText(mContext.getResources().getString(R.string.cancel_by_agent));

                break;
            case "12": // Canceled by Branch Agent
                holder.tx_status.setText(mContext.getResources().getString(R.string.inpantry));

                break;
            case "13": // Canceled by Branch Agent
                holder.tx_status.setText(mContext.getResources().getString(R.string.p_ready_to_pickup));

                break;
            case "98": // Unpaid
                holder.tx_status.setText(mContext.getResources().getString(R.string.unpaid));

                break;
            case "99": // paid
                holder.tx_status.setText(mContext.getResources().getString(R.string.paid));

                break;
            case "100": // all
                holder.tx_status.setText(mContext.getResources().getString(R.string.all));

                break;
        }

        //holder.tx_status.setText(statusList.get(position));
        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    index = position;
                        notifyDataSetChanged();
                    int pos= Integer.parseInt(statusList.get(position));
                    baseItemClickListener.onClickOfListItem(true, pos, "sort");
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                // Toast.makeText(mContext, subCategoryList.get(position).getFoodgroupName()+" RL", Toast.LENGTH_SHORT).show();

            }
        });

        if (index == position) {
            holder.tx_status.setBackground(mContext.getResources().getDrawable(R.drawable.dr_rectangle_green));
            holder.tx_status.setPadding(20,5,20,5);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
               // holder.tvName.setTextColor(mContext.getColor(R.color.white));
            }
        } else {
            holder.tx_status.setBackground(mContext.getResources().getDrawable(R.drawable.dr_rectengle));
            holder.tx_status.setPadding(20,5,20,5);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
               // holder.tvName.setTextColor(mContext.getColor(R.color.black));
                // holder.tvName.setTextSize(15);
            }
        }
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

//    public MenuFoodListModel.FoodGroups getOrderDetail(int position) {
//        return subCategoryList.get(position);
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tx_status;
        LinearLayout llMain;

        public ViewHolder(View itemView) {
            super(itemView);
            tx_status = itemView.findViewById(R.id.tx_status);
            llMain = itemView.findViewById(R.id.llMain);
        }
    }

}