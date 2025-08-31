package com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterJobList.WaiterJoblistDetail;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.btracsolution.deliverysystem.AppsPackageUpdateChecker.AppUpdateAndShowingEmergencyMessages;
import com.btracsolution.deliverysystem.AppsPackageUpdateChecker.RemoteKeys.FirebaseRemoteKeys;
import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Model.MemberInfoResponse;
import com.btracsolution.deliverysystem.Model.MemberUpdateResponse;
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

public class WaiterOrderDetailRepo implements AppUpdateAndShowingEmergencyMessages.OnGetBaseUrlListener {

    Context context;
    BaseServerListener baseServerListener;

    @Inject
    RetroHubApiInterface retroHubApiInterface;
    @Inject
    SharedData sharedData;
    @Inject
    ProgressDialogOwn progressDialogOwn;
    private static final String TAG = "WaiterOrderDetailRepo";


    public WaiterOrderDetailRepo(Context context, BaseServerListener baseServerListener) {
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
        System.out.println("respnse injectedd WaiterOrderDetailRepo " + updateUrl);
    }

    public void sendJobAcceptResponse(String acceptType, String orderID, String ShortNOte, int kitchenid) {
        if (Connectivity.isConnected(context)) {
            if (retroHubApiInterface != null && sharedData != null) {
                Log.d(TAG, "sendJobAcceptResponse Called " + sharedData.getMyData().getData().getUserEmail());

                progressDialogOwn.showDialog(context, context.getString(R.string.loading));
                Call<OrderDetailsModel> orderDetailsModelCall = retroHubApiInterface.acceptOrder(
                        sharedData.getMyData().getData().getApiToken(),
                        sharedData.getMyData().getData().getUserId(),
                        // "47",
                        orderID,
                        acceptType,
                        ShortNOte,
                        String.valueOf(kitchenid),
                        ""
                        //String.valueOf(sharedData.getMyData().getData().getKitchenInfo().get(0).getKitchenId())
                );
                orderDetailsModelCall.enqueue(new Callback<OrderDetailsModel>() {
                    @Override
                    public void onResponse(Call<OrderDetailsModel> call, Response<OrderDetailsModel> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "ApiResponse Successful");

                            if (response.body().getStatus().contentEquals("success")) {
                                Log.d(TAG, "ApiResponse Successful and content success");
                                baseServerListener.onServerSuccessOrFailure(true, response.body(), "accpet");
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

    public void updateMember(String orderID, String memberid) {
        if (Connectivity.isConnected(context)) {
            if (retroHubApiInterface != null && sharedData != null) {
                // Log.d(TAG, "sendJobAcceptResponse Called " + sharedData.getMyData().getData().getUserEmail());

                progressDialogOwn.showDialog(context, context.getString(R.string.loading));
                Log.d(TAG, "sendJobAcceptResponse Called " + sharedData.getMyData().getData().getApiToken()+": "+sharedData.getMyData().getData().getUserId()
                +": "+orderID+" : "+memberid);
                Call<MemberUpdateResponse> orderDetailsModelCall = retroHubApiInterface.updateMember(
                        sharedData.getMyData().getData().getApiToken(),
                        sharedData.getMyData().getData().getUserId(),
                        orderID,
                        memberid
                );
                orderDetailsModelCall.enqueue(new Callback<MemberUpdateResponse>() {
                    @Override
                    public void onResponse(Call<MemberUpdateResponse> call, Response<MemberUpdateResponse> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "ApiResponse Successful"+response.toString());

                            if (response.body().getStatus().contentEquals("success")) {
                                Log.d(TAG, "ApiResponse Successful and content success");
                                baseServerListener.onServerSuccessOrFailure(true, response.body(), "memberupdate");
                            } else {
                                Log.d(TAG, "ApiResponse not success");
                                baseServerListener.onServerSuccessOrFailure(false, null, response.body().getMessage());
                            }
                        } else
                            baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                        progressDialogOwn.hideDialog();

                    }

                    @Override
                    public void onFailure(Call<MemberUpdateResponse> call, Throwable t) {
                        if (t != null) {
                            Log.d(TAG, "ApiResponse Failed"+t.getMessage());
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

    public void getSingleOrderData(String orderid) {
        if (Connectivity.isConnected(context)) {
            if (retroHubApiInterface != null && sharedData != null) {
                Log.d(TAG, "ApiResponse Called " + orderid);
                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
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

                                if (response.body().getStatus().contentEquals("success")) {
                                    Log.d(TAG, "ApiResponse Successful and content success");
                                    progressDialog.hide();
                                    baseServerListener.onServerSuccessOrFailure(true, response.body(), "single");
                                } else {
                                    Log.d(TAG, "ApiResponse not success");
                                    progressDialog.hide();
                                    baseServerListener.onServerSuccessOrFailure(false, null, response.body().getError().getError_msg());
                                }
                            } else {
                                progressDialog.hide();
                                baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            progressDialog.hide();

                        }

                    }

                    @Override
                    public void onFailure(Call<OrderDetailsModel> call, Throwable t) {
                        if (t != null) {
                            Log.d(TAG, "ApiResponse Failed");
                            baseServerListener.onServerSuccessOrFailure(false, null, t.getMessage());
                            progressDialog.hide();
                        } else {
                            baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                            progressDialog.hide();

                        }

                    }
                });

            }
        } else {
            progressDialogOwn.showInfoAlertDialog(context, context.getResources().getString(R.string.no_internet_connection));

        }

    }

    public void getSingleMemberData(final boolean isLoading, String memid) {
        if (Connectivity.isConnected(context)) {
            if (retroHubApiInterface != null) {
                if (sharedData.getMyData() != null) {
                    if (isLoading)
                        progressDialogOwn.showDialog(context, context.getString(R.string.loading));
                    Call<MemberInfoResponse> orderSubmitResponseCall = retroHubApiInterface.getMember(
                            sharedData.getMyData().getData().getApiToken(),
                            sharedData.getMyData().getData().getUserId(),
                            memid
                            //sharedData.getMyData().getData().getBranchInfo().get(0).getBranchId()
                    );
                    orderSubmitResponseCall.enqueue(new Callback<MemberInfoResponse>() {
                        @Override
                        public void onResponse(Call<MemberInfoResponse> call, Response<MemberInfoResponse> response) {
                            try {
                                if (response.isSuccessful()) {
                                    progressDialogOwn.hideDialog();
                                    if (response.body().getStatus().contentEquals("success")) {
                                        baseServerListener.onServerSuccessOrFailure(true, response.body(), "singlemember");
                                    } else {
                                        progressDialogOwn.hideDialog();
                                        baseServerListener.onServerSuccessOrFailure(false, null, response.body().getMessage());
                                        progressDialogOwn.showToast(context, response.body().getMessage());
                                        //                                if (response.body().getError().getError_code().contentEquals("401"))
                                        //                                    new LogoutUtility().setLoggedOut((Activity) context);
                                    }

                                } else {
                                    baseServerListener.onServerSuccessOrFailure(false, null, response.body().getMessage());
                                    //progressDialogOwn.showToast(context, context.getString(R.string.error_into_server));
                                    progressDialogOwn.hideDialog();

                                }
                                // if (isLoading)
                                progressDialogOwn.hideDialog();
                            } catch (Exception e) {
                                e.printStackTrace();
                                progressDialogOwn.hideDialog();

                            }

                        }

                        @Override
                        public void onFailure(Call<MemberInfoResponse> call, Throwable t) {
                            if (t != null) {
                                baseServerListener.onServerSuccessOrFailure(false, null, t.getMessage());
                            } else {
                                baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                            }
                            progressDialogOwn.hideDialog();

                        }
                    });
                }
            }
        } else {
            progressDialogOwn.showInfoAlertDialog(context, context.getResources().getString(R.string.no_internet_connection));

        }
    }
}