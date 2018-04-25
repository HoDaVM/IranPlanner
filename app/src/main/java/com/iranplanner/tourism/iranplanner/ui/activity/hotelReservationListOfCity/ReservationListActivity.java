package com.iranplanner.tourism.iranplanner.ui.activity.hotelReservationListOfCity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coinpany.core.android.widget.Utils;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.RecyclerItemOnClickListener;
import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.standard.DataTransferInterface;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.reservationHotelList.ReservationHotelListActivity;
import com.iranplanner.tourism.iranplanner.ui.filterManager.FilterManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import entity.ResultItinerary;
import entity.ResultLodging;
import entity.ResultLodgingList;
import tools.Util;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by h.vahidimehr on 21/02/2017.
 */

public class ReservationListActivity extends StandardActivity implements DataTransferInterface, ReservationContract.View {
    private ReseveDateListAdapter adapter;
    private LinearLayoutManager mLayoutManager;
    private Date startOfTravel;
    private ResultItinerary itineraryData;
    private int durationTravel = 1;
    private ProgressDialog progressDialog;
    DaggerReservationComponent.Builder builder;
    @Inject
    ReservationPresenter reservationPresenter;
    protected Toolbar toolbar;

    private View selectHolderTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list);

        builder = DaggerReservationComponent.builder()
                .netComponent(((App) getApplicationContext()).getNetComponent())
                .reservationModule(new ReservationModule(this));
        builder.build().inject(this);

        Bundle extras = getIntent().getExtras();
        itineraryData = (ResultItinerary) extras.getSerializable("itineraryData");
        startOfTravel = (Date) extras.getSerializable("startOfTravel");

        setupToolbar();
        init();

        FilterManager filterManager = new FilterManager(this, findViewById(R.id.filterView));
        filterManager.hideToggleButtons();
    }

    private void init() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.reservationListRecyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ReseveDateListAdapter(ReservationListActivity.this, this, itineraryData, getApplicationContext(), R.layout.fragment_itinerary_item, startOfTravel);
        recyclerView.setAdapter(adapter);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addOnItemTouchListener(new RecyclerItemOnClickListener(getApplicationContext(), new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                ImageView reservationBtn = (ImageView) view.findViewById(R.id.ReservationBtn);
                TextView txtDurationLodgingCity = (TextView) view.findViewById(R.id.txtDurationLodgingCity);
                durationTravel = Integer.valueOf((String) txtDurationLodgingCity.getText());
                reservationBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("reserve", "click");
                        reservationPresenter.getLodgingList("list", itineraryData.getItineraryLodgingCity().get(position + 1).getCityId(), Util.getTokenFromSharedPreferences(getApplicationContext()), "20", "0", Util.getAndroidIdFromSharedPreferences(getApplicationContext()), "");
                    }
                });
            }
        }));

        selectHolderTop = findViewById(R.id.selectHoldetTop);
        selectHolderTop.setVisibility(View.GONE);

    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("اقامت های پیشنهادی");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction("KILL");
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reservation_list;
    }

    @Override
    public void setValues(ArrayList<String> al) {
        al.get(0);
    }

    @Override
    public void showLodgingList(ResultLodgingList resultLodgingList) {
        if(resultLodgingList.getResultLodging()!=null &&resultLodgingList.getResultLodging().size()>0){
            List<ResultLodging> resultLodgings = resultLodgingList.getResultLodging();
            Intent intent = new Intent(getApplicationContext(), ReservationHotelListActivity.class);
            intent.putExtra("resultLodgings", (Serializable) resultLodgings);
            intent.putExtra("startOfTravel", startOfTravel);
            intent.putExtra("durationTravel", durationTravel);
            intent.putExtra("todayDate", resultLodgingList.getStatistics().getDateNow());
            startActivity(intent);
        }else {
            Toast.makeText(getApplicationContext(),"برای شهر مذکور هنوز اقامتگاهی ثبت نشده است",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void showError(String message) {
        if (progressDialog != null)
            progressDialog.dismiss();
        if (message.contains("Unable to resolve host ") || message.contains("Software caused connection abort"))
            Toast.makeText(getApplicationContext(), "عدم دسترسی به اینترنت", Toast.LENGTH_LONG).show();
        if (message.contains("HTTP 400 BAD REQUEST"))
            Toast.makeText(getApplicationContext(), "مرکز اقامتی وجود ندارد", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showComplete() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(this);
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

}
