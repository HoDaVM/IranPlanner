package com.iranplanner.tourism.iranplanner.ui.activity.reservationHotelList;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coinpany.core.android.widget.Utils;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.RecyclerItemOnClickListener;
import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.standard.DataTransferInterface;
import com.iranplanner.tourism.iranplanner.ui.activity.filterMap.FilterMap;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.hotelDetails.ReservationHotelDetailActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.hotelReservationListOfCity.ReservationContract;
import com.iranplanner.tourism.iranplanner.ui.activity.hotelReservationListOfCity.ReservationPresenter;
import com.iranplanner.tourism.iranplanner.ui.filterManager.FilterListener;
import com.iranplanner.tourism.iranplanner.ui.filterManager.FilterManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import entity.ResultLodging;
import entity.ResultLodgingHotel;
import entity.ResultLodgingList;
import tools.Constants;
import tools.CustomDialogDate;
import tools.CustomDialogNumberPicker;
import tools.Util;

/**
 * Created by h.vahidimehr on 21/02/2017.
 */

public class ReservationHotelListActivity extends StandardActivity implements DataTransferInterface, ReservationHotelListContract.View, View.OnClickListener, ReservationContract.View, FilterListener {
    @Inject
    ReservationPresenter reservationPresenter;
    @Inject
    ReservationHotelListPresenter reservationHotelListPresenter;
    ProgressDialog progressDialog;
    private ReseveHotelListAdapter adapter;
    LinearLayoutManager mLayoutManager;
    Date startOfTravel;
    List<ResultLodging> resultLodgings;
    int durationTravel;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private ProgressBar waitingLoading;
    private boolean loading = true;
    @BindView(R.id.TypeDurationHolder)
    RelativeLayout typeDurationHolder;
    @BindView(R.id.holderDate)
    RelativeLayout holderDate;
    @BindView(R.id.reservationListRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.txtDateCheckIn)
    TextView txtTypeHotel;
    @BindView(R.id.txtDurationHotel)
    TextView txtDurationHotel;
    private String nextOffset;
    private String todayDate, cityName;

    private String order;
    private String rate0, rate1, rate2, rate3, rate4, rate5;

    private String typeHotel, typeLocalhost, typeTraditional, typeApartment, type;

    private FilterManager filterManager;

