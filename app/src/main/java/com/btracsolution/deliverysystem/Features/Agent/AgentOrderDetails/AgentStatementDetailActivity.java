package com.btracsolution.deliverysystem.Features.Agent.AgentOrderDetails;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.content.ContextCompat;

import com.btracsolution.deliverysystem.Base.BaseActivity;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.databinding.ActivityStatementDetailsBinding;

/**
 * Created by mahmudul.hasan on 1/4/2018.
 */

public class AgentStatementDetailActivity extends BaseActivity<ActivityStatementDetailsBinding>   {


    public static void open(Context context) {
        Intent intent = new Intent(context, AgentStatementDetailActivity.class);
        context.startActivity(intent);
    }

/*    @Override
    protected int getLayoutId() {
        return R.layout.activity_statement_details;
    }*/

    @Override
    public void initView() {
        super.initView();
        initializeToolbar();

    }

    @Override
    protected ActivityStatementDetailsBinding getViewBinding() {
        return ActivityStatementDetailsBinding.inflate(getLayoutInflater());
    }

    private void initializeToolbar() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(
                    ContextCompat.getColor(AgentStatementDetailActivity.this, R.color.colorAccent));
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


}
