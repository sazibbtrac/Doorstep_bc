package com.btracsolution.deliverysystem.Features.Agent.IncomingCall;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
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
import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Features.Agent.AgentOrderDetails.AgentOrderDetailActivity;
import com.btracsolution.deliverysystem.Features.Agent.AgentOrderDetails.AgentOrderDetailsModel;
import com.btracsolution.deliverysystem.Firebase.MyRingtoneService;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.Model.PushModelData;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.AgentGlobal;
import com.btracsolution.deliverysystem.Utility.CustomAlertDialog;
import com.btracsolution.deliverysystem.Utility.DialogClickInterface;
import com.btracsolution.deliverysystem.Utility.PrintUtility;
import com.btracsolution.deliverysystem.databinding.AgentActivityIncomingCallBinding;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

/*
import butterknife.BindView;
import butterknife.OnClick;
*/

/**
 * Created by mahmudul.hasan on 2/20/2018.
 */

public class IncomingCallActivity extends BaseActivity<AgentActivityIncomingCallBinding> implements DialogClickInterface,Runnable,View.OnClickListener{

    private static final String TAG = "IncomingCallActivity";

    String AcceptType;
    String orderID;
    String shortNote;

   /* @BindView(R.id.tx_customer_name)
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

*/    //For BluetoothConnection

    BluetoothAdapter mBluetoothAdapter;
    private UUID applicationUUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;
    private BluetoothSocket mBluetoothSocket;
    BluetoothDevice mBluetoothDevice;


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
                    case "2":

                        if (countdownTimer != null)
                            countdownTimer.cancel();


                        if (vibrator != null)
                            vibrator.cancel();

                        AgentGlobal.isComesFromDetails = true;

                        showToast(message);
                        //OrderList.setOrderstatus("2");
                        AgentOrderDetailActivity.open(IncomingCallActivity.this, null,"push",orderID);
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

//
//        if(OrderList.getOrderfrom()!=null && OrderList.getOrderfrom().equals("1")) {
//            reverseTimer(60, tx_order_time_count_down);
//        }
//        else{
//            reverseTimer(60, tx_order_time_count_down);
//        }

    }

  //  @OnClick(R.id.iv_decline)
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
            CustomAlertDialog.getInstance().showConfirmDialog("CONFIRM REJECT?","Are you sure want to reject the request?","Reject","Close",this,4, this);

        }


    }

  //  @OnClick(R.id.iv_accept)
    public void accept() {
        stopService(new Intent(this, MyRingtoneService.class));
        if (fullWakeLock.isHeld()) {
            fullWakeLock.release();
        }
        if (partialWakeLock.isHeld()) {
            partialWakeLock.release();
        }

        AcceptType = "2";
        if (agentOrderDetailsModel != null) {
            Log.d(TAG, "Accept Button Pressed, AcceptType: " + AcceptType+"Order ID : "+orderID+"Short Note : "+shortNote);

            agentOrderDetailsModel.sendJobAcceptResponse(AcceptType, orderID, shortNote);
        }
        if (mBluetoothSocket==null){
            mBluetoothSocket=DeliveryApp.getInstance().getCurrentBluetoothConnection();
        }
        if (mBluetoothSocket!=null && mBluetoothSocket.isConnected()) {
          //  PrintUtility.printKitchen(mBluetoothSocket,OrderList);
        }
    }
