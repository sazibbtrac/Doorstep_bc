package com.btracsolution.deliverysystem.Features.Rider.Features.MyJobPack;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.MainViewHolder;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.databinding.RowAgentFoodListBinding;
import com.btracsolution.deliverysystem.databinding.RowMyOrderBinding;


/**
 * Created by mahmudul.hasan on 2/26/2018.
 */

public class RiderJobViewHolder extends MainViewHolder implements View.OnClickListener {
    public BaseItemClickListener baseItemClickListener;
    private RowMyOrderBinding binding;
/*
    @BindView(R.id.tx_customer_name)
    TextView tx_customer_name;

    @BindView(R.id.tx_customer_address)
    TextView tx_customer_address;

    @BindView(R.id.tx_phone_number)
    TextView tx_phone_number;

    @BindView(R.id.tx_detail_of_job)
    TextView tx_detail_of_job;


    @BindView(R.id.lin_call)
    LinearLayout lin_call;

    @BindView(R.id.lin_location)
    LinearLayout lin_location;*/


    int current_position;

    public RiderJobViewHolder(View itemView) {
        super(itemView);
        binding = RowMyOrderBinding.bind(itemView);
        binding.txDetailOfJob.setOnClickListener(this);
        binding.linCall.setOnClickListener(this);
        binding.linLocation.setOnClickListener(this);
    }

    public void setDataIntoView(Context context, OrderDetailsModel.orderBasicData orderBasicData, BaseItemClickListener baseItemClickListener, int current_position) {
        this.baseItemClickListener = baseItemClickListener;
        this.current_position = current_position;
        if (orderBasicData.getWaiter()!=null) {
            binding.txCustomerName.setText(orderBasicData.getWaiter().getFullName());
            binding.txCustomerAddress.setText(orderBasicData.getWaiter().getContactno());
            binding.txPhoneNumber.setText(orderBasicData.getWaiter().getContactno());
        }

    }


  //  @OnClick(R.id.tx_detail_of_job)
    public void onClickOfDetails() {
        baseItemClickListener.onClickOfListItem(true, current_position, "details");

    }


   // @OnClick(R.id.lin_call)
    public void onClickOfCall() {
        baseItemClickListener.onClickOfListItem(true, current_position, "call");


    }

 //   @OnClick(R.id.lin_location)
    public void onLocation() {
        baseItemClickListener.onClickOfListItem(true, current_position, "location");


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lin_location:
                onLocation();
                break;

            case R.id.lin_call:
                onClickOfCall();
                break;

            case R.id.tx_detail_of_job:
                onClickOfDetails();
                break;

        }
    }
}
