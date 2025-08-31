package com.btracsolution.deliverysystem.Features.Agent;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.btracsolution.deliverysystem.AppsPackageUpdateChecker.AppUpdateAndShowingEmergencyMessages;
import com.btracsolution.deliverysystem.AppsPackageUpdateChecker.RemoteKeys.FirebaseRemoteKeys;
import com.btracsolution.deliverysystem.Base.BaseActivity;
import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentRider.AllRider;
import com.btracsolution.deliverysystem.Features.Agent.Fragments.Statement.AgentDayStatement;
import com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentDayWiseResult.AgentDayReport;
import com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentMenuPac.AgentMenu;
import com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentOrder.AgentOrderList;
import com.btracsolution.deliverysystem.Features.Agent.Fragments.Profile.ProfileAgent;
import com.btracsolution.deliverysystem.Features.Rider.HomePresenterImpl;
import com.btracsolution.deliverysystem.Features.Rider.HomepageView;
import com.btracsolution.deliverysystem.Firebase.MyRingtoneService;
import com.btracsolution.deliverysystem.Model.CommonDataResponse;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.AgentGlobal;
import com.btracsolution.deliverysystem.Utility.SharedData;
import com.btracsolution.deliverysystem.databinding.ActivityHomeAgentBinding;
import com.btracsolution.deliverysystem.network.api.RetroHubApiInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
//import com.gun0912.tedpermission.PermissionListener;
//import com.gun0912.tedpermission.TedPermission;

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

public class AgentHomeActivity extends BaseActivity<ActivityHomeAgentBinding> implements HomepageView, AppUpdateAndShowingEmergencyMessages.OnGetBaseUrlListener {


    @Inject
    SharedData sharedData;

    @Inject
    RetroHubApiInterface retroHubApiInterface;

  /*  @BindView(R.id.bottom_navigation)
    BottomNavigationView bottom_navigation;*/

    HomePresenterImpl homePresenter;

    FragmentManager fragmentManager;
    Fragment fragment;
    private BroadcastReceiver receiver;


    public static void open(Context context) {
        Intent intent = new Intent(context, AgentHomeActivity.class);
        context.startActivity(intent);
    }

    // might need to change restrictedApi later
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

    /*@Override
    protected int getLayoutId() {
        return R.layout.activity_home_agent;
    }*/

    @Override
    public void initView() {
        super.initView();
        initializeToolbar();



        ((DeliveryApp) getApplication()).getDoNetworkComponent().inject(this);

        homePresenter = new HomePresenterImpl();
        homePresenter.attach(this);

        setUpNavigationManager();
        doRestTask();

        checkAppUpdateAndMessage();

        try {
            stopService(new Intent(this, MyRingtoneService.class));
        } catch (Exception e) {
            e.printStackTrace();
        }

        checkAppsOverlayPermission();
        checkBluetoothPermission();
        checkNotificationPermission();
    }

