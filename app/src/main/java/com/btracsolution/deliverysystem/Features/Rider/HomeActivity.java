package com.btracsolution.deliverysystem.Features.Rider;

import static android.content.ContentValues.TAG;

import static com.btracsolution.deliverysystem.Features.Rider.LocationBackground.Constants.UPDATE_INTERVAL;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.btracsolution.deliverysystem.AppsPackageUpdateChecker.AppUpdateAndShowingEmergencyMessages;
import com.btracsolution.deliverysystem.AppsPackageUpdateChecker.RemoteKeys.FirebaseRemoteKeys;
import com.btracsolution.deliverysystem.Base.BaseActivity;
import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Features.Agent.AgentHomeActivity;
import com.btracsolution.deliverysystem.Features.Rider.Features.MyJobPack.RiderJobList;
import com.btracsolution.deliverysystem.Features.Rider.Features.RiderProfile.ProfileRider;
import com.btracsolution.deliverysystem.Features.Rider.Features.RiderRecentOrder.RiderDayReport;
import com.btracsolution.deliverysystem.Features.Rider.LocationBackground.BackgroundLocationService;
import com.btracsolution.deliverysystem.Features.Rider.LocationBackground.BackgroundLocationUpdateModel;
import com.btracsolution.deliverysystem.Features.Rider.LocationBackground.Constants;
import com.btracsolution.deliverysystem.Model.CommonDataResponse;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.Connectivity;
import com.btracsolution.deliverysystem.Utility.FirebaseReportManualLog;
import com.btracsolution.deliverysystem.Utility.RiderGlobal;
import com.btracsolution.deliverysystem.Utility.SharedData;
import com.btracsolution.deliverysystem.databinding.ActivityHomeBinding;
import com.btracsolution.deliverysystem.databinding.ActivityWaiterBinding;
import com.btracsolution.deliverysystem.network.api.RetroHubApiInterface;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.gun0912.tedpermission.PermissionListener;
//import com.gun0912.tedpermission.TedPermission;
import com.gun0912.tedpermission.normal.TedPermission;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

//import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mahmudul.hasan on 12/28/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class HomeActivity extends BaseActivity<ActivityHomeBinding> implements HomepageView, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, AppUpdateAndShowingEmergencyMessages.OnGetBaseUrlListener {


    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    @Inject
    SharedData sharedData;
    @Inject
    RetroHubApiInterface retroHubApiInterface;
