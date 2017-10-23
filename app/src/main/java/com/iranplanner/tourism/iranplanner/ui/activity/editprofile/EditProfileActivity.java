package com.iranplanner.tourism.iranplanner.ui.activity.editprofile;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coinpany.core.android.widget.Utils;
import com.coinpany.core.android.widget.calendar.dateutil.PersianCalendar;
import com.iranplanner.tourism.iranplanner.R;

import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import entity.EmailVerifyReq;
import entity.ResultUpdateReturn;
import entity.ResultUserInfo;
import entity.ResultVerifyEmail;
import entity.updateProfileSend;
import tools.Util;
import tools.widget.PersianDatePicker;

/**
 * Created by h.vahidimehr on 12/06/2017.
 */

public class EditProfileActivity extends StandardActivity implements View.OnClickListener, EditProfileContract.View {
    private TextView txtNewsValueShow, txtBirthdayValueShow, email_verify, email_address, txtDate, btnEditProfile, btnOpenEditProfile, txtTitle, txtGenderValueShow, txtNameValueShow, txtFamilyValueShow, txtPhonValueShow, txtBirthdayShow, txtLodgingValueShow;
    private EditText input_tel, input_name, input_family, input_lodging;
    private RelativeLayout changeDateHolder, verifyEmailHolder;
    private CheckBox checkBoxNews;
    private RadioButton radioWoman, radioMan;
    private ProgressDialog progressDialog;
    private LinearLayout editProfileHolder, showProfileHolder;
    private ImageView ImgUserEmailStatus;

    private String from;
    private long birthday;

    @Inject
    EditProfilePresenter editProfilePresenter;
    DaggerEditProfileComponent.Builder builder;
    private ResultUserInfo resultUserInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("مشخصات حساب کاربری");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void init() {
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        resultUserInfo = (ResultUserInfo) intent.getSerializableExtra("infoUserResult");

        editProfileHolder = (LinearLayout) findViewById(R.id.editProfileHolder);
        email_address = (TextView) findViewById(R.id.email_address);
        email_verify = (TextView) findViewById(R.id.email_verify);
        verifyEmailHolder = (RelativeLayout) findViewById(R.id.verifyEmailHolder);
        email_address.setText(Util.getEmailFromShareprefrence(getApplicationContext()));
        changeDateHolder = (RelativeLayout) findViewById(R.id.changeDateHolder);
        txtDate = (TextView) findViewById(R.id.txtDate);
        btnEditProfile = (TextView) findViewById(R.id.btnEditProfile);
        btnOpenEditProfile = (TextView) findViewById(R.id.btnOpenEditProfile);
        input_name = (EditText) findViewById(R.id.input_name);
        input_tel = (EditText) findViewById(R.id.input_tel);
        input_family = (EditText) findViewById(R.id.input_family);
        input_lodging = (EditText) findViewById(R.id.input_lodging);
        radioMan = (RadioButton) findViewById(R.id.radioMan);
        radioWoman = (RadioButton) findViewById(R.id.radioWoman);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        checkBoxNews = (CheckBox) findViewById(R.id.checkBoxNews);
        ImgUserEmailStatus = (ImageView) findViewById(R.id.ImgUserEmailStatus);
        showProfileHolder = (LinearLayout) findViewById(R.id.showProfileHolder);

        txtGenderValueShow = (TextView) findViewById(R.id.txtGenderValueShow);
        txtNameValueShow = (TextView) findViewById(R.id.txtNameValueShow);
        txtFamilyValueShow = (TextView) findViewById(R.id.txtFamilyValueShow);
        txtPhonValueShow = (TextView) findViewById(R.id.txtPhonValueShow);
        txtBirthdayShow = (TextView) findViewById(R.id.txtBirthdayShow);
        txtLodgingValueShow = (TextView) findViewById(R.id.txtLodgingValueShow);
        txtBirthdayValueShow = (TextView) findViewById(R.id.txtBirthdayValueShow);
        txtNewsValueShow = (TextView) findViewById(R.id.txtNewsValueShow);
        //----
        if (from == null || from.equals("editKey")) {
            editProfileHolder.setVisibility(View.VISIBLE);
            showProfileHolder.setVisibility(View.GONE);
            btnOpenEditProfile.setVisibility(View.GONE);
            editProfileHolder.setVisibility(View.VISIBLE);

            //toggle toolbar to edit
            getSupportActionBar().setTitle("ویرایش حساب کاربری");
        } else if (from.equals("showKey")) {
            editProfileHolder.setVisibility(View.GONE);
            showProfileHolder.setVisibility(View.VISIBLE);
            btnOpenEditProfile.setVisibility(View.VISIBLE);
            editProfileHolder.setVisibility(View.GONE);

            //toggle toolbar to show
            getSupportActionBar().setTitle("مشخصات حساب کاربری");
        }

        setValues(resultUserInfo);
        changeDateHolder.setOnClickListener(this);
        btnEditProfile.setOnClickListener(this);
        btnOpenEditProfile.setOnClickListener(this);
        verifyEmailHolder.setOnClickListener(this);
    }

