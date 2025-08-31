package com.btracsolution.deliverysystem.Features.Rider.Features.IncomingCall;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewbinding.ViewBinding;

import com.btracsolution.deliverysystem.Base.BaseActivity;
import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.Features.Rider.Features.RiderOrderDetails.RiderOrderDetailActivity;
import com.btracsolution.deliverysystem.Features.Rider.Features.RiderOrderDetails.RiderOrderDetailsModel;
import com.btracsolution.deliverysystem.Firebase.MyRingtoneService;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.Model.PushModelData;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.AgentGlobal;
import com.btracsolution.deliverysystem.Utility.RiderGlobal;
import com.btracsolution.deliverysystem.databinding.RiderActivityIncomingCallBinding;
import com.google.gson.Gson;



/**
 * Created by mahmudul.hasan on 2/20/2018.
 */

public class RiderIncomingCallActivity extends BaseActivity<RiderActivityIncomingCallBinding> implements View.OnClickListener {


    String AcceptType;
    String orderID;
    String shortNote;
    //private RiderActivityIncomingCallBinding binding;

    //OrderDetailsModel.orderBasicData OrderList;
    RiderOrderDetailsModel agentOrderDetailsModel;
    private PowerManager.WakeLock fullWakeLock;
    private PowerManager.WakeLock partialWakeLock;
    private CountDownTimer countdownTimer;
    PushModelData.Datum pushData;

    private Vibrator vibrator;

    BaseServerListener baseServerListener = new BaseServerListener() {
        @Override
        public void onServerSuccessOrFailure(boolean isSuccess, Object object, String message) {
            if (isSuccess) {

                switch (AcceptType) {


                    case "3":
                        if (countdownTimer != null)
                            countdownTimer.cancel();

                        if (vibrator != null)
                            vibrator.cancel();

                        RiderGlobal.isComesFromDetails = true;
                        showToast(message);
                        //OrderList.setDeliverystatus("1");
                        RiderOrderDetailActivity.open(RiderIncomingCallActivity.this, null,"push",orderID);
                        finish();

                        break;

                }

            } else {
                showAlert(message);
            }

        }
    };
    private Window wind;
    private String usertype;

    public void unloackDevice() {

//        getWindow().setType(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        wind = this.getWindow();
        wind.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        wind.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        wind.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        reverseTimer(30, binding.txOrderTimeCountDown);

    }

    //@OnClick(R.id.iv_decline)
    public void decline() {
        stopService(new Intent(this, MyRingtoneService.class));
        if (fullWakeLock.isHeld()) {
            fullWakeLock.release();
        }
        if (partialWakeLock.isHeld()) {
            partialWakeLock.release();
        }

        if (vibrator != null)
            vibrator.cancel();


    }

  //  @OnClick(R.id.iv_accept)
    public void accept() {

        AcceptType = "3";
        if (agentOrderDetailsModel != null) {
            agentOrderDetailsModel.sendJobAcknowldegementResponse(orderID, "1");
        }
        stopService(new Intent(this, MyRingtoneService.class));
        if (fullWakeLock.isHeld()) {
            fullWakeLock.release();
        }
        if (partialWakeLock.isHeld()) {
            partialWakeLock.release();
        }

        if (vibrator != null)
            vibrator.cancel();


    }



    @Override
    public void initView() {
        super.initView();
        agentOrderDetailsModel = new RiderOrderDetailsModel(RiderIncomingCallActivity.this, baseServerListener);
        createWakeLocks();
        wakeDevice();
        getIntentData();
        setOnClickListener();


    }

    @Override
    protected RiderActivityIncomingCallBinding getViewBinding() {
       return RiderActivityIncomingCallBinding.inflate(getLayoutInflater());
    }

    private void setOnClickListener() {
        binding.ivDecline.setOnClickListener(this);
        binding.ivAccept.setOnClickListener(this);
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)) {
            //Do something
            stopService(new Intent(this, MyRingtoneService.class));

            if (vibrator != null)
                vibrator.cancel();

        }
        return true;
    }

    // Called from onCreate
    protected void createWakeLocks() {
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);

