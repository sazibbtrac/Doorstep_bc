package com.btracsolution.deliverysystem.Features.Forgot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.btracsolution.deliverysystem.Base.BaseActivity;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.databinding.ActivityForgotPasswordBinding;

/*import butterknife.BindView;*/

/**
 * Created by mahmudul.hasan on 3/11/2018.
 */

public class ForgotPassword extends BaseActivity<ActivityForgotPasswordBinding> {
/*    @BindView(R.id.frm_forgot)
    FrameLayout frm_forgot;*/
    Fragment fragment;
    private FragmentManager fragmentManager;

    public static void open(Context context, String type_open, String previous_type) {
        Intent intent = new Intent(context, ForgotPassword.class);
        intent.putExtra("type_open", type_open);
        intent.putExtra("previous_type", previous_type);
        context.startActivity(intent);
    }

/*    @Override
    protected int getLayoutId() {
        return R.layout.activity_forgot_password;
    }*/

    @Override
    public void initView() {
        super.initView();
        workAsPerIntent();
        setHomeUpEnable(true);


    }

    @Override
    protected ActivityForgotPasswordBinding getViewBinding() {
        return ActivityForgotPasswordBinding.inflate(getLayoutInflater());
    }


    public void workAsPerIntent() {
        if (getIntent() != null) {
            String type = getIntent().getStringExtra("type_open");
            String previous_type = getIntent().getStringExtra("previous_type");
            translateIntoCode(type, previous_type);
        }
    }

    public void translateIntoCode(String type, String previous_type) {
        fragmentManager = getSupportFragmentManager();
        switch (type) {
            case "send_sms":
                Bundle bundle = new Bundle();
                bundle.putString("previous_type", previous_type);
                fragment = new ForgotPasswordSMSsend();
                fragment.setArguments(bundle);
                break;
            case "change_password":
                fragment = new ForgotUpdatePassword();
                break;
        }


        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frm_forgot, fragment).commit();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
