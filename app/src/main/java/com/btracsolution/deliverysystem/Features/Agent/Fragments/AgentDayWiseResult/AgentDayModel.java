package com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentDayWiseResult;

import android.content.Context;

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
 * Created by mahmudul.hasan on 2/8/2018.
 */

public class AgentDayModel {

    Context context;
    BaseServerListener baseServerListener;

    @Inject
    RetroHubApiInterface retroHubApiInterface;
    @Inject
    SharedData sharedData;
    @Inject
    ProgressDialogOwn progressDialogOwn;

    public AgentDayModel(Context context, BaseServerListener baseServerListener) {
        this.context = context;
        this.baseServerListener = baseServerListener;
        ((DeliveryApp) context.getApplicationContext()).getDoNetworkComponent().inject(this);

    }

    public void getServerData(String FromDate, String ToDate) {
        if (Connectivity.isConnected(context)) {
            if (retroHubApiInterface != null) {
                if (sharedData.getMyData() != null) {

                    progressDialogOwn.showDialog(context, context.getString(R.string.loading));
                    Call<OrderDetailsModel> orderDetailsModelCall = retroHubApiInterface.getOrderList(
                            sharedData.getMyData().getData().getApiToken(),
                            sharedData.getMyData().getData().getUserId(),
                            String.valueOf(sharedData.getMyData().getData().getKitchenInfo().get(0).getKitchenId()),
                            //sharedData.getMyData().getData().getBranchInfo().get(0).getBranchId(),
                            FromDate,
                            ToDate
                    );
                    orderDetailsModelCall.enqueue(new Callback<OrderDetailsModel>() {
                        @Override
                        public void onResponse(Call<OrderDetailsModel> call, Response<OrderDetailsModel> response) {
                            // System.out.println("response "+response.raw().toString());
                            if (response.isSuccessful()) {
                                if (response.body().getStatus().contentEquals("success")) {
                                    baseServerListener.onServerSuccessOrFailure(true, response.body(), "success");
                                } else
                                    baseServerListener.onServerSuccessOrFailure(false, response.body(), response.body().getError().getError_msg());

                            } else
                                baseServerListener.onServerSuccessOrFailure(false, response.body(), context.getString(R.string.error_into_server));
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
    }

}
