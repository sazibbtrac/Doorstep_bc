package com.btracsolution.deliverysystem.Features.Waiter.Features.Order.Cart;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Model.DeliveryZoneResponse;
import com.btracsolution.deliverysystem.Model.MemberInfoResponse;
import com.btracsolution.deliverysystem.Model.MemberListResponse;
import com.btracsolution.deliverysystem.Model.MenuFoodListModel;
import com.btracsolution.deliverysystem.Model.OrderSubmitResponse;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.Connectivity;
import com.btracsolution.deliverysystem.Utility.LogoutUtility;
import com.btracsolution.deliverysystem.Utility.ProgressDialogOwn;
import com.btracsolution.deliverysystem.Utility.SharedData;
import com.btracsolution.deliverysystem.network.api.RetroHubApiInterface;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCartRepo {

    @Inject
    RetroHubApiInterface retroHubApiInterface;
    @Inject
    SharedData sharedData;
    @Inject
    ProgressDialogOwn progressDialogOwn;
    Context context;
    BaseServerListener baseServerListener;

    public MyCartRepo(Context context, BaseServerListener baseServerListener) {
        ((DeliveryApp) context.getApplicationContext()).getDoNetworkComponent().inject(this);
        this.context = context;
        this.baseServerListener = baseServerListener;
    }

    public void sendOrderToServer(final boolean isLoading, String memid, String locationid, String payMethod, String payStatus, String cartData, String tableid) {
        System.out.println("My Cart Items "+cartData);
        System.out.println("MyCartRepo " + sharedData.getMyData().getData().getApiToken()
        +": "+sharedData.getMyData().getData().getUserId()+" : "+memid
        +" : "+locationid+": "+payMethod+" : "+payStatus+" : "+sharedData.getMyData().getData().getUserId()+" : "+cartData+" : "+tableid);
        if (Connectivity.isConnected(context)) {
            if (retroHubApiInterface != null) {
                if (sharedData.getMyData() != null) {
                    if (isLoading)
                        progressDialogOwn.showDialog(context, context.getString(R.string.loading));
                    Call<OrderSubmitResponse> orderSubmitResponseCall = retroHubApiInterface.submitOrder(
                            sharedData.getMyData().getData().getApiToken(),
                            sharedData.getMyData().getData().getUserId(),
                            memid,
                            locationid,
                            payMethod,
                            payStatus,
                            "",
                            "" + sharedData.getMyData().getData().getUserId(),
                            cartData,
                            tableid
                            //sharedData.getMyData().getData().getBranchInfo().get(0).getBranchId()
                    );
                    orderSubmitResponseCall.enqueue(new Callback<OrderSubmitResponse>() {
                        @Override
                        public void onResponse(Call<OrderSubmitResponse> call, Response<OrderSubmitResponse> response) {
                            Log.w("MyCartRepo","REsponse of my cart"+response.toString());
                            if (response.isSuccessful()) {
                                if (response.body().getStatus().contentEquals("success")) {
                                    baseServerListener.onServerSuccessOrFailure(true, response.body(), "0");
                                } else {
                                    // baseServerListener.onServerSuccessOrFailure(false, null, response.body().getError().getError_msg());
                                    progressDialogOwn.showToast(context, response.body().getMessage());
//                                if (response.body().getError().getError_code().contentEquals("401"))
//                                    new LogoutUtility().setLoggedOut((Activity) context);
                                }

                            } else {
                                baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                                progressDialogOwn.showToast(context, context.getString(R.string.error_into_server));
                                System.out.println("error " + response.message());

                            }
                            if (isLoading)
                                progressDialogOwn.hideDialog();

                        }

                        @Override
                        public void onFailure(Call<OrderSubmitResponse> call, Throwable t) {
                            if (t != null)
                                baseServerListener.onServerSuccessOrFailure(false, null, t.getMessage());
                            else
                                baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                            progressDialogOwn.hideDialog();

                        }
                    });
                }
            }
        } else {
            progressDialogOwn.showInfoAlertDialog(context, context.getResources().getString(R.string.no_internet_connection));

        }
    }


    public void getMemberData(final boolean isLoading, String memberid) {
        if (Connectivity.isConnected(context)) {
            if (retroHubApiInterface != null) {
                if (sharedData.getMyData() != null) {
                    if (isLoading)
                        progressDialogOwn.showDialog(context, context.getString(R.string.loading));
                    Call<MemberInfoResponse> orderSubmitResponseCall = retroHubApiInterface.getMemberInfo(
                            sharedData.getMyData().getData().getApiToken(),
                            sharedData.getMyData().getData().getUserId(),
                            memberid
                            //sharedData.getMyData().getData().getBranchInfo().get(0).getBranchId()
                    );
                    orderSubmitResponseCall.enqueue(new Callback<MemberInfoResponse>() {
                        @Override
                        public void onResponse(Call<MemberInfoResponse> call, Response<MemberInfoResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getStatus().contentEquals("success")) {
                                    baseServerListener.onServerSuccessOrFailure(true, response.body(), "1");
                                } else {
                                    // baseServerListener.onServerSuccessOrFailure(false, null, response.body().getError().getError_msg());
                                    progressDialogOwn.showToast(context, response.body().getMessage());
//                                if (response.body().getError().getError_code().contentEquals("401"))
//                                    new LogoutUtility().setLoggedOut((Activity) context);
                                }

                            } else {
                                baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                                progressDialogOwn.showToast(context, context.getString(R.string.error_into_server));

                            }
                            if (isLoading)
                                progressDialogOwn.hideDialog();

                        }

                        @Override
                        public void onFailure(Call<MemberInfoResponse> call, Throwable t) {
                            if (t != null)
                                baseServerListener.onServerSuccessOrFailure(false, null, t.getMessage());
                            else
                                baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                            progressDialogOwn.hideDialog();

                        }
                    });
                }
            }
        } else {
            progressDialogOwn.showInfoAlertDialog(context, context.getResources().getString(R.string.no_internet_connection));

        }
    }

    public void getAllMemberData(final boolean isLoading) {
        if (Connectivity.isConnected(context)) {
            if (retroHubApiInterface != null) {
                if (sharedData.getMyData() != null) {
                    if (isLoading)
                        progressDialogOwn.showDialog(context, context.getString(R.string.loading));
                    Call<MemberListResponse> orderSubmitResponseCall = retroHubApiInterface.getMemberList(
                            sharedData.getMyData().getData().getApiToken(),
                            sharedData.getMyData().getData().getUserId()
                            //sharedData.getMyData().getData().getBranchInfo().get(0).getBranchId()
                    );
                    orderSubmitResponseCall.enqueue(new Callback<MemberListResponse>() {
                        @Override
                        public void onResponse(Call<MemberListResponse> call, Response<MemberListResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getStatus().contentEquals("success")) {
                                    baseServerListener.onServerSuccessOrFailure(true, response.body(), "1");
                                } else {
                                    // baseServerListener.onServerSuccessOrFailure(false, null, response.body().getError().getError_msg());
                                    progressDialogOwn.showToast(context, response.body().getMessage());
//                                if (response.body().getError().getError_code().contentEquals("401"))
//                                    new LogoutUtility().setLoggedOut((Activity) context);
                                }

                            } else {
                                baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                                progressDialogOwn.showToast(context, context.getString(R.string.error_into_server));

                            }
                            if (isLoading)
                                progressDialogOwn.hideDialog();

                        }

                        @Override
                        public void onFailure(Call<MemberListResponse> call, Throwable t) {
                            if (t != null)
                                baseServerListener.onServerSuccessOrFailure(false, null, t.getMessage());
                            else
                                baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                            progressDialogOwn.hideDialog();

                        }
                    });
                }
            }
        } else {
            progressDialogOwn.showInfoAlertDialog(context, context.getResources().getString(R.string.no_internet_connection));

        }
    }

    public void getDeliveryZone(boolean isLoading) {
        if (Connectivity.isConnected(context)) {
            if (retroHubApiInterface != null) {
                if (sharedData.getMyData() != null) {
                    if (isLoading)
                        progressDialogOwn.showDialog(context, context.getString(R.string.loading));
                    Call<DeliveryZoneResponse> orderSubmitResponseCall = retroHubApiInterface.getDeliveryZone(
                            sharedData.getMyData().getData().getApiToken(),
                            sharedData.getMyData().getData().getUserId()
                    );
                    orderSubmitResponseCall.enqueue(new Callback<DeliveryZoneResponse>() {
                        @Override
                        public void onResponse(Call<DeliveryZoneResponse> call, Response<DeliveryZoneResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getStatus().contentEquals("success")) {
                                    baseServerListener.onServerSuccessOrFailure(true, response.body(), "2");
                                } else {
                                    // baseServerListener.onServerSuccessOrFailure(false, null, response.body().getError().getError_msg());
                                    progressDialogOwn.showToast(context, response.body().getMessage());
//                                if (response.body().getError().getError_code().contentEquals("401"))
//                                    new LogoutUtility().setLoggedOut((Activity) context);
                                }

                            } else {
                                baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                                progressDialogOwn.showToast(context, context.getString(R.string.error_into_server));

                            }
                            if (isLoading)
                                progressDialogOwn.hideDialog();

                        }

                        @Override
                        public void onFailure(Call<DeliveryZoneResponse> call, Throwable t) {
                            if (t != null)
                                baseServerListener.onServerSuccessOrFailure(false, null, t.getMessage());
                            else
                                baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                            progressDialogOwn.hideDialog();

                        }
                    });
                }
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
                                    if (response.body().getStatus().contentEquals("success")) {
                                        baseServerListener.onServerSuccessOrFailure(true, response.body(), "3");
                                    } else {
                                        baseServerListener.onServerSuccessOrFailure(false, null, response.body().getMessage());
                                        progressDialogOwn.showToast(context, response.body().getMessage());
                                        //                                if (response.body().getError().getError_code().contentEquals("401"))
                                        //                                    new LogoutUtility().setLoggedOut((Activity) context);
                                    }

                                } else {
                                    baseServerListener.onServerSuccessOrFailure(false, null, "Not found");
                                    //progressDialogOwn.showToast(context, context.getString(R.string.error_into_server));

                                }
                                // if (isLoading)
                                progressDialogOwn.hideDialog();
                            } catch (Exception e) {
                                progressDialogOwn.hideDialog();
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(Call<MemberInfoResponse> call, Throwable t) {
                            if (t != null)
                                baseServerListener.onServerSuccessOrFailure(false, null, "Not found");
                            else
                                baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                            progressDialogOwn.hideDialog();

                        }
                    });
                }
            }
        }else

        {
            progressDialogOwn.showInfoAlertDialog(context, context.getResources().getString(R.string.no_internet_connection));

        }
    }
}
