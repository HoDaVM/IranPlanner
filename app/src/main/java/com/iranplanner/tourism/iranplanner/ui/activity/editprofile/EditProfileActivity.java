package com.iranplanner.tourism.iranplanner.ui.activity.editprofile;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
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

import java.io.File;
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
    private static final int GALLERY_REQUEST = 1;
    private TextView txtNewsValueShow, txtBirthdayValueShow, email_verify, email_address, txtDate, btnEditProfile, btnOpenEditProfile, txtTitle, txtGenderValueShow, txtNameValueShow, txtFamilyValueShow, txtPhonValueShow, txtBirthdayShow, txtLodgingValueShow;
    private EditText input_tel, input_name, input_family, input_lodging;
    private RelativeLayout changeDateHolder, verifyEmailHolder;
    private CheckBox checkBoxNews;
    private RadioButton radioWoman, radioMan;
    private ProgressDialog progressDialog;
    private LinearLayout editProfileHolder, showProfileHolder;
    private ImageView ImgUserEmailStatus,imageProfile;

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
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnableRuntimePermission();
//                selectImage();
                GetImageFromGallery();

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
        imageProfile =  findViewById(R.id.imageProfile);

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
    protected void onPause() {
        super.onPause();
        if (from.equals("showKey"))
            onBackPressed();
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




    private void selectImage() {
        final CharSequence[] items = {"از دوربین", "از گالری", "انصراف"};
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
        builder.setTitle("انتخاب عکس");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("از دوربین")) {
                    ClickImageFromCamera();
//                    dispatchTakePictureIntent();
                } else if (items[item].equals("از گالری")) {
                    GetImageFromGallery();
//                    if (ApplicationContext.checkGroupPermissions(ApplicationContext.STORAGE_PERMISSIONS)) {
//                        Intent intent = new Intent(
//                                Intent.ACTION_PICK,
//                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        intent.setType("image/*");
//                        startActivityForResult(
//                                Intent.createChooser(intent, "انتخاب عکس"),
//                                SELECT_FILE);
//                    } else {
//                        ApplicationContext.createPermissionDialog(getActivity(), getString(R.string.app_name), getString(R.string.PermissionNoStorage));
//                    }
                } else if (items[item].equals("انصراف")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    Intent CamIntent, GalIntent, CropIntent ;
    File file;
    Uri uri;
    public  static final int RequestPermissionCode  = 1 ;
    ImageView imageView;
    public void ClickImageFromCamera() {

        CamIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        file = new File(Environment.getExternalStorageDirectory(),
                "file" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        uri = Uri.fromFile(file);

        CamIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);

        CamIntent.putExtra("return-data", true);

        startActivityForResult(CamIntent, 0);

    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    public void GetImageFromGallery(){

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);

//        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//        photoPickerIntent.setType("image/*");
//        startActivityForResult(photoPickerIntent, 2);

//        GalIntent = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//
//        startActivityForResult(Intent.createChooser(GalIntent, "Select Image From Gallery"), 2);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (requestCode == 0 && resultCode == RESULT_OK) {
//
//            ImageCropFunction();
//
//        }
//        else if (requestCode == 2) {
//
//            if (data != null) {
//
//                uri = data.getData();
//
//                ImageCropFunction();
//
//            }
//        }
//        else if (requestCode == 1) {
//
//            if (data != null) {
//
//                Bundle bundle = data.getExtras();
//
//                Bitmap bitmap = bundle.getParcelable("data");
//
//                imageView.setImageBitmap(bitmap);
//
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 0 && resultCode == RESULT_OK) {
//
//            ImageCropFunction();
//
//        }
//        else if (requestCode == 2) {
//
//            if (data != null) {
//
//                uri = data.getData();
//
//                ImageCropFunction();
//
//            }
//        }
//        else if (requestCode == 1) {
//
//            if (data != null) {
//
//                Bundle bundle = data.getExtras();
//
//                Bitmap bitmap = bundle.getParcelable("data");
//
//                imageView.setImageBitmap(bitmap);
//
//            }
//        }
//    }

    public void ImageCropFunction() {

        // Image Crop Code
        try {
            CropIntent = new Intent("com.android.camera.action.CROP");

            CropIntent.setDataAndType(uri, "image/*");

            CropIntent.putExtra("crop", "true");
            CropIntent.putExtra("outputX", 180);
            CropIntent.putExtra("outputY", 180);
            CropIntent.putExtra("aspectX", 3);
            CropIntent.putExtra("aspectY", 4);
            CropIntent.putExtra("scaleUpIfNeeded", true);
            CropIntent.putExtra("return-data", true);

            startActivityForResult(CropIntent, 1);

        } catch (ActivityNotFoundException e) {

        }
    }
    //Image Crop Code End Here

    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(EditProfileActivity.this,
                Manifest.permission.CAMERA))
        {

            Toast.makeText(getApplicationContext(),"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(EditProfileActivity.this,new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getApplicationContext(),"Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(getApplicationContext(),"Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }
}
