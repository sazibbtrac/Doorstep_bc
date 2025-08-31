package com.btracsolution.deliverysystem.Features.Agent.Fragments.Profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Features.Login.LoginActivity;
import com.btracsolution.deliverysystem.Model.CommonDataResponse;
import com.btracsolution.deliverysystem.Model.LoginModel;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.CustomAlertDialog;
import com.btracsolution.deliverysystem.Utility.DialogClickInterface;
import com.btracsolution.deliverysystem.Utility.ProgressDialogOwn;
import com.btracsolution.deliverysystem.Utility.SharedData;
import com.btracsolution.deliverysystem.databinding.ActivityProfileBinding;
import com.btracsolution.deliverysystem.databinding.RowProfileBottomBinding;
import com.btracsolution.deliverysystem.databinding.RowProfileTopBinding;
import com.btracsolution.deliverysystem.network.api.RetroHubApiInterface;

import javax.inject.Inject;

/*import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;*/
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mahmudul.hasan on 1/4/2018.
 */

public class ProfileAgent extends Fragment implements DialogClickInterface,View.OnClickListener {

    @Inject
    SharedData sharedData;
    @Inject
    ProgressDialogOwn progressDialogOwn;

    @Inject
    RetroHubApiInterface retroHubApiInterface;

/*
    @BindView(R.id.tx_logout)
    TextView tx_logout;


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

    private ActivityProfileBinding binding;
    private RowProfileTopBinding topBinding;
    private RowProfileBottomBinding bottomBinding;

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

   // @OnClick(R.id.tx_upate_password)
    public void updatePassword() {
        if (isPasswordLengthIsValid(bottomBinding.password)) {
            if (isPasswordLengthIsValid(bottomBinding.confirmPassword)) {
                if (isPasswordLengthIsValid(bottomBinding.oldPassword)) {
                    if (checkPasswordAndConfirmPasswordMatched()) {
                        setUpdatePassword();
                    } else
                        progressDialogOwn.showInfoAlertDialog(getActivity(), getString(R.string.pass_conf_not_matched));


                } else
                    progressDialogOwn.showInfoAlertDialog(getActivity(), getString(R.string.old_pass_con_must));

            } else

                progressDialogOwn.showInfoAlertDialog(getActivity(), getString(R.string.confirm_pass_must));

        } else
            progressDialogOwn.showInfoAlertDialog(getActivity(), getString(R.string.password_new));

    }

    public boolean checkPasswordAndConfirmPasswordMatched() {
        return bottomBinding.password.getText().toString().trim().contentEquals(bottomBinding.confirmPassword.getText().toString().trim());
    }

    public boolean isPasswordLengthIsValid(EditText editText) {
        return editText.getText().toString().trim().length() >= 6;
    }

    //@OnClick(R.id.tx_logout)
    public void onClickLogout() {


        if (getActivity() != null && !getActivity().isFinishing())
            CustomAlertDialog.getInstance().showConfirmDialog(getString(R.string.logout), getString(R.string.sure_logout), getString(R.string.confirm), getString(R.string.close), getActivity(), 1, ProfileAgent.this);


    }

    public void logoutSuccess() {
        if (sharedData.getMyData() != null) {
            sharedData.unsetMyData();
            getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        }

    }

    public void setDataIntoTextView() {
        if (sharedData.getMyData() != null) {
            topBinding.txAgentName.setText(sharedData.getMyData().getData().getFullName());
            String address = "";

            for (LoginModel.KitchenInfo branchInfo : sharedData.getMyData().getData().getKitchenInfo()
            ) {

                address += branchInfo.getKitchenAddress() + "<br>";

            }
            if (sharedData.getMyData().getData().getUserEmail() != null)
                address += "Email: " + sharedData.getMyData().getData().getUserEmail() + "<br>";
            if (sharedData.getMyData().getData().getMobileNo() != null)
                address += "Mobile: " + sharedData.getMyData().getData().getMobileNo();
            topBinding.txAgentAddress.setText(fromHtml(address));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ActivityProfileBinding.inflate(inflater, container, false);
        topBinding = RowProfileTopBinding.bind(binding.rowProfileTop.getRoot());
        bottomBinding = RowProfileBottomBinding.bind(binding.rowProfileBottom.getRoot());
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null; // Avoid memory leaks
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((DeliveryApp) getActivity().getApplicationContext()).getDoNetworkComponent().inject(this);

        setDataIntoTextView();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOnlickListener();
        setHasOptionsMenu(true);
    }

    private void setOnlickListener() {
        bottomBinding.txUpatePassword.setOnClickListener(this);
        bottomBinding.txLogout.setOnClickListener(this);
    }

    public void setUpdatePassword() {
        if (retroHubApiInterface != null) {
            if (sharedData.getMyData() != null) {

                progressDialogOwn.showDialog(getActivity(), getString(R.string.loading));
                Call<OrderDetailsModel> orderDetailsModelCall = retroHubApiInterface.updatePassword(
                        sharedData.getMyData().getData().getApiToken(),
                        sharedData.getMyData().getData().getUserId(),
                        bottomBinding.oldPassword.getText().toString(),
                        bottomBinding.password.getText().toString()


                );
                orderDetailsModelCall.enqueue(new Callback<OrderDetailsModel>() {
                    @Override
                    public void onResponse(Call<OrderDetailsModel> call, Response<OrderDetailsModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus().contentEquals("success")) {
                                progressDialogOwn.showInfoAlertDialog(getActivity(), getString(R.string.pass_change_succes));

                            } else

                                progressDialogOwn.showInfoAlertDialog(getActivity(), response.body().getError().getError_msg());

//                            baseServerListener.onServerSuccessOrFailure(false, null, response.body().getError().getError_msg());


                        } else
                            progressDialogOwn.showInfoAlertDialog(getActivity(), getString(R.string.error_into_server));

                        progressDialogOwn.hideDialog();

                    }

                    @Override
                    public void onFailure(Call<OrderDetailsModel> call, Throwable t) {
                        if (t != null)
                            progressDialogOwn.showInfoAlertDialog(getActivity(), t.getMessage());

                        else
                            progressDialogOwn.showInfoAlertDialog(getActivity(), getString(R.string.error_into_server));

                        progressDialogOwn.hideDialog();

                    }
                });
            }
        }
    }

    @Override
    public void onClickPositiveButton(DialogInterface pDialog, int pDialogIntefier,String shortNote) {

        switch (pDialogIntefier) {
            case 1:


                if (retroHubApiInterface != null && sharedData != null && sharedData.getMyData() != null) {

                    Call<CommonDataResponse> call = retroHubApiInterface.logout(
                            sharedData.getMyData().getData().getApiToken(),
                            sharedData.getMyData().getData().getUserId()
                    );
//                    CommonDataResponse CommonDataResponse = call.execute().body();
                    progressDialogOwn.showDialog(getActivity(), getString(R.string.loading));
                    call.enqueue(new Callback<CommonDataResponse>() {

                        @Override
                        public void onResponse(Call<CommonDataResponse> call, Response<CommonDataResponse> response) {

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
                            Toast.makeText(getActivity(), "Error" + t.toString(), Toast.LENGTH_SHORT).show();
                            progressDialogOwn.hideDialog();

                        }
                    });
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
