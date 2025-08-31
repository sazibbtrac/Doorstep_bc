package com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterJobList;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.MainViewHolder;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.databinding.RowJobCategoryHeaderBinding;
/*
import butterknife.BindView;
import butterknife.ButterKnife;*/

public class WaiterJobListViewHolderWithHeader extends MainViewHolder {

    public BaseItemClickListener baseItemClickListener;
    private RowJobCategoryHeaderBinding binding;

/*    @BindView(R.id.tx_header)
    TextView tx_header;
    @BindView(R.id.tx_total_count)
    TextView tx_total_count;*/
    private int current_position;


    public WaiterJobListViewHolderWithHeader(View itemView) {
        super(itemView);
        //ButterKnife.bind(this, itemView);
        binding = RowJobCategoryHeaderBinding.bind(itemView);
    }

    public void setDataIntoView(Context context, OrderDetailsModel.orderBasicData agentJobListHeaderModel, BaseItemClickListener baseItemClickListener, int current_position) {
        this.baseItemClickListener = baseItemClickListener;
        this.current_position = current_position;
        binding.txHeader.setText(agentJobListHeaderModel.getTypeOfHeader());
        binding.txTotalCount.setText(agentJobListHeaderModel.getTotalOrder());


    }


}
