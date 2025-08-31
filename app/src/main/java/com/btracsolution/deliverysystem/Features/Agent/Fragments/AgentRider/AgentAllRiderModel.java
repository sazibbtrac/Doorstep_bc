package com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentRider;

import android.app.Activity;
import android.content.Context;

import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Model.RiderAssignModel;
import com.btracsolution.deliverysystem.Model.RiderListSingleModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.LogoutUtility;
import com.btracsolution.deliverysystem.Utility.ProgressDialogOwn;
import com.btracsolution.deliverysystem.Utility.SharedData;
import com.btracsolution.deliverysystem.network.api.RetroHubApiInterface;

import java.util.Calendar;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgentAllRiderModel {
    @Inject
    RetroHubApiInterface retroHubApiInterface;
    @Inject
    SharedData sharedData;
    @Inject
    ProgressDialogOwn progressDialogOwn;

    BaseServerListener baseServerListener;
    Context context;

    public AgentAllRiderModel(BaseServerListener baseServerListener, Context context) {
        ((DeliveryApp) context.getApplicationContext()).getDoNetworkComponent().inject(this);

        this.baseServerListener = baseServerListener;
        this.context = context;
    }

    public void getRiderList() {
        if (retroHubApiInterface != null) {
            if (sharedData.getMyData() != null) {

                progressDialogOwn.showDialog(context, context.getString(R.string.loading));
                System.out.println("ggdhsdjksf: "+ sharedData.getMyData().getData().getApiToken()+": "+sharedData.getMyData().getData().getUserId()+
                        ": "+                        String.valueOf(sharedData.getMyData().getData().getKitchenInfo().get(0).getKitchenId())
                );
                Call<RiderListSingleModel> orderDetailsModelCall = retroHubApiInterface.getRiderList(
                        sharedData.getMyData().getData().getApiToken(),
                        sharedData.getMyData().getData().getUserId(),
                        String.valueOf(sharedData.getMyData().getData().getKitchenInfo().get(0).getKitchenId())

                );
                orderDetailsModelCall.enqueue(new Callback<RiderListSingleModel>() {
                    @Override
                    public void onResponse(Call<RiderListSingleModel> call, Response<RiderListSingleModel> response) {
                        System.out.println("Agent_wall_system: "+response.toString());
                        if (response.isSuccessful()) {
                            if (response.body().getStatus().contentEquals("success")) {
                                baseServerListener.onServerSuccessOrFailure(true, response.body(), "success");
                            } else {
                                baseServerListener.onServerSuccessOrFailure(false, null, response.body().getMessage());
                                if (response.body().getError().getError_code().contentEquals("401"))
                                    new LogoutUtility().setLoggedOut((Activity) context);
                            }

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
    }

    public void assignRider(String rider_id, String orderID) {
        if (retroHubApiInterface != null) {
            if (sharedData.getMyData() != null) {

                progressDialogOwn.showDialog(context, context.getString(R.string.loading));
                Call<RiderAssignModel> orderDetailsModelCall = retroHubApiInterface.assignRider(
                        sharedData.getMyData().getData().getApiToken(),
                        sharedData.getMyData().getData().getUserId(),
                        orderID,
                        rider_id,
                        "",
                        ""
                );
                orderDetailsModelCall.enqueue(new Callback<RiderAssignModel>() {
                    @Override
                    public void onResponse(Call<RiderAssignModel> call, Response<RiderAssignModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus().contentEquals("success")) {
                                baseServerListener.onServerSuccessOrFailure(true, response.body(), response.body().getMessage());
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
}
