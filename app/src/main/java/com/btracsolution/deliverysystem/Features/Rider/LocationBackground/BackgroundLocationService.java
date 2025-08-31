package com.btracsolution.deliverysystem.Features.Rider.LocationBackground;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Features.Login.LoginActivity;
import com.btracsolution.deliverysystem.Model.CommonDataResponse;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.Connectivity;
import com.btracsolution.deliverysystem.Utility.FirebaseReportManualLog;
import com.btracsolution.deliverysystem.Utility.SharedData;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

/**
 * BackgroundLocationService used for tracking user location in the background.
 *
 * @author cblack
 */
public class BackgroundLocationService extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    @Inject
    SharedData sharedData;

    IBinder mBinder = new LocalBinder();
    BaseServerListener baseServerListener = new BaseServerListener() {
        @Override
        public void onServerSuccessOrFailure(boolean isSuccess, Object object, String message) {
            if (isSuccess) {
                Log.d("location", "updated");
            } else {
                if (object != null) {
                    try {
                        CommonDataResponse commonDataResponse = (CommonDataResponse) object;
                        Log.d("location", "not updated" + commonDataResponse.getError().getError_msg());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else
                    Log.d("location", "not updated" + message);


            }
        }
    };
    private GoogleApiClient mGoogleApiClient;
    private PowerManager.WakeLock mWakeLock;
    private LocationRequest mLocationRequest;
    // Flag that indicates if a request is underway.
    private boolean mInProgress;
    private Boolean servicesAvailable = false;

    @Override
    public void onCreate() {
        super.onCreate();
        ((DeliveryApp) getApplicationContext()).getDoNetworkComponent().inject(this);


        mInProgress = false;
        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create();
        // Use high accuracy
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        // Set the update interval to 5 seconds
        mLocationRequest.setInterval(Constants.UPDATE_INTERVAL);
        // Set the fastest update interval to 1 second
        mLocationRequest.setFastestInterval(Constants.FASTEST_INTERVAL);

        mLocationRequest.setSmallestDisplacement(10);

        servicesAvailable = servicesConnected();

        /*
         * Create a new location client, using the enclosing class to
         * handle callbacks.
         */
        setUpLocationClientIfNeeded();


    }

    /*
     * Create a new location client, using the enclosing class to
     * handle callbacks.
     */
    protected synchronized void buildGoogleApiClient() {
        this.mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private boolean servicesConnected() {

        // Check that Google Play services is available
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {

            return true;
        } else {

            return false;
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        PowerManager mgr = (PowerManager) getSystemService(Context.POWER_SERVICE);

        /*
        WakeLock is reference counted so we don't want to create multiple WakeLocks. So do a check before initializing and acquiring.

        This will fix the "java.lang.Exception: WakeLock finalized while still held: MyWakeLock" error that you may find.
        */
        if (this.mWakeLock == null) { //**Added this
            this.mWakeLock = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakeLock");
        }

        if (!this.mWakeLock.isHeld()) { //**Added this
            this.mWakeLock.acquire();
        }

        if (!servicesAvailable || mGoogleApiClient.isConnected() || mInProgress)
            return START_STICKY;

        setUpLocationClientIfNeeded();
        if (!mGoogleApiClient.isConnected() || !mGoogleApiClient.isConnecting() && !mInProgress) {
            appendLog(DateFormat.getDateTimeInstance().format(new Date()) + ": Started", Constants.LOG_FILE);
            mInProgress = true;
            mGoogleApiClient.connect();


        }
        return START_STICKY;
    }

    private void setUpLocationClientIfNeeded() {
        if (mGoogleApiClient == null)
            buildGoogleApiClient();
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }

    }

    // Define the callback method that receives location updates
    @Override
    public void onLocationChanged(Location location) {
        // Report to the UI that the location was updated
        /*String msg = Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Log.d("debug", msg);*/
//         Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
//        appendLog(DateFormat.getDateTimeInstance().format(new Date()) + ":" + msg, Constants.LOCATION_FILE);
        updateLocationIntoServer(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    public void updateLocationIntoServer(LatLng latlng) {


        if (Connectivity.isConnected(this)) {
            BackgroundLocationUpdateModel backgroundLocationUpdateModel = new BackgroundLocationUpdateModel(this, baseServerListener);
            if (sharedData != null && sharedData.getMyData() != null) {
                showNotification("connected", getString(R.string.have_connected_internet));
                backgroundLocationUpdateModel.updateMyCurrentLocation(latlng);

            } else {
                showNotification("connected", getString(R.string.not_logged_in));

            }
        } else
            showNotification("non_connected", getString(R.string.no_internet_connection));


    }

    public void showNotification(String type, String message) {
        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("default", name, importance);
            channel.setDescription(description);

            // Don't see these lines in your code...
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        switch (type) {
            case "connected":
                notification = new NotificationCompat.Builder(this,"default")
                        .setSmallIcon(R.drawable.icon_door)
                        .setContentTitle("Rider - Doorstep")
                        .setContentText("Online")
                        .build();


                break;
            case "non_connected":
                notification = new NotificationCompat.Builder(this,"notification")
                        .setSmallIcon(R.drawable.icon_door)
                        .setContentTitle("Rider - Doorstep")
                        .setContentText(message)
                        .build();
                break;
        }
        if (notification != null) {
            notification.contentIntent = PendingIntent.getActivity(this, 0,
                    new Intent(this, LoginActivity.class), PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
            startForeground(56, notification);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public String getTime() {
        SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return mDateFormat.format(new Date());
    }

    public void appendLog(String text, String filename) {
        File logFile = new File(filename);
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(text);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        // Turn off the request flag
        try {
            this.mInProgress = false;

            if (this.servicesAvailable && this.mGoogleApiClient != null) {
                this.mGoogleApiClient.unregisterConnectionCallbacks(this);
                this.mGoogleApiClient.unregisterConnectionFailedListener(this);
                this.mGoogleApiClient.disconnect();
                // Destroy the current location client
                this.mGoogleApiClient = null;
            }
            // Display the connection status
            // Toast.makeText(this, DateFormat.getDateTimeInstance().format(new Date()) + ":
            // Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();

            if (this.mWakeLock != null) {
                this.mWakeLock.release();
                this.mWakeLock = null;
            }
            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(56);
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseReportManualLog.report("service-crash", e);
        }
        super.onDestroy();
    }

    /*
     * Called by Location Services when the request to connect the
     * client finishes successfully. At this point, you can
     * request the current location or start periodic updates
     */
    @Override
    public void onConnected(Bundle bundle) {

        // Request location updates using static settings
        Intent intent = new Intent(this, LocationReceiver.class);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        setUpLocationClientIfNeeded();

        try {
            if (this.mGoogleApiClient != null)
                LocationServices.FusedLocationApi.requestLocationUpdates(this.mGoogleApiClient,
                        mLocationRequest, this); // This is the changed line.
            appendLog(DateFormat.getDateTimeInstance().format(new Date()) + ": Connected", Constants.LOG_FILE);
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseReportManualLog.report("firebase-location-service-exception", e);
        }

    }

    /*
 * Called by Location Services if the connection to the
 * location client drops because of an error.
 */
    @Override
    public void onConnectionSuspended(int i) {
        // Turn off the request flag
        mInProgress = false;
        // Destroy the current location client
        mGoogleApiClient = null;
        // Display the connection status
        // Toast.makeText(this, DateFormat.getDateTimeInstance().format(new Date()) + ": Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
        appendLog(DateFormat.getDateTimeInstance().format(new Date()) + ": Disconnected", Constants.LOG_FILE);
    }

    /*
     * Called by Location Services if the attempt to
     * Location Services fails.
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        mInProgress = false;

        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {

            // If no resolution is available, display an error dialog
        } else {

        }
    }

    public class LocalBinder extends Binder {
        public BackgroundLocationService getServerInstance() {
            return BackgroundLocationService.this;
        }
    }
}