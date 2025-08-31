package com.btracsolution.deliverysystem.Features.Forgot;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.ProgressDialogOwn;
import com.btracsolution.deliverysystem.databinding.ActivityRiderDaydetailsBinding;
import com.btracsolution.deliverysystem.databinding.FragmentUpdatePasswordBinding;


/**
 * Created by mahmudul.hasan on 3/11/2018.
 */

public class ForgotUpdatePassword extends Fragment implements View.OnClickListener {

    private FragmentUpdatePasswordBinding binding;

/*    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.confirm_password)
    EditText confirm_password;


    @BindView(R.id.tx_upate_password)
    TextView tx_upate_password;*/
    ForgotUpdatePasswordPresenter forgotUpdatePasswordPresenter;
    private View v;
    private String resetCode;

   // @OnClick(R.id.tx_upate_password)
    public void updatePassword() {
        if (binding.rowChangePass.password.getText().toString().length() > 0 && binding.rowChangePass.confirmPassword.getText().toString().length() > 0 && resetCode != null) {

            if (!isPasswordLengthIsValid(binding.rowChangePass.password)) {
                showDialog(getString(R.string.mimimum_length_password));
                return;
            }
            if (!isPasswordLengthIsValid(binding.rowChangePass.confirmPassword)) {
                showDialog(getString(R.string.confirm_pass_must));
                return;
            }
            if (binding.rowChangePass.password.getText().toString().contentEquals(binding.rowChangePass.confirmPassword.getText().toString()))
                forgotUpdatePasswordPresenter.updatePassword(resetCode, binding.rowChangePass.password.getText().toString(), binding.rowChangePass.confirmPassword.getText().toString());
            else
                showDialog(getString(R.string.pass_conf_not_matched));

        } else {
            showDialog(getString(R.string.password_length_empty));
        }
    }

    public void showDialog(String message) {
        ProgressDialogOwn progressDialogOwn = new ProgressDialogOwn();
        progressDialogOwn.showInfoAlertDialog(getActivity(), message);
    }

    public boolean isPasswordLengthIsValid(EditText editText) {
        return editText.getText().toString().trim().length() >= 6;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

 /*       v = inflater.inflate(R.layout.fragment_update_password, container, false);
        ButterKnife.bind(this, v);
        return v;*/

        binding = FragmentUpdatePasswordBinding.inflate(inflater, container, false);
        setOnClicklistener();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }

    private void setOnClicklistener() {
        binding.rowChangePass.txUpatePassword.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            resetCode = bundle.getString("activation_code", null);
        }
        forgotUpdatePasswordPresenter = new ForgotUpdatePasswordPresenter(getActivity(), ForgotUpdatePassword.this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tx_upate_password:
                updatePassword();
                break;
        }
    }
}