/*
    @Override
    protected int getLayoutId() {
        return R.layout.agent_activity_incoming_call;
    }*/

    @Override
    public void initView() {
        super.initView();
        agentOrderDetailsModel = new AgentOrderDetailsModel(IncomingCallActivity.this, baseServerListener);
        setOnclicklistener();
        createWakeLocks();
        wakeDevice();
        getIntentData();

        mBluetoothSocket=DeliveryApp.getInstance().getCurrentBluetoothConnection();
        if (mBluetoothSocket!=null && mBluetoothSocket.isConnected()){
            showToast("Printer Connected");
        }else{
            DeliveryApp.getInstance().setupBluetoothConnection();
        }

    }

    private void setOnclicklistener() {
        binding.ivAccept.setOnClickListener(this);
        binding.ivDecline.setOnClickListener(this);
    }

    @Override
    protected AgentActivityIncomingCallBinding getViewBinding() {
        return AgentActivityIncomingCallBinding.inflate(getLayoutInflater());
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
        String prefix = "com.yourapp.loneworker";

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
        AgentGlobal.isIncomingCallInitiated = false;
        partialWakeLock.acquire();

        if (countdownTimer != null)
            countdownTimer.cancel();

        if (vibrator != null)
            vibrator.cancel();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AgentGlobal.isIncomingCallInitiated = false;

        stopService(new Intent(this, MyRingtoneService.class));

        if (countdownTimer != null)
            countdownTimer.cancel();

        if (vibrator != null)
            vibrator.cancel();
        unregisterReceiver(receiver);

//        try {
//            if (mBluetoothSocket != null)
//                mBluetoothSocket.close();
//        } catch (Exception e) {
//            Log.e("Tag", "Exe ", e);
//        }

    }

    // Called implicitly when device is about to wake up or foregrounded
    protected void onResume() {
        super.onResume();

        AgentGlobal.isIncomingCallInitiated = true;

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
        try {
            if (getIntent() != null) {
                usertype = getIntent().getStringExtra("usertype");
                pushData = new Gson().fromJson(getIntent().getStringExtra("basic_data"), PushModelData.Datum.class);
              //  pushData=pushModelData.getData().get(0);

                binding.txCustomerName.setText(""+pushData.getWaiter().getFullname());
                binding.txOrderNumber.setText(""+pushData.getOrdernumber());
                if (pushData.getDeliveryzone() !=null && pushData.getTableno()!=null){
                    binding.txShippingAddress.setText(pushData.getDeliveryzone().getZonename()+", "+pushData.getTableno().getTablename());
                }
                //setPaymentMethodStatus();
                binding.txPmethod.setVisibility(View.GONE);
                orderID = pushData.getOrderid();
                shortNote = "no-short-note";
                checkUserTypeAndChangeUiAsPerUserType(usertype);

            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
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
        System.out.println("Order ID"+orderID+" AcceptType"+AcceptType+"shortNote"+shortNote);
        agentOrderDetailsModel.sendJobAcceptResponse(AcceptType, orderID, shortNote);


    }

    @Override
    public void onClickNegativeButton(DialogInterface pDialog, int pDialogIntefier) {
        hideKeyboard();
        pDialog.dismiss();

    }
    public  void hideKeyboard(){
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }


    //BluetoothPrinter Connection
    @SuppressLint("MissingPermission")
    private void ListPairedDevices() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter
                .getBondedDevices();
        if (mPairedDevices.size() > 0) {
            for (BluetoothDevice mDevice : mPairedDevices) {
                Log.v(TAG, "PairedDevices: " + mDevice.getName() + "  "
                        + mDevice.getAddress());
                if (mDevice.getName().contentEquals("InnerPrinter")) {
                    String mDeviceAddress = mDevice.getAddress();
                    Log.v(TAG, "Coming incoming address " + mDeviceAddress);
                    mBluetoothDevice = mBluetoothAdapter
                            .getRemoteDevice(mDeviceAddress);

                    Thread mBlutoothConnectThread = new Thread(IncomingCallActivity.this);
                    mBlutoothConnectThread.start();
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void run() {
        try {
            mBluetoothSocket = mBluetoothDevice
                    .createRfcommSocketToServiceRecord(applicationUUID);
            mBluetoothAdapter.cancelDiscovery();
            mBluetoothSocket.connect();
        } catch (IOException eConnectException) {
            Log.d(TAG, "CouldNotConnectToSocket", eConnectException);
            closeSocket(mBluetoothSocket);
            return;
        }
    }
    private void closeSocket(BluetoothSocket nOpenSocket) {
        try {
            nOpenSocket.close();
            Log.d(TAG, "SocketClosed");
        } catch (IOException ex) {
            Log.d(TAG, "CouldNotCloseSocket");
        }
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
