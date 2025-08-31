package com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentOrder;

import android.content.Context;
import android.content.Intent;

import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Features.Login.LoginActivity;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.Connectivity;
import com.btracsolution.deliverysystem.Utility.ProgressDialogOwn;
import com.btracsolution.deliverysystem.Utility.SharedData;
import com.btracsolution.deliverysystem.network.api.RetroHubApiInterface;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mahmudul.hasan on 2/7/2018.
 */

public class AgentModel {

    Context context;
    BaseServerListener baseServerListener;

    @Inject
    RetroHubApiInterface retroHubApiInterface;
    @Inject
    SharedData sharedData;
    @Inject
    ProgressDialogOwn progressDialogOwn;

    public AgentModel(Context context, BaseServerListener baseServerListener) {
        ((DeliveryApp) context.getApplicationContext()).getDoNetworkComponent().inject(this);
        this.context = context;
        this.baseServerListener = baseServerListener;
    }

    public void getServerData(final boolean isLoading) {
        if (Connectivity.isConnected(context)) {
            if (retroHubApiInterface != null) {
                if (sharedData.getMyData() != null) {

                    System.out.println("Token " + sharedData.getMyData().getData().getApiToken());
                    System.out.println("Token kitchenid " + sharedData.getMyData().getData().getKitchenInfo().get(0).getKitchenId());
                    System.out.println("Token userid " + sharedData.getMyData().getData().getUserId()+" Data: "+getTodayDate());
                    if (isLoading)
                        progressDialogOwn.showDialog(context, context.getString(R.string.loading));
                    Call<OrderDetailsModel> orderDetailsModelCall = retroHubApiInterface.getOrderList(
                            sharedData.getMyData().getData().getApiToken(),
                            sharedData.getMyData().getData().getUserId(),
                            String.valueOf(sharedData.getMyData().getData().getKitchenInfo().get(0).getKitchenId()),
                            getTodayDate(),
                            getTodayDate()
                    );
                    orderDetailsModelCall.enqueue(new Callback<OrderDetailsModel>() {
                        @Override
                        public void onResponse(Call<OrderDetailsModel> call, Response<OrderDetailsModel> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getStatus().contentEquals("success")) {
                                    baseServerListener.onServerSuccessOrFailure(true, response.body(), "success");
                                } else
                                    baseServerListener.onServerSuccessOrFailure(false, response.body(), response.body().getError().getError_msg());

                            } else
                                baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                            // if (isLoading)
                            progressDialogOwn.hideDialog();

                        }

                        @Override
                        public void onFailure(Call<OrderDetailsModel> call, Throwable t) {
                            if (t != null)
                                baseServerListener.onServerSuccessOrFailure(false, null, t.getMessage());
                            else
                                baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));

                            if (isLoading)
                                progressDialogOwn.hideDialog();

                        }
                    });
                }
            }
        }else{
            progressDialogOwn.showInfoAlertDialog(context, context.getResources().getString(R.string.no_internet_connection));
        }
    }

    public String getTodayDate() {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return date;
    }
}
