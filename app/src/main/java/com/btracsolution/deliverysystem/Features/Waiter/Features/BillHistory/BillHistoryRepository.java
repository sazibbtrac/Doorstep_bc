package com.btracsolution.deliverysystem.Features.Waiter.Features.BillHistory;

import android.content.Context;

import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Model.BillHistoryResponse;
import com.btracsolution.deliverysystem.Model.CommonDataResponse;
import com.btracsolution.deliverysystem.Model.LoginModel;
import com.btracsolution.deliverysystem.Model.MemberInfoResponse;
import com.btracsolution.deliverysystem.Model.MemberListResponse;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.Model.SyncDataResponse;
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

public class BillHistoryRepository {

    Context context;
    BaseServerListener baseServerListener;

    @Inject
    RetroHubApiInterface retroHubApiInterface;
    @Inject
    SharedData sharedData;
    @Inject
    ProgressDialogOwn progressDialogOwn;

    public BillHistoryRepository(Context context, BaseServerListener baseServerListener) {
        ((DeliveryApp) context.getApplicationContext()).getDoNetworkComponent().inject(this);
        this.context = context;
        this.baseServerListener = baseServerListener;
    }

    public String getOrderIdList(ArrayList<OrderDetailsModel.orderBasicData> orderBasicData){
        List<Integer> orderids = new ArrayList<>();
        if (orderBasicData!=null && orderBasicData.size()>0) {
            for (OrderDetailsModel.orderBasicData orderBasicData1 : orderBasicData) {
                orderids.add(Integer.valueOf(orderBasicData1.getOrderid()));
            }
        }
        //return kitchenids;
        return new Gson().toJson(orderids);
    }
    public String getKitchenidList(){
        List<LoginModel.KitchenInfo> kitchenInfos = new ArrayList<>();
        List<Integer> kitchenids = new ArrayList<>();
        kitchenInfos.addAll(sharedData.getMyData().getData().getKitchenInfo());
        if (kitchenInfos!=null && kitchenInfos.size()>0) {
            for (LoginModel.KitchenInfo kitcheninfo : kitchenInfos) {
                kitchenids.add(kitcheninfo.getKitchenId());
            }
        }
        //return kitchenids;
        return new Gson().toJson(kitchenids);
    }
    public void getServerData(final boolean isLoading,String memid) {
        if (Connectivity.isConnected(context)) {
            if (retroHubApiInterface != null) {
                if (sharedData.getMyData() != null) {

                    System.out.println("Token " + getKitchenidList());
                    System.out.println("Token " + sharedData.getMyData().getData().getApiToken());
                    System.out.println("Token userid" + sharedData.getMyData().getData().getUserId());
                    System.out.println("Token kitchenid " + getKitchenidList());
                    if (isLoading)
                        progressDialogOwn.showDialog(context, context.getString(R.string.loading));
                    Call<OrderDetailsModel> orderDetailsModelCall = retroHubApiInterface.getBill(
                            sharedData.getMyData().getData().getApiToken(),
                            sharedData.getMyData().getData().getUserId(),
                            memid,
                            getKitchenidList()
//                        getTodayDate(),
//                        getTodayDate()
                    );
                    orderDetailsModelCall.enqueue(new Callback<OrderDetailsModel>() {
                        @Override
                        public void onResponse(Call<OrderDetailsModel> call, Response<OrderDetailsModel> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getStatus().contentEquals("success")) {
                                    baseServerListener.onServerSuccessOrFailure(true, response.body(), "0");
                                } else
                                    baseServerListener.onServerSuccessOrFailure(false, response.body(), response.body().getError().getError_msg());

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
        }else{
            progressDialogOwn.showInfoAlertDialog(context, context.getResources().getString(R.string.no_internet_connection));

        }
    }

    public void setSyncData(boolean isloading,int position, int bill_id){
        if (retroHubApiInterface != null) {
            if (sharedData.getMyData() != null) {

               // System.out.println("dfjskfdf "+bill_id);
                if (isloading)
                    progressDialogOwn.showDialog(context, context.getString(R.string.loading));

                Call<SyncDataResponse> syncBillData = retroHubApiInterface.syncBill(
                        sharedData.getMyData().getData().getApiToken(),
                        sharedData.getMyData().getData().getUserId(),
                        bill_id
//                        getTodayDate(),
//                        getTodayDate()
                );
                syncBillData.enqueue(new Callback<SyncDataResponse>() {
                    @Override
                    public void onResponse(Call<SyncDataResponse> call, Response<SyncDataResponse> response) {
                        if (response.isSuccessful()) {

                            if (isloading)
                                progressDialogOwn.hideDialog();

                         //   System.out.println("fksjhdkf "+response.body().getMessage());

                            if(response.body().getStatus().contentEquals("success")){
                                baseServerListener.onServerSuccessOrFailure(true, response.body(), "4");
                                progressDialogOwn.showToast(context, response.body().getMessage());
                            }
                            else {
                                progressDialogOwn.showToast(context, response.body().getMessage());
                            }

//                            if (response.body().getStatus().contentEquals("success")) {
//                                baseServerListener.onServerSuccessOrFailure(true, response.body(), "3");
//                            } else
//                                baseServerListener.onServerSuccessOrFailure(false, response.body(), response.body().getMessage());


                        }
                        else {
                            baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                            progressDialogOwn.showToast(context, context.getString(R.string.error_into_server));
                        }
                           // baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));


                    }

                    @Override
                    public void onFailure(Call<SyncDataResponse> call, Throwable t) {
                        progressDialogOwn.hideDialog();
                        if (t != null)
                            baseServerListener.onServerSuccessOrFailure(false, null, t.getMessage());
                        else
                            baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));



                    }
                });
            }
        }


    }

    public void getBillFromServer(final boolean isLoading,String mem_id) {
        if (retroHubApiInterface != null) {
            if (sharedData.getMyData() != null) {

                System.out.println("dsjfdf "+ sharedData.getMyData().getData().getApiToken()+": "+ sharedData.getMyData().getData().getUserId()
                +": "+mem_id);


                if (isLoading)
                    progressDialogOwn.showDialog(context, context.getString(R.string.loading));
                Call<BillHistoryResponse> orderDetailsModelCall = retroHubApiInterface.getBillHistory(
                        sharedData.getMyData().getData().getApiToken(),
                        sharedData.getMyData().getData().getUserId(),
                        mem_id
//                        getTodayDate(),
//                        getTodayDate()
                );
                orderDetailsModelCall.enqueue(new Callback<BillHistoryResponse>() {
                    @Override
                    public void onResponse(Call<BillHistoryResponse> call, Response<BillHistoryResponse> response) {
                        if (response.isSuccessful()) {
                          //  System.out.println("testttt "+response.body().getMessage());
                            if (response.body().getStatus().contentEquals("success")) {
                                baseServerListener.onServerSuccessOrFailure(true, response.body(), "3");
                            } else
                                baseServerListener.onServerSuccessOrFailure(false, response.body(), response.body().getMessage());

                        } else
                            baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                        if (isLoading)
                            progressDialogOwn.hideDialog();

                    }

                    @Override
                    public void onFailure(Call<BillHistoryResponse> call, Throwable t) {
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

    public void getAllMemberData(final boolean isLoading) {
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
    }

    public void getSingleMemberData(final boolean isLoading,String memid) {
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
                                    System.out.println("So its working fine!"+response.body().getMessage());
                                    if (response.body().getStatus().contentEquals("success")) {
                                        baseServerListener.onServerSuccessOrFailure(true, response.body(), "2");
                                    } else {
                                        baseServerListener.onServerSuccessOrFailure(false, null, response.body().getMessage());
                                        progressDialogOwn.showToast(context, response.body().getMessage());
                                        //                                if (response.body().getError().getError_code().contentEquals("401"))
                                        //                                    new LogoutUtility().setLoggedOut((Activity) context);
                                    }

                                } else {
                                    baseServerListener.onServerSuccessOrFailure(false, null, response.body().getMessage());
                                    //progressDialogOwn.showToast(context, context.getString(R.string.error_into_server));

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
                            if (t != null)
                                baseServerListener.onServerSuccessOrFailure(false, null, t.getMessage());
                            else
                                baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
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