    private void setValues(ResultUserInfo resultUserInfo) {
        String gender = (resultUserInfo.getUserGender().equals("0")) ? "زن" : "مرد";
        txtNewsValueShow.setText((resultUserInfo.getUserNewsletter().equals("1")) ? "دارم" : "ندارم");
        if (resultUserInfo.getUserEmailStatus().equals("0")) {
            verifyEmailHolder.setVisibility(View.VISIBLE);
            ImgUserEmailStatus.setBackgroundResource(R.mipmap.ic_cancel_red);


        } else {
            verifyEmailHolder.setVisibility(View.GONE);
            ImgUserEmailStatus.setBackgroundResource(R.mipmap.ic_checked_green);
        }

        txtGenderValueShow.setText(gender);
        txtNameValueShow.setText(resultUserInfo.getUserFname());
        txtFamilyValueShow.setText(resultUserInfo.getUserLname());
        txtPhonValueShow.setText(Util.persianNumbers(resultUserInfo.getUserPhone()));
//                txtBirthdayShow
        txtLodgingValueShow.setText(resultUserInfo.getUserCityName());

        input_name.setText(resultUserInfo.getUserFname());
        birthday = (resultUserInfo.getUserBirthday() != null) ? Long.valueOf(resultUserInfo.getUserBirthday()) : new Date().getTime();
        txtDate.setText(Util.persianNumbers(Utils.getSimpleDateMilli(birthday)));

        //---edit

        email_address.setText(resultUserInfo.getUserEmail());
        input_name.setText(resultUserInfo.getUserFname());
        input_tel.setText(resultUserInfo.getUserPhone());
        input_family.setText(resultUserInfo.getUserLname());
        input_lodging.setText(resultUserInfo.getUserCityName());

        radioWoman.setChecked((resultUserInfo.getUserGender().equals("0")));
        radioMan.setChecked((resultUserInfo.getUserGender().equals("1")));
        if (resultUserInfo.getUserBirthday() != null) {
            txtBirthdayValueShow.setText(Util.persianNumbers(Utils.getSimpleDateMilli(birthday)));
        } else {
            txtBirthdayValueShow.setText("");
        }

        if (resultUserInfo.getUserNewsletter().equals("1")) {
            checkBoxNews.setChecked(true);
        } else {
            checkBoxNews.setChecked(false);
        }

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.input_tel:
                input_tel.setText("");
                break;
            case R.id.verifyEmailHolder:
                DaggerEditProfileComponent.builder()
                        .netComponent(((App) getApplicationContext()).getNetComponent())
                        .editProfileModule(new EditProfileModule(this)).build().inject(this);
                editProfilePresenter.getVerifyEmail(new EmailVerifyReq(resultUserInfo.getUserEmail().toString(), resultUserInfo.getUserUid().toString()), Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                break;
            case R.id.changeDateHolder:
                CustomDialogTravel cdd = new CustomDialogTravel(this);
                cdd.show();
                break;
            case R.id.btnOpenEditProfile:
                Intent intent = new Intent(this, EditProfileActivity.class);
                intent.putExtra("from", "editKey");
                intent.putExtra("infoUserResult", resultUserInfo);
                startActivity(intent);
            case R.id.btnEditProfile:
                if (from.equals("showKey"))
                    return;

                if (!validate()) {
                    Toast.makeText(getApplicationContext(), "اشکال در مقادیر ورودی", Toast.LENGTH_SHORT).show();
                    return;
                }

                builder = DaggerEditProfileComponent.builder()
                        .netComponent(((App) getApplicationContext()).getNetComponent())
                        .editProfileModule(new EditProfileModule(this));
                builder.build().inject(this);

                String gender = "0";
                if (radioMan.isChecked())
                    gender = "1";
                else if (radioWoman.isChecked())
                    gender = "0";

                String news = (checkBoxNews.isChecked()) ? "1" : "0";
                updateProfileSend updateProfileSend = new updateProfileSend(String.valueOf(Util.getUseRIdFromShareprefrence(getApplicationContext())),
                        input_name.getText().toString(),
                        input_family.getText().toString(),
                        gender,
                        email_address.getText().toString(),
                        String.valueOf(birthday),
                        "", input_lodging.getText().toString(),
                        "0",//this.userPhoneStatus
                        "1",//userEmailStatus
                        input_tel.getText().toString(),
                        news,//userNewsletter
                        Util.getTokenFromSharedPreferences(getApplicationContext()));
                editProfilePresenter.getEditProfilePostResul(updateProfileSend, Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));

                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void
    showEditProfilePostResul(ResultUpdateReturn resultUpdate) {
        resultUserInfo = resultUpdate.getResultUserInfo();
        setValues(resultUserInfo);
        Intent intentShowProfile = new Intent(getApplicationContext(), EditProfileActivity.class);
        Util.saveDataINShareprefrence(getApplicationContext(), resultUserInfo.getUserEmail(), resultUserInfo.getUserLname(), resultUserInfo.getUserGender().toString(), resultUserInfo.getUserUid().toString());
        String tagFrom = "showKey";
        intentShowProfile.putExtra("from", tagFrom);
        intentShowProfile.putExtra("infoUserResult", (Serializable) resultUserInfo);
        startActivity(intentShowProfile);
        finish();
    }

    @Override
    public void showVerifyEmailResult(ResultVerifyEmail resultVerifyEmail) {
        if (resultVerifyEmail.getResultUserEmailVerify().getStatus().equals("Succesfull")) {
            email_verify.setText("لینک فعال سازی در ایمیل خود را چک کنید");
        }
    }

    @Override
    public void showError(String message) {
        Log.e("error", " in get attraction list" + message);
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        if (message.contains("Unable to resolve host ") || message.contains("Software caused connection abort")) {
            Toast.makeText(getApplicationContext(), "عدم دسترسی به اینترنت", Toast.LENGTH_LONG).show();
        }
//        if (message.contains("HTTP 400 BAD REQUEST")) {
//            Toast.makeText(getApplicationContext(), "در این مسیر برنامه سفری یافت نشد", Toast.LENGTH_LONG).show();
//        }
    }

    @Override
    public void showComplete() {

    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(EditProfileActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("لطفا منتظر بمانید");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }


    @Override
    public void dismissProgress() {
        progressDialog.dismiss();
    }


    public class CustomDialogTravel extends Dialog implements
            View.OnClickListener {

        public Activity c;
        public Dialog d;
        public TextView yes, no;
        PersianDatePicker persianDatePickr;


        public CustomDialogTravel(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            //        bara inke keybord bala nayad
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_date_travel);
            persianDatePickr = (PersianDatePicker) findViewById(R.id.travelDate);
            yes = (TextView) findViewById(R.id.txtOk);
            no = (TextView) findViewById(R.id.txtNo);
            yes.setOnClickListener(this);
            no.setOnClickListener(this);
            persianDatePickr.setMinYear(1300);
            Date date = new Date();
            PersianCalendar persianCalendar = new PersianCalendar(date.getTime());
///-----------------------
            persianDatePickr.setMaxYear(persianCalendar.getPersianYear());
//            birthday = (resultUserInfo.getUserBirthday() != null) ? Long.valueOf(resultUserInfo.getUserBirthday()) : date.getTime();

            PersianCalendar persianCalendar1 = new PersianCalendar(birthday);
            persianDatePickr.setDisplayPersianDate(persianCalendar1);

            persianDatePickr.setOnDateChangedListener(new PersianDatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(int newYear, int newMonth, int newDay) {
                    txtDate.setText(Util.persianNumbers(Utils.getSimpleDate(persianDatePickr.getDisplayDate())));
                    birthday = persianDatePickr.getDisplayDate().getTime();
                }
            });
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.txtOk:
                    persianDatePickr.getDisplayDate();
                    dismiss();
                    break;
                case R.id.txtNo:
                    dismiss();
                    break;
                default:
                    break;
            }
            dismiss();
        }
    }
}
