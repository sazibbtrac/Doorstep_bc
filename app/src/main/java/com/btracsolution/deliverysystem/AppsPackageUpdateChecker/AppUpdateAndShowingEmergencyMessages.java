package com.btracsolution.deliverysystem.AppsPackageUpdateChecker;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.btracsolution.deliverysystem.AppsPackageUpdateChecker.RemoteKeys.FirebaseRemoteKeys;
import com.btracsolution.deliverysystem.BuildConfig;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.FirebaseReportManualLog;
import com.btracsolution.deliverysystem.Utility.SharedData;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.concurrent.Executor;

/**
 * Created by mahmudul.hasan on 3/7/2018.
 */

public class AppUpdateAndShowingEmergencyMessages {

    public static FirebaseRemoteConfig mFirebaseRemoteConfig;
    private OnGetBaseUrlListener OnGetBaseUrlListener;
    public boolean isBaseUrlOrAppUpdate = false; // when get called base url it will be true but whent get called for appupdate check its false

    Context context;

    public interface OnGetBaseUrlListener {
        void onGetBaseUrl(String updateUrl);
    }

    public AppUpdateAndShowingEmergencyMessages(Context context, OnGetBaseUrlListener onGetBaseUrlListener) {
        this.context = context;
        this.OnGetBaseUrlListener = onGetBaseUrlListener;
    }


    public static FirebaseRemoteConfig getConfigInstance() {
        return mFirebaseRemoteConfig;
    }

    public String getBaseDafualt() {
        //  return mFirebaseRemoteConfig.getString("base_url");
        //return "https://banglacatrental.com/doorstep/public/api/v1/";
        return FirebaseRemoteKeys.base_url_link;
    }

    public void prepareConfig() {
        try {
            mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
            FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                    .setMinimumFetchIntervalInSeconds(3600)
                    .build();
            mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
            mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
            fetchWelcome();
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseReportManualLog.report("prepare-firebase-config", e);

        }
    }

    private void fetchWelcome() {

        mFirebaseRemoteConfig.fetch().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    mFirebaseRemoteConfig.activate();
                    fetchRemoteConfigForMobileVerification();

                }
            }
        });

    }

    public void fetchRemoteConfigForMobileVerification() {
        try {
            if (getConfigInstance() != null) {
                OnGetBaseUrlListener.onGetBaseUrl(FirebaseRemoteKeys.base_url_link);
               // OnGetBaseUrlListener.onGetBaseUrl("https://banglacatrental.com/doorstep/public/api/v1/");
                checkAppVersion();

            }

        } catch (Exception e) {
            e.printStackTrace();
            FirebaseReportManualLog.report("firebase-fetch-config", e);

        }
    }

    private void checkAppVersion() {
        if (getConfigInstance().getBoolean(FirebaseRemoteKeys.rider_mendatory_update)) {

            int versionCode = BuildConfig.VERSION_CODE;
            int latestVersion=Integer.parseInt(getConfigInstance().getString(FirebaseRemoteKeys.appVersion));
            if (latestVersion > versionCode) {

                showDialogWithMessage(getConfigInstance().getString(FirebaseRemoteKeys.rider_update_text), false, getConfigInstance().getString(FirebaseRemoteKeys.updateurl));


            }else {
                String message_code = getConfigInstance().getString(FirebaseRemoteKeys.rider_message_code);
                SharedData sharedData = new SharedData(context);

                if (sharedData.isPermitToShowMessage(message_code)) {
                   // showSimpleMessage(getConfigInstance().getString(FirebaseRemoteKeys.rider_message), true, message_code);

                }
            }
        }else {
            String message_code = getConfigInstance().getString(FirebaseRemoteKeys.rider_message_code);
            SharedData sharedData = new SharedData(context);

            if (sharedData.isPermitToShowMessage(message_code)) {
            //    showSimpleMessage(getConfigInstance().getString(FirebaseRemoteKeys.rider_message), true, message_code);

            }
        }
    }

    public void showDialogWithMessage(String message, boolean isCancellable, String url) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(isCancellable);
        alertDialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Please install a web browser first", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (isCancellable)
            alertDialogBuilder.setNegativeButton("Later", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        alertDialogBuilder.show();

    }

    public void showSimpleMessage(String message, boolean isCancellable, String message_code) {


        try {
            new MaterialStyledDialog.Builder(context)
                    .setTitle(context.getString(R.string.information_top))
                    .setDescription(message)
                    .setStyle(Style.HEADER_WITH_ICON)
                    .withIconAnimation(true)
                    .withDialogAnimation(true)
                    .setIcon(R.drawable.ic_email_white_48dp)
                    //.setStyle(Style.HEADER_WITH_TITLE)
                    .setPositiveText(R.string.close)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        }
                    }).show();

            SharedData sharedData = new SharedData(context);
            sharedData.saveLastMessage(message);


        } catch (Exception e) {
            e.printStackTrace();
            FirebaseReportManualLog.report("information-show-dialog", e);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setMessage(message);
            alertDialogBuilder.setCancelable(isCancellable);
            alertDialogBuilder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                }
            });

            alertDialogBuilder.show();
        }

    }


}
