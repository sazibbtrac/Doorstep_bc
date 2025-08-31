package com.btracsolution.deliverysystem.Features.Agent.Fragments.Profile;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.btracsolution.deliverysystem.Base.BaseActivity;
import com.btracsolution.deliverysystem.BuildConfig;
import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Features.Login.LoginActivity;
import com.btracsolution.deliverysystem.Model.CommonDataResponse;
import com.btracsolution.deliverysystem.Model.LoginModel;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.Connectivity;
import com.btracsolution.deliverysystem.Utility.CustomAlertDialog;
import com.btracsolution.deliverysystem.Utility.DialogClickInterface;
import com.btracsolution.deliverysystem.Utility.ProgressDialogOwn;
import com.btracsolution.deliverysystem.Utility.SharedData;
import com.btracsolution.deliverysystem.databinding.ActivityHomeBinding;
import com.btracsolution.deliverysystem.databinding.ActivityProfileBinding;
import com.btracsolution.deliverysystem.databinding.RowProfileBottomBinding;
import com.btracsolution.deliverysystem.databinding.RowProfileTopBinding;
import com.btracsolution.deliverysystem.network.api.RetroHubApiInterface;

import javax.inject.Inject;

/*import butterknife.BindView;
import butterknife.OnClick;*/
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityProfileAgent extends BaseActivity<ActivityProfileBinding> implements DialogClickInterface, View.OnClickListener {
    @Inject
    SharedData sharedData;
    @Inject
    ProgressDialogOwn progressDialogOwn;

    @Inject
    RetroHubApiInterface retroHubApiInterface;
    private RowProfileTopBinding topBinding;
    private RowProfileBottomBinding bottomBinding;

/*
    @BindView(R.id.tx_logout)
    TextView tx_logout;
    @BindView(R.id.tvVersion)
    TextView tvVersion;


    @BindView(R.id.tx_upate_password)
    TextView tx_upate_password;


    @BindView(R.id.tx_agent_name)
    TextView tx_agent_name;


    @BindView(R.id.tx_agent_address)
    TextView tx_agent_address;


    @BindView(R.id.old_password)
    EditText old_password;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.confirm_password)
    EditText confirm_password;

*/

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }


    public static void open(Activity context) {
        Intent intent = new Intent(context, ActivityProfileAgent.class);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        super.initView();
        setHomeUpEnable(true);
        setTitle("Profile");

        ((DeliveryApp) getApplicationContext()).getDoNetworkComponent().inject(this);
        setDataIntoTextView();
        setOnClickListener();

        String versionName = BuildConfig.VERSION_NAME;
        binding.tvVersion.setText("v"+versionName);
    }



    private void setOnClickListener() {
        binding.rowProfileBottom.txUpatePassword.setOnClickListener(this);
        binding.rowProfileBottom.txLogout.setOnClickListener(this);
    }

    @Override
    protected ActivityProfileBinding getViewBinding() {
        return ActivityProfileBinding.inflate(getLayoutInflater());
    }

