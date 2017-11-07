package com.iranplanner.tourism.iranplanner.ui.activity.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.forgetPassword.ForgetPasswordActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.mainActivity.MainActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.register.RegisterActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import entity.GetHomeResult;
import entity.GoogleLoginReqSend;
import entity.LoginReqSend;
import entity.LoginResult;
import entity.ResultUserLogin;
import tools.Util;

/**
 * Created by h.vahidimehr on 04/02/2017.
 */

public class LoginActivity extends StandardActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LoginContract.View, View.OnClickListener {

    private EditText etMail, etPassword;
    private TextView tvLogin, tvSignUp, tvLoginCommand;
    private ProgressDialog progressDialog;
    private int counter = 0;
    private boolean block = false;
    private GetHomeResult HomeResult;

    //Google SignIn Constants
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;

    @Inject
    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Bundle extras = getIntent().getExtras();
        HomeResult = (GetHomeResult) extras.getSerializable("HomeResult");

        ButterKnife.bind(this);
        if (Util.getUseRIdFromShareprefrence(getApplicationContext()) == null || Util.getUseRIdFromShareprefrence(getApplicationContext()) == "") {
            setContentView(R.layout.login);
            etMail = (EditText) findViewById(R.id.input_email);
            etPassword = (EditText) findViewById(R.id.input_password);
            tvLogin = (TextView) findViewById(R.id.btn_login);
            tvSignUp = (TextView) findViewById(R.id.link_signup);

            tvLoginCommand = (TextView) findViewById(R.id.loginCommand);
            tvLoginCommand.setText("");

            etMail.requestFocus();

            //Load Background Image
            Glide.with(this).load(R.drawable.splash_bg_blur).centerCrop().override(600, 400).into((ImageView) findViewById(R.id.loginBgIv));

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                    .enableAutoManage(this, this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();

            mAuth = FirebaseAuth.getInstance();

            findViewById(R.id.btnSignInGoogle).setOnClickListener(this);
            findViewById(R.id.txtForgetPassword).setOnClickListener(this);

            etMail.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!block)
                        setLoginButton(true);
                }
            });
            etPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!block)
                        setLoginButton(true);
                }
            });

            tvLogin.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {
                    if (counter >= 3) {
                        counter = 0;
                        block = true;
                        tvLoginCommand.setText("تلاش ناموفق برای سه بار! چند دقیقه دیگر امتحان کنید");

                        setLoginButton(false);
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                cleaner();
                                setLoginButton(true);
                            }
                        }, 150000);
                    } else
                        login();

                }
            });

            tvSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                    intent.putExtra("HomeResult", HomeResult);
                    startActivity(intent);
                }
            });
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("HomeResult", HomeResult);
            finish();
            startActivity(intent);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.login;
    }

    private void cleaner() {
        tvLoginCommand.setText("");
        etMail.setText("");
        etPassword.setText("");
    }

    private void setLoginButton(boolean setEnable) {
        if (setEnable) {
            block = false;
            tvLogin.setEnabled(true);
            tvLogin.setBackground(getResources().getDrawable(R.drawable.button_corner_blue_stroke));
            tvLogin.setClickable(true);

        } else {
            tvLogin.setEnabled(false);
            tvLogin.setBackground(getResources().getDrawable(R.drawable.button_corner_grey_stroke));
            tvLogin.setClickable(false);
        }
    }

    public void login() {

        if (!validate()) {
            etPassword.setError("اشکال در مقادیر ورودی");
            etMail.setError("اشکال در مقادیر ورودی");
            setLoginButton(false);
            return;
        }
        setLoginButton(true);
        requestLogin(etMail.getText().toString(), Util.md5(etPassword.getText().toString()));
    }

    private void requestLogin(String email, String password) {
        DaggerLoginComponent.builder().netComponent(((App) getApplicationContext().getApplicationContext()).getNetComponent())
                .loginModule(new LoginModule(this))
                .build().inject(this);
        String cid = Util.getTokenFromSharedPreferences(getApplicationContext());
        String andId = Util.getAndroidIdFromSharedPreferences(getApplicationContext());
        loginPresenter.getLoginPostResul(new LoginReqSend("login", email, password, cid, andId), cid, andId);
    }

    private void requestGoogleLogin(GoogleLoginReqSend googleLoginReqSend) {
        DaggerLoginComponent.builder().netComponent(((App) getApplicationContext().getApplicationContext()).getNetComponent())
                .loginModule(new LoginModule(this))
                .build().inject(this);
        loginPresenter.getGoogleLoginPostResult(googleLoginReqSend, Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
    }

    @Override
    public void showLoginResult(LoginResult loginResult) {
        ResultUserLogin resultUserLogin = loginResult.getResultUserLogin();
        setSaveDataInSharedPreference(resultUserLogin.getUserEmail(), resultUserLogin.getUserFname(), resultUserLogin.getUserLname(), resultUserLogin.getUserUid().toString());
    }

    private void setSaveDataInSharedPreference(String email, String name, String lName, String userId) {
        Util.saveDataINShareprefrence(getApplicationContext(), email, name, lName, userId);
        tvLoginCommand.setVisibility(View.GONE);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("HomeResult", HomeResult);
        startActivity(intent);
        finish();
    }

    @Override
    public void showError(String message) {
        counter++;
        //if (message.equals("HTTP 400 BAD REQUEST")) {
        tvLoginCommand.setVisibility(View.VISIBLE);
        tvLoginCommand.setText("نام کاربری یا کلمه عبور اشتباه است.");
        //} else {
        //}
    }

    @Override
    public void showComplete() {

    }

    public void showProgress() {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("احراز هویت...");
        progressDialog.show();
    }

    @Override
    public void dismissProgress() {
        if (progressDialog.isShowing() || progressDialog != null)
            progressDialog.dismiss();
    }

    public boolean validate() {
        boolean valid = true;
        String email = etMail.getText().toString();
        String password = etPassword.getText().toString();
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etMail.setError("فرمت ایمیل اشتباه است");
            valid = false;
        } else {
            etMail.setError(null);
        }
        return valid;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            GoogleLoginReqSend googleLoginReqSend = new GoogleLoginReqSend("googleoauth2", acct.getPhotoUrl().toString(), acct.getId(), acct.getFamilyName(), acct.getGivenName(), acct.getEmail());
            requestGoogleLogin(googleLoginReqSend);
        } else {
            // Signed out, show unauthenticated UI.
//            updateUI(false);
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtForgetPassword:
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                intent.putExtra("email", etMail.getText().toString());
                startActivity(intent);
                break;
            case R.id.btnSignInGoogle:
                signIn();
                break;
        }
    }
}
