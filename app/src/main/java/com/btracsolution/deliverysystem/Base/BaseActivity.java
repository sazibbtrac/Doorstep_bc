package com.btracsolution.deliverysystem.Base;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.AgentGlobal;
import com.btracsolution.deliverysystem.Utility.RiderGlobal;

import androidx.viewbinding.ViewBinding;

import java.util.List;

/**
 * Created by mahmudul.hasan on 12/28/2017.
 */

public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {

    private Toolbar mToolbar;
    private ProgressDialog mProgressDialog;
    protected VB binding;  

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = getViewBinding();
        setContentView(binding.getRoot());

        CreateNotificationChannel();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = getSystemService(NotificationManager.class);
            List<NotificationChannel> channels = manager.getNotificationChannels();
            for (NotificationChannel channel : channels) {
                Log.d("Channels", "Channel: " + channel.getName() +
                        " ID: " + channel.getId());
            }
        }

        initView();
        doRestTask();
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
    private void doRestTask() {
        // Add any additional tasks here
    }


    public void initView() {
        // Initialize views here
    }

    @Nullable
    public Toolbar getToolbar() {
        return mToolbar;
    }

    /**
     * @return The layout id that's gonna be the activity view.
     */
    protected abstract VB getViewBinding();

    protected void showProgress(String msg) {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            dismissProgress();

        mProgressDialog = ProgressDialog.show(this, getResources().getString(R.string.app_name), msg);
    }

    protected void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void showAlert(String msg) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.app_name))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setHomeUpEnable(boolean isEnable) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(isEnable);
    }

    public BaseBeautifulDialog showBeautifulDialog(BaseBeautifulDialogListener baseBeautifulDialogListener) {
        return new BaseBeautifulDialog(baseBeautifulDialogListener, this);
    }

    public void hideKeyboard() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

  }