    private String cityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list);

        ButterKnife.bind(this);
        DaggerReservationHotelListComponent.builder()
                .netComponent(((App) getApplicationContext()).getNetComponent())
                .reservationHotelListModule(new ReservationHotelListModule(this, this))
                .build().injectReservationHotelList(this);

        getExtras();
        txtDurationHotel.setText(" به مدت " + Util.persianNumbers(durationTravel + " شب "));
        txtTypeHotel.setText("از " + Util.persianNumbers(Utils.getSimpleDate(startOfTravel)));
        if (resultLodgings.size() > 0) {
            setUpRecyclerView();
        }
        setupToolbar();


        holderDate.setOnClickListener(this);
        typeDurationHolder.setOnClickListener(this);

        findViewById(R.id.mapToggleView).setOnClickListener(this);

        filterManager = new FilterManager(this, findViewById(R.id.container));
        filterManager.setListener(this);
        filterManager.enableSort();
        filterManager.enablePlaceType();
        filterManager.enablePlaceRate();
    }

    @Override
    public void onDataChanged(HashMap<String, String> hashMap) {

        order = hashMap.get(FilterManager.KEY_ORDER);

        rate0 = hashMap.get(FilterManager.KEY_RATE_ZERO);
        rate1 = hashMap.get(FilterManager.KEY_RATE_ONE);
        rate2 = hashMap.get(FilterManager.KEY_RATE_TWO);
        rate3 = hashMap.get(FilterManager.KEY_RATE_THREE);
        rate4 = hashMap.get(FilterManager.KEY_RATE_FOUR);
        rate5 = hashMap.get(FilterManager.KEY_RATE_FIVE);

        typeHotel = hashMap.get(FilterManager.KEY_HOTEL);
        typeApartment = hashMap.get(FilterManager.KEY_APARTEMENT_HOTEL);
        typeLocalhost = hashMap.get(FilterManager.KEY_LOCAL_HOTEL);
        typeTraditional = hashMap.get(FilterManager.KEY_TRADITIONAL_HOTEL);

        reservationHotelListPresenter.getHotelFilter("list", cityId, "20", "0", type, order, rate0, rate1, rate2, rate3, rate4, rate5, typeHotel, typeLocalhost, typeTraditional, typeApartment, Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));

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

    private void getExtras() {
        Bundle extras = getIntent().getExtras();

        resultLodgings = (List<ResultLodging>) extras.getSerializable("resultLodgings");
        startOfTravel = (Date) extras.getSerializable("startOfTravel");

        durationTravel = (int) extras.getSerializable("durationTravel");
        nextOffset = extras.getString("nextOffset");
        todayDate = extras.getString("todayDate");
        cityName = extras.getString("cityName");


        if (resultLodgings.size() > 0) {
            cityId = resultLodgings.get(0).getLodgingCityId();
            type = resultLodgings.get(0).getLodginTypeId();
        } else {
            type = "";
        }


        order = "";
        rate0 = "";
        rate1 = "";
        rate2 = "";
        rate3 = "";
        rate4 = "";
        rate5 = "";
        if (type.equals(Constants.hotelCode)) {
            typeHotel = "1";
        } else if (type.equals(Constants.hotelApartmentCode)) {
            typeApartment = "1";
        } else if (type.equals(Constants.hotelboomgardiCode)) {
            typeLocalhost = "1";
        } else if (type.equals(Constants.hotelSonnatiCode)) {
            typeTraditional = "1";
        }
        type = "";


    }

    private void setUpRecyclerView() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new ReseveHotelListAdapter(ReservationHotelListActivity.this, this, resultLodgings, getApplicationContext(), R.layout.fragment_itinerary_item);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemOnClickListener(getApplicationContext(), new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
//                showProgressDialog();
                String offset = "0";
                reservationHotelListPresenter.getHotelReserve("full", String.valueOf(resultLodgings.get(position).getLodgingId()), "20", offset, Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
//               reservationHotelListPresenter.getHotelFilter("list", String.valueOf(resultLodgings.get(0).getLodgingCityId()), "20", offset, type, order, rate0, rate1, rate2, rate3, rate4, rate5, typeHotel, typeLocalhost, typeTraditional, typeApartment,  Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));

            }
        }));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && loading) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = false;
                        reservationHotelListPresenter.getHotelFilterONDemandLoading("list", String.valueOf(resultLodgings.get(0).getLodgingCityId()), "20", nextOffset, type, order, rate0, rate1, rate2, rate3, rate4, rate5, typeHotel, typeLocalhost, typeTraditional, typeApartment, Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                    }
                }
            }

        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reservation_list;
    }

    private void showDialogNumber() {
        CustomDialogNumberPicker cdd = new CustomDialogNumberPicker(this, 10, 1, "مدت زمان اقامت", null);
        cdd.show();
        cdd.setDialogResult(new CustomDialogNumberPicker.OnDialogNumberPick() {
            @Override
            public void finish(int result) {
                durationTravel = result;
                txtDurationHotel.setText(" به مدت " + Util.persianNumbers(result + " شب "));
            }
        });
    }

    private void showDialogDate() {
        CustomDialogDate customDialogDate = new CustomDialogDate(this);
        customDialogDate.show();
        customDialogDate.setDialogDateResult(new CustomDialogDate.OnDialogDatePick() {
            @Override
            public void finish(Date result) {
                startOfTravel = result;
                txtTypeHotel.setText("از " + Util.persianNumbers(Utils.getSimpleDate(result)));
            }
        });
    }

    void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (!TextUtils.isEmpty(cityName)){
            getSupportActionBar().setTitle("هتل های " + cityName);
        }else {
            getSupportActionBar().setTitle("هتل های " + resultLodgings.get(0).getLodgingCityName());
        }

    }

    @Override
    public void setValues(ArrayList<String> al) {
        al.get(0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.holderDate:
                showDialogDate();
                break;
            case R.id.TypeDurationHolder:
                showDialogNumber();
                break;
            case R.id.toolbarBack:
                Log.e("ddd", "toolbarback");
                break;
            case R.id.mapToggleView:
                long time = System.currentTimeMillis();
                Date startOfTravel = new Date(time);

                Intent intent = new Intent(this, FilterMap.class);
                intent.putExtra("resultLodgings", (Serializable) resultLodgings);
                intent.putExtra("nextOffset", "0");
                intent.putExtra("startOfTravel", startOfTravel);
                intent.putExtra("durationTravel", 3);
                intent.putExtra("todayDate", todayDate);
                intent.putExtra("cityName", cityName);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        filterManager.onBackPressed();
    }

    @Override
    public void showLodgingList(ResultLodgingList resultLodgingList) {
//        loading = true;
        List<ResultLodging> jj = resultLodgingList.getResultLodging();

        if (!nextOffset.equals(resultLodgingList.getStatistics().getOffsetNext())) {
            resultLodgings.addAll(jj);
            adapter.notifyDataSetChanged();
            nextOffset = resultLodgingList.getStatistics().getOffsetNext().toString();
            if (resultLodgingList.getResultLodging().size() > 0) {
                loading = true;
            }
        } else {
        }
    }

    @Override
    public void showHotelReserveList(ResultLodgingHotel resultLodgingHotel) {
        if (resultLodgingHotel != null) {
            ResultLodging resultLodgingHotelDetail = resultLodgingHotel.getResultLodging();
            Intent intent = new Intent(getApplicationContext(), ReservationHotelDetailActivity.class);
            intent.putExtra("resultLodgingHotelDetail", (Serializable) resultLodgingHotelDetail);
            intent.putExtra("startOfTravel", startOfTravel);
            intent.putExtra("durationTravel", durationTravel);
            intent.putExtra("todayDate", todayDate);
            startActivity(intent);
        }
    }

    @Override
    public void showError(String message) {
        Log.e(TAG, message);
    }

    @Override
    public void showComplete() {
        Util.dismissProgress(progressDialog);
    }


    @Override
    public void showProgress() {
        progressDialog = Util.showProgressDialog(getApplicationContext(), "لطفا منتظر بمانید", ReservationHotelListActivity.this);
    }

    @Override
    public void showLodgingList(ResultLodgingList resultLodgingList, String filter) {
        Log.e("lodgingList", "filter");
//        loading = true;
        List<ResultLodging> jj = resultLodgingList.getResultLodging();
//        if (!nextOffset.equals(resultLodgingList.getStatistics().getOffsetNext().toString())) {
        if (filter.equals("filter")) {
            resultLodgings.clear();
        }

        resultLodgings.addAll(jj);
        adapter.notifyDataSetChanged();
        nextOffset = resultLodgingList.getStatistics().getOffsetNext().toString();
        if (resultLodgingList.getResultLodging().size() > 0) {
            loading = true;
        }

//        }

    }

    @Override
    public void dismissProgress() {
        Util.dismissProgress(progressDialog);
    }

}
