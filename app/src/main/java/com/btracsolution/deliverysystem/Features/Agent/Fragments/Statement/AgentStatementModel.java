package com.btracsolution.deliverysystem.Features.Agent.Fragments.Statement;

import android.content.Context;

import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Model.StatementModel;
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
 * Created by mahmudul.hasan on 3/20/2018.
 */

public class AgentStatementModel {


    Context context;
    BaseServerListener baseServerListener;

    @Inject
    RetroHubApiInterface retroHubApiInterface;
    @Inject
    SharedData sharedData;
    @Inject
    ProgressDialogOwn progressDialogOwn;

    public AgentStatementModel(Context context, BaseServerListener baseServerListener) {
        ((DeliveryApp) context.getApplicationContext()).getDoNetworkComponent().inject(this);
        this.context = context;
        this.baseServerListener = baseServerListener;
    }


    public void getServerData(final boolean isLoading, String fromDate, String toDate) {
        if (Connectivity.isConnected(context)) {
            if (retroHubApiInterface != null) {
                if (sharedData.getMyData() != null) {

                    if (isLoading)
                        progressDialogOwn.showDialog(context, context.getString(R.string.loading));
                    Call<StatementModel> orderDetailsModelCall = retroHubApiInterface.getStatementData(
                            sharedData.getMyData().getData().getApiToken(),
                            sharedData.getMyData().getData().getUserId(),
                            String.valueOf(sharedData.getMyData().getData().getKitchenInfo().get(0).getKitchenId()),
                            fromDate,
                            toDate
                    );
                    orderDetailsModelCall.enqueue(new Callback<StatementModel>() {
                        @Override
                        public void onResponse(Call<StatementModel> call, Response<StatementModel> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getStatus().contentEquals("success")) {
                                    baseServerListener.onServerSuccessOrFailure(true, response.body(), "success");
                                } else
                                    baseServerListener.onServerSuccessOrFailure(false, response.body(), response.body().getError().getError_msg());

                            } else
                                baseServerListener.onServerSuccessOrFailure(false, response.body(), context.getString(R.string.error_into_server));
                            if (isLoading)
                                progressDialogOwn.hideDialog();

                        }

                        @Override
                        public void onFailure(Call<StatementModel> call, Throwable t) {
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
        }else {
            progressDialogOwn.showInfoAlertDialog(context, context.getResources().getString(R.string.no_internet_connection));
        }
    }


}
