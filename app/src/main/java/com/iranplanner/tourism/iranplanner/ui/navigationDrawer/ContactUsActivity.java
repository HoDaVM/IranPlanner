package com.iranplanner.tourism.iranplanner.ui.navigationDrawer;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputEditText;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.ui.fragment.myaccount.DaggerSettingComponent;
import com.iranplanner.tourism.iranplanner.ui.fragment.myaccount.SettingContract;
import com.iranplanner.tourism.iranplanner.ui.fragment.myaccount.SettingModule;
import com.iranplanner.tourism.iranplanner.ui.fragment.myaccount.SettingPresenter;

import javax.inject.Inject;

import entity.ContactUs;
import entity.GetInfoResult;
import entity.ResultReservationReqStatus;
import entity.SendParamContactUs;
import tools.Util;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback, SettingContract.View {
    private GoogleMap mMap;
    private EditText etMessage;
    private EditText etName;
    private EditText etMail;

    @Inject
    SettingPresenter settingPresenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        init();
        DaggerSettingComponent.builder().netComponent(((App) getApplicationContext().getApplicationContext()).getNetComponent())
                .settingModule(new SettingModule(this))
                .build().injectUser(this);
    }

    private void init() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("تماس باما");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        findViewById(R.id.contactUsSubmitBtn).setOnClickListener(this);

        etMessage = (EditText) findViewById(R.id.contactUsMessageEt);
//        etName = (TextInputEditText) findViewById(R.id.contactUsNameEt);
        etMail = (EditText) findViewById(R.id.input_email);

//        FragmentManager fm = getSupportFragmentManager();
//        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentByTag("mapFragment");
//        if (mapFragment == null) {
//            mapFragment = new SupportMapFragment();
//            FragmentTransaction ft = fm.beginTransaction();
//            ft.add(R.id.thisShit, mapFragment, "mapFragment");
//            ft.commit();
//            fm.executePendingTransactions();
//        }

    }

    private void submitMessage() {
        if (validate()) {
            Toast.makeText(this, "Your message has been sent !", Toast.LENGTH_SHORT).show();
            settingPresenter.sendParamContactUs(new SendParamContactUs("", etMail.getText().toString(), "d", "d", etMessage.getText().toString(), "app-android-feedback"), "", "");
        }
    }



    private boolean validate() {
        boolean valid = true;
        if (etMail.getText().toString().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(etMail.getText().toString()).matches()) {
            etMail.setError("فرمت ایمیل اشتباه است");
            valid = false;
        } else {
            etMail.setError(null);
        }
        if (etMessage.getText().toString().equals("")) {
            valid = false;
            etMessage.setError("متنی وارد نشده است");
        }
        return valid;
    }

    @Override
    public void onClick(View view) {
        submitMessage();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap != null) {
            mMap = googleMap;
            mMap.getUiSettings().setAllGesturesEnabled(false);
            mMap.getUiSettings().setMapToolbarEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker));
            Double lan = 35.812881;
            Double lon = 51.420301;

            googleMap.addMarker(markerOptions
                    .position(new LatLng(lan, lon))
                    .title("ایران پلنر")
            );
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lan, lon), 12.0f));
        }
    }

    @Override
    public void showInfoUserResult(GetInfoResult infoResult) {

    }

    @Override
    public void showError(String message) {
        Toast.makeText(getApplicationContext(), "اشکال در فرستادن پیام", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showComplete() {

    }

    @Override
    public void showProgress() {
        progressDialog = Util.showProgressDialog(getApplicationContext(), "منتظر بمانید", ContactUsActivity.this);

    }

    @Override
    public void dismissProgress() {
        Util.dismissProgress(progressDialog);

    }

    @Override
    public void getContactUsMessage(ContactUs CcntactUs) {
        Toast.makeText(getApplicationContext(), "پیام با موفقیت ارسال شد", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showResultReservationReqStatus(ResultReservationReqStatus resultReservationReqStatus) {

    }
}
