package com.iranplanner.tourism.iranplanner.ui.activity.event;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.coinpany.core.android.widget.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.ui.activity.MapFullActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entity.ItineraryLodgingCity;
import entity.ResultEvent;
import entity.ResultLodging;
import tools.Util;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EventActivity extends AppCompatActivity implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private Marker marker;
    private ResultEvent resultEvent;

    private TextView
            tvEventStatus, tvEventName, tvEventCity, tvEventSubTitle, tvEventHoldingDate, tvEventVisitationHour, tvEventAddress, tvEventAbout;
    private ImageView banner;

    private long holdingDateTimestamp, endDateTimestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        getExtra();
        initToolbar();
        init();
    }

    private void getExtra() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        resultEvent = (ResultEvent) bundle.getSerializable("ResultEvent");
    }

    private void initToolbar() {
        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);

        collapsingToolbarLayout.setTitle("  ");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void init() {

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tvEventStatus = (TextView) findViewById(R.id.eventStatusTv);
        tvEventName = (TextView) findViewById(R.id.eventTitleTv);
        tvEventCity = (TextView) findViewById(R.id.eventCityTv);
        tvEventSubTitle = (TextView) findViewById(R.id.eventSubTitleTv);
        tvEventHoldingDate = (TextView) findViewById(R.id.eventHoldingDateTv);
        tvEventVisitationHour = (TextView) findViewById(R.id.eventVisitationHourTv);
        tvEventAddress = (TextView) findViewById(R.id.eventAddressTv);
        tvEventAbout = (TextView) findViewById(R.id.eventAboutTv);

        banner = (ImageView) findViewById(R.id.expandedImage);
        Glide.with(this).load(resultEvent.getEventInfo().getImgUrl()).into(banner);

        String address;
        if (resultEvent.getEventInfo().getEventAddress() == null)
            address = "آدرس : ";
        else
            address = "آدرس : " + resultEvent.getEventInfo().getEventAddress();

        tvEventName.setText(resultEvent.getEventInfo().getEventTitle());
        tvEventCity.setText(resultEvent.getEventInfo().getEventCityTitle());
        tvEventSubTitle.setText(resultEvent.getEventInfo().getEventProvinceTitle());

        holdingDateTimestamp = Long.parseLong(resultEvent.getEventInfo().getEventDateStart());
        endDateTimestamp = Long.parseLong(resultEvent.getEventInfo().getEventDateEnd());
        String holdingDate = Util.persianNumbers("تاریخ برگزاری : " + "از " + Utils.getSimpleDate(convertTime(holdingDateTimestamp)) + " تا "
                + Utils.getSimpleDate(convertTime(endDateTimestamp)));

        tvEventHoldingDate.setText(holdingDate);
        tvEventVisitationHour.setText(resultEvent.getEventInfo().getEventDateDuration());

        tvEventAddress.setText(address);
        tvEventAbout.setText(Html.fromHtml(resultEvent.getEventInfo().getEventBody()));

        setEventStatus();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(false);

        MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker));
        Double lan = 35.6892;
        Double lon = 51.3890;

        marker = mMap.addMarker(markerOptions
                .position(new LatLng(lan, lon))
                .title("Amin is Awesome")
        );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lan, lon), 15.0f));
    }

    private void setEventStatus() {
        Long currentTimestamp = System.currentTimeMillis();
        if (currentTimestamp > holdingDateTimestamp && currentTimestamp < endDateTimestamp) {
            tvEventStatus.setText("درحال برگزاری");
            tvEventStatus.getBackground().setColorFilter(getColor(R.color.greenpress), PorterDuff.Mode.SRC_ATOP);
        }
    }

    private Date convertTime(long time) {
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        return date;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_event;
//    }
}