// Use a unique prefix like your app's package name
        fullWakeLock = powerManager.newWakeLock(
                (PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP),
                "com.myapp:FULL_WAKE_LOCK"
        );

        partialWakeLock = powerManager.newWakeLock(
                PowerManager.PARTIAL_WAKE_LOCK,
                "com.myapp:PARTIAL_WAKE_LOCK"
        );

    }

    // Called implicitly when device is about to sleep or application is backgrounded
    protected void onPause() {
        super.onPause();

        stopService(new Intent(this, MyRingtoneService.class));
        RiderGlobal.isIncomingCallInitiated = false;
        partialWakeLock.acquire();

        if (countdownTimer != null)
            countdownTimer.cancel();

        if (vibrator != null)
            vibrator.cancel();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RiderGlobal.isIncomingCallInitiated = false;

        stopService(new Intent(this, MyRingtoneService.class));

        if (countdownTimer != null)
            countdownTimer.cancel();

        if (vibrator != null)
            vibrator.cancel();

    }

    // Called implicitly when device is about to wake up or foregrounded
    protected void onResume() {
        super.onResume();

        RiderGlobal.isIncomingCallInitiated = true;

        if (fullWakeLock.isHeld()) {
            fullWakeLock.release();
        }
        if (partialWakeLock.isHeld()) {
            partialWakeLock.release();
        }

        unloackDevice();
        vibrate();
        startService(new Intent(this, MyRingtoneService.class));
        setTitleOfActivity(getString(R.string.incoming_job));
      //  registerReceiver();



    }


    private BroadcastReceiver receiver;

    public void registerReceiver() {
        System.out.println("called on received registerReceiver ");
        IntentFilter filter = new IntentFilter();
        filter.addAction("notification.details");


        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //do something based on the intent's action
                System.out.println("called on received");

                if (intent != null) {
                    String type = intent.getStringExtra("type");
                    String order_type = intent.getStringExtra("order_type");
                    if (type.equals("uiUpdate")) {
                        switch (order_type) {
                            case "cancelJob":
                                stopService(new Intent(getApplicationContext(), MyRingtoneService.class));
                                if (fullWakeLock.isHeld()) {
                                    fullWakeLock.release();
                                }
                                if (partialWakeLock.isHeld()) {
                                    partialWakeLock.release();
                                }

                                if (countdownTimer != null)
                                    countdownTimer.cancel();

                                if (vibrator != null)
                                    vibrator.cancel();

                                RiderGlobal.isComesFromDetails = true;

                                showToast(getString(R.string.order_cancel_by_callcenter_agent));
                                finish();
                                break;
                        }
                    }
                }


            }
        };
        try {
            registerReceiver(receiver, filter);


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("called exceptoin " + e.toString());
        }
    }

    // Called whenever we need to wake up the device
    public void wakeDevice() {
        fullWakeLock.acquire();

        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("TAG");
        keyguardLock.disableKeyguard();
    }

    public void getIntentData() {
        if (getIntent() != null) {
            usertype = getIntent().getStringExtra("usertype");
            pushData = new Gson().fromJson(getIntent().getStringExtra("basic_data"), PushModelData.Datum.class);
            //OrderList = new Gson().fromJson(getIntent().getStringExtra("basic_data"), OrderDetailsModel.orderBasicData.class);
            if (pushData.getWaiter()!=null){binding.txCustomerName.setText(pushData.getWaiter().getFullname());}
            binding.txOrderNumber.setText(pushData.getOrdernumber());
            orderID = pushData.getOrderid();
            shortNote = "no-short-note";
          //  setPaymentMethodStatus();
            binding.txPmethod.setVisibility(View.GONE);
            checkUserTypeAndChangeUiAsPerUserType(usertype);

        }
    }
    private void setPaymentMethodStatus() {
        if (pushData.getPaymentmethod()!=null && pushData.getPaymentmethod().equals("1")){
            binding.txPmethod.setText("Payment Method: COD");
        }else if(pushData.getPaymentmethod()!=null && pushData.getPaymentmethod().equals("2")){
            binding.txPmethod.setText("Payment Method: CARD");
        }else if(pushData.getPaymentmethod()!=null && pushData.getPaymentmethod().equals("3")){
            binding.txPmethod.setText("Payment Method: bKash");
        }
    }
    public void checkUserTypeAndChangeUiAsPerUserType(String usertype) {
        if (usertype != null) {
            switch (usertype) {
                case "rider":
                    binding.ivDecline.setVisibility(View.GONE);
                    break;
                case "agent":
                    break;
            }
        }
    }

    public void setTitleOfActivity(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    public void reverseTimer(int Seconds, final TextView tv) {

        countdownTimer = new CountDownTimer(Seconds * 1000 + 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
/*
                int hours = seconds / (60 * 60);
                int tempMint = (seconds - (hours * 60 * 60));
                int minutes = tempMint / 60;
                seconds = tempMint - (minutes * 60);*/
                tv.setText("" + seconds + " sec");


            }

            public void onFinish() {
                RiderGlobal.isComesFromDetails = true;
                showToast(getString(R.string.job_auto_canceled));
                finish();


            }
        }.start();
    }


    public void vibrate() {
        // Get instance of Vibrator from current Context
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

// Start without a delay
// Vibrate for 100 milliseconds
// Sleep for 1000 milliseconds
        long[] pattern = {0, 1000, 1000};

// The '0' here means to repeat indefinitely
// '0' is actually the index at which the pattern keeps repeating from (the start)
// To repeat the pattern from any other point, you could increase the index, e.g. '1'
        vibrator.vibrate(pattern, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_accept:
                accept();
                break;

            case R.id.iv_decline:
                decline();
                break;

        }
    }
}