    private void checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {


      /*      new TedPermission(AgentHomeActivity.this)
                    .setPermissionListener(permissionlistener)
                    .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                    .setPermissions(Manifest.permission.POST_NOTIFICATIONS)
                    .check();*/

            TedPermission.create()
                    .setPermissionListener(permissionlistenerpush)
                    .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                    .setPermissions(Manifest.permission.POST_NOTIFICATIONS)
                    .check();
        }

    }

    @Override
    protected ActivityHomeAgentBinding getViewBinding() {
        return ActivityHomeAgentBinding.inflate(getLayoutInflater());
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

    public void setSubTitleOfActivity(String subtitle) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setSubtitle(subtitle);
        }
    }

    public void doRestTask() {
        if (sharedData != null) {
//            Toast.makeText(this, sharedData.getMyData(), Toast.LENGTH_SHORT).show();
            if (sharedData.getMyData() != null) {
               // setTitleOfActivity(sharedData.getMyData().getData().getRestaurantInfo().getName() + " " + "➞" + " " + sharedData.getMyData().getData().getBranchInfo().get(0).getBranchName());
                setTitleOfActivity(sharedData.getMyData().getData().getRestaurantInfo().getName() + " " + "" + " ");
                setSubTitleOfActivity(sharedData.getMyData().getData().getFullName());
            }

        } else
            Toast.makeText(this, "Null", Toast.LENGTH_SHORT).show();

        getFirebaseToken();
    }

  /*  @Override
    protected int getLayoutId() {
        return R.layout.activity_home_agent;
    }*/


    private void initializeToolbar() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(
                    ContextCompat.getColor(AgentHomeActivity.this, R.color.colorAccent));
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
                    case R.id.action_home:
                        fragment = new AgentOrderList();
                        doRestTask();
                        break;
                    case R.id.action_recent:
                        fragment = new AgentDayReport();
                        doRestTask();
                        break;
                    case R.id.action_statement:
                        fragment = new AgentDayStatement();
                        setTitleOfActivity(getString(R.string.statement));
                        doRestTask();
                        break;
                    case R.id.action_menu:
                        fragment = new AgentMenu();
                        setTitleOfActivity(getString(R.string.menu));
                        doRestTask();

                        break;
                    case R.id.action_profile:
                        fragment = new AllRider();
                        setTitleOfActivity(AgentHomeActivity.this.getResources().getString(R.string.runner_list));
                        setSubTitleOfActivity("");
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
        fragment = new AgentOrderList();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, fragment).commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Back", "Worked");
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
                        String fcmmToken = task.getResult();
                        sendTokenToServer(fcmmToken);
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

                            Log.d("TokenUpdate", "Sucess , FCMToken: "+fcmToken);

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

    public void relodAgentDayReport(){
        if (currentFragment() instanceof AgentDayReport) {
            fragment = new AgentDayReport();
            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container, fragment).commit();
        }
    }
    public  void hideKeyboard(){
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }
    @Override
    protected void onResume() {
        super.onResume();

        AgentGlobal.isAgentActivityOpen = true;
        registerReceiver();
        relodAgentDayReport();
        hideKeyboard();
    }

    @Override
    protected void onPause() {
        super.onPause();

        AgentGlobal.isAgentActivityOpen = false;
    }

    @Override
    protected void onDestroy() {
        AgentGlobal.isAgentActivityOpen = false;
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
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
                    if (type.equals("uiUpdate")) {
                        checkFragment();

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
        if (currentFragment() instanceof AgentOrderList) {
            // Do something here
            ((AgentOrderList) currentFragment()).checkMyCurrentJob();
        } else {
//            bottom_navigation.setSelectedItemId(R.id.action_home);
        }
    }

    public Fragment currentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.main_container);
    }


    public void checkAppUpdateAndMessage() {
        AppUpdateAndShowingEmergencyMessages appUpdateAndShowingEmergencyMessages = new AppUpdateAndShowingEmergencyMessages(AgentHomeActivity.this, this);
        appUpdateAndShowingEmergencyMessages.prepareConfig();
    }

    @Override
    public void onGetBaseUrl(String updateUrl) {
        FirebaseRemoteKeys.base_url_link = updateUrl;
        ((DeliveryApp) getApplication()).initializeInjector();
        ((DeliveryApp) getApplication()).getDoNetworkComponent().inject(this);
        System.out.println("respnse injectedd agernthomeactivtiy " + updateUrl);
    }

    private void checkBluetoothPermission() {

        try {
            if (Build.VERSION.SDK_INT >= 31) {
            /*    new TedPermission(this)
                        .setPermissionListener(permissionlistener)
                        //.setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                        // .setPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .setPermissions(Manifest.permission.BLUETOOTH_CONNECT,Manifest.permission.BLUETOOTH_SCAN)
                        .check();*/

                TedPermission.create()
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                        .setPermissions(Manifest.permission.BLUETOOTH_CONNECT,Manifest.permission.BLUETOOTH_SCAN)
                        .check();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
/*    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            DeliveryApp.getInstance().setupBluetoothConnection();
        }


        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            // Toast.makeText(WaiterActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();

        }

    };*/

    PermissionListener permissionlistenerpush = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            //  Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(AgentHomeActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }


    };

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            DeliveryApp.getInstance().setupBluetoothConnection();        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
           // Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }


    };


}
