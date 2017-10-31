package com.iranplanner.tourism.iranplanner.ui.activity.getPhoneNumber;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.iranplanner.tourism.iranplanner.R;

import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.getPhoneNumber.DaggerSendPhoneComponent;
import com.iranplanner.tourism.iranplanner.ui.activity.getPhoneNumber.SendPhoneModule;

import com.iranplanner.tourism.iranplanner.ui.activity.getPhoneNumber.SendPhonePresenter;
import com.iranplanner.tourism.iranplanner.ui.activity.getPhoneNumber.SendPhoneContract;

import javax.inject.Inject;

import entity.ResultSendPhone;
import tools.Util;

/**
 * Created by h.vahidimehr on 02/06/2017.
 */

public class SendPhoneActivity extends StandardActivity implements SendPhoneContract.View {
    TextView input_tel;
    Button btnSendPhone;
    @Inject
    SendPhonePresenter sendPhonePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        input_tel = (TextView) findViewById(R.id.input_tel);
        btnSendPhone = (Button) findViewById(R.id.btnSendPhone);
        DaggerSendPhoneComponent.builder()
                .netComponent(((App) getApplicationContext()).getNetComponent())
                .sendPhoneModule(new SendPhoneModule(this))
                .build().inject(this);


        btnSendPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    Toast.makeText(getApplicationContext(), "salam ", Toast.LENGTH_SHORT).show();
                    sendPhonePresenter.sendPhoneResult("phonetoken", input_tel.getText().toString(), Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));

                }
            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.send_phone_number;
    }

    public boolean validate() {
        boolean valid = true;
        String phoneNumber = input_tel.getText().toString();
        if (!TextUtils.isEmpty(phoneNumber)) {

            if (phoneNumber.trim().length() < 10
                    || phoneNumber.trim().length() > 11
                    || (phoneNumber.trim().length() == 11 && !phoneNumber.trim().startsWith("09"))
                    || (phoneNumber.trim().length() == 10 && !phoneNumber.trim().startsWith("9"))) {
                String message = "شماره تلفن همراه وارد شده صحیح نیست.";
                input_tel.setError(message);
                valid = false;
            }
        } else if (TextUtils.isEmpty(phoneNumber)) {
            String message = "ثبت شماره تلفن اجباری است";
            input_tel.setError(message);
            valid = false;
        }
        return valid;
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showComplete() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void getPhoneResult(ResultSendPhone resultSendPhone) {

    }
}
