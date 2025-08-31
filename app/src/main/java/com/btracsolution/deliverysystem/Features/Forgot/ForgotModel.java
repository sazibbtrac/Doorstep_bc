package com.btracsolution.deliverysystem.Features.Forgot;

import android.content.Context;

import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Model.ActivationDataResponse;
import com.btracsolution.deliverysystem.Model.CommonDataResponse;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.ProgressDialogOwn;
import com.btracsolution.deliverysystem.network.api.RetroHubApiInterface;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mahmudul.hasan on 3/15/2018.
 */

public class ForgotModel {

    Context context;
    BaseServerListener baseServerListener;

    @Inject
    RetroHubApiInterface retroHubApiInterface;
    @Inject
    ProgressDialogOwn progressDialogOwn;

    public ForgotModel(Context context, BaseServerListener baseServerListener) {
        ((DeliveryApp) context.getApplicationContext()).getDoNetworkComponent().inject(this);
        this.context = context;
        this.baseServerListener = baseServerListener;
    }

    public void sendValidationCode(String username, String emailormobile) {
        if (retroHubApiInterface != null) {

            progressDialogOwn.showDialog(context, context.getString(R.string.loading));
            Call<ActivationDataResponse> orderDetailsModelCall = retroHubApiInterface.sendActivationCode(
                    username,
                    emailormobile
            );
            orderDetailsModelCall.enqueue(new Callback<ActivationDataResponse>() {
                @Override
                public void onResponse(Call<ActivationDataResponse> call, Response<ActivationDataResponse> response) {
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
                public void onFailure(Call<ActivationDataResponse> call, Throwable t) {
                    if (t != null)
                        baseServerListener.onServerSuccessOrFailure(false, null, t.getMessage());
                    else
                        baseServerListener.onServerSuccessOrFailure(false, null, context.getString(R.string.error_into_server));
                    progressDialogOwn.hideDialog();

                }
            });
        }
    }

    public void updatePassword(String resetcode, String password, String password_confirmation) {
        if (retroHubApiInterface != null) {

            progressDialogOwn.showDialog(context, context.getString(R.string.loading));
            Call<CommonDataResponse> orderDetailsModelCall = retroHubApiInterface.udpateResetPassword(
                    resetcode,
                    password,
                    password_confirmation
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