/*    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottom_navigation;*/
    HomePresenterImpl homePresenter;
    FragmentManager fragmentManager;
    Fragment fragment;
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
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
/*
    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
//            buildGoogleApiClient();
//            initBackgroundLocation();


        }


        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(HomeActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();

        }


    };
*/

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            if (canGetLocation()) {
                //DO SOMETHING USEFUL HERE. ALL GPS PROVIDERS ARE CURRENTLY ENABLED
            } else {
                //SHOW OUR SETTINGS ALERT, AND LET THE USE TURN ON ALL THE GPS PROVIDERS
                enableLoc();
                //showSettingsAlert();
            }

            initBackgroundLocation();
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(HomeActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }


    };

    private void enableLoc() {



        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(UPDATE_INTERVAL);


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result =
                LocationServices.getSettingsClient(this).checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {


            @Override
            public void onComplete(Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    // All location settings are satisfied. The client can initialize location
                    // requests here.

                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                // Cast to a resolvable exception.
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                int LOCATION_SETTINGS_REQUEST=1010;
                                resolvable.startResolutionForResult(
                                        HomeActivity.this,
                                        LOCATION_SETTINGS_REQUEST);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            } catch (ClassCastException e) {
                                // Ignore, should be an impossible error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            }
        });

    }



    public boolean canGetLocation() {
        boolean result = true;
        LocationManager lm;
        boolean gpsEnabled = false;
        boolean networkEnabled = false;

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // exceptions will be thrown if provider is not permitted.
        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            networkEnabled = lm
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        return gpsEnabled && networkEnabled;
    }


    private BroadcastReceiver receiver;

    public static void open(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //item.setShiftingMode(false);
                item.setShifting(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            //Timber.e(e, "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            //Timber.e(e, "Unable to change value of shift mode");
        }
    }

    @Override
    public void initView() {
        super.initView();
        initializeToolbar();
        ((DeliveryApp) getApplication()).initializeInjector();
        ((DeliveryApp) getApplication()).getDoNetworkComponent().inject(this);

        homePresenter = new HomePresenterImpl();
        homePresenter.attach(this);
        checkAppUpdateAndMessage();
        setUpNavigationManager();
        doRestTask();
        //setTitleOfActivity(getString(R.string.ResName));

        checkLocationPermission();
        checkAppsOverlayPermission();
        checkBatteryOptimization();
        checkNotificationPermission();

    }

    public void checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {


/*            new TedPermission(HomeActivity.this)
                    .setPermissionListener(permissionlistener)
                    .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                    .setPermissions(Manifest.permission.POST_NOTIFICATIONS)
                    .check();*/

            //new
            TedPermission.create()
                    .setPermissionListener(permissionlistenerpush)
                    .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                    .setPermissions(Manifest.permission.POST_NOTIFICATIONS)
                    .check();
        }
    }

    PermissionListener permissionlistenerpush = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            //  Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(HomeActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }


    };



    @Override
    protected ActivityHomeBinding getViewBinding() {
        return ActivityHomeBinding.inflate(getLayoutInflater());
    }

    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5469;
    public void checkAppsOverlayPermission(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
            }

        }
    }


    public void setTitleOfActivity(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

/*    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }*/

    private void initializeToolbar() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(
                    ContextCompat.getColor(HomeActivity.this, R.color.colorAccent));
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    public void setUpNavigationManager() {
        fragmentManager = getSupportFragmentManager();
        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_my_job:
                        fragment = new RiderJobList();
                        break;
                    case R.id.action_my_completed_job:
                        fragment = new RiderDayReport();
                        break;
                    case R.id.action_my_profile:
                        fragment = new ProfileRider();
                        break;

                }
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();
                return true;
            }
        });
        disableShiftMode(binding.bottomNavigation);

        firstTime();
    }

    public void firstTime() {
        fragment = new RiderJobList();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, fragment).commit();
    }

    public void doRestTask() {
        if (sharedData != null) {
//            Toast.makeText(this, sharedData.getMyData(), Toast.LENGTH_SHORT).show();
            if (sharedData.getMyData() != null) {
                setTitleOfActivity(sharedData.getMyData().getData().getRestaurantInfo().getName() + " " + "" + " ");
                setSubTitleOfActivity(sharedData.getMyData().getData().getFullName());
                //System.out.println("response "+sharedData.getMyData().getData().getBranchInfo().get(0).getBranchName());
            }

        } else
            Toast.makeText(this, "Null", Toast.LENGTH_SHORT).show();

        getFirebaseToken();
    }

    public void setSubTitleOfActivity(String subtitle) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setSubtitle(subtitle);
        }
    }

    public void getFirebaseToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String fcmToken = task.getResult();
                        Log.w(TAG,"fireaser token: "+fcmToken);
                        sendTokenToServer(fcmToken);
                        // Log and toast

                    }
                });


    }

    private void sendTokenToServer(String fcmToken) {
        if (fcmToken != null && fcmToken.length() > 0) {
            if (retroHubApiInterface != null) {
                Call<CommonDataResponse> orderDetailsModelCall = retroHubApiInterface.updatedeviceid(
                        sharedData.getMyData().getData().getApiToken(),
                        sharedData.getMyData().getData().getUserId(),
                        fcmToken,
                        "no-imei");

                orderDetailsModelCall.enqueue(new Callback<CommonDataResponse>() {
                    @Override
                    public void onResponse(Call<CommonDataResponse> call, Response<CommonDataResponse> response) {
                        if (response.isSuccessful()) {

                            Log.d("TokenUpdate", "Sucess");

                        } else {
                            Log.d("TokenUpdate", "Failed");

                        }

                    }

                    @Override
                    public void onFailure(Call<CommonDataResponse> call, Throwable t) {
                        Log.d("TokenUpdate", "Failed");

                    }
                });
            }
        }
    }

    public void reloadRiderJobList(){
        if (currentFragment() instanceof RiderDayReport) {
            fragment = new RiderDayReport();
            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container, fragment).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        RiderGlobal.isAgentActivityOpen = true;
        registerReceiver();
        reloadRiderJobList();
    }

    @Override
    protected void onPause() {
        super.onPause();

        RiderGlobal.isAgentActivityOpen = false;
    }

    @Override
    protected void onDestroy() {
        RiderGlobal.isAgentActivityOpen = false;
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
//
//
//        try {
//
//            if (this.mGoogleApiClient != null) {
//                this.mGoogleApiClient.unregisterConnectionCallbacks(this);
//                this.mGoogleApiClient.unregisterConnectionFailedListener(this);
//                this.mGoogleApiClient.disconnect();
//                // Destroy the current location client
//                this.mGoogleApiClient = null;
//            }
//            // Display the connection status
//            // Toast.makeText(this, DateFormat.getDateTimeInstance().format(new Date()) + ":
//            // Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
//
//
//            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//            notificationManager.cancel(56);
//        } catch (Exception e) {
//            e.printStackTrace();
//            FirebaseReportManualLog.report("service-crash", e);
//        }


        super.onDestroy();
    }

    public void registerReceiver() {

        IntentFilter filter = new IntentFilter();
        filter.addAction("notification");


        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //do something based on the intent's action

                if (intent != null) {
                    String type = intent.getStringExtra("type");

                    switch (type) {
                        case "uiUpdate":
                            checkFragment();


                            break;
                        case "cancelJob":
                            checkFragment();
                            showToast(getString(R.string.order_cancel_by_agent));
                            break;
                    }

                }


            }
        };
        try {
            registerReceiver(receiver, filter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkFragment() {
        if (currentFragment() instanceof RiderJobList) {
            // Do something here
            ((RiderJobList) currentFragment()).riderJobPresenter.getServerData(false);
        } else {
//            bottom_navigation.setSelectedItemId(R.id.action_home);
        }
    }

    public Fragment currentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.main_container);
    }

    public void initBackgroundLocation() {
        if (!Connectivity.isMyServiceRunning(this,BackgroundLocationService.class)) {
            if (Connectivity.isConnected(HomeActivity.this)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    ContextCompat.startForegroundService(HomeActivity.this, new Intent(HomeActivity.this, BackgroundLocationService.class));
                } else {
                    startService(new Intent(HomeActivity.this, BackgroundLocationService.class));

                }
            }
        }
    }

    public void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.FOREGROUND_SERVICE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            // ✅ Permissions already granted, start location tracking
            System.out.println("sdskdss "+"ffffff");
            initBackgroundLocation();
        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                TedPermission.create()
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("If you reject permission, you cannot use this service.\n\nPlease enable permissions in [Settings] > [Permissions]")
                        .setPermissions(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                        .check();

                // Request FOREGROUND_SERVICE_LOCATION only on Android 14+
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) { // SDK 34
                    TedPermission.create()
                            .setPermissionListener(permissionlistener)
                            .setPermissions(Manifest.permission.FOREGROUND_SERVICE_LOCATION)
                            .check();
                }
            }

        }
    }


   /* public void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            initBackgroundLocation();
        }else {


            //new
            TedPermission.create()
                    .setPermissionListener(permissionlistener)
                    .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                    .setPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    .check();
        }
    }

*/
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(HomeActivity.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {


        mLocationRequest = new LocationRequest();
        // Set the update interval to 5 seconds
        mLocationRequest.setInterval(Constants.UPDATE_INTERVAL);
        // Set the fastest update interval to 1 second
        mLocationRequest.setFastestInterval(Constants.FASTEST_INTERVAL);


        mLocationRequest.setSmallestDisplacement(10);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        builder.setAlwaysShow(true); //this is the key ingredient

        if (ContextCompat.checkSelfPermission(HomeActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(HomeActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            //Place current location marker
            LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());


            if (Connectivity.isConnected(HomeActivity.this)) {
                updateLocationIntoServer(latlng);
            }
            else
                showNotification("non_connected", getString(R.string.no_internet_connection));


         /*   if (mGoogleApiClient != null) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            }*/


        } catch (Exception e) {
            e.printStackTrace();
            Log.e("LocationCheck","dsjhdfd "+e.getMessage());
            FirebaseReportManualLog.report("local-change", e);

        }

    }

    public void updateLocationIntoServer(LatLng latlng) {
        System.out.println("LocationCheck "+latlng.longitude);


        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }


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
                notification = new NotificationCompat.Builder(HomeActivity.this, "Default")
                        .setSmallIcon(R.drawable.dr_logo)
                        .setContentTitle("Rider - doorstep")
                        .setContentText("Online")
                        .build();


                break;
            case "non_connected":
                notification = new NotificationCompat.Builder(HomeActivity.this, "Default")
                        .setSmallIcon(R.drawable.dr_logo)
                        .setContentTitle("Rider - doorstep")
                        .setContentText(message)
                        .build();
                break;
        }
        if (notification != null) {
            notification.contentIntent = PendingIntent.getActivity(HomeActivity.this, 0,
                    new Intent(HomeActivity.this, HomeActivity.class), PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
//            this.startForeground(56, notification);

            NotificationManager mNotificationManager =
                    (NotificationManager) HomeActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(56, notification);
        }
    }

    public void showDialog(String message) {

        AlertDialog.Builder alert = new AlertDialog.Builder(HomeActivity.this);
        alert.setCancelable(false);
        alert.setMessage(message).setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        }).show();

    }

    public void checkAppUpdateAndMessage() {
        AppUpdateAndShowingEmergencyMessages appUpdateAndShowingEmergencyMessages = new AppUpdateAndShowingEmergencyMessages(HomeActivity.this, this);
        appUpdateAndShowingEmergencyMessages.prepareConfig();
    }


    @Override
    public void onGetBaseUrl(String updateUrl) {
        FirebaseRemoteKeys.base_url_link = updateUrl;
        ((DeliveryApp) getApplication()).initializeInjector();
        ((DeliveryApp) getApplication()).getDoNetworkComponent().inject(this);
        System.out.println("respnse injectedd homeactivtiy" + updateUrl);
    }

    public void checkBatteryOptimization() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
                boolean isIgnoringBatteryOptimizations = powerManager.isIgnoringBatteryOptimizations(getPackageName());


                if (!isIgnoringBatteryOptimizations) {
                    // Battery optimization is enabled for your app

                    // Create an intent to request to disable battery optimization
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    intent.setData(Uri.parse("package:" + getPackageName()));

                    // Start the activity to request to disable battery optimization
                    startActivity(intent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
