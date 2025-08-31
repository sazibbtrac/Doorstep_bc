package com.btracsolution.deliverysystem.Features.Agent.AgentOrderDetails;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.content.ContextCompat;

import com.btracsolution.deliverysystem.Base.BaseActivity;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.databinding.ActivityDaydetailsBinding;

/**
 * Created by mahmudul.hasan on 1/4/2018.
 */

public class AgentDayDetailActivity extends BaseActivity<ActivityDaydetailsBinding>   {


    public static void open(Context context) {
        Intent intent = new Intent(context, AgentDayDetailActivity.class);
        context.startActivity(intent);
    }

 /*   @Override
    protected int getLayoutId() {
        return R.layout.activity_daydetails;
    }*/

    @Override
    public void initView() {
        super.initView();
        initializeToolbar();

    }

    @Override
    protected ActivityDaydetailsBinding getViewBinding() {
        return ActivityDaydetailsBinding.inflate(getLayoutInflater());
    }

    private void initializeToolbar() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(
                    ContextCompat.getColor(AgentDayDetailActivity.this, R.color.colorAccent));
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


}
