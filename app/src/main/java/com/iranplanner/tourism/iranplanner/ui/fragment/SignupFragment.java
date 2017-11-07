package com.iranplanner.tourism.iranplanner.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.ui.activity.register.DaggerRegisterComponent;
import com.iranplanner.tourism.iranplanner.ui.activity.register.RegisterModule;
import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.standard.StandardFragment;
import com.iranplanner.tourism.iranplanner.ui.activity.register.RegisterPresenter;
import com.iranplanner.tourism.iranplanner.ui.activity.register.RegisterContract;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import entity.ResultRegister;
import entity.ResultUserRegister;
import tools.Util;

/**
 * Created by h.vahidimehr on 03/02/2017.
 */

public class SignupFragment extends StandardFragment implements RegisterContract.View {
    private static final String TAG = "SignupActivity";
    ProgressDialog progressDialog;
    @BindView(R.id.input_name)
    EditText nameText;
    @BindView(R.id.input_tel)
    EditText input_tel;
    @BindView(R.id.input_family)
    EditText input_family;
    @BindView(R.id.input_email)
    EditText editText;
    @BindView(R.id.input_password)
    EditText passwordText;
    @BindView(R.id.input_password_repeat)
    EditText input_password_repeat;
    @BindView(R.id.btn_signup)
    Button _signupButton;
    @BindView(R.id.link_login)
    TextView _loginLink;
    @BindView(R.id.radioWoman)
    RadioButton radioWoman;
    @BindView(R.id.radioMan)
    RadioButton radioMan;

    @Inject
    RegisterPresenter registerPresenter;


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View view = inflater.inflate(R.layout.activity_signup, container, false);
        ButterKnife.bind(getActivity());



        return view;
    }

    @Override
    public void showRegisterMessage(ResultRegister resultRegister) {

        ResultUserRegister result = resultRegister.getResultUserRegister();
        if (result.getStatus().equals("Succesfull")) {
            Toast.makeText(getContext(), "حساب کاربری با موفقیت انجام شد", Toast.LENGTH_LONG).show();

        } else if (result.getStatus().equals("Duplicate Phone")) {
            Toast.makeText(getContext(), "حساب کاربری قبلاایجاد شده است ", Toast.LENGTH_LONG).show();

        } else if (result.getStatus().equals("Invalid info")) {
            Toast.makeText(getContext(), "اشکال در مقادیر ورودی", Toast.LENGTH_LONG).show();

        }
        progressDialog.dismiss();

        _signupButton.setEnabled(true);
//        setResult(RESULT_OK, null);
        progressDialog.dismiss();
    }

    @Override
    public void showError(String message) {
        Log.e("error", message);
        progressDialog.dismiss();
    }

    @Override
    public void showComplete() {
        progressDialog.dismiss();
    }

    public void showProgress() {
//        progressDialog = new ProgressDialog(.this, R.style.AppTheme);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("ساخت حساب کاربری");
//        progressDialog.show();
    }

    @Override
    public void dismissProgress() {

    }

    public void signup() {
        Log.d(TAG, "Signup");
        if (!validate()) {
            _signupButton.setEnabled(true);
            return;
        }
        _signupButton.setEnabled(false);

        String name = nameText.getText().toString();
        String lastName = input_family.getText().toString();
        String email = editText.getText().toString();
        String phoneNumber = input_tel.getText().toString();
        String password = Util.md5(passwordText.getText().toString());

        String gender = "0";
        if (radioMan.isChecked()) {
            gender = "1";
        } else if (radioWoman.isChecked()) {
            gender = "0";
        }


        DaggerRegisterComponent.builder()
                .netComponent(((App) getContext()).getNetComponent())
                .registerModule(new RegisterModule(this))
                .build().injectFragment(this);
        registerPresenter.getRegisterResult("register", email, password, name, lastName, gender, Util.getTokenFromSharedPreferences(getContext()), phoneNumber);

//        getRegisterResponce(email, password, name, lastName, gender, Util.getTokenFromSharedPreferences(getApplicationContext()), phoneNumber);
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String email = editText.getText().toString();
        String password = passwordText.getText().toString();
        String passwordRepeat = input_password_repeat.getText().toString();
        String phoneNumber = input_tel.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("حداقل سه حرف وارد شود");
            valid = false;
        } else {
            nameText.setError(null);
        }
        if (name.isEmpty() || name.length() < 2) {
            input_family.setError("حداقل دو حرف وارد شود");
            valid = false;
        } else {
            input_family.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editText.setError("فرمت ایمیل اشتباه است");
            valid = false;
        } else {
            editText.setError(null);
        }

        if (password.isEmpty() || password.length() < 8) {
            passwordText.setError("کلمه عبور می بایست بیشتر از 8 حرف باشد");


            valid = false;
        } else if (password.isEmpty() || password.length() >= 8) {
            int digitSize = 0;
            for (int i = 0; i < password.length(); i++) {
                if (Character.isDigit(password.charAt(i))) {
                    digitSize = digitSize + 1;
                }
            }
            if (digitSize == password.length() || digitSize == 0) {
                passwordText.setError("کلمه عبور باید شامل حرف و عدد باشد");
                valid = false;
            }
        } else {

            passwordText.setError(null);
        }
        if (password.isEmpty() || password.length() < 8) {
            input_password_repeat.setError("کلمه عبور می بایست بیشتر از 8 حرف باشد");
            valid = false;
        } else {
            input_password_repeat.setError(null);
        }
        if (!passwordRepeat.equals(password)) {
            passwordText.setError("کلمه عبور و تکرار آن یکسان نیستند");
            valid = false;
        } else {
            input_password_repeat.setError(null);
        }
        if (!TextUtils.isEmpty(phoneNumber)) {

            if (phoneNumber.trim().length() < 10
                    || phoneNumber.trim().length() > 11
                    || (phoneNumber.trim().length() == 11 && !phoneNumber.trim().startsWith("09"))
                    || (phoneNumber.trim().length() == 10 && !phoneNumber.trim().startsWith("9"))) {
                String message = "شماره تلفن همراه وارد شده صحیح نیست.";
                input_tel.setError(message);
                valid = false;
            }
        }
        return valid;
    }

