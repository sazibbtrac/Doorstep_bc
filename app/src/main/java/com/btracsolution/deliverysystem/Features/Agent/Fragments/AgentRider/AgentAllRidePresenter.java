package com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentRider;

import static android.content.Context.TELEPHONY_SERVICE;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.btracsolution.deliverysystem.Base.BaseBeautifulDialogListener;
import com.btracsolution.deliverysystem.Base.BaseItemClickListenerRiderSelect;
import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.Features.Agent.AgentSelectRider.AgentRiderListAdapter;
import com.btracsolution.deliverysystem.Features.Agent.AgentSelectRider.AgentSelectRider;
import com.btracsolution.deliverysystem.Features.Agent.AgentSelectRider.AgentSelectRiderModel;
import com.btracsolution.deliverysystem.Model.RiderAssignModel;
import com.btracsolution.deliverysystem.Model.RiderListSingleModel;
import com.btracsolution.deliverysystem.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AgentAllRidePresenter {
    Activity activity;
    RiderListSingleModel riderListSingleModel;
    AllRider allRider;
    AgentAllRiderModel agentSelectRiderModel;
    BaseBeautifulDialogListener baseBeautifulDialogListener = new BaseBeautifulDialogListener() {
        @Override
        public void onClickOfPositiveButton(boolean isClick, int position, String extra, MaterialDialog dialog, int identifier) {
            if (isClick) {
                dialog.dismiss();

            }

        }

        @Override
        public void onClickOfNegativeButton(boolean isClick, int position, String extra, MaterialDialog dialog, int identifier) {
            if (isClick) {
                dialog.dismiss();


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

    public AgentAllRidePresenter(Activity activity, AllRider allRider) {
        this.activity = activity;
        this.allRider = allRider;
        agentSelectRiderModel = new AgentAllRiderModel(baseServerListener, activity);
    }

    public void location(String lat, String lon, String riderName) {
        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=" + lat + "," + lon + "", 12f, 2f, riderName + " is here");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        try {
            activity.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            try {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                activity.startActivity(unrestrictedIntent);
            } catch (ActivityNotFoundException innerEx) {
                Toast.makeText(this.activity, "Please install a maps application", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean isTelephonyEnabled() {
        TelephonyManager tm = (TelephonyManager) activity.getSystemService(TELEPHONY_SERVICE);
        return tm != null && tm.getSimState() == TelephonyManager.SIM_STATE_READY;
    }
    public void callAction(String phoneNumber) {
        try {
            if (isTelephonyEnabled()) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));

                this.activity.startActivity(intent);
            } else {
                Toast.makeText(this.activity, "Call not possible", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getRiderData() {
        agentSelectRiderModel.getRiderList();
    }

    public void setDataIntoRecycleView(RiderListSingleModel riderListSingleModel) {


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
        AgentAllRiderListAdapter agentRiderListAdapter = new AgentAllRiderListAdapter(activity, filteredList, baseItemClickListener);
        allRider.setadapterIntoRecycleView(agentRiderListAdapter);


//        if (riderListSingleModel != null) {
//            AgentAllRiderListAdapter agentRiderListAdapter = new AgentAllRiderListAdapter(activity, riderListSingleModel.getRiderListInfo(), baseItemClickListener);
//            allRider.setadapterIntoRecycleView(agentRiderListAdapter);
//        }

    }


}
