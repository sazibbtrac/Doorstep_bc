package com.btracsolution.deliverysystem.Features.Login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.btracsolution.deliverysystem.AppsPackageUpdateChecker.AppUpdateAndShowingEmergencyMessages;
import com.btracsolution.deliverysystem.AppsPackageUpdateChecker.RemoteKeys.FirebaseRemoteKeys;
import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Features.Agent.AgentHomeActivity;
import com.btracsolution.deliverysystem.Features.Forgot.ForgotPassword;
import com.btracsolution.deliverysystem.Features.Rider.HomeActivity;
import com.btracsolution.deliverysystem.Features.Waiter.WaiterActivity;
import com.btracsolution.deliverysystem.Model.LoginModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.Connectivity;
import com.btracsolution.deliverysystem.Utility.FirebaseReportManualLog;
import com.btracsolution.deliverysystem.Utility.LinearLayoutThatDetectsSoftKeyboard;
import com.btracsolution.deliverysystem.Utility.ProgressDialogOwn;
import com.btracsolution.deliverysystem.Utility.SharedData;
import com.btracsolution.deliverysystem.Utility.Validation;
import com.btracsolution.deliverysystem.databinding.ActivityLoginBinding;
import com.btracsolution.deliverysystem.network.api.RetroHubApiInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoaderCallbacks<Cursor>, LinearLayoutThatDetectsSoftKeyboard.Listener, AppUpdateAndShowingEmergencyMessages.OnGetBaseUrlListener {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
   /* @BindView(R.id.rel_login)
    LinearLayoutThatDetectsSoftKeyboard rel_login;
    @BindView(R.id.iv_top_logo)
    ImageView iv_top_logo;

    @BindView(R.id.chk_email)
    CheckBox chk_email;
    @BindView(R.id.chk_phone)
    CheckBox chk_phone;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.email_sign_in_button)
    TextView email_sign_in_button;
    @BindView(R.id.tx_lost_password)
    TextView tx_lost_password;*/
    @Inject
    RetroHubApiInterface retroHubApiInterface;

    private ActivityLoginBinding loginBinding;

    @Inject
    ProgressDialogOwn progressDialogOwn;

    @Inject
    Validation validation;

    @Inject
    SharedData sharedData;
    // UI references.
    private View mProgressView;
    private View mLoginFormView;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */


  //  @OnClick(R.id.tx_lost_password)
    public void lostPassword() {
        if (loginBinding.etEmail.getText().length() > 0)
            ForgotPassword.open(LoginActivity.this, "send_sms", loginBinding.etEmail.getText().toString().trim());
        else
            ForgotPassword.open(LoginActivity.this, "send_sms", "");
    }

    public boolean isEmpty(EditText editText) {
        return editText.getText().toString().length() <= 0;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        loginBinding.txLostPassword.setOnClickListener(this);
        AppUpdateAndShowingEmergencyMessages appUpdateAndShowingEmergencyMessages = new AppUpdateAndShowingEmergencyMessages(this, this);
        appUpdateAndShowingEmergencyMessages.prepareConfig();
        FirebaseRemoteKeys.base_url_link = appUpdateAndShowingEmergencyMessages.getBaseDafualt();
       // ButterKnife.bind(this);
        ((DeliveryApp) getApplication()).initializeInjector();
        ((DeliveryApp) getApplication()).getDoNetworkComponent().inject(this);

        // Set up the login form.

      Log.d("Login base url",  appUpdateAndShowingEmergencyMessages.getBaseDafualt());

//        populateAutoComplete();

        loginBinding.etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });


        loginBinding.emailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
             //   throw new RuntimeException("Test Crash");
                attemptLogin();
             //   WaiterActivity.open(LoginActivity.this);

            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        setTitleOfActivity();
        checkLoggedInOrNot();


        loginBinding.relLogin.setListener(this);

    }

    public void checkLoggedInOrNot() {
        if (sharedData.getMyData() != null) {
            switch (sharedData.getMyData().getData().getUserType()) {

                case "2":
                    AgentHomeActivity.open(LoginActivity.this);
                    finish();
                    break;
                case "4":

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        HomeActivity.open(LoginActivity.this);
                    }
                    finish();
                    break;

                case "3":

                    WaiterActivity.open(LoginActivity.this);
                    finish();
                    break;
            }


        }
    }

    public void setTitleOfActivity() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.login_name));
        }
    }


    /**
     * Callback received when a permissions request has been completed.
     */


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

       /* AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
        alertDialog.setMessage("What would you like to check?");
        alertDialog.setPositiveButton("AGENT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                AgentHomeActivity.open(LoginActivity.this);



            }
        }).setNegativeButton("RIDER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                HomeActivity.open(LoginActivity.this);

            }
        }).show();
        *//*
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }*/

        if (Connectivity.isConnected(this)) {
            if (!isEmpty(loginBinding.etEmail) && !isEmpty(loginBinding.etPassword)) {
//            if (isEmailValid(et_email.getText().toString())) {
                if (loginBinding.etPassword.getText().toString().length() >= 4) {
                    if (retroHubApiInterface != null) {

                        Call<LoginModel> call = retroHubApiInterface.getLoginData(
                                loginBinding.etEmail.getText().toString(),
                                loginBinding.etPassword.getText().toString(),
                                "test",
                                "test",
                                "test",
                                "1"
                        );
//                    LoginModel loginModel = call.execute().body();
                        progressDialogOwn.showDialog(LoginActivity.this, getString(R.string.loading));
                        call.enqueue(new Callback<LoginModel>() {

                            @Override
                            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                                Log.w("Login Info","jdkss"+response.body().toString());

                                progressDialogOwn.hideDialog();
                                try {
                                    if (response.isSuccessful()) {
                                        LoginModel loginModel = response.body();
                                        showToast(response.body().getMessage()+" "+response.body().getStatus());
                                        if (loginModel != null) {
                                            if (loginModel.getStatus()!=null && loginModel.getStatus().equals("success")) {
                                                showToast(response.body().getMessage()+" inside "+response.body().getStatus());

                                                if (sharedData != null) {


                                                    sharedData.setMyData(new Gson().toJson(loginModel));

                                                    switch (sharedData.getMyData().getData().getUserType()) {

                                                        case "2":
                                                            AgentHomeActivity.open(LoginActivity.this);
                                                            finish();
                                                            break;
                                                        case "4":
                                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                                HomeActivity.open(LoginActivity.this);
                                                            }
                                                            finish();
                                                            break;
                                                        case "3":
                                                            WaiterActivity.open(LoginActivity.this);
                                                            finish();
                                                            break;
                                                        default:
                                                            progressDialogOwn.showInfoAlertDialog(LoginActivity.this, getString(R.string.not_valid_user));

                                                            break;
                                                    }


                                                } else
                                                    progressDialogOwn.showInfoAlertDialog(LoginActivity.this, getString(R.string.error_saving_date));

                                            } else
                                                progressDialogOwn.showInfoAlertDialog(LoginActivity.this, loginModel.getError().getError_msg());

                                        }


                                    } else {

                                        if (response.message() != null) {
                                            progressDialogOwn.showInfoAlertDialog(LoginActivity.this, response.message());

                                        }

                                    }
                                } catch (Exception e) {
                                    Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                                    Log.w("Login Info","error 1"+e.getMessage());

                                }
                            }

                            @Override
                            public void onFailure(Call<LoginModel> call, Throwable t) {
                                Toast.makeText(LoginActivity.this, "Error" + t.toString(), Toast.LENGTH_SHORT).show();
                                progressDialogOwn.hideDialog();

                            }
                        });
                    }
                } else
                    progressDialogOwn.showInfoAlertDialog(LoginActivity.this, getString(R.string.mimimum_length_password));


            /*} else
                Toast.makeText(this, R.string.error_invalid_email, Toast.LENGTH_SHORT).show();*/

            } else {
                progressDialogOwn.showInfoAlertDialog(LoginActivity.this, getString(R.string.please_fill_all_field));

            }
        }
        else{
            progressDialogOwn.showInfoAlertDialog(LoginActivity.this, getString(R.string.no_internet_connection));

        }
    }

    public void showToast(String msg){
        Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_LONG).show();

    }
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    @Override
    public void onSoftKeyboardShown(boolean isShowing) {
        if (isShowing) {
           // iv_top_logo.setVisibility(View.GONE);
        } else {
            loginBinding.ivTopLogo.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onGetBaseUrl(String updateUrl) {
        FirebaseRemoteKeys.base_url_link = updateUrl;
        ((DeliveryApp) getApplication()).initializeInjector();
        ((DeliveryApp) getApplication()).getDoNetworkComponent().inject(this);
        System.out.println("respnse injectedd " + updateUrl);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tx_lost_password:
                lostPassword();
                break;

        }
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
}

