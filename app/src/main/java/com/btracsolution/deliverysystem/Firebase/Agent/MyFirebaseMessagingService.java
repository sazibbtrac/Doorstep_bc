package com.btracsolution.deliverysystem.Firebase.Agent;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.btracsolution.deliverysystem.AppsPackageUpdateChecker.AppUpdateAndShowingEmergencyMessages;
import com.btracsolution.deliverysystem.AppsPackageUpdateChecker.RemoteKeys.FirebaseRemoteKeys;
import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Features.Agent.AgentHomeActivity;
import com.btracsolution.deliverysystem.Features.Agent.IncomingCall.IncomingCallActivity;
import com.btracsolution.deliverysystem.Features.Rider.Features.IncomingCall.RiderIncomingCallActivity;
import com.btracsolution.deliverysystem.Features.Rider.HomeActivity;
import com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterIncommingCall.WaiterIncommingCall;
import com.btracsolution.deliverysystem.Features.Waiter.WaiterActivity;
import com.btracsolution.deliverysystem.Firebase.MyRingtoneService;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Services.ConstantApp;
import com.btracsolution.deliverysystem.Services.HeadsUpNotificationService;
import com.btracsolution.deliverysystem.Utility.AgentGlobal;
import com.btracsolution.deliverysystem.Utility.RiderGlobal;
import com.btracsolution.deliverysystem.Utility.SharedData;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import javax.inject.Inject;


