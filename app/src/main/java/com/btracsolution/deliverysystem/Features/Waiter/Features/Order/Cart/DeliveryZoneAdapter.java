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

public class DeliveryZoneAdapter extends RecyclerView.Adapter<DeliveryZoneAdapter.VersionViewHolder> {

    List<DeliveryZoneResponse.Zone> deliveryZoneResponses;

    String letter;
    public int mSelectedItem = -1;
    DeliveryZoneListener recyclerViewItemClickListener;

    public DeliveryZoneAdapter(DeliveryZoneListener listener, List<DeliveryZoneResponse.Zone> deliveryZoneResponses, int mSelectedItem) {
        this.deliveryZoneResponses = deliveryZoneResponses;
        this.recyclerViewItemClickListener = listener;
        this.mSelectedItem = mSelectedItem;
    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_deliveryzone_list, viewGroup, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final VersionViewHolder versionViewHolder, int i) {
        final DeliveryZoneResponse.Zone command = deliveryZoneResponses.get(i);
        versionViewHolder.textType.setText(command.getZonename());
        versionViewHolder.radioButton.setChecked(i == mSelectedItem);

    }

    @Override
    public int getItemCount() {
        if (deliveryZoneResponses == null)
            return 0;
        else return deliveryZoneResponses.size();

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
                    recyclerViewItemClickListener.onClickDeliveryZone(mSelectedItem);
                }
            };
            itemView.setOnClickListener(clickListener);
            radioButton.setOnClickListener(clickListener);
        }
    }

    public interface DeliveryZoneListener {
        void onClickDeliveryZone(int position);
    }
}