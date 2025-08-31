package com.btracsolution.deliverysystem.Features.Rider.Features.MyJobPack;

import android.app.Activity;
import android.content.Context;

import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.Connectivity;
import com.btracsolution.deliverysystem.Utility.LogoutUtility;
import com.btracsolution.deliverysystem.Utility.ProgressDialogOwn;
import com.btracsolution.deliverysystem.Utility.SharedData;
import com.btracsolution.deliverysystem.network.api.RetroHubApiInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mahmudul.hasan on 2/26/2018.
 */

public class RiderJobModel {
    Context context;
    BaseServerListener baseServerListener;

    @Inject
    RetroHubApiInterface retroHubApiInterface;
    @Inject
    SharedData sharedData;
    @Inject
    ProgressDialogOwn progressDialogOwn;

    public RiderJobModel(Context context, BaseServerListener baseServerListener) {
        ((DeliveryApp) context.getApplicationContext()).getDoNetworkComponent().inject(this);
        this.context = context;
        this.baseServerListener = baseServerListener;
    }

    public void getServerData(final boolean isLoading) {
        if (Connectivity.isConnected(context)) {
            if (retroHubApiInterface != null) {
                if (sharedData.getMyData() != null) {

                    /*List<String> kitchenIds = new ArrayList<>();
                    for (int j = 0; j < sharedData.getMyData().getData().getKitchenInfo().size(); j++) {
                        String kitchenId =String.valueOf(sharedData.getMyData().getData().getKitchenInfo().get(j).getKitchenId()) ;
                        kitchenIds.add(kitchenId);
                    }*/

                    System.out.println("Token "+sharedData.getMyData().getData().getApiToken());
                    System.out.println("Token userid "+sharedData.getMyData().getData().getUserId());
                    System.out.println("Token kitchenid"+String.valueOf(sharedData.getMyData().getData().getKitchenInfo().get(0).getKitchenId()));
                    if (isLoading)
                        progressDialogOwn.showDialog(context, context.getString(R.string.loading));
                    Call<OrderDetailsModel> orderDetailsModelCall = retroHubApiInterface.getJobForRider(
                            sharedData.getMyData().getData().getApiToken(),
                            sharedData.getMyData().getData().getUserId(),
                          // kitchenIds
                            String.valueOf(sharedData.getMyData().getData().getKitchenInfo().get(0).getKitchenId()),
                            //sharedData.getMyData().getData().getBranchInfo().get(0).getBranchId(),
                            getTodayDate(),
                            getTodayDate()
                    );
                    orderDetailsModelCall.enqueue(new Callback<OrderDetailsModel>() {
                        @Override
                        public void onResponse(Call<OrderDetailsModel> call, Response<OrderDetailsModel> response) {
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
        }
        else{
            baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.no_internet_connection2));

        }
    }

    public String getTodayDate() {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return date;
    }
}
