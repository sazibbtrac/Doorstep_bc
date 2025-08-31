package com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterIncommingCall;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.btracsolution.deliverysystem.Base.BaseActivity;
import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.Features.Agent.AgentOrderDetails.AgentOrderDetailsModel;
import com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterJobList.WaiterJoblistDetail.WaiterOrderDetailActivity;
import com.btracsolution.deliverysystem.Firebase.MyRingtoneService;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.Model.PushModelData;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.AgentGlobal;
import com.btracsolution.deliverysystem.Utility.CustomAlertDialog;
import com.btracsolution.deliverysystem.Utility.DialogClickInterface;
import com.btracsolution.deliverysystem.Utility.ProgressDialogOwn;
import com.btracsolution.deliverysystem.databinding.WaiterIncommingCallBinding;
import com.google.gson.Gson;



public class WaiterIncommingCall extends BaseActivity<WaiterIncommingCallBinding> implements DialogClickInterface,View.OnClickListener {

    private static final String TAG = "IncomingCallActivity";

    String AcceptType;
    String orderID;
    String shortNote;
/*

    @BindView(R.id.tx_customer_name)
    TextView tx_customer_name;
    @BindView(R.id.tx_order_number)
    TextView tx_order_number;
    @BindView(R.id.iv_accept)
    ImageView iv_accept;
    @BindView(R.id.iv_decline)
    ImageView iv_decline;
    @BindView(R.id.tx_order_time_count_down)
    TextView tx_order_time_count_down;
    @BindView(R.id.tx_shipping_address)
    TextView tx_shipping_address;
    @BindView(R.id.tx_pmethod)
    TextView tx_pmethod;
*/

    //OrderDetailsModel.orderBasicData OrderList;
    PushModelData.Datum pushData;
    AgentOrderDetailsModel agentOrderDetailsModel;
    private PowerManager.WakeLock fullWakeLock;
    private PowerManager.WakeLock partialWakeLock;
    private CountDownTimer countdownTimer;

    private Vibrator vibrator;