/*    @Override
    protected int getLayoutId() {
        return R.layout.activity_profile;
    }*/


  //  @OnClick(R.id.tx_upate_password)
    public void updatePassword() {
        if (isPasswordLengthIsValid(binding.rowProfileBottom.password)) {
            if (isPasswordLengthIsValid(binding.rowProfileBottom.confirmPassword)) {
                if (isPasswordLengthIsValid(binding.rowProfileBottom.oldPassword)) {
                    if (checkPasswordAndConfirmPasswordMatched()) {
                        setUpdatePassword();
                    } else
                        progressDialogOwn.showInfoAlertDialog(this, getString(R.string.pass_conf_not_matched));


                } else
                    progressDialogOwn.showInfoAlertDialog(this, getString(R.string.old_pass_con_must));

            } else

                progressDialogOwn.showInfoAlertDialog(this, getString(R.string.confirm_pass_must));

        } else
            progressDialogOwn.showInfoAlertDialog(this, getString(R.string.password_new));

    }

    public boolean checkPasswordAndConfirmPasswordMatched() {
        return binding.rowProfileBottom.password.getText().toString().trim().contentEquals(binding.rowProfileBottom.confirmPassword.getText().toString().trim());
    }

    public boolean isPasswordLengthIsValid(EditText editText) {
        return editText.getText().toString().trim().length() >= 6;
    }

   // @OnClick(R.id.tx_logout)
    public void onClickLogout() {


        if (this != null && !this.isFinishing())
            CustomAlertDialog.getInstance().showConfirmDialog(getString(R.string.logout), getString(R.string.sure_logout), getString(R.string.confirm), getString(R.string.close), this, 1, this);


    }

    public void logoutSuccess() {
        if (sharedData.getMyData() != null) {
            sharedData.unsetMyData();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

    }

    public void setDataIntoTextView() {
        if (sharedData.getMyData() != null) {
            binding.rowProfileTop.txAgentName.setText(sharedData.getMyData().getData().getFullName());
            String address = "";

            for (LoginModel.KitchenInfo branchInfo : sharedData.getMyData().getData().getKitchenInfo()
            ) {

                address += branchInfo.getKitchenAddress() + "<br>";

            }
            if (sharedData.getMyData().getData().getUserEmail() != null)
                address += "Email: " + sharedData.getMyData().getData().getUserEmail() + "<br>";
            if (sharedData.getMyData().getData().getMobileNo() != null)
                address += "Mobile: " + sharedData.getMyData().getData().getMobileNo();
            binding.rowProfileTop.txAgentAddress.setText(fromHtml(address));
        }
    }

    public void setUpdatePassword() {
        if (Connectivity.isConnected(this)) {
            if (retroHubApiInterface != null) {
                if (sharedData.getMyData() != null) {

                    progressDialogOwn.showDialog(ActivityProfileAgent.this, getString(R.string.loading));
                    Call<OrderDetailsModel> orderDetailsModelCall = retroHubApiInterface.updatePassword(
                            sharedData.getMyData().getData().getApiToken(),
                            sharedData.getMyData().getData().getUserId(),
                            binding.rowProfileBottom.oldPassword.getText().toString(),
                            binding.rowProfileBottom.password.getText().toString()


                    );
                    orderDetailsModelCall.enqueue(new Callback<OrderDetailsModel>() {
                        @Override
                        public void onResponse(Call<OrderDetailsModel> call, Response<OrderDetailsModel> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getStatus().contentEquals("success")) {
                                    progressDialogOwn.showInfoAlertDialog(ActivityProfileAgent.this, getString(R.string.pass_change_succes));

                                } else

                                    progressDialogOwn.showInfoAlertDialog(ActivityProfileAgent.this, response.body().getError().getError_msg());

//                            baseServerListener.onServerSuccessOrFailure(false, null, response.body().getError().getError_msg());


                            } else
                                progressDialogOwn.showInfoAlertDialog(ActivityProfileAgent.this, getString(R.string.error_into_server));

                            progressDialogOwn.hideDialog();

                        }

                        @Override
                        public void onFailure(Call<OrderDetailsModel> call, Throwable t) {
                            if (t != null)
                                progressDialogOwn.showInfoAlertDialog(ActivityProfileAgent.this, t.getMessage());

                            else
                                progressDialogOwn.showInfoAlertDialog(ActivityProfileAgent.this, getString(R.string.error_into_server));

                            progressDialogOwn.hideDialog();

                        }
                    });
                }
            }
        }else {
            progressDialogOwn.showInfoAlertDialog(this, this.getResources().getString(R.string.no_internet_connection));
        }
    }

    @Override
    public void onClickPositiveButton(DialogInterface pDialog, int pDialogIntefier,String shortNote) {

        switch (pDialogIntefier) {
            case 1:

                if (Connectivity.isConnected(this)) {
                    if (retroHubApiInterface != null && sharedData != null && sharedData.getMyData() != null) {

                        Call<CommonDataResponse> call = retroHubApiInterface.logout(
                                sharedData.getMyData().getData().getApiToken(),
                                sharedData.getMyData().getData().getUserId()
                        );
//                    CommonDataResponse CommonDataResponse = call.execute().body();
                        progressDialogOwn.showDialog(ActivityProfileAgent.this, getString(R.string.loading));
                        call.enqueue(new Callback<CommonDataResponse>() {

                            @Override
                            public void onResponse(Call<CommonDataResponse> call, Response<CommonDataResponse> response) {

                                System.out.println("dhfsdjfgdf 1"+response.toString());

                                progressDialogOwn.hideDialog();
                                if (response.isSuccessful()) {
                                    CommonDataResponse CommonDataResponse = response.body();
                                    if (CommonDataResponse != null) {
                                        if (CommonDataResponse.getStatus().contentEquals("success")) {
                                            logoutSuccess();

                                        } else {
                                            logoutSuccess();

                                        }
                                    }


                                } else {

                                    if (response.message() != null) {
                                        logoutSuccess();

                                    }

                                }
                            }

                            @Override
                            public void onFailure(Call<CommonDataResponse> call, Throwable t) {
                                System.out.println("dhfsdjfgdf 1"+t.toString());
                                Toast.makeText(ActivityProfileAgent.this, "Error" + t.toString(), Toast.LENGTH_SHORT).show();
                                progressDialogOwn.hideDialog();

                            }
                        });
                    }
                }else {
                    progressDialogOwn.showInfoAlertDialog(this, this.getResources().getString(R.string.no_internet_connection));
                }

                break;

        }
        pDialog.dismiss();


    }

    @Override
    public void onClickNegativeButton(DialogInterface pDialog, int pDialogIntefier) {
        pDialog.dismiss();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tx_upate_password:
                updatePassword();
                break;

            case R.id.tx_logout:
                onClickLogout();
                break;


        }
    }
}
