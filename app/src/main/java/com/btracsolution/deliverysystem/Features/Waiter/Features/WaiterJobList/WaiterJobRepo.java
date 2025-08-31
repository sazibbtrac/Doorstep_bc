package com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterJobList;

import android.app.Activity;
import android.content.Context;

import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Features.Login.LoginActivity;
import com.btracsolution.deliverysystem.Model.LoginModel;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.Connectivity;
import com.btracsolution.deliverysystem.Utility.ProgressDialogOwn;
import com.btracsolution.deliverysystem.Utility.SharedData;
import com.btracsolution.deliverysystem.network.api.RetroHubApiInterface;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WaiterJobRepo {

    Context context;
    BaseServerListener baseServerListener;

    @Inject
    RetroHubApiInterface retroHubApiInterface;
    @Inject
    SharedData sharedData;
    @Inject
    ProgressDialogOwn progressDialogOwn;

    public WaiterJobRepo(Context context, BaseServerListener baseServerListener) {
        ((DeliveryApp) context.getApplicationContext()).getDoNetworkComponent().inject(this);
        this.context = context;
        this.baseServerListener = baseServerListener;
    }

    public String getKitchenidList() {
        List<Integer> kitchenids = null;
        try {
            List<LoginModel.KitchenInfo> kitchenInfos = new ArrayList<>();
            kitchenids = new ArrayList<>();
            kitchenInfos.addAll(sharedData.getMyData().getData().getKitchenInfo());
            if (kitchenInfos != null && kitchenInfos.size() > 0) {
                for (LoginModel.KitchenInfo kitcheninfo : kitchenInfos) {
                    kitchenids.add(kitcheninfo.getKitchenId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Gson().toJson(kitchenids);
    }

    public void getServerData(final boolean isLoading) {
        if (Connectivity.isConnected(context)) {
            if (retroHubApiInterface != null) {
                if (sharedData.getMyData() != null) {

                    System.out.println("Token ssfdsdgfgfdggfgf" + getTodayDate());
                    System.out.println("Token fdfdfgfg" + sharedData.getMyData().getData().getApiToken());
                    System.out.println("Token userid" + sharedData.getMyData().getData().getUserId());
                    System.out.println("Token kitchenid " + sharedData.getMyData().getData().getKitchenInfo().get(0).getKitchenId());
                    if (isLoading) {
                        progressDialogOwn.showDialog(context, context.getString(R.string.loading));
                    }
                    Call<OrderDetailsModel> orderDetailsModelCall = retroHubApiInterface.getOrderList(
                            sharedData.getMyData().getData().getApiToken(),
                            sharedData.getMyData().getData().getUserId(),
                            "0",
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
                                {
                                  //  progressDialogOwn.showToast(context,response.body().getMessage());
                                    baseServerListener.onServerSuccessOrFailure(false, response.body(), response.body().getError().getError_msg());

                                }

                            } else
                                baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                            if (isLoading)
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
        } else {
            progressDialogOwn.showInfoAlertDialog(context, context.getResources().getString(R.string.no_internet_connection));

        }
    }

    public String getTodayDate() {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return date;
    }
}
