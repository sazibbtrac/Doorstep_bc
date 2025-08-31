package com.btracsolution.deliverysystem.Features.Rider.Features.RiderOrderDetails;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
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
 * Created by mahmudul.hasan on 2/12/2018.
 */

public class RiderOrderDetailsModel {

    Context context;
    BaseServerListener baseServerListener;

    @Inject
    RetroHubApiInterface retroHubApiInterface;
    @Inject
    SharedData sharedData;
    @Inject
    ProgressDialogOwn progressDialogOwn;

    public RiderOrderDetailsModel(Context context, BaseServerListener baseServerListener) {
        this.context = context;
        this.baseServerListener = baseServerListener;
        ((DeliveryApp) context.getApplicationContext()).getDoNetworkComponent().inject(this);

    }

    public void sendJobAcceptResponse(String acceptType, String orderID, String ShortNOte) {
        if (Connectivity.isConnected(context)) {
            if (retroHubApiInterface != null && sharedData != null) {
                progressDialogOwn.showDialog(context, context.getString(R.string.loading));
                Call<OrderDetailsModel> orderDetailsModelCall = retroHubApiInterface.acceptOrder(
                        sharedData.getMyData().getData().getApiToken(),
                        sharedData.getMyData().getData().getUserId(),
                        orderID,
                        acceptType,
                        ShortNOte,
                        String.valueOf(sharedData.getMyData().getData().getKitchenInfo().get(0).getKitchenId()),
                        ""
                );
                orderDetailsModelCall.enqueue(new Callback<OrderDetailsModel>() {
                    @Override
                    public void onResponse(Call<OrderDetailsModel> call, Response<OrderDetailsModel> response) {
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
                    public void onFailure(Call<OrderDetailsModel> call, Throwable t) {
                        if (t != null)
                            baseServerListener.onServerSuccessOrFailure(false, null, t.getMessage());
                        else
                            baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                        progressDialogOwn.hideDialog();

                    }
                });

            }
        } else {
            progressDialogOwn.showInfoAlertDialog(context, context.getResources().getString(R.string.no_internet_connection));
        }

    }

    public void sendJobAcknowldegementResponse(String orderID, String deliverystatus) {
        if (Connectivity.isConnected(context)) {
            System.out.println("dsfjsdfdg "+sharedData.getMyData().getData().getApiToken()+": id "+sharedData.getMyData().getData().getUserId()
            +" rder id: "+orderID+"ffg "+deliverystatus);
            if (retroHubApiInterface != null && sharedData != null) {
                progressDialogOwn.showDialog(context, context.getString(R.string.loading));
                Call<OrderDetailsModel> orderDetailsModelCall = retroHubApiInterface.acknowledgement(
                        sharedData.getMyData().getData().getApiToken(),
                        sharedData.getMyData().getData().getUserId(),
                        orderID,
                        deliverystatus
                );
                orderDetailsModelCall.enqueue(new Callback<OrderDetailsModel>() {
                    @Override
                    public void onResponse(Call<OrderDetailsModel> call, Response<OrderDetailsModel> response) {
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
                    public void onFailure(Call<OrderDetailsModel> call, Throwable t) {
                        if (t != null)
                            baseServerListener.onServerSuccessOrFailure(false, null, t.getMessage());
                        else
                            baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                        progressDialogOwn.hideDialog();

                    }
                });

            }
        } else {
            progressDialogOwn.showInfoAlertDialog(context, context.getResources().getString(R.string.no_internet_connection));
        }

    }

    public void getSingleOrderData(String orderid) {
        if (Connectivity.isConnected(context)) {
            if (retroHubApiInterface != null && sharedData != null) {
                Log.d(TAG, "ApiResponse Called " + orderid);

                progressDialogOwn.showDialog(context, context.getString(R.string.loading));
                Call<OrderDetailsModel> orderDetailsModelCall = retroHubApiInterface.getSingleOrderData(
                        sharedData.getMyData().getData().getApiToken(),
                        sharedData.getMyData().getData().getUserId(),
                        orderid
                );
                orderDetailsModelCall.enqueue(new Callback<OrderDetailsModel>() {
                    @Override
                    public void onResponse(Call<OrderDetailsModel> call, Response<OrderDetailsModel> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "ApiResponse Successful");

                            if (response.body().getStatus().contentEquals("success")) {
                                Log.d(TAG, "ApiResponse Successful and content success");
                                baseServerListener.onServerSuccessOrFailure(true, response.body(), "single");
                            } else {
                                Log.d(TAG, "ApiResponse not success");
                                baseServerListener.onServerSuccessOrFailure(false, null, response.body().getError().getError_msg());
                            }
                        } else
                            baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                        progressDialogOwn.hideDialog();

                    }

                    @Override
                    public void onFailure(Call<OrderDetailsModel> call, Throwable t) {
                        if (t != null) {
                            Log.d(TAG, "ApiResponse Failed");
                            baseServerListener.onServerSuccessOrFailure(false, null, t.getMessage());
                        } else
                            baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                        progressDialogOwn.hideDialog();

                    }
                });

            }
        } else {
            progressDialogOwn.showInfoAlertDialog(context, context.getResources().getString(R.string.no_internet_connection));
        }

    }
}
