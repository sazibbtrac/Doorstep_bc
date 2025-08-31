package com.btracsolution.deliverysystem.Features.Agent.AgentMenuList;

import android.content.Context;

import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Model.CommonDataResponse;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.ProgressDialogOwn;
import com.btracsolution.deliverysystem.Utility.SharedData;
import com.btracsolution.deliverysystem.network.api.RetroHubApiInterface;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mahmudul.hasan on 2/14/2018.
 */

public class AgentMenuDetailsModel {
    Context context;
    BaseServerListener baseServerListener;

    @Inject
    RetroHubApiInterface retroHubApiInterface;
    @Inject
    SharedData sharedData;
    @Inject
    ProgressDialogOwn progressDialogOwn;

    public AgentMenuDetailsModel(Context context, BaseServerListener baseServerListener) {
        ((DeliveryApp) context.getApplicationContext()).getDoNetworkComponent().inject(this);
        this.context = context;
        this.baseServerListener = baseServerListener;
    }

    public void changeMenuFoodAvailability(String food_id, String FoodStatus) {
        if (retroHubApiInterface != null) {
            if (sharedData.getMyData() != null) {

                progressDialogOwn.showDialog(context, context.getString(R.string.loading));
                Call<CommonDataResponse> orderDetailsModelCall = retroHubApiInterface.changeOrderStatus(
                        sharedData.getMyData().getData().getApiToken(),
                        sharedData.getMyData().getData().getUserId(),
                        food_id,
                        FoodStatus
                );
                orderDetailsModelCall.enqueue(new Callback<CommonDataResponse>() {
                    @Override
                    public void onResponse(Call<CommonDataResponse> call, Response<CommonDataResponse> response) {
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
                    public void onFailure(Call<CommonDataResponse> call, Throwable t) {
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

}
