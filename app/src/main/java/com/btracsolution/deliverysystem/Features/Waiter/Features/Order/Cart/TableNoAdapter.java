package com.btracsolution.deliverysystem.Features.Waiter.Features.Order.Cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.btracsolution.deliverysystem.Model.DeliveryZoneResponse;
import com.btracsolution.deliverysystem.R;

import java.util.List;

public class TableNoAdapter extends RecyclerView.Adapter<TableNoAdapter.VersionViewHolder> {

    List<DeliveryZoneResponse.TableNo> tableNoList;

    String letter;
    public int mSelectedItem = -1;
    TableNoAdapter.TableNoListener recyclerViewItemClickListener;

    public TableNoAdapter(TableNoAdapter.TableNoListener listener, List<DeliveryZoneResponse.TableNo> deliveryZoneResponses, int mSelectedItem) {
        this.tableNoList = deliveryZoneResponses;
        this.recyclerViewItemClickListener = listener;
        this.mSelectedItem = mSelectedItem;
    }

    @Override
    public TableNoAdapter.VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_deliveryzone_list, viewGroup, false);
        TableNoAdapter.VersionViewHolder viewHolder = new TableNoAdapter.VersionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TableNoAdapter.VersionViewHolder versionViewHolder, int i) {
        final DeliveryZoneResponse.TableNo command = tableNoList.get(i);
        versionViewHolder.textType.setText(command.getTablename());
        versionViewHolder.radioButton.setChecked(i == mSelectedItem);

    }

    @Override
    public int getItemCount() {
        if (tableNoList == null)
            return 0;
        else return tableNoList.size();

    }

    public class VersionViewHolder extends RecyclerView.ViewHolder {

        TextView textType;
        RadioButton radioButton;

        public VersionViewHolder(View itemView) {
            super(itemView);
            textType = (TextView) itemView.findViewById(R.id.textType);
            radioButton = itemView.findViewById(R.id.radioButton);
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedItem = getAdapterPosition();
                    notifyDataSetChanged();
                    recyclerViewItemClickListener.onClickTableNo(mSelectedItem);
                }
            };
            itemView.setOnClickListener(clickListener);
            radioButton.setOnClickListener(clickListener);
        }
    }

    public interface TableNoListener {
        void onClickTableNo(int position);
    }
}