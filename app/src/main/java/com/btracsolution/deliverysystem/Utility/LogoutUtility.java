package com.btracsolution.deliverysystem.Utility;

import android.app.Activity;
import android.content.Intent;

import com.btracsolution.deliverysystem.Features.Login.LoginActivity;

public class LogoutUtility {
    SharedData sharedData;

    public void setLoggedOut(Activity activity) {
        sharedData = new SharedData(activity);
        if (sharedData.getMyData() != null) {
            sharedData.unsetMyData();
            Intent intent = new Intent(activity, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
            activity.finish();
        }

    }
}
