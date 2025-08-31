package com.btracsolution.deliverysystem.Features.Forgot;

import android.app.Activity;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Model.CommonDataResponse;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.ProgressDialogOwn;

import javax.inject.Inject;

/**
 * Created by mahmudul.hasan on 3/15/2018.
 */

public class ForgotUpdatePasswordPresenter {

    Activity activity;
    ForgotUpdatePassword forgotUpdatePassword;
    ForgotModel forgotModel;


    @Inject
    ProgressDialogOwn progressDialogOwn;


    BaseServerListener baseServerListener = new BaseServerListener() {
        @Override
        public void onServerSuccessOrFailure(boolean isSuccess, Object object, String message) {
            if (isSuccess) {
                CommonDataResponse activationDataResponse = (CommonDataResponse) object;
                if (activationDataResponse != null) {
                    showDialogSuccess(activationDataResponse.getMessage());
                }
            } else
                showDialog(message);


        }
    };

    public ForgotUpdatePasswordPresenter(Activity activity, ForgotUpdatePassword forgotUpdatePassword) {
        this.activity = activity;
        this.forgotUpdatePassword = forgotUpdatePassword;
        ((DeliveryApp) activity.getApplicationContext()).getDoNetworkComponent().inject(this);

        forgotModel = new ForgotModel(activity, baseServerListener);
    }


    public void showDialog(String message) {
        if (progressDialogOwn != null)
            progressDialogOwn.showInfoAlertDialog(activity, message);
    }


    public void showDialogSuccess(String message) {
        AlertDialog.Builder alBuilder = new AlertDialog.Builder(activity);
        alBuilder.setMessage(message);
        alBuilder.setPositiveButton(activity.getString(R.string.close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finish();
            }
        }).show();
    }


    public void updatePassword(String resetcode, String password, String password_confirmation) {
        forgotModel.updatePassword(resetcode, password, password_confirmation);
    }
}
