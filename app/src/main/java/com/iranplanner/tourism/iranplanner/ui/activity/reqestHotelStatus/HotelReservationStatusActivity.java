package com.iranplanner.tourism.iranplanner.ui.activity.reqestHotelStatus;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.RecyclerItemOnClickListener;
import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.confirmHotelReservation.ActivityHotelReservationConfirm;
import com.iranplanner.tourism.iranplanner.ui.activity.reservationRequestList.ReservationRequestActivity;
import com.iranplanner.tourism.iranplanner.ui.fragment.myaccount.SettingContract;
import com.iranplanner.tourism.iranplanner.ui.fragment.myaccount.SettingPresenter;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import entity.GetInfoResult;
import entity.ReservationRequestList;
import entity.ResultBundleStatus;
import entity.ResultReqBundle;
import entity.ResultReqCount;
import entity.ResultReservationReqStatus;
import tools.Util;

/**
 * Created by h.vahidimehr on 30/08/2017.
 */

public class HotelReservationStatusActivity extends StandardActivity
        implements HotelReservationStatusContract.View, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private TextView tvCountPending, tvCountExamine, tvCountPaid, tvCountReject;
    private int which;
    private SwipeRefreshLayout swipe;

    @Inject
    HotelReservationStatusListPresenter hotelReservationStatusListPresenter;

    List<ResultReqCount> resultReqCountList;
    private ProgressDialog progress;
    List<ResultReqBundle> resultReqBundleList;

    private void getExtras() {
        Bundle extras = getIntent().getExtras();
        resultReqCountList = (List<ResultReqCount>) extras.getSerializable("resultReqCountList");
        resultReqBundleList = (List<ResultReqBundle>) extras.getSerializable("resultReqBundleList");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_reservation_hotel);
        ButterKnife.bind(this);

        initToolbar();
        getExtras();
        init();
        initRequestStatusRecyclerView(resultReqCountList, resultReqBundleList);
        DaggerHotelReservationStatusListComponent.builder()
                .netComponent(((App) getApplicationContext()).getNetComponent())
                .hotelReservationStatusListModule(new HotelReservationStatusListModule(this))
                .build().injectHotelReservationStatusActivity(this);

        //This finishes the activities remain open :D
        Intent intent = new Intent();
        intent.setAction("KILL");
        sendBroadcast(intent);
    }

    private void init() {
        tvCountPending = (TextView) findViewById(R.id.requestStatusPendingCountTv);
        tvCountExamine = (TextView) findViewById(R.id.requestStatusExamineCountTv);
        tvCountPaid = (TextView) findViewById(R.id.requestStatusPaidCountTv);
        tvCountReject = (TextView) findViewById(R.id.requestStatusRejectCountTv);

        swipe = (SwipeRefreshLayout) findViewById(R.id.requestStatusSwipe);
        swipe.setOnRefreshListener(this);

        tvCountPending.setText(Util.persianNumbers(resultReqCountList.get(1).getReservationReqStatus().getStatusCount()));
        tvCountExamine.setText(Util.persianNumbers(resultReqCountList.get(2).getReservationReqStatus().getStatusCount()));
        tvCountPaid.setText(Util.persianNumbers(resultReqCountList.get(3).getReservationReqStatus().getStatusCount()));
        tvCountReject.setText(Util.persianNumbers(resultReqCountList.get(4).getReservationReqStatus().getStatusCount()));

        findViewById(R.id.requestStatusPendingBtn).setOnClickListener(this);
        findViewById(R.id.requestStatusExamineBtn).setOnClickListener(this);
        findViewById(R.id.requestStatusPaidBtn).setOnClickListener(this);
        findViewById(R.id.requestStatusRejectBtn).setOnClickListener(this);

    }

    //swipe to refresh layout
    @Override
    public void onRefresh() {
//        swipe.setRefreshing(false);
//        settingPresenter.getResultReservationReqStatus("req_user_count_bundle", Util.getUseRIdFromShareprefrence(getApplicationContext()), "fa", Util.getTokenFromSharedPreferences(getApplicationContext()),Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
        hotelReservationStatusListPresenter.getResultReservationReqStatus("req_user_count_bundle", Util.getUseRIdFromShareprefrence(getApplicationContext()), "fa", Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.requestStatusPendingBtn:
                hotelReservationStatusListPresenter.getHotelReservationStatusList("req_user_list", "fa", Util.getUseRIdFromShareprefrence(getApplicationContext()), String.valueOf(1), "20", "0", Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                which = 1;
                break;
            case R.id.requestStatusExamineBtn:
                hotelReservationStatusListPresenter.getHotelReservationStatusList("req_user_list", "fa", Util.getUseRIdFromShareprefrence(getApplicationContext()), String.valueOf(2), "20", "0", Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                which = 2;
                break;
            case R.id.requestStatusPaidBtn:
                hotelReservationStatusListPresenter.getHotelReservationStatusList("req_user_list", "fa", Util.getUseRIdFromShareprefrence(getApplicationContext()), String.valueOf(3), "20", "0", Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                which = 3;
                break;
            case R.id.requestStatusRejectBtn:
                hotelReservationStatusListPresenter.getHotelReservationStatusList("req_user_list", "fa", Util.getUseRIdFromShareprefrence(getApplicationContext()), String.valueOf(4), "20", "0", Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                which = 4;
                break;
        }
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("اقامتهای من");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        hotelReservationStatusListPresenter.getResultReservationReqStatus("req_user_count_bundle", Util.getUseRIdFromShareprefrence(getApplicationContext()), "fa", Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
        super.onResume();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_status_reservation_hotel;
    }

    private void initRequestStatusRecyclerView(final List<ResultReqCount> resultReqCountList, final List<ResultReqBundle> resultReqBundleList) {
        RecyclerView settingRequestBundle = (RecyclerView) findViewById(R.id.settingRequestBundle);
        settingRequestBundle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        settingRequestBundle.setAdapter(new RequestBundleAdapter(getApplicationContext(), resultReqBundleList));

        settingRequestBundle.addOnItemTouchListener(new RecyclerItemOnClickListener(getApplicationContext(), new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                String offset = "0";
                ((Button) view.findViewById(R.id.btnComplete)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        hotelReservationStatusListPresenter.getHotelReservationBundleFull("req_user_bundle_full", "fa", Util.getUseRIdFromShareprefrence(getApplicationContext()), resultReqBundleList.get(position).getBundleRequest().getBundleId(), Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));

                    }
                });
                ((Button) view.findViewById(R.id.requestStatusRowRemoveBtn)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                      remove
                    }
                });
            }
        }));
    }

    @Override
    public void showHotelReservationStatusList(ReservationRequestList reservationRequestList) {

        Intent intent = new Intent(HotelReservationStatusActivity.this, ReservationRequestActivity.class);
        intent.putExtra
                (ReservationRequestList.INTENT_KEY_RESULT_RESERVATION, (Serializable) reservationRequestList.getResultReservationReqList());
        switch (which) {
            case 1:
                intent.putExtra("status", "در حال بررسی");
                break;
            case 2:
                intent.putExtra("status", "درانتظار پرداخت");
                break;
            case 3:
                intent.putExtra("status", "پرداخت شده");
                break;
            case 4:
                intent.putExtra("status", "رد شده");
                break;
        }
        startActivity(intent);
    }

    @Override
    public void showHotelReservationBundleStatus(ResultBundleStatus resultBundleStatus) {
        Intent intentReservationRegisterRoom = new Intent(getApplicationContext(), ActivityHotelReservationConfirm.class);
        entity.Bundle bb = (entity.Bundle) resultBundleStatus.getResultReservationBundleList().getBundle();
        intentReservationRegisterRoom.putExtra("RoomBundle", (Serializable) bb);
        intentReservationRegisterRoom.putExtra("BundleFrom", "HotelReservationStatusActivity");
        startActivity(intentReservationRegisterRoom);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showComplete() {

    }

    @Override
    public void dismissProgress() {
        Util.dismissProgress(progress);
    }

    @Override
    public void showResultReservationReqStatus(ResultReservationReqStatus resultReservationReqStatus) {
        resultReqCountList = resultReservationReqStatus.getResultReqCountBundle().getResultReqCount();
        resultReqBundleList = resultReservationReqStatus.getResultReqCountBundle().getResultReqBundle();
        init();
        initRequestStatusRecyclerView(resultReqCountList, resultReqBundleList);
        swipe.setRefreshing(false);
    }

    @Override
    public void showProgress() {
        progress = Util.showProgressDialog(getApplicationContext(), "لطفا منتظر بمانید", this);
    }
}
