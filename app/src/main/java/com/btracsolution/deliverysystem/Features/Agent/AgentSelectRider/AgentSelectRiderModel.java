package com.btracsolution.deliverysystem.Features.Agent.AgentSelectRider;

import android.content.Context;

import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Model.RiderAssignModel;
import com.btracsolution.deliverysystem.Model.RiderListSingleModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.Connectivity;
import com.btracsolution.deliverysystem.Utility.ProgressDialogOwn;
import com.btracsolution.deliverysystem.Utility.SharedData;
import com.btracsolution.deliverysystem.network.api.RetroHubApiInterface;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mahmudul.hasan on 2/18/2018.
 */

public class AgentSelectRiderModel {

    @Inject
    RetroHubApiInterface retroHubApiInterface;
    @Inject
    SharedData sharedData;
    @Inject
    ProgressDialogOwn progressDialogOwn;

    BaseServerListener baseServerListener;
    Context context;

    public AgentSelectRiderModel(BaseServerListener baseServerListener, Context context) {
        ((DeliveryApp) context.getApplicationContext()).getDoNetworkComponent().inject(this);

        this.baseServerListener = baseServerListener;
        this.context = context;
    }

    public void getRiderList() {
        if (Connectivity.isConnected(context)) {
            if (retroHubApiInterface != null) {
                if (sharedData.getMyData() != null) {

                    progressDialogOwn.showDialog(context, context.getString(R.string.loading));
                    Call<RiderListSingleModel> orderDetailsModelCall = retroHubApiInterface.getRiderList(
                            sharedData.getMyData().getData().getApiToken(),
                            sharedData.getMyData().getData().getUserId(),
                            String.valueOf(sharedData.getMyData().getData().getKitchenInfo().get(0).getKitchenId())
                            // sharedData.getMyData().getData().getBranchInfo().get(0).getBranchId()
                    );
                    orderDetailsModelCall.enqueue(new Callback<RiderListSingleModel>() {
                        @Override
                        public void onResponse(Call<RiderListSingleModel> call, Response<RiderListSingleModel> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getStatus().contentEquals("success")) {
                                    baseServerListener.onServerSuccessOrFailure(true, response.body(), "success");
                                } else
                                    baseServerListener.onServerSuccessOrFailure(false, null, response.body().getError().getError_msg());

                            } else
                                baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                            progressDialogOwn.hideDialog();

                        }

                        @Override
                        public void onFailure(Call<RiderListSingleModel> call, Throwable t) {
                            if (t != null)
                                baseServerListener.onServerSuccessOrFailure(false, null, t.getMessage());
                            else
                                baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                            progressDialogOwn.hideDialog();

                        }
                    });
                }
            }
        }else {
            progressDialogOwn.showInfoAlertDialog(context, context.getResources().getString(R.string.no_internet_connection));
        }
    }

    public void assignRider(String rider_id, String orderID, String checkedOrders,String orderStatus) {
        if (Connectivity.isConnected(context)) {
            if (retroHubApiInterface != null) {
                if (sharedData.getMyData() != null) {

                    System.out.println("sdsdhsdfsd "+sharedData.getMyData().getData().getApiToken()+": "+ sharedData.getMyData().getData().getUserId()
                    +": "+orderID+": "+rider_id+": "+checkedOrders+": "+orderStatus);

                    progressDialogOwn.showDialog(context, context.getString(R.string.loading));
                    Call<RiderAssignModel> orderDetailsModelCall = retroHubApiInterface.assignRider(
                            sharedData.getMyData().getData().getApiToken(),
                            sharedData.getMyData().getData().getUserId(),
                            orderID,
                            rider_id,
                            checkedOrders,
                            orderStatus
                    );
                    orderDetailsModelCall.enqueue(new Callback<RiderAssignModel>() {
                        @Override
                        public void onResponse(Call<RiderAssignModel> call, Response<RiderAssignModel> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getStatus().contentEquals("success")) {
                                    if(response.body().getOrderBasicDatas().isEmpty()){
                                        progressDialogOwn.showToast(context,response.body().getMessage());
                                    }
                                    else {
                                        baseServerListener.onServerSuccessOrFailure(true, response.body(), response.body().getMessage());

                                    }
                                } else
                                    baseServerListener.onServerSuccessOrFailure(false, response.body(), response.body().getError().getError_msg());

                            } else
                                baseServerListener.onServerSuccessOrFailure(false, response.body(), context.getString(R.string.error_into_server));
                            progressDialogOwn.hideDialog();

                        }

                        @Override
                        public void onFailure(Call<RiderAssignModel> call, Throwable t) {
                            if (t != null)
                                baseServerListener.onServerSuccessOrFailure(false, null, t.getMessage());
                            else
                                baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                            progressDialogOwn.hideDialog();

                        }
                    });
                }
            }
        }
        else {
            progressDialogOwn.showInfoAlertDialog(context, context.getResources().getString(R.string.no_internet_connection));
        }
    }


}
