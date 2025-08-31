package com.btracsolution.deliverysystem.Features.Agent.AgentSelectRider;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.btracsolution.deliverysystem.Base.BaseBeautifulDialogListener;
import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.BaseItemClickListenerRiderSelect;
import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.Features.Agent.AgentOrderDetails.AgentOrderDetailActivity;
import com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentRider.AgentAllRiderListAdapter;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.Model.RiderAssignModel;
import com.btracsolution.deliverysystem.Model.RiderListSingleModel;
import com.btracsolution.deliverysystem.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by mahmudul.hasan on 2/18/2018.
 */

public class AgentSelectRidePresenter {
    AgentSelectRider agentSelectRider;
    RiderListSingleModel riderListSingleModel;
    AgentSelectRider context;
    AgentSelectRiderModel agentSelectRiderModel;
    String orderStatus="3";
    BaseBeautifulDialogListener baseBeautifulDialogListener = new BaseBeautifulDialogListener() {
        @Override
        public void onClickOfPositiveButton(boolean isClick, int position, String extra, MaterialDialog dialog, int identifier) {
            if (isClick) {
                dialog.dismiss();

                switch (identifier) {
                    case 104:
                        agentSelectRider.setDataForFinish();
                        break;
                }
            }

        }

        @Override
        public void onClickOfNegativeButton(boolean isClick, int position, String extra, MaterialDialog dialog, int identifier) {
            if (isClick) {
                dialog.dismiss();

                switch (identifier) {
                    case 104:
                        agentSelectRider.setDataForFinish();
                        break;
                }

            }

        }
    };
    BaseItemClickListenerRiderSelect baseItemClickListener = new BaseItemClickListenerRiderSelect() {
        @Override
        public void onClickOfListItem(boolean isClick, RiderListSingleModel.riderInfo riderInfo, String extra) {
            if (isClick) {
                if (riderListSingleModel != null) {
                    switch (extra) {
                        case "call":
                            callAction(riderInfo.getContactno());
                            break;
                        case "location":
                            location(riderInfo.getLat(),
                                    riderInfo.getLng(),
                                    riderInfo.getFullname());
                            break;
                        case "assign":
//                        agentSelectRider.showToast(context.getString(R.string.assign_rider));
                            String checkedOrders= getSelectedItems(context.orderBasicData);

                            new AgentSelectRiderModel(new BaseServerListener() {
                                @Override
                                public void onServerSuccessOrFailure(boolean isSuccess, Object object, String message) {
                                    if (isSuccess) {

                                        RiderAssignModel riderAssignModel = (RiderAssignModel) object;
                                        agentSelectRider.showToast(message);
                                        agentSelectRider.setDataAndFinishProperly(new Gson().toJson(riderAssignModel.getOrderBasicDatas().get(0)));
                                    } else {
                                        RiderAssignModel riderAssignModel = (RiderAssignModel) object;
                                        if (riderAssignModel != null) {
                                            switch (riderAssignModel.getError().getError_code()) {
                                                case "700":
                                                    agentSelectRider.showBeautifulDialog(baseBeautifulDialogListener).setIconDrawable(R.drawable.ic_people_outline_white_36dp).showMessageWithTitle(context.getString(R.string.same_rider),
                                                            riderAssignModel.getError().getError_msg(), 101);
                                                    break;
                                                case "701":
                                                    agentSelectRider.showBeautifulDialog(baseBeautifulDialogListener).setIconDrawable(R.drawable.ic_access_time_white_36dp).showMessageWithTitle(context.getString(R.string.time_expired),
                                                            riderAssignModel.getError().getError_msg(), 102);
                                                    break;
                                                case "702":
                                                    agentSelectRider.showBeautifulDialog(baseBeautifulDialogListener).setIconDrawable(R.drawable.ic_access_time_white_36dp).showMessageWithTitle(context.getString(R.string.wait),
                                                            riderAssignModel.getError().getError_msg(), 103);
                                                    break;
                                                case "703": // Refresh job
                                                    agentSelectRider.showBeautifulDialog(baseBeautifulDialogListener).setIconDrawable(R.drawable.ic_info_outline_white_36dp).showMessageWithTitle(context.getString(R.string.info),
                                                            riderAssignModel.getError().getError_msg(), 104);
                                                    break;
                                                default:
                                                    agentSelectRider.showBeautifulDialog(baseBeautifulDialogListener).setIconDrawable(R.drawable.ic_info_outline_white_36dp).showMessageWithTitle(context.getString(R.string.info),
                                                            riderAssignModel.getError().getError_msg(), 105);
                                                    break;
                                            }
                                        } else
                                            agentSelectRider.showAlert(message);


                                    }
                                }
                            }, agentSelectRider).assignRider(riderInfo.getRiderid(), agentSelectRider.order_id, checkedOrders,orderStatus);
                            break;
                    }
                }

            }
        }
    };
    BaseServerListener baseServerListener = new BaseServerListener() {
        @Override
        public void onServerSuccessOrFailure(boolean isSuccess, Object object, String message) {
            if (isSuccess) {
                riderListSingleModel = (RiderListSingleModel) object;
                setDataIntoRecycleView(riderListSingleModel);
            }
        }
    };

