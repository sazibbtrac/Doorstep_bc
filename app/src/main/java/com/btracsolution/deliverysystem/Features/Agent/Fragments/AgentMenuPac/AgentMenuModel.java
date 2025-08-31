package com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentMenuPac;

import android.app.Activity;
import android.content.Context;

import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Model.MenuFoodListModel;
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

/**
 * Created by mahmudul.hasan on 2/13/2018.
 */

public class AgentMenuModel {

    @Inject
    RetroHubApiInterface retroHubApiInterface;
    @Inject
    SharedData sharedData;
    @Inject
    ProgressDialogOwn progressDialogOwn;
    Context context;
    BaseServerListener baseServerListener;

    public AgentMenuModel(Context context, BaseServerListener baseServerListener) {
        ((DeliveryApp) context.getApplicationContext()).getDoNetworkComponent().inject(this);
        this.context = context;
        this.baseServerListener = baseServerListener;
    }

    public void getServerData(final boolean isLoading) {
        if (Connectivity.isConnected(context)) {
            if (retroHubApiInterface != null) {
                if (sharedData.getMyData() != null) {
                    if (isLoading)
                        progressDialogOwn.showDialog(context, context.getString(R.string.loading));
                    Call<MenuFoodListModel> orderDetailsModelCall = retroHubApiInterface.getMenuList(
                            sharedData.getMyData().getData().getApiToken(),
                            sharedData.getMyData().getData().getUserId()
                            // sharedData.getMyData().getData().getBranchInfo().get(0).getBranchId()
                    );
                    orderDetailsModelCall.enqueue(new Callback<MenuFoodListModel>() {
                        @Override
                        public void onResponse(Call<MenuFoodListModel> call, Response<MenuFoodListModel> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getStatus().contentEquals("success")) {
                                    baseServerListener.onServerSuccessOrFailure(true, response.body(), "success");
                                } else {
                                    baseServerListener.onServerSuccessOrFailure(false, null, response.body().getError().getError_msg());
                                    progressDialogOwn.showToast(context, response.body().getMessage());
                                    if (response.body().getError().getError_code().contentEquals("401"))
                                        new LogoutUtility().setLoggedOut((Activity) context);
                                }

                            } else {
                                baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                                progressDialogOwn.showToast(context, context.getString(R.string.error_into_server));

                            }
                            if (isLoading)
                                progressDialogOwn.hideDialog();

                        }

                        @Override
                        public void onFailure(Call<MenuFoodListModel> call, Throwable t) {
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
}
