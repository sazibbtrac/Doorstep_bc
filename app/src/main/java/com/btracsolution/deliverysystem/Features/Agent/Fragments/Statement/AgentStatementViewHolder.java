package com.btracsolution.deliverysystem.Features.Agent.Fragments.Statement;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.MainViewHolder;
import com.btracsolution.deliverysystem.Model.StatementModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.databinding.AgentAllRiderListBinding;
import com.btracsolution.deliverysystem.databinding.RowDailyStatementGenericBinding;

/*
import butterknife.BindView;
import butterknife.ButterKnife;
*/

/**
 * Created by mahmudul.hasan on 2/7/2018.
 */

public class AgentStatementViewHolder extends MainViewHolder {

    public BaseItemClickListener baseItemClickListener;
    private RowDailyStatementGenericBinding binding;

/*
    @BindView(R.id.tx_date)
    TextView tx_date;

    @BindView(R.id.tx_t_delivery)
    TextView tx_t_delivery;

    @BindView(R.id.tx_t_subTotal)
    TextView tx_t_subTotal;

    @BindView(R.id.tx_t_vat)
    TextView tx_t_vat;

    @BindView(R.id.tx_t_delivery_charge)
    TextView tx_t_delivery_charge;

    @BindView(R.id.tx_t_grand_total)
    TextView tx_t_grand_total;
*/


    int current_position;

    public AgentStatementViewHolder(View itemView) {
        super(itemView);
        binding = RowDailyStatementGenericBinding.bind(itemView);
    }

    public void setDataIntoView(Context context, StatementModel.IndividualData individualData, BaseItemClickListener baseItemClickListener, int current_position) {
        this.baseItemClickListener = baseItemClickListener;
        this.current_position = current_position;
        binding.txDate.setText(individualData.getOrderDate());
        binding.txTDelivery.setText(individualData.getTotalOrder() + "");
        binding.txTSubTotal.setText(context.getString(R.string.tk_sign) + " " + individualData.getSubTotal());
        binding.txTVat.setText(context.getString(R.string.tk_sign) + " " + individualData.getTotalVatamount());
        binding.txTDeliveryCharge.setText(context.getString(R.string.tk_sign) + " " + individualData.getTotalDeliverycharge());
        binding.txTGrandTotal.setText(context.getString(R.string.tk_sign) + " " + individualData.getGrandTotal());


    }


}