    BaseServerListener baseServerListener = new BaseServerListener() {
        @Override
        public void onServerSuccessOrFailure(boolean isSuccess, Object object, String message) {
            if (isSuccess) {
                Log.d(TAG, "Server Success " + message);

                switch (AcceptType) {
                    case "12":

                        if (countdownTimer != null)
                            countdownTimer.cancel();


                        if (vibrator != null)
                            vibrator.cancel();

                        AgentGlobal.isComesFromDetails = true;

                        showToast(message);
                        //OrderList.setOrderstatus("12");
                        WaiterOrderDetailActivity.open(WaiterIncommingCall.this, null,"push",orderID,AcceptType);
                        finish();

                        break;

                    case "11":
                        if (countdownTimer != null)
                            countdownTimer.cancel();

                        if (vibrator != null)
                            vibrator.cancel();

                        AgentGlobal.isComesFromDetails = true;

                        showToast(message);
                        finish();
                        break;

                    case "15":

                        if (countdownTimer != null)
                            countdownTimer.cancel();


                        if (vibrator != null)
                            vibrator.cancel();

                        AgentGlobal.isComesFromDetails = true;

                        showToast(message);
                       // OrderList.setOrderstatus("15");
                        WaiterOrderDetailActivity.open(WaiterIncommingCall.this, null,"push",orderID,AcceptType);
                        finish();

                        break;

                }

            } else {
                showAlert(message);
                Log.d(TAG, "Server Failed " + message);

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

        reverseTimer(60, binding.txOrderTimeCountDown);


    }

   // @OnClick(R.id.iv_decline)
    public void decline() {
        stopService(new Intent(this, MyRingtoneService.class));
        if (fullWakeLock.isHeld()) {
            fullWakeLock.release();
        }
        if (partialWakeLock.isHeld()) {
            partialWakeLock.release();
        }

        AcceptType = "11";
        if (agentOrderDetailsModel != null) {
            CustomAlertDialog.getInstance().showConfirmDialog("CONFIRM REJECT?", "Are you sure want to reject the request?", "Reject", "Close", this, 4, this);

        }


    }

    //@OnClick(R.id.iv_accept)
    public void accept() {
        try {
            stopService(new Intent(this, MyRingtoneService.class));
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

            AgentGlobal.isComesFromDetails = true;

            // showToast(message);
            // OrderList.setOrderstatus(OrderList.getOrderstatus());
            WaiterOrderDetailActivity.open(WaiterIncommingCall.this, null,"push",orderID,String.valueOf(pushData.getOrderstatus()));
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        AcceptType = "2";
//        if (agentOrderDetailsModel != null) {
//            Log.d(TAG, "Accept Button Pressed, AcceptType: " + AcceptType + "Order ID : " + orderID + "Short Note : " + shortNote);
//
//            agentOrderDetailsModel.sendJobAcceptResponse(AcceptType, orderID, shortNote);
//        }


    }



    @Override
    public void initView() {
        super.initView();
        agentOrderDetailsModel = new AgentOrderDetailsModel(WaiterIncommingCall.this, baseServerListener);
        createWakeLocks();
        wakeDevice();
        getIntentData();
        setOnclickListener();


    }

    private void setOnclickListener() {
        binding.ivAccept.setOnClickListener(this);
        binding.ivDecline.setOnClickListener(this);
    }

    @Override
    protected WaiterIncommingCallBinding getViewBinding() {
        return WaiterIncommingCallBinding.inflate(getLayoutInflater());
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

// Use a unique prefix, such as your app's package name
        String prefix = "com.Doorstep.loneworker";

        fullWakeLock = powerManager.newWakeLock(
                (PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP),
                prefix + ":FULL_WAKE_LOCK"
        );

        partialWakeLock = powerManager.newWakeLock(
                PowerManager.PARTIAL_WAKE_LOCK,
                prefix + ":PARTIAL_WAKE_LOCK"
        );
    }

    // Called implicitly when device is about to sleep or application is backgrounded
    protected void onPause() {
        super.onPause();

        stopService(new Intent(this, MyRingtoneService.class));
        AgentGlobal.isIncomingCallInitiatedWaiter = false;
        partialWakeLock.acquire();

        if (countdownTimer != null)
            countdownTimer.cancel();

        if (vibrator != null)
            vibrator.cancel();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AgentGlobal.isIncomingCallInitiatedWaiter = false;

        stopService(new Intent(this, MyRingtoneService.class));

        if (countdownTimer != null)
            countdownTimer.cancel();

        if (vibrator != null)
            vibrator.cancel();
        unregisterReceiver(receiver);

    }

    // Called implicitly when device is about to wake up or foregrounded
    protected void onResume() {
        super.onResume();

        AgentGlobal.isIncomingCallInitiatedWaiter = true;

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
        registerReceiver();

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
            binding.txCustomerName.setText(pushData.getMember().getName());
            binding.txOrderNumber.setText(pushData.getOrdernumber());
            if (pushData.getDeliveryzone() !=null && pushData.getTableno()!=null) {
                binding.txShippingAddress.setText(pushData.getDeliveryzone().getZonename()+", "+pushData.getTableno().getTablename());
            }
            setPaymentMethodStatus();
            orderID = pushData.getOrderid();
            shortNote = "no-short-note";
            checkUserTypeAndChangeUiAsPerUserType(usertype);

        }
    }

    private void setPaymentMethodStatus() {
//        if (OrderList.getPaymentmethod() != null && OrderList.getPaymentmethod().equals("1")) {
//            tx_pmethod.setText("Payment Method: COD");
//        } else if (OrderList.getPaymentmethod() != null && OrderList.getPaymentmethod().equals("2")) {
//            tx_pmethod.setText("Payment Method: CARD");
//        } else if (OrderList.getPaymentmethod() != null && OrderList.getPaymentmethod().equals("3")) {
//            tx_pmethod.setText("Payment Method: bKash");
//        }
        binding.txPmethod.setVisibility(View.GONE);
    }

    public void checkUserTypeAndChangeUiAsPerUserType(String usertype) {
        if (usertype != null) {
            switch (usertype) {
                case "rider":

                    break;
                case "agent":
                    break;
                case "waiter":
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
                AgentGlobal.isComesFromDetails = true;
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

                                AgentGlobal.isComesFromDetails = true;

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

    @Override
    public void onClickPositiveButton(DialogInterface pDialog, int pDialogIntefier, String shortNote) {

        hideKeyboard();
        System.out.println("Order ID" + orderID + " AcceptType" + AcceptType + "shortNote" + shortNote);
        agentOrderDetailsModel.sendJobAcceptResponse(AcceptType, orderID, shortNote);


    }

    @Override
    public void onClickNegativeButton(DialogInterface pDialog, int pDialogIntefier) {
        hideKeyboard();
        pDialog.dismiss();

    }

    public void hideKeyboard() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
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
