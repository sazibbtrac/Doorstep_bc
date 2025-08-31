package com.btracsolution.deliverysystem.Features.Waiter;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.btracsolution.deliverysystem.AppsPackageUpdateChecker.AppUpdateAndShowingEmergencyMessages;
import com.btracsolution.deliverysystem.AppsPackageUpdateChecker.RemoteKeys.FirebaseRemoteKeys;
import com.btracsolution.deliverysystem.Base.BaseActivity;
import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Features.Agent.AgentHomeActivity;
import com.btracsolution.deliverysystem.Features.Rider.HomeActivity;
import com.btracsolution.deliverysystem.Features.Rider.HomePresenterImpl;
import com.btracsolution.deliverysystem.Features.Waiter.Features.Bill.BillActivity;
import com.btracsolution.deliverysystem.Features.Waiter.Features.BillHistory.BillHistoryFragment;
import com.btracsolution.deliverysystem.Features.Waiter.Features.Order.Menu.WaiterMenuCategory;
import com.btracsolution.deliverysystem.Features.Waiter.Features.ProfileWaiter.ProfileWaiter;
import com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterDayReport.WaiterDayReport;
import com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterJobList.WaiterJobList;
import com.btracsolution.deliverysystem.Model.CommonDataResponse;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.AgentGlobal;
import com.btracsolution.deliverysystem.Utility.SharedData;
import com.btracsolution.deliverysystem.databinding.ActivityWaiterBinding;
import com.btracsolution.deliverysystem.network.api.RetroHubApiInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
//import com.gun0912.tedpermission.TedPermission;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

//import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WaiterActivity extends BaseActivity<ActivityWaiterBinding> implements AppUpdateAndShowingEmergencyMessages.OnGetBaseUrlListener {


    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    @Inject
    SharedData sharedData;
    @Inject
    RetroHubApiInterface retroHubApiInterface;
   /* @BindView(R.id.bottom_navigation)
    BottomNavigationView bottom_navigation;*/
    HomePresenterImpl homePresenter;
    FragmentManager fragmentManager;
    Fragment fragment;
    private BroadcastReceiver receiver;

//    @Inject
//    CartItemDao userDao;

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

    public static void open(Context context) {
        Intent intent = new Intent(context, WaiterActivity.class);
        context.startActivity(intent);
    }

/*    @Override
    protected int getLayoutId() {
        return R.layout.activity_waiter;
    }*/

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

        checkBluetoothPermission();
        checkAppsOverlayPermission();
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

    PermissionListener permissionlistenerpush = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            //  Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(WaiterActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }


    };



    @Override
    protected ActivityWaiterBinding getViewBinding() {
        return ActivityWaiterBinding.inflate(getLayoutInflater());
    }

    private void checkBluetoothPermission() {

        if (Build.VERSION.SDK_INT >= 31) {
            TedPermission.create()
                    .setPermissionListener(permissionlistener)
                    .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                    .setPermissions(Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_SCAN)
                    .check();
            /*
            new TedPermission(WaiterActivity.this)
                    .setPermissionListener(permissionlistener)
                    //.setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                    // .setPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .setPermissions(Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_SCAN)
                    .check();*/
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

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            DeliveryApp.getInstance().setupBluetoothConnection();
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            //Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }


    };

    private void initializeToolbar() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(
                    ContextCompat.getColor(WaiterActivity.this, R.color.colorAccent));
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    public void setUpNavigationManager() {
        fragmentManager = getSupportFragmentManager();
        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_my_job:
                        fragment = new WaiterJobList();
                        break;
                    case R.id.action_my_completed_job:
                        fragment = new WaiterMenuCategory();
                        break;
                    case R.id.action_recent:
                        fragment = new WaiterDayReport();
                        break;
                    case R.id.action_my_profile:
                        fragment = new ProfileWaiter();
                        break;
                    case R.id.action_my_bill:
                        fragment = new BillActivity();
                        break;
//                    case R.id.action_my_bill_history:
//                        fragment = new BillHistoryFragment();
//                        break;

                }
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();
                return true;
            }
        });
        disableShiftMode(binding.bottomNavigation
        );

        firstTime();
    }

    public void firstTime() {
        fragment = new WaiterJobList();
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

    public void getFirebaseToken() {
        System.out.println("Token " + sharedData.getMyData().getData().getApiToken());
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
                            Log.d("TokenUpdate", "Sucess , FCMToken: " + fcmToken);
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

    @Override
    protected void onResume() {
        super.onResume();

        AgentGlobal.isAgentActivityOpenWaiter = true;
        registerReceiver();
        relodAgentDayReport();
        hideKeyboard();
    }

    public void relodAgentDayReport() {
        if (currentFragment() instanceof WaiterJobList) {
            fragment = new WaiterJobList();
            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container, fragment).commit();
        }
    }

    public void hideKeyboard() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    @Override
    protected void onPause() {
        super.onPause();

        AgentGlobal.isAgentActivityOpenWaiter = false;
    }

    @Override
    protected void onDestroy() {
        AgentGlobal.isAgentActivityOpenWaiter = false;
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
        if (currentFragment() instanceof WaiterJobList) {
            // Do something here
            ((WaiterJobList) currentFragment()).checkMyCurrentJob();
        } else {
//            bottom_navigation.setSelectedItemId(R.id.action_home);
        }
    }

    public Fragment currentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.main_container);
    }


    public void checkAppUpdateAndMessage() {
        AppUpdateAndShowingEmergencyMessages appUpdateAndShowingEmergencyMessages = new AppUpdateAndShowingEmergencyMessages(WaiterActivity.this, this);
        appUpdateAndShowingEmergencyMessages.prepareConfig();
    }


    @Override
    public void onGetBaseUrl(String updateUrl) {
        FirebaseRemoteKeys.base_url_link = updateUrl;
        ((DeliveryApp) getApplication()).initializeInjector();
        ((DeliveryApp) getApplication()).getDoNetworkComponent().inject(this);
        System.out.println("respnse injectedd homeactivtiy" + updateUrl);
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

    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5469;

    public void checkAppsOverlayPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
            }

        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}