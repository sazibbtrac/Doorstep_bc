package com.btracsolution.deliverysystem.Features.Forgot;

import android.app.Activity;

import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Model.ActivationDataResponse;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.ProgressDialogOwn;
import com.btracsolution.deliverysystem.Utility.Validation;

import javax.inject.Inject;

/**
 * Created by mahmudul.hasan on 3/15/2018.
 */

public class ForgotOwnPresenter {

    Activity activity;
    ForgotPasswordSMSsend forgotPasswordSMSsend;
    ForgotModel forgotModel;

    @Inject
    Validation validation;

    @Inject
    ProgressDialogOwn progressDialogOwn;

    BaseServerListener baseServerListener = new BaseServerListener() {
        @Override
        public void onServerSuccessOrFailure(boolean isSuccess, Object object, String message) {
            if (isSuccess) {
                ActivationDataResponse activationDataResponse = (ActivationDataResponse) object;
                if (activationDataResponse != null) {
                    showDialog(activationDataResponse.getMessage());
                    forgotPasswordSMSsend.ViewVisibleSubmitCode(activationDataResponse.getResponsData().getResetcode());
                }
            } else
                showDialog(message);


        }
    };

    public ForgotOwnPresenter(Activity activity, ForgotPasswordSMSsend forgotPasswordSMSsend) {
        this.activity = activity;
        this.forgotPasswordSMSsend = forgotPasswordSMSsend;
        ((DeliveryApp) activity.getApplicationContext()).getDoNetworkComponent().inject(this);
        forgotModel = new ForgotModel(activity, baseServerListener);

    }

    public void sendActivationCode(String type, String phoneEmail) {
        if (isValidationPass(type, phoneEmail)) {
            switch (type) {
                case "email":
                    forgotModel.sendValidationCode(phoneEmail, "1");
                    break;
                case "phone":
                    forgotModel.sendValidationCode(phoneEmail, "0");
                    break;
            }
        } else {
            switch (type) {
                case "email":
                    showDialog(activity.getString(R.string.error_invalid_email));
                    break;
                case "phone":
                    showDialog(activity.getString(R.string.error_invalid_phone));
                    break;
            }
        }

    }

    public void showDialog(String message) {
        if (progressDialogOwn != null)
            progressDialogOwn.showInfoAlertDialog(activity, message);
    }

    boolean isValidationPass(String type, String phoneEmail) {
        switch (type) {
            case "email":
                if (validation != null) {
                    return validation.isValidEmail(phoneEmail);
                }

                break;
            case "phone":
                if (validation != null) {
                    return validation.isValidPhoneNumber(phoneEmail);
                }
                break;
        }
        return false;

    }
}
