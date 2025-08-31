package com.btracsolution.deliverysystem.Features.Agent.AgentMenuList;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.MainViewHolder;
import com.btracsolution.deliverysystem.Model.FoodMenuShowListModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.databinding.ActivityMenuDeatilsHeaderBinding;
import com.btracsolution.deliverysystem.databinding.RowMenuAgentBinding;

/*
import butterknife.BindView;
import butterknife.ButterKnife;
*/

/**
 * Created by mahmudul.hasan on 2/7/2018.
 */

public class AgentMenuDetailViewHolderHeader extends MainViewHolder {
    private ActivityMenuDeatilsHeaderBinding binding;

    public BaseItemClickListener baseItemClickListener;

/*    @BindView(R.id.tx_header_title)
    TextView tx_header_title;*/


    int current_position;

    public AgentMenuDetailViewHolderHeader(View itemView) {
        super(itemView);
        binding = ActivityMenuDeatilsHeaderBinding.bind(itemView);
       // binding.swMenuOff.setOnClickListener(this);
    }

    public void setDataIntoView(Context context, FoodMenuShowListModel foodName, BaseItemClickListener baseItemClickListener, int current_position) {
        this.baseItemClickListener = baseItemClickListener;
        this.current_position = current_position;
        binding.txHeaderTitle.setText(foodName.getHeaderTitle());


    }


}
