package com.iranplanner.tourism.iranplanner.ui.navigationDrawer;

import android.support.design.widget.TextInputEditText;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.iranplanner.tourism.iranplanner.R;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    private EditText etMessage;
    private TextInputEditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        init();
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("تماس باما");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        findViewById(R.id.contactUsSubmitBtn).setOnClickListener(this);

        etMessage = (EditText) findViewById(R.id.contactUsMessageEt);
        etName = (TextInputEditText) findViewById(R.id.contactUsNameEt);

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
        if (validate())
            Toast.makeText(this, "Your message has been sent !", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Please Fill All the Fields", Toast.LENGTH_SHORT).show();
    }

    private boolean validate() {
        return !TextUtils.isEmpty(etMessage.getText()) && !TextUtils.isEmpty(etName.getText())
                && TextUtils.getTrimmedLength(etMessage.getText()) > 15;
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
    }

}