/**
 * Created by mahmudul.hasan on 2/20/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService implements AppUpdateAndShowingEmergencyMessages.OnGetBaseUrlListener{


    private static final String TAG = "MyFirebaseMsgService";
    @Inject
    SharedData sharedData;

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
      //  sendRegistrationToServer(token);
    }
    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

      //  ((DeliveryApp) getApplicationContext()).initializeInjector();
       // ((DeliveryApp) getApplicationContext()).getDoNetworkComponent().inject(this);


        DeliveryApp app = (DeliveryApp) getApplicationContext(); // 🔹 Use getApplicationContext()

        if (app != null) {
            app.initializeInjector(); // Ensure injector is initialized
            if (app.getDoNetworkComponent() != null) {
                app.getDoNetworkComponent().inject(this);
            } else {
                Log.e("FCM", "Dagger component is NULL after initialization!");
            }
        } else {
            Log.e("FCM", "Application instance is NULL!");
        }





        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom()+ ": "+remoteMessage.getData());

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (sharedData.getMyData() != null) {

           //     CreateNotificationChannel();

                Log.d(TAG,"Vlaues for activity "+AgentGlobal.isAgentActivityOpen+"Rider: "+RiderGlobal.isAgentActivityOpen
                +"Waiter: "+AgentGlobal.isAgentActivityOpenWaiter+" incoming Agent: "+AgentGlobal.isIncomingCallInitiated+" incoming Rider: "
                +RiderGlobal.isIncomingCallInitiated+" incoming waiter : "+AgentGlobal.isIncomingCallInitiatedWaiter);

                if (remoteMessage.getData().get("usertype") != null && remoteMessage.getData().get("usertype").contentEquals("branchagent") && sharedData.getMyData().getData().getUserType().contentEquals("2")) {


                    switch (remoteMessage.getData().get("tag")) {

                        case "new_order_placed":
                            if (AgentGlobal.isAgentActivityOpen) {
                                Log.d(TAG,"Values for kitchen: "+AgentGlobal.isAgentActivityOpen);
                                sendBroadcast("uiUpdate");
                               // sendNotification(getString(R.string.new_order_placed), getString(R.string.a_new_order_placed), "payload", remoteMessage.getData().get("usertype"));


                                //new
                                try {

                                    JSONArray jsonArray = new JSONArray(remoteMessage.getData().get("data"));


                                    //JSONObject jsonObject = new JSONObject(remoteMessage.getData().get("body"));
                                    Intent intentHome = new Intent(this, IncomingCallActivity.class);
                                    intentHome.putExtra("usertype", "agent");
                                    intentHome.putExtra("basic_data", jsonArray.get(0).toString());
                                    //intentHome.putExtra("basic_data", jsonObject.getJSONArray("data").get(0).toString());
                                    intentHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intentHome);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.d(TAG, "Message error: " + e.getMessage());

                                }


                            } else {
                                Log.d(TAG,"cmscsdfjsd Rider: "+AgentGlobal.isIncomingCallInitiated);
                                Log.d(TAG,"Values for kitchen else: "+AgentGlobal.isAgentActivityOpen);
                             //   sendNotification(getString(R.string.new_order_placed), getString(R.string.a_new_order_placed), "payload", remoteMessage.getData().get("usertype"));
                             //   checkAppUpdateAndMessage();
                                if (!AgentGlobal.isIncomingCallInitiated) {

                                    Log.d(TAG,"Values for kitchen else call: "+AgentGlobal.isAgentActivityOpen);

                                    try {

                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                            JSONArray jsonArray = new JSONArray(remoteMessage.getData().get("data"));
                                            //JSONObject jsonObject = new JSONObject(remoteMessage.getData().get("body"));
                                           // startFullScreenIntentNotification(jsonArray,"agent");
                                        }
                                        {
                                            //new
                                            JSONArray jsonArray = new JSONArray(remoteMessage.getData().get("data"));
                                            // JSONObject jsonObject = new JSONObject(remoteMessage.getData().get("body"));
                                            Intent intentHome = new Intent(this, IncomingCallActivity.class);
                                            intentHome.putExtra("usertype", "agent");
                                            intentHome.putExtra("basic_data", jsonArray.get(0).toString());
                                            intentHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intentHome);
                                        }




                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                            break;

                        case "order_canceled":
                            sendBroadcast("cancelJob");
                            sendBroadcastDetails("uiUpdate", "cancelJob");
                            sendNotification(getString(R.string.new_order_cancel), getString(R.string.order_cancel_by_callcenter_agent)/*"order_cancel" + remoteMessage.getData().get("body")*/, "payload", remoteMessage.getData().get("usertype"));
                            break;

                        case "new_message":
                            sendNotification(getString(R.string.new_message), "new_message" + remoteMessage.getData().get("body"), "payload", remoteMessage.getData().get("usertype"));
                            break;


                        case "order_picked_up":
                            sendBroadcast("uiUpdate");
                            sendBroadcastDetails("uiUpdate", "order_picked_up");
                            sendNotification(remoteMessage.getData().get("title"), getString(R.string.a_order_pickedup), "payload", remoteMessage.getData().get("usertype"));
                            break;

                        case "order_partially_picked_up":
                            sendBroadcast("uiUpdate");
                            sendBroadcastDetails("uiUpdate", "order_partially_picked_up");
                            sendNotification(remoteMessage.getData().get("title"), getString(R.string.a_order_pickedup), "payload", remoteMessage.getData().get("usertype"));
                            break;

                        case "order_in_pantry":
                            sendBroadcast("uiUpdate");
                            sendBroadcastDetails("uiUpdate", "order_in_pantry");
                            sendNotification(getString(R.string.inpantry), getString(R.string.food_placed_in_pantry)/*"order_cancel" + remoteMessage.getData().get("body")*/, "payload", remoteMessage.getData().get("usertype"));
                            break;


                        case "order_partially_in_pantry":
                            sendBroadcast("uiUpdate");
                            sendBroadcastDetails("uiUpdate", "order_partially_in_pantry");
                            sendNotification(getString(R.string.inpantry), getString(R.string.food_placed_in_pantry)/*"order_cancel" + remoteMessage.getData().get("body")*/, "payload", remoteMessage.getData().get("usertype"));
                            break;

                        case "order_delivered":
                            sendBroadcast("uiUpdate");
                            sendBroadcastDetails("uiUpdate", "order_delivered");
                            sendNotification(remoteMessage.getData().get("title"), getString(R.string.a_order_delivered), "payload", remoteMessage.getData().get("usertype"));
                            break;
                        case "order_partially_delivered":
                            sendBroadcast("uiUpdate");
                            sendBroadcastDetails("uiUpdate", "order_partially_delivered");
                            sendNotification(remoteMessage.getData().get("title"), getString(R.string.a_order_delivered), "payload", remoteMessage.getData().get("usertype"));
                            break;

                        default:
                            sendBroadcast("uiUpdate");
                            sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("title"), "payload", remoteMessage.getData().get("usertype"));


                    }
                } else if (remoteMessage.getData().get("usertype") != null && remoteMessage.getData().get("usertype").contentEquals("rider") && sharedData.getMyData().getData().getUserType().contentEquals("4")) {
                    switch (remoteMessage.getData().get("tag")) {

                        case "incoming_order_rider":

                            //new
                            try {
                                // Parse the JSON array

                                // this is done now
                                if (!isThisUserGotPush("rider", getRiderID(remoteMessage.getData().get("data")))) {
                                   // System.out.println("MyFirebaseMsgService "+RiderGlobal.isAgentActivityOpen+": "+RiderGlobal.isIncomingCallInitiated);

                                    Log.d(TAG, "hahshddfhdf "+RiderGlobal.isAgentActivityOpen);
                                    return;
                                }
                                // System.out.println("this is one 1 "+new JSONArray(remoteMessage.getData().get("data")).get(0));
                            } catch (Exception e) {
                                System.out.println("sdhfdhfd "+e.toString());
                                throw new RuntimeException(e);
                            }

                            if (RiderGlobal.isAgentActivityOpen) {
                                sendBroadcast("uiUpdate");
                                //sendNotification(getString(R.string.new_order_placed), getString(R.string.click_to_open_order), "payload", remoteMessage.getData().get("usertype"));


                                //new

                                try {
                                    JSONArray jsonArray = new JSONArray(remoteMessage.getData().get("data"));
                                    //JSONObject jsonObject = new JSONObject(remoteMessage.getData().get("body"));
                                    Intent intentHome = new Intent(this, RiderIncomingCallActivity.class);
                                    intentHome.putExtra("usertype", "rider");
                                    intentHome.putExtra("basic_data", jsonArray.get(0).toString());
                                    intentHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intentHome);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.d(TAG, "Message error: " + e.getMessage());

                                }


                            } else {
                                Log.d(TAG,"cmscsdfjsd Rider outside: "+RiderGlobal.isIncomingCallInitiated+": "+RiderGlobal.isAgentActivityOpen);
                               // sendNotification(getString(R.string.new_order_placed), getString(R.string.click_to_open_order), "payload", remoteMessage.getData().get("usertype"));

                                if (!RiderGlobal.isIncomingCallInitiated) {
                                    Log.d(TAG,"cmscsdfjsd Rider: "+RiderGlobal.isIncomingCallInitiated+": "+RiderGlobal.isAgentActivityOpen);


                                    try {

                                        JSONArray jsonArray = new JSONArray(remoteMessage.getData().get("data"));
                                        //  JSONObject jsonObject = new JSONObject(remoteMessage.getData().get("body"));
                                        Intent intentHome = new Intent(this, RiderIncomingCallActivity.class);
                                        intentHome.putExtra("usertype", "rider");
                                        intentHome.putExtra("basic_data", jsonArray.get(0).toString());
                                        intentHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intentHome);

                                     /*
                                       // close it for incoming call issue
                                       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                            System.out.println("this is one 5 ");
                                            Log.d(TAG,"cmscsdfjsd Rider if: "+RiderGlobal.isIncomingCallInitiated+": "+RiderGlobal.isAgentActivityOpen);

                                            JSONArray jsonObject = new JSONArray(remoteMessage.getData().get("data"));
                                          //  startFullScreenIntentNotification(jsonObject,"rider");
                                        }
                                        else {
                                            Log.d(TAG,"cmscsdfjsd Rider else: "+RiderGlobal.isIncomingCallInitiated+": "+RiderGlobal.isAgentActivityOpen);

                                            //new
                                            JSONArray jsonArray = new JSONArray(remoteMessage.getData().get("data"));
                                            //  JSONObject jsonObject = new JSONObject(remoteMessage.getData().get("body"));
                                            Intent intentHome = new Intent(this, RiderIncomingCallActivity.class);
                                            intentHome.putExtra("usertype", "rider");
                                            intentHome.putExtra("basic_data", jsonArray.get(0).toString());
                                            intentHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intentHome);

                                        }

*/
                                    } catch (Exception e) {
                                        Log.d(TAG,"cmscsdfjsd exception "+e.toString());
                                        e.printStackTrace();
                                    }

                                }
                            }
                            break;

                        case "order_canceled":

                            //new
                            if (!isThisUserGotPush("rider", getRiderID(remoteMessage.getData().get("data")))) {
                                return;
                            }
                            sendBroadcast("cancelJob");
                            sendNotification(getString(R.string.new_order_cancel), getString(R.string.order_cancel_by_agent)/*"order_cancel" + remoteMessage.getData().get("body")*/, "payload", remoteMessage.getData().get("usertype"));
                            break;



                        case "new_message":

                            //new
                            if (!isThisUserGotPush("rider", getRiderID(remoteMessage.getData().get("data")))) {
                                return;
                            }
                            sendNotification(getString(R.string.new_message), "new_message" + remoteMessage.getData().get("body"), "payload", remoteMessage.getData().get("usertype"));
                            break;

                        default:

                            //new
                            if (!isThisUserGotPush("rider", getRiderID(remoteMessage.getData().get("data")))) {
                                return;
                            }
                            sendBroadcast("uiUpdate");
                            sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("title"), "payload", remoteMessage.getData().get("usertype"));
                            break;


                    }
                }

                else if (remoteMessage.getData().get("usertype") != null && remoteMessage.getData().get("usertype").contentEquals("waiter") && sharedData.getMyData().getData().getUserType().contentEquals("3")) {
                    switch (remoteMessage.getData().get("tag")) {

                        case "order_in_pantry":

                            /* This is very sophisticated filter where no rider will get push if the rider not logged in */

                            //new
                            if (!isThisUserGotPush("waiter", getWaiterID(remoteMessage.getData().get("data")))) {
                                return;
                            }
                            if (AgentGlobal.isAgentActivityOpenWaiter) {
                                sendBroadcast("uiUpdate");
                               // sendNotification(getString(R.string.inpantry), getString(R.string.click_to_open_order), "payload", remoteMessage.getData().get("usertype"));

                                try {

                                    //new
                                    JSONArray jsonArray = new JSONArray(remoteMessage.getData().get("data"));
                                    //  JSONObject jsonObject = new JSONObject(remoteMessage.getData().get("body"));
                                    Intent intentHome = new Intent(this, WaiterIncommingCall.class);
                                    intentHome.putExtra("usertype", "waiter");
                                    intentHome.putExtra("basic_data", jsonArray.get(0).toString());
                                    intentHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intentHome);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            } else {
                                sendNotification(getString(R.string.inpantry), getString(R.string.click_to_open_order), "payload", remoteMessage.getData().get("usertype"));

                                if (!AgentGlobal.isIncomingCallInitiatedWaiter) {


                                    try {

                                        //new
                                        JSONArray jsonArray = new JSONArray(remoteMessage.getData().get("data"));
                                        //  JSONObject jsonObject = new JSONObject(remoteMessage.getData().get("body"));
                                        Intent intentHome = new Intent(this, WaiterIncommingCall.class);
                                        intentHome.putExtra("usertype", "waiter");
                                        intentHome.putExtra("basic_data", jsonArray.get(0).toString());
                                        intentHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intentHome);


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                            break;

             /*           case "confirmed_processing":
                            Log.d(TAG, "confirm processing  " + remoteMessage.getData().get("body") );

                            break;*/

                        case "order_partially_in_pantry":



                            //new
                            if (!isThisUserGotPush("waiter", getWaiterID(remoteMessage.getData().get("data")))) {
                                return;
                            }

                            if (AgentGlobal.isAgentActivityOpenWaiter) {
                                sendBroadcast("uiUpdate");
                              //  sendNotification(getString(R.string.p_inpantry), getString(R.string.click_to_open_order), "payload", remoteMessage.getData().get("usertype"));

                                try {

                                    //new
                                    JSONArray jsonArray = new JSONArray(remoteMessage.getData().get("data"));
                                    //  JSONObject jsonObject = new JSONObject(remoteMessage.getData().get("body"));
                                    Intent intentHome = new Intent(this, WaiterIncommingCall.class);
                                    intentHome.putExtra("usertype", "waiter");
                                    intentHome.putExtra("basic_data", jsonArray.get(0).toString());
                                    intentHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intentHome);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            } else {
                                sendNotification(getString(R.string.p_inpantry), getString(R.string.click_to_open_order), "payload", remoteMessage.getData().get("usertype"));

                                if (!AgentGlobal.isIncomingCallInitiatedWaiter) {


                                    try {

                                        //new
                                        JSONArray jsonArray = new JSONArray(remoteMessage.getData().get("data"));
                                        //  JSONObject jsonObject = new JSONObject(remoteMessage.getData().get("body"));
                                        Intent intentHome = new Intent(this, WaiterIncommingCall.class);
                                        intentHome.putExtra("usertype", "waiter");
                                        intentHome.putExtra("basic_data", jsonArray.get(0).toString());
                                        intentHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intentHome);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                            break;

                        case "order_canceled":
                            /*if (!isThisUserGotPush("waiter", remoteMessage.getData().get("body"))) {
                                return;
                            }*/
                            //new
                            if (!isThisUserGotPush("waiter", getWaiterID(remoteMessage.getData().get("data")))) {
                                return;
                            }


                            sendBroadcast("cancelJob");
                            sendNotification(getString(R.string.new_order_cancel), getString(R.string.order_cancel_by_agent)/*"order_cancel" + remoteMessage.getData().get("body")*/, "payload", remoteMessage.getData().get("usertype"));
                            break;

                        case "new_message":
                        /*    if (!isThisUserGotPush("waiter", remoteMessage.getData().get("body"))) {
                                return;
                            }*/
                            //new
                            if (!isThisUserGotPush("waiter", getWaiterID(remoteMessage.getData().get("data")))) {
                                return;
                            }
                            sendNotification(getString(R.string.new_message), "new_message" + remoteMessage.getData().get("body"), "payload", remoteMessage.getData().get("usertype"));
                            break;
                        case "ready_to_pickup":
                            sendBroadcast("uiUpdate");
                            sendBroadcastDetails("uiUpdate", "ready_to_pickup");
                            sendNotification(remoteMessage.getData().get("title"), getString(R.string.ready_to_pickuup), "payload", remoteMessage.getData().get("usertype"));
                            break;
                        case "order_picked_up":
                            sendBroadcast("uiUpdate");
                            sendBroadcastDetails("uiUpdate", "order_picked_up");
                            sendNotification(remoteMessage.getData().get("title"), getString(R.string.a_order_pickedup), "payload", remoteMessage.getData().get("usertype"));
                            break;
                        case "order_partially_picked_up":
                            sendBroadcast("uiUpdate");
                            sendBroadcastDetails("uiUpdate", "order_picked_up");
                            sendNotification(remoteMessage.getData().get("title"), getString(R.string.a_order_pickedup), "payload", remoteMessage.getData().get("usertype"));
                            break;
                        default:
                         /*   if (!isThisUserGotPush("waiter", remoteMessage.getData().get("body"))) {
                                return;
                            }*/

                            //new
                            if (!isThisUserGotPush("waiter", getWaiterID(remoteMessage.getData().get("data")))) {
                                return;
                            }
                            sendBroadcast("uiUpdate");
                            sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("title"), "payload", remoteMessage.getData().get("usertype"));
                            break;


                    }
                }
            }


        }


    }

    private void CreateNotificationChannel(){
        System.out.println("Attempting to send notification with custom sound");
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);

        // Create a unique channel ID
        String channelId = "delivery_notifications_2";

        // Define the custom sound URI - make sure "bc_new.mp3" exists in res/raw
        Uri customSoundUri = Uri.parse("android.resource://" + getPackageName() + "/raw/bc_new");
        System.out.println("Sound URI: " + customSoundUri.toString());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(channelId, "Notification From Waiter", importance);
            channel.setDescription("When Waiter Places and Order");

            // Set sound with proper attributes
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            channel.setSound(customSoundUri, audioAttributes);
            // Delete existing channel first (if updating settings)
            notificationManager.deleteNotificationChannel(channelId);
            // Create the channel
            notificationManager.createNotificationChannel(channel);

        }
        else{
            System.out.println("Channel Not Set, Version Not available");
        }
    }


    public void startFullScreenIntentNotification(JSONArray jsonArray,String userType){
        try {
            Intent serviceIntent=new Intent(MyFirebaseMessagingService.this, HeadsUpNotificationService.class);
            serviceIntent.putExtra(ConstantApp.FCM_DATA_KEY,jsonArray.get(0).toString());
            serviceIntent.putExtra(ConstantApp.USERTYPE,userType);
            ContextCompat.startForegroundService(getApplicationContext(), serviceIntent);




        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // [END receive_message]

    private String getRiderID(String data){

        int riderId = 0;

        try
        {
            JSONArray jsonArray = new JSONArray(data);

            // Access the first object in the array
            JSONObject firstObject = jsonArray.getJSONObject(0);

            // Access the "rider" object
            JSONObject riderObject = firstObject.getJSONObject("rider");

            // Get the "riderid"
            riderId = riderObject.getInt("riderid");

            // Print the riderid
            System.out.println("Rider ID: " + riderId);

        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return String.valueOf(riderId);
    }

    private String getWaiterID(String data){

        int riderId = 0;

        try
        {
            JSONArray jsonArray = new JSONArray(data);

            // Access the first object in the array
            JSONObject firstObject = jsonArray.getJSONObject(0);

            // Access the "rider" object
            JSONObject riderObject = firstObject.getJSONObject("waiter");

            // Get the "riderid"
            riderId = riderObject.getInt("userid");

            // Print the riderid
            System.out.println("Rider ID: " + riderId);

        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return String.valueOf(riderId);
    }




    private void sendBroadcast(String uiType) {
        Intent in = new Intent();
        in.setAction("notification");
        in.putExtra("type", uiType);
        in.setPackage("com.btracsolutions.doorstep");
        Log.d("sohail", "onHandleIntent: sending broadcast");
        sendBroadcast(in);
    }

    private void sendBroadcastDetails(String uiType, String order_type) {
        Intent in = new Intent();
        in.setAction("notification.details");
        in.putExtra("type", uiType);
        in.putExtra("order_type", order_type);
        in.setPackage("com.btracsolutions.doorstep");
        Log.d("sohail", "onHandleIntent: sending broadcast");
        sendBroadcast(in);
    }

    //test
    private void sendNotification(String title, String body, String data, String userType) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Define the custom sound URI (Ensure bc.mp3 or bc.ogg is in res/raw/)
        Uri customSoundUri = Uri.parse("android.resource://" + getPackageName() + "/raw/bc");

        // Create a NEW Notification Channel for Android 8+ with a unique ID
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "delivery_channel_v2"; // NEW channel ID
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);

            // Attach custom sound to the NotificationChannel
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            channel.setSound(customSoundUri, audioAttributes);
            channel.enableLights(true);
            channel.enableVibration(true);

            // Create the channel
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        // Generate a unique notification ID
        int notificationId = (int) System.currentTimeMillis();

        // Set up the notification intent
        Intent intent = null;
        if (userType.contentEquals("branchagent")) {
            intent = new Intent(this, AgentHomeActivity.class);
        } else if (userType.contentEquals("rider")) {
            intent = new Intent(this, HomeActivity.class);
        } else if (userType.contentEquals("waiter")) {
            intent = new Intent(this, WaiterActivity.class);
        }

        intent.putExtra("data", data);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_MUTABLE);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "delivery_channel_v2")
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.icon_door)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH) // Ensure high priority
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        // Show the notification
        if (notificationManager != null) {
            notificationManager.notify(notificationId, builder.build());
        }
    }


