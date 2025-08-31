package com.btracsolution.deliverysystem;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.btracsolution.deliverysystem.AppsPackageUpdateChecker.RemoteKeys.FirebaseRemoteKeys;
import com.btracsolution.deliverysystem.Depedency.di.components.DaggerDoNetworkComponent;
import com.btracsolution.deliverysystem.Depedency.di.components.DaggerNetComponent;
import com.btracsolution.deliverysystem.Depedency.di.components.DoNetworkComponent;
import com.btracsolution.deliverysystem.Depedency.di.components.NetComponent;
import com.btracsolution.deliverysystem.Depedency.di.modules.AppModule;
import com.btracsolution.deliverysystem.Depedency.di.modules.DoNetworkModule;
import com.btracsolution.deliverysystem.Depedency.di.modules.NetModule;
import com.btracsolution.deliverysystem.Depedency.di.modules.RoomModule;
import com.btracsolution.deliverysystem.Features.Waiter.Features.Bill.BillActivity;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import dagger.internal.DaggerCollections;

/**
 * Created by mahmudul.hasan on 12/28/2017.
 */

public class DeliveryApp extends Application implements Runnable {


    private static DeliveryApp sInstance;
    BluetoothAdapter mBluetoothAdapter;
    private UUID applicationUUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;
    public BluetoothSocket mBluetoothSocket;
    BluetoothDevice mBluetoothDevice;
    private static final String TAG = "BillActivity";


    private NetComponent mNetComponent;
    private DoNetworkComponent doNetworkComponent;


    public static DeliveryApp getInstance() {
        return sInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        initializeInjector();
        if (Build.VERSION.SDK_INT < 31) {
            setupBluetoothConnection();
        }

        registerActivityLifecycleCallbacks(new AppLifecycleTracker());

    }

    public void initializeInjector() {

        // specify the full namespace of the component
        // Dagger_xxxx (where xxxx = component name)
        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(FirebaseRemoteKeys.base_url_link))
                .build();

        doNetworkComponent = DaggerDoNetworkComponent.builder()
                .netComponent(mNetComponent)
                .doNetworkModule(new DoNetworkModule())
                .build();
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }

    public DoNetworkComponent getDoNetworkComponent() {
        if (doNetworkComponent == null) {
            Log.e("DeliveryApp", "Dagger component is NULL! Reinitializing...");
            initializeInjector(); // Reinitialize if needed
        }
        return doNetworkComponent;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    public void setupBluetoothConnection() {
        // Either setup your connection here, or pass it in
        ListPairedDevices();
    }

    public BluetoothSocket getCurrentBluetoothConnection() {
        return mBluetoothSocket;
    }

    @SuppressLint("MissingPermission")
    private void ListPairedDevices() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            Log.e("BluetoothError", "Device does not support Bluetooth");
            return;  // Exit early if Bluetooth is not supported
        }


        Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter
                .getBondedDevices();
        if (mPairedDevices.size() > 0) {
            for (BluetoothDevice mDevice : mPairedDevices) {
                Log.v(TAG, "PairedDevices: " + mDevice.getName() + "  "
                        + mDevice.getAddress());
                if (mDevice.getName().contentEquals("InnerPrinter") || mDevice.getName().contentEquals("V2")) {
                    String mDeviceAddress = mDevice.getAddress();
                    Log.v(TAG, "Coming incoming address " + mDeviceAddress);
                    mBluetoothDevice = mBluetoothAdapter
                            .getRemoteDevice(mDeviceAddress);

                    Thread mBlutoothConnectThread = new Thread(this);
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
        } finally {
//            Looper.prepare();// to be able to make toast
//            if (mBluetoothSocket.isConnected()) {
//                showToast("Printer connected");
//            }else{
//             //   showToast("Not connected");
//            }
//            Looper.loop();
        }
    }

    private void closeSocket(BluetoothSocket nOpenSocket) {
        try {
            if (nOpenSocket != null) {
                nOpenSocket.close();
                //  Log.d(TAG, "SocketClosed");
            }
        } catch (IOException ex) {
            //  Log.d(TAG, "CouldNotCloseSocket");
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        try {
            if (mBluetoothSocket != null)
                mBluetoothSocket.close();
        } catch (Exception e) {
            Log.e("Tag", "Exe ", e);
        }
    }
}
