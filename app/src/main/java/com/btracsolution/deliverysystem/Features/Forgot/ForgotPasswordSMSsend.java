package com.btracsolution.deliverysystem.Features.Forgot;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.ProgressDialogOwn;
import com.btracsolution.deliverysystem.databinding.FragmentSmsSendBinding;
import com.btracsolution.deliverysystem.databinding.FragmentUpdatePasswordBinding;

/*import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;*/

/**
 * Created by mahmudul.hasan on 3/11/2018.
 */

public class ForgotPasswordSMSsend extends Fragment implements View.OnClickListener{
    private FragmentSmsSendBinding binding;

 /*   @BindView(R.id.tx_resend_verify_code)
    TextView tx_resend_verify_code;

    @BindView(R.id.tx_submit_code)
    TextView tx_submit_code;

    @BindView(R.id.tx_send_code)
    TextView tx_send_code;

    @BindView(R.id.et_activation_code)
    EditText et_activation_code;

    @BindView(R.id.et_verify_phone_number)
    EditText et_verify_phone_number;

    @BindView(R.id.tx_title_forgot_password)
    TextView tx_title_forgot_password;

    @BindView(R.id.rel_checkout)
    LinearLayout rel_checkout;

    @BindView(R.id.chk_email)
    CheckBox chk_email;


    @BindView(R.id.chk_phone)
    CheckBox chk_phone;

*/
    private View v;
    private FragmentManager fragmentManager;
    private ForgotUpdatePassword fragment;
    private ForgotOwnPresenter forgotOwnPresenter;
    private String resetCode;

   // @OnClick(R.id.chk_email)
    public void onClickOfEmail() {
        if (binding.rowVerifySms.chkPhone.isChecked())
            binding.rowVerifySms.chkPhone.setChecked(false);
        else {
            binding.rowVerifySms.chkPhone.setChecked(true);

        }

        checkInputTypeOfTheField();


    }

   // @OnClick(R.id.chk_phone)
    public void onClickOfPhone() {
        if (binding.rowVerifySms.chkEmail.isChecked())
            binding.rowVerifySms.chkEmail.setChecked(false);
        else {
            binding.rowVerifySms.chkEmail.setChecked(true);
        }

        checkInputTypeOfTheField();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }

    public void checkInputTypeOfTheField() {
        if (binding.rowVerifySms.chkPhone.isChecked()) {
            binding.rowVerifySms.etVerifyPhoneNumber.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
            binding.rowVerifySms.etVerifyPhoneNumber.setHint(getString(R.string.enter_phone_number));
        } else if (binding.rowVerifySms.chkEmail.isChecked()) {
            binding.rowVerifySms.etVerifyPhoneNumber.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            binding.rowVerifySms.etVerifyPhoneNumber.setHint(getString(R.string.enter_email_address));

        }
        binding.rowVerifySms.etVerifyPhoneNumber.setText("");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

 /*       v = inflater.inflate(R.layout.fragment_sms_send, container, false);
        ButterKnife.bind(this, v);
        return v;*/

        binding = FragmentSmsSendBinding.inflate(inflater, container, false);
        setOnClicklistener();
        return binding.getRoot();
    }

    private void setOnClicklistener() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String previous_type = bundle.getString("previous_type", "");
            binding.rowVerifySms.etVerifyPhoneNumber.setText(previous_type);
        }

        ViewVisibleSMSSendCode();

        forgotOwnPresenter = new ForgotOwnPresenter(getActivity(), ForgotPasswordSMSsend.this);

    }


  //  @OnClick({R.id.tx_send_code, R.id.tx_resend_verify_code})
    public void sendVerifyCode() {
//        ViewVisibleSubmitCode();
        if (binding.rowVerifySms.chkEmail.isChecked())
            forgotOwnPresenter.sendActivationCode("email", binding.rowVerifySms.etVerifyPhoneNumber.getText().toString());
        if (binding.rowVerifySms.chkPhone.isChecked())
            forgotOwnPresenter.sendActivationCode("phone", binding.rowVerifySms.etVerifyPhoneNumber.getText().toString());
    }

    //@OnClick(R.id.tx_submit_code)
    public void sendSubmitCode() {
        if (binding.rowVerifySms.etActivationCode.getText().length() > 0 || resetCode != null) {
            if (binding.rowVerifySms.etActivationCode.getText().toString().contentEquals(resetCode)) {
                fragmentManager = getActivity().getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("activation_code", resetCode);
                fragment = new ForgotUpdatePassword();
                fragment.setArguments(bundle);
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.frm_forgot, fragment).commit();
            } else {
                showDialog(getString(R.string.activation_code_miss));

            }
        } else {
            showDialog(getString(R.string.empty_field));

        }


    }

    public void showDialog(String message) {
        ProgressDialogOwn progressDialogOwn = new ProgressDialogOwn();
        progressDialogOwn.showInfoAlertDialog(getActivity(), message);
    }


    public void ViewVisibleSMSSendCode() {

        binding.rowVerifySms.txTitleForgotPassword.setText(getString(R.string.verifu_phn));


        binding.rowVerifySms.etVerifyPhoneNumber.setVisibility(View.VISIBLE);
        binding.rowVerifySms.etActivationCode.setVisibility(View.GONE);


        binding.rowVerifySms.txSendCode.setVisibility(View.VISIBLE);
        binding.rowVerifySms.txSubmitCode.setVisibility(View.GONE);


        binding.rowVerifySms.txResendVerifyCode.setVisibility(View.GONE);


    }

    public void ViewVisibleSubmitCode(String resetCode) {

        this.resetCode = resetCode;
        binding.rowVerifySms.txTitleForgotPassword.setText(getString(R.string.sumit_code_ins));


        binding.rowVerifySms.etVerifyPhoneNumber.setVisibility(View.GONE);
        binding.rowVerifySms.etActivationCode.setVisibility(View.VISIBLE);


        binding.rowVerifySms.txSendCode.setVisibility(View.GONE);
        binding.rowVerifySms.txSubmitCode.setVisibility(View.VISIBLE);


        binding.rowVerifySms.txResendVerifyCode.setVisibility(View.VISIBLE);

        binding.rowVerifySms.relCheckout.setVisibility(View.GONE);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chk_email:
                onClickOfEmail();
                break;

            case R.id.chk_phone:
                onClickOfPhone();
                break;

            case R.id.tx_send_code:
                sendVerifyCode();
                break;

            case R.id.tx_resend_verify_code:
                sendVerifyCode();
                break;

            case R.id.tx_submit_code:
                sendSubmitCode();
                break;
        }
    }
}