/*
//main
    private void sendNotification(String title, String body, String data, String userType) {
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);

        // Define the custom sound URI
        Uri customSoundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                getPackageName() + "/raw/bc"); // Replace 'custom_sound' with your actual sound file name

        // For Android 8.0 and above, configure NotificationChannel with custom sound
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.deleteNotificationChannel("default");
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel("com.btracsolutions.deliverysystem", name, importance);
            channel.setDescription(description);

            // reset sound
          //  channel.setSound(null, null);

            // Attach custom sound to the NotificationChannel
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            channel.setSound(customSoundUri, audioAttributes);

            // Create the channel
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        // Generate a unique notification ID
        long time = new Date().getTime();
        String tmpStr = String.valueOf(time);
        String last4Str = tmpStr.substring(tmpStr.length() - 5);
        int notificationId = Integer.parseInt(last4Str);

        // Set up the notification intent
        Intent intent = null;
        if (userType.contentEquals("branchagent")){
            intent = new Intent(this, AgentHomeActivity.class);
        }

        else if(userType.contentEquals("rider")){
            intent = new Intent(this, HomeActivity.class);
        }
        else if (userType.contentEquals("waiter")){
            intent = new Intent(this,WaiterActivity.class);
        }



        intent.putExtra("data", data);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "com.btracsolutions.deliverysystem")
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.icon_door) // Replace with your app's notification icon
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setSound(customSoundUri) // Custom sound for Android < 8.0
                .setContentIntent(pendingIntent);

        // Show the notification
        if (notificationManager != null) {
            notificationManager.notify(notificationId, builder.build());
        }
    }
*/



   /* private void sendNotification(String title, String body, String data, String userType) {
        // Start the foreground service to play sound
        Intent serviceIntent = new Intent(this, NotificationService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
        } else {
            startService(serviceIntent);
        }

        // Continue with notification setup
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Uri customSoundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/raw/bc");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("com.btracsolutions.deliverysystem",
                    "Delivery Notifications", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Delivery system notifications");
            notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(this, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "com.btracsolutions.deliverysystem")
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.icon_door)
                .setContentIntent(pendingIntent);

        notificationManager.notify(1001, builder.build());
    }

*/




    public boolean isThisUserGotPush(String typeOfUser, String myDataResponse) {

        switch (typeOfUser) {
            case "rider":
                //new
                if (myDataResponse != null) {
                    if (sharedData.getMyData() != null) {
                        if (sharedData.getMyData().getData().getUserId().contentEquals(myDataResponse))
                            return true;
                    }

                   else{
                        return false;
                    }

                }
                return false;
                //new





            case "agent":



                //new
            if (myDataResponse != null) {
                Gson gson = new Gson();
                OrderDetailsModel orderDetailsModel = gson.fromJson(myDataResponse, OrderDetailsModel.class);
                if (sharedData.getMyData() != null) {
                    if (sharedData.getMyData().getData().getUserId().contentEquals(orderDetailsModel.getOrderList().get(0).getRider().getRiderid()))
                        return true;
                } else
                    return false;
            }
            return false;

            case "waiter":
                //new
                //new
                Log.w(TAG,"user id is "+sharedData.getMyData().getData().getUserId()+":  "+myDataResponse);
                if (myDataResponse != null) {
                    if (sharedData.getMyData() != null) {
                        if (sharedData.getMyData().getData().getUserId().contentEquals(myDataResponse))
                            return true;
                    }

                    else{
                        return false;
                    }

                }
                return false;
                //new




            default:
                return false;
        }

    }

    public void checkAppUpdateAndMessage() {
        AppUpdateAndShowingEmergencyMessages appUpdateAndShowingEmergencyMessages = new AppUpdateAndShowingEmergencyMessages(MyFirebaseMessagingService.this, this);
        appUpdateAndShowingEmergencyMessages.prepareConfig();
        FirebaseRemoteKeys.base_url_link = appUpdateAndShowingEmergencyMessages.getBaseDafualt();

    }
    @Override
    public void onGetBaseUrl(String updateUrl) {
        FirebaseRemoteKeys.base_url_link = updateUrl;
        ((DeliveryApp) getApplication()).initializeInjector();
        ((DeliveryApp) getApplication()).getDoNetworkComponent().inject(this);
        System.out.println("respnse injectedd FirebaseMsgingService " + updateUrl);
    }
}
