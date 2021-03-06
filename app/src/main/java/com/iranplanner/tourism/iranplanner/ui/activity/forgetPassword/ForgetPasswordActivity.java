package com.iranplanner.tourism.iranplanner.ui.activity.forgetPassword;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;

import javax.inject.Inject;

import entity.ResetPassword;
import entity.SendEmailResetPassword;
import tools.Util;

public class ForgetPasswordActivity extends StandardActivity implements View.OnClickListener, ForgetPasswordContract.View {

    private String email;
    private TextView tvError;
    private TextInputEditText etMail;
    private ProgressDialog dialog;
    @Inject
    ForgetPasswordPresenter forgetPasswordPresenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        getExtras();
        init();

        //Load Background Image
        Glide.with(this).load(R.drawable.splash_bg_blur).centerCrop().override(600, 400).into((ImageView) findViewById(R.id.loginBgIv));
    }

    private void init() {
        tvError = (TextView) findViewById(R.id.loginCommand);
        etMail = (TextInputEditText) findViewById(R.id.input_email);
        if (!TextUtils.isEmpty(email))
            etMail.setText(email);

        findViewById(R.id.link_signup).setOnClickListener(this);

        dialog = new ProgressDialog(getApplicationContext());
        dialog.setTitle("ایران پلنر");
        dialog.setMessage("در حال ارسال درخواست");
    }

    private void getExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            email = extras.getString("email");
    }

    private boolean validate() {
        email = etMail.getText().toString();
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void forgetPassword() {
        if (validate()) {
            DaggerForgetComponent.builder().netComponent(((App) getApplicationContext().getApplicationContext()).getNetComponent())
                    .forgetPasswordModule(new ForgetPasswordModule(this))
                    .build().inject(this);
            String cid = Util.getTokenFromSharedPreferences(getApplicationContext());
            String andId = Util.getAndroidIdFromSharedPreferences(getApplicationContext());
            forgetPasswordPresenter.getResetPassword(new SendEmailResetPassword(email), cid, andId);

        } else
            tvError.setText("فرمت ایمیل اشتباه است");
    }

    private void showStatus() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("ایران پلنر")
                .setMessage("یک ایمیل شامل لینک تعویض رمز عبور برای شما ارسال شد!")
                .setPositiveButton("خیلی خب", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.dismiss();
                    }
                })
                .create();
        alertDialog.show();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void onClick(View view) {
        tvError.setText("");
        forgetPassword();
    }

    @Override
    public void showForgetPassword(ResetPassword resetPassword) {
        showStatus();
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showComplete() {

    }

    @Override
    public void showProgress() {
        progressDialog=Util.showProgressDialog(getApplicationContext(),"لطفا منتظر بمانید",this);
    }

    @Override
    public void dismissProgress() {
Util.dismissProgress(progressDialog);
    }
}