    public String getSelectedItems(OrderDetailsModel.orderBasicData orderBasicData){
        ArrayList<Integer> checkedOrders = new ArrayList<>();
        orderStatus="3";
        for (OrderDetailsModel.OrderItem orderItem: orderBasicData.getOrderitem()) {
            if (orderItem.isChecked() && orderItem.getItemstatus().contentEquals("0")){
                checkedOrders.add(orderItem.getOrderitemid());
            }
            else if (!orderItem.isChecked()){
                orderStatus="13";
            }
            else if(orderItem.isChecked() && orderItem.getItemstatus().contentEquals("1")){

            }
        }
        return new Gson().toJson(checkedOrders);
    }
    public AgentSelectRidePresenter(AgentSelectRider agentSelectRider, AgentSelectRider context) {
        this.agentSelectRider = agentSelectRider;
        this.context = context;
        agentSelectRiderModel = new AgentSelectRiderModel(baseServerListener, context);
    }

    public void location(String lat, String lon, String riderName) {
        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=" + lat + "," + lon + "", 12f, 2f, riderName + " is here");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            try {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                context.startActivity(unrestrictedIntent);
            } catch (ActivityNotFoundException innerEx) {
                Toast.makeText(context, "Please install a maps application", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void callAction(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));

        agentSelectRider.startActivity(intent);
    }

    public void getRiderData() {
        agentSelectRiderModel.getRiderList();
    }

    public void setDataIntoRecycleView(RiderListSingleModel riderListSingleModel) {
//        System.out.println("Riderlist "+riderListSingleModel.getRiderListInfo().size());
//        if (riderListSingleModel != null) {
//            AgentRiderListAdapter agentRiderListAdapter = new AgentRiderListAdapter(context, riderListSingleModel.getRiderListInfo(), baseItemClickListener);
//            agentSelectRider.setadapterIntoRecycleView(agentRiderListAdapter);
//        }


        ArrayList<RiderListSingleModel.riderInfo> filteredList = new ArrayList<>();

// Ensure riderListSingleModel is not null
        if (riderListSingleModel != null && riderListSingleModel.getRiderListInfo() != null) {
            for (RiderListSingleModel.riderInfo rider : riderListSingleModel.getRiderListInfo()) {
                if ("1".equals(rider.getLoginstatus())) {  // Avoids NullPointerException
                    filteredList.add(rider);
                }
            }
        }

// Set adapter only once with the filtered list
        AgentRiderListAdapter agentRiderListAdapter = new AgentRiderListAdapter(context, filteredList, baseItemClickListener);
        agentSelectRider.setadapterIntoRecycleView(agentRiderListAdapter);



    }
}
