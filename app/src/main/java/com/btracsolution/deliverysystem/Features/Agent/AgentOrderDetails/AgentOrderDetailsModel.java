package com.btracsolution.deliverysystem.Features.Agent.AgentOrderDetails;

import android.content.Context;
import android.util.Log;

import com.btracsolution.deliverysystem.AppsPackageUpdateChecker.AppUpdateAndShowingEmergencyMessages;
import com.btracsolution.deliverysystem.AppsPackageUpdateChecker.RemoteKeys.FirebaseRemoteKeys;
import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Features.Agent.AgentHomeActivity;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.Connectivity;
import com.btracsolution.deliverysystem.Utility.ProgressDialogOwn;
import com.btracsolution.deliverysystem.Utility.SharedData;
import com.btracsolution.deliverysystem.network.api.RetroHubApiInterface;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mahmudul.hasan on 2/12/2018.
 */

public class AgentOrderDetailsModel implements AppUpdateAndShowingEmergencyMessages.OnGetBaseUrlListener{

    Context context;
    BaseServerListener baseServerListener;

    @Inject
    RetroHubApiInterface retroHubApiInterface;
    @Inject
    SharedData sharedData;
    @Inject
    ProgressDialogOwn progressDialogOwn;
    private static final String TAG = "AgentOrderDetailsModel";


    public AgentOrderDetailsModel(Context context, BaseServerListener baseServerListener) {
        this.context = context;
        this.baseServerListener = baseServerListener;
        ((DeliveryApp) context.getApplicationContext()).getDoNetworkComponent().inject(this);
        checkAppUpdateAndMessage();

    }

    public void checkAppUpdateAndMessage() {
        AppUpdateAndShowingEmergencyMessages appUpdateAndShowingEmergencyMessages = new AppUpdateAndShowingEmergencyMessages(context, this);
        appUpdateAndShowingEmergencyMessages.prepareConfig();
    }

    @Override
    public void onGetBaseUrl(String updateUrl) {
        FirebaseRemoteKeys.base_url_link = updateUrl;
        ((DeliveryApp) context.getApplicationContext()).initializeInjector();
        ((DeliveryApp) context.getApplicationContext()).getDoNetworkComponent().inject(this);
        System.out.println("respnse injectedd AgentOrderDetailSModel " + updateUrl);
    }

    public void sendJobAcceptResponse(String acceptType, String orderID, String ShortNOte) {
        if (Connectivity.isConnected(context)) {
            if (retroHubApiInterface != null && sharedData != null) {
                Log.d(TAG, "ApiResponse param acceptType: " + acceptType + " orderID: " + orderID + " Shortnote: " + ShortNOte);

                progressDialogOwn.showDialog(context, "Call");
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
                        try {
                            if (response.isSuccessful()) {
                                progressDialogOwn.hideDialog();

                                Log.d(TAG, "ApiResponse Successful");

                                if (response.body().getStatus().contentEquals("success")) {
                                    Log.d(TAG, "ApiResponse Successful and content success");
                                    baseServerListener.onServerSuccessOrFailure(true, response.body(), "success");
                                } else {
                                    Log.d(TAG, "ApiResponse not success " + response.body().getMessage());
                                    baseServerListener.onServerSuccessOrFailure(false, null, response.body().getError().getError_msg());
                                }
                            } else {
                                progressDialogOwn.hideDialog();
                                baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                                Log.d(TAG, "ApiResponse  FAIL " + response.body().getError().getError_msg());
                                Log.d(TAG, "ApiResponse  FAIL " + response.body().getMessage());

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            progressDialogOwn.hideDialog();

                        }

                    }

                    @Override
                    public void onFailure(Call<OrderDetailsModel> call, Throwable t) {
                        if (t != null) {
                            Log.d(TAG, "ApiResponse Failed " + t.getMessage());
                            baseServerListener.onServerSuccessOrFailure(false, null, t.getMessage());
                        } else
                            baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                        progressDialogOwn.hideDialog();

                    }
                });

            }
        }else {
            progressDialogOwn.showInfoAlertDialog(context, context.getResources().getString(R.string.no_internet_connection));
        }

    }

    public void sendJobAcceptResponseForReadyToPickUp(String acceptType, String orderID, String ShortNOte, String checkedOrders) {
        if (Connectivity.isConnected(context)) {
            if (retroHubApiInterface != null && sharedData != null) {
                Log.d(TAG, "sendJobAcceptResponse Called " + sharedData.getMyData().getData().getUserEmail());

                progressDialogOwn.showDialog(context, context.getString(R.string.loading));
                Call<OrderDetailsModel> orderDetailsModelCall = retroHubApiInterface.acceptOrder(
                        sharedData.getMyData().getData().getApiToken(),
                        sharedData.getMyData().getData().getUserId(),
                        orderID,
                        acceptType,
                        ShortNOte,
                        String.valueOf(sharedData.getMyData().getData().getKitchenInfo().get(0).getKitchenId()),
                        checkedOrders
                );
                orderDetailsModelCall.enqueue(new Callback<OrderDetailsModel>() {
                    @Override
                    public void onResponse(Call<OrderDetailsModel> call, Response<OrderDetailsModel> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "ApiResponse Successful");

                            if (response.body().getStatus().contentEquals("success")) {
                                Log.d(TAG, "ApiResponse Successful and content success");
                                baseServerListener.onServerSuccessOrFailure(true, response.body(), "success");
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
        }
        else {
            progressDialogOwn.showInfoAlertDialog(context, context.getResources().getString(R.string.no_internet_connection));
        }

    }

    public void getSingleOrderData(String orderid) {
        if (Connectivity.isConnected(context)) {
            if (retroHubApiInterface != null && sharedData != null) {
                Log.d(TAG, "sendJobAcceptResponse Called " + sharedData.getMyData().getData().getUserEmail());

                //progressDialogOwn.showDialog(context, "SingleOrder");
                Call<OrderDetailsModel> orderDetailsModelCall = retroHubApiInterface.getSingleOrderData(
                        sharedData.getMyData().getData().getApiToken(),
                        sharedData.getMyData().getData().getUserId(),
                        orderid
                );
                orderDetailsModelCall.enqueue(new Callback<OrderDetailsModel>() {
                    @Override
                    public void onResponse(Call<OrderDetailsModel> call, Response<OrderDetailsModel> response) {
                        try {
                            if (response.isSuccessful()) {
                                Log.d(TAG, "ApiResponse Successful");
                                progressDialogOwn.hideDialog();

                                if (response.body().getStatus().contentEquals("success")) {
                                    Log.d(TAG, "ApiResponse Successful and content success");
                                    baseServerListener.onServerSuccessOrFailure(true, response.body(), "single");
                                } else {
                                    Log.d(TAG, "ApiResponse not success");
                                    baseServerListener.onServerSuccessOrFailure(false, null, response.body().getError().getError_msg());
                                }
                            } else {
                                baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                                progressDialogOwn.hideDialog();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            progressDialogOwn.hideDialog();
                        }

                    }

                    @Override
                    public void onFailure(Call<OrderDetailsModel> call, Throwable t) {
                        if (t != null) {
                            Log.d(TAG, "ApiResponse Failed");
                            baseServerListener.onServerSuccessOrFailure(false, null, t.getMessage());
                            progressDialogOwn.hideDialog();
                        } else {
                            baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                            progressDialogOwn.hideDialog();
                        }

                    }
                });

            }
        }else {
            progressDialogOwn.showInfoAlertDialog(context, context.getResources().getString(R.string.no_internet_connection));
        }

    }
}