//    public void getRegisterResponce(String email, String password, String fname, String lname, String gender, String cid, String phone) {
//        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
//                .connectTimeout(60, TimeUnit.SECONDS)
//                .readTimeout(60, TimeUnit.SECONDS)
//                .writeTimeout(60, TimeUnit.SECONDS)
//                .build();
//        Retrofit retrofit = new Retrofit.Builder()
//                .client(okHttpClient)
//                .baseUrl(Config.BASEURL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        getJsonInterface getJsonInterface = retrofit.create(server.getJsonInterface.class);
//
//        Call<ResultRegister> callc = getJsonInterface.getRegisterResult("register", email, password, fname, lname, gender, cid, phone);
//        callc.enqueue(this);
//    }
//
//    @Override
//    public void onResponse(Call<ResultRegister> call, Response<ResultRegister> response) {
//        response.body();
//        //// TODO: 04/02/2017
//        if (response.body() != null) {
//
//            ResultRegister jsonResponse = response.body();
//            ResultUserRegister result = jsonResponse.getResultUserRegister();
//            if (result.getStatus().equals("Succesfull")) {
//                Toast.makeText(getApplicationContext(), "حساب کاربری با موفقیت انجام شد", Toast.LENGTH_LONG).show();
//
//            } else if (result.getStatus().equals("Duplicate Phone")) {
//                Toast.makeText(getApplicationContext(), "حساب کاربری قبلاایجاد شده است ", Toast.LENGTH_LONG).show();
//
//            } else if (result.getStatus().equals("Invalid info")) {
//                Toast.makeText(getApplicationContext(), "اشکال در مقادیر ورودی", Toast.LENGTH_LONG).show();
//
//            }
//            progressDialog.dismiss();
//        } else {
//            Log.e("Responce body", "null");
//        }
//        _signupButton.setEnabled(true);
//        setResult(RESULT_OK, null);
//        progressDialog.dismiss();
//    }
//
//    @Override
//    public void onFailure(Call<ResultRegister> call, Throwable t) {
//        Log.e("error", t.toString());
//        Toast.makeText(getApplicationContext(), "عدم دسترسی به اینترنت", Toast.LENGTH_LONG).show();
//        progressDialog.dismiss();
//    }

//    public void attemptLogin(String phonePrefix, String phoneNumber) {
////        if (userLoginTask != null) {
////            return;
////        }
//
//        if (!TextUtils.isEmpty(phoneNumber) && !TextUtils.isEmpty(phonePrefix)) {
//
//            if (phoneNumber.trim().length() < 10
//                    || phoneNumber.trim().length() > 11
//                    || (phoneNumber.trim().length() == 11 && !phoneNumber.trim().startsWith("09"))
//                    || (phoneNumber.trim().length() == 10 && !phoneNumber.trim().startsWith("9"))) {
//                String message = "شماره تلفن همراه وارد شده صحیح نیست.";
//
//            } else {
//
//            }
//        }
//    }

}
