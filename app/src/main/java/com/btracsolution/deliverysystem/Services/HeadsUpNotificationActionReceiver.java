package com.btracsolution.deliverysystem.Services;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.btracsolution.deliverysystem.Features.Agent.IncomingCall.IncomingCallActivity;
import com.btracsolution.deliverysystem.Features.Rider.Features.IncomingCall.RiderIncomingCallActivity;


public class HeadsUpNotificationActionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getExtras() != null) {
            String action = intent.getStringExtra(ConstantApp.CALL_RESPONSE_ACTION_KEY);
            String data = intent.getStringExtra(ConstantApp.FCM_DATA_KEY);
            String userType = intent.getStringExtra(ConstantApp.USERTYPE);

            if (action != null) {
                performClickAction(context, action, data,userType);
            }

            // Close the notification after the click action is performed.

//            Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
//            context.sendBroadcast(it);
//            context.stopService(new Intent(context, HeadsUpNotificationService.class));
        }
    }

    private void performClickAction(Context context, String action, String data,String userType) {
        if (action.equals(ConstantApp.CALL_RECEIVE_ACTION) && data != null && userType.contentEquals("agent")) {
            Intent openIntent = null;
            try {
                openIntent = new Intent(context, IncomingCallActivity.class);
                openIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                openIntent.putExtra("usertype", "agent");
                openIntent.putExtra("basic_data", data);
                context.startActivity(openIntent);

                context.stopService(new Intent(context, HeadsUpNotificationService.class));


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (action.equals(ConstantApp.CALL_RECEIVE_ACTION) && data != null && userType.contentEquals("rider")) {
            Intent openIntent = null;
            try {
                openIntent = new Intent(context, RiderIncomingCallActivity.class);
                openIntent.putExtra("usertype", "rider");
                openIntent.putExtra("basic_data", data);
                openIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(openIntent);

                context.stopService(new Intent(context, HeadsUpNotificationService.class));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (action.equals(ConstantApp.CALL_CANCEL_ACTION)) {
            try {
                context.stopService(new Intent(context, HeadsUpNotificationService.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
//            Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
//            context.sendBroadcast(it);

        }
    }
}
