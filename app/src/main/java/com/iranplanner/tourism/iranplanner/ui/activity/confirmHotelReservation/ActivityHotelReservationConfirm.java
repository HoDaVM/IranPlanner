package com.iranplanner.tourism.iranplanner.ui.activity.confirmHotelReservation;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.standard.ClickableViewPager;
import com.iranplanner.tourism.iranplanner.standard.DataTransferInterface;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.reqestHotelStatus.HotelReservationStatusActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.reqestHotelStatus.HotelReservationStatusContract;
import com.iranplanner.tourism.iranplanner.ui.activity.reqestHotelStatus.HotelReservationStatusListPresenter;
import com.iranplanner.tourism.iranplanner.ui.activity.showRoom.ShowRoomActivity;
import com.iranplanner.tourism.iranplanner.ui.fragment.myaccount.SettingContract;
import com.iranplanner.tourism.iranplanner.ui.fragment.myaccount.SettingFragment;
import com.iranplanner.tourism.iranplanner.ui.fragment.myaccount.SettingPresenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import entity.GetInfoResult;
import entity.ReqLodgingReservation;
import entity.RequestLodgingReservationMain;
import entity.ReservationRequestComplete;
import entity.ReservationRequestDeleteRoom;
import entity.ReservationRequestList;
import entity.ResultBundleStatus;
import entity.ResultLodging;
import entity.ResultLodgingReservation;
import entity.ResultReqBundle;
import entity.ResultReqCount;
import entity.ResultReservationReqStatus;
import entity.ResultRoom;
import tools.CustomDialogNumberPicker;
import tools.Util;

/**
 * Created by HoDA on 8/5/2017.
 */

public class ActivityHotelReservationConfirm extends StandardActivity implements DataTransferInterface, View.OnClickListener, ConfirmHotelContract.View, HotelReservationStatusContract.View/*, HotelReservationConfirmListAdapter.GoBackInterface*/ {
    private List<ResultRoom> ResultRooms;
    private Date startOfTravel;
    private int durationTravel;
    private RelativeLayout chooseHolder;
    private TextView txtNumberRoom;
    private ResultLodging resultLodgingHotelDetail;
    private String bundleId, edtNameReservation, edtEmailReservation, edtLastNameReservation, textPhoneAddress, bundleFrom;
    private Map<Integer, Integer> selectedRooms;
    private ProgressDialog progressDialog;
    private List<ReqLodgingReservation> ReqLodgingReservationList;
    private View viewAdapter, viewEnds;
    private ConfirmReservationViewPagerAdapter confirmReservationViewPagerAdapter;
    private entity.Bundle roomBundle;
    private String hotelName;

    @Inject
    ConfirmHotelPresenter confirmHotelPresenter;
    @Inject
    HotelReservationStatusListPresenter hotelReservationStatusListPresenter;
    @BindView(R.id.pager)
    ClickableViewPager pager;

    private void getExtra() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        bundleFrom = bundle.getString("BundleFrom");
        if (bundleFrom.equals("ActivityReservationRegisterRoom")) {
            ResultRooms = (List<ResultRoom>) bundle.getSerializable("ResultRooms");
            selectedRooms = (Map<Integer, Integer>) bundle.getSerializable("selectedRooms");
            resultLodgingHotelDetail = (ResultLodging) bundle.getSerializable("resultLodgingHotelDetail");
            startOfTravel = (Date) bundle.getSerializable("startOfTravel");
            durationTravel = (int) bundle.getSerializable("durationTravel");
            bundleId = bundle.getString("bundleId");
            edtNameReservation = bundle.getString("edtNameReservation");
            edtEmailReservation = bundle.getString("edtEmailReservation");
            edtLastNameReservation = bundle.getString("edtLastNameReservation");
            textPhoneAddress = bundle.getString("textPhoneAddress");
            roomBundle = (entity.Bundle) bundle.getSerializable("RoomBundle");
            hotelName = bundle.getString("hoelName");
        } else if (bundleFrom.equals("HotelReservationStatusActivity")) {
            roomBundle = (entity.Bundle) bundle.getSerializable("RoomBundle");
            ResultRooms = roomBundle.getResultRoom();
            resultLodgingHotelDetail = (ResultLodging) bundle.getSerializable("resultLodgingHotelDetail");
            startOfTravel = new Date(Long.valueOf(roomBundle.getBundleDateFrom()));
            durationTravel = Integer.valueOf(roomBundle.getBundleDateCount());
            bundleId = roomBundle.getBundleId();
            edtNameReservation = "";
            edtEmailReservation = "";
            edtLastNameReservation = "";
            textPhoneAddress = "";
            hotelName = bundle.getString("");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_show_room);
        ButterKnife.bind(this);
        getExtra();
        DaggerConfirmHotelComponent.builder().netComponent(((App) getApplicationContext().getApplicationContext()).getNetComponent())
                .confirmHotelModule(new ConfirmHotelModule(this, this))
                .build().injectConfirmHotel(this);
        ReqLodgingReservationList = new ArrayList<ReqLodgingReservation>();

        confirmReservationViewPagerAdapter = new ConfirmReservationViewPagerAdapter(getApplicationContext(), this, ResultRooms, durationTravel, startOfTravel, bundleId,"");
        pager.setAdapter(confirmReservationViewPagerAdapter);
        pager.setCurrentItem(ResultRooms.size() - 1);
        confirmReservationViewPagerAdapter.setOnItemClickListener(new ConfirmReservationViewPagerAdapter.OnItemClickViewPagerListener() {
            @Override
            public void onItemClick(int position, String viewName, entity.ResultRoom room, View view, View viewEnd) {
                viewAdapter = view;
                viewEnds = viewEnd;
                String cid = Util.getTokenFromSharedPreferences(getApplicationContext());
                String andId = Util.getAndroidIdFromSharedPreferences(getApplicationContext());
                if (viewName.equals("nextHolder")) {
                    pager.setCurrentItem(position - 1, true);

                } else if (viewName.equals("txtOkRoom")) {
//                    getRequestMain(position,room);
                    requestConfirmHotel(getRequestMain(position, room));
                } else if (viewName.equals("okEndHolder")) {

                    confirmHotelPresenter.getReservationRequestComplete("complete_bundle", bundleId, Util.getUseRIdFromShareprefrence(getApplicationContext()), cid, andId);
                } else if (viewName.equals("roomDelete")) {

                    confirmHotelPresenter.getReservationRequestDeleteRoom("complete_bundle", bundleId, Util.getUseRIdFromShareprefrence(getApplicationContext()), cid, andId);
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("ورود مشخصات ");

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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

    private void requestConfirmHotel(ResultLodgingReservation resultLodgingReservation) {

        String cid = Util.getTokenFromSharedPreferences(getApplicationContext());
        String andId = Util.getAndroidIdFromSharedPreferences(getApplicationContext());
        confirmHotelPresenter.sendRequestReservation(resultLodgingReservation, cid, andId);

    }

    CustomDialogNumberPicker cdd;

    private void showDialogNumber(final int position, final TextView txtNumberRoom) {
        cdd = new CustomDialogNumberPicker(this, Integer.valueOf(ResultRooms.get(position).getRoomPriceQuantity()), 0, "تعداد اتاق های درخواستی", null);

        cdd.show();
        cdd.setDialogResult(new CustomDialogNumberPicker.OnDialogNumberPick() {
            @Override
            public void finish(int result) {
//                txtNumberRoom.setText(String.valueOf(result));
                selectedRooms.put(position, result);
                sums();
            }
        });
    }

    private void sums() {
        int sumationRoomsd = 0;
        for (Integer number : selectedRooms.values()) {
            sumationRoomsd = sumationRoomsd + number;
        }
//        txtNumber.setText(sumationRoomsd + "");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_show_room_viewpager;

    }

    @Override
    public void setValues(ArrayList<String> al) {
        al.get(0);
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.hotelReservationOkHolder:
                boolean fillComplete = true;
                for (ResultRoom resultRoom : ResultRooms) {
                    if (!resultRoom.getOkConfirmChange()) {
                        fillComplete = false;
                    }
                }
//                if (fillComplete) {
//                    getRequestMain(0,null);
//                    requestConfirmHotel(resultLodgingReservation);
//                } else {
//                    Toast.makeText(getApplicationContext(), "سرپرست اتاق ها وارد نشده است", Toast.LENGTH_LONG).show();
//                }
                break;
        }

    }

    ResultLodgingReservation resultLodgingReservation;

    private ResultLodgingReservation getRequestMain(int position, ResultRoom room) {
        resultLodgingReservation = new ResultLodgingReservation();
        resultLodgingReservation.setReqBundleId(bundleId);
        resultLodgingReservation.setReqHeadEmail(edtEmailReservation);
        resultLodgingReservation.setReqHeadMobile(textPhoneAddress);
        resultLodgingReservation.setReqHeadNameFirst(edtNameReservation);
        resultLodgingReservation.setReqHeadNameLast(edtLastNameReservation);
        resultLodgingReservation.setReqLodgingDateFrom(String.valueOf(startOfTravel.getTime()));
        resultLodgingReservation.setReqLodgingDateCount(String.valueOf(durationTravel));
        if (bundleFrom.equals("ActivityReservationRegisterRoom")) {
            resultLodgingReservation.setReqLodgingId(String.valueOf(resultLodgingHotelDetail.getLodgingId()));
        } else if (bundleFrom.equals("HotelReservationStatusActivity")) {
            resultLodgingReservation.setReqLodgingId(roomBundle.getBundleLodgingId());
        }

        resultLodgingReservation.setReqUid(Util.getUseRIdFromShareprefrence(getApplicationContext()));
        List<ReqLodgingReservation> reqLodgingReservations = getRequest(position, room);
        if (reqLodgingReservations.size() != 0) {
            resultLodgingReservation.setReqLodgingReservation(reqLodgingReservations);
        }
        return resultLodgingReservation;
    }

    private List<ReqLodgingReservation> getRequest(int position, ResultRoom resultRoom) {
        ReqLodgingReservationList.clear();


//        for (ResultRoom resultRoom : ResultRooms) {

        ReqLodgingReservation reqLodgingReservation = new ReqLodgingReservation();
        reqLodgingReservation.setReqRoomNo(position + "");
        reqLodgingReservation.setReqRoomId(position + "");
        reqLodgingReservation.setReqRoomReqId(bundleId + position + "");
        reqLodgingReservation.setReqRoomId(resultRoom.getRoomId());
        reqLodgingReservation.setReqRoomBundleId(bundleId);
        reqLodgingReservation.setReqRoomLodgingId(resultRoom.getRoomLodgingId());
        reqLodgingReservation.setReqRoomNameFirst(resultRoom.getHeadName());
        reqLodgingReservation.setReqRoomNameLast(resultRoom.getHeadLastName());
        reqLodgingReservation.setReqRoomNation((resultRoom.getSelectedForeign() == "0") ? "ir" : "for");
        reqLodgingReservation.setReqRoomExtraCount(resultRoom.getSelectedAddNumbers() != null ? resultRoom.getSelectedAddNumbers() : "0");
        reqLodgingReservation.setReqRoomHalfIn((resultRoom.getHalfIn() != null &&
                resultRoom.getHalfIn()) ? "1" : "0");
        reqLodgingReservation.setReqRoomHalfOut((resultRoom.getHalfOut() != null && resultRoom.getHalfOut()) ? "1" : "0");


        ReqLodgingReservationList.add(reqLodgingReservation);
        return ReqLodgingReservationList;
    }

    @Override
    public void showHotelReservationResult(RequestLodgingReservationMain loginResult) {
        pager.setCurrentItem(pager.getCurrentItem(), true);
        ((TextView) viewAdapter.findViewById(R.id.txtOkRoom)).setText("ویرایش");
        ((TextView) viewAdapter.findViewById(R.id.txtOkRoom)).setBackgroundResource(R.drawable.button_corner_grey_stroke);
        ((TextView) viewAdapter.findViewById(R.id.txtOkRoom)).setTextColor(ContextCompat.getColor(this,R.color.dark_blue));
        if (0 == pager.getCurrentItem()) {
            viewEnds.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showReservationRequestComplete(ReservationRequestComplete reservationRequestComplete) {
        if (reservationRequestComplete.getStatus().getStatus() == 200) {
            String cid = Util.getTokenFromSharedPreferences(getApplicationContext());
            String andId = Util.getAndroidIdFromSharedPreferences(getApplicationContext());
            hotelReservationStatusListPresenter.getResultReservationReqStatus("req_user_count_bundle", Util.getUseRIdFromShareprefrence(getApplicationContext()), "fa", cid, andId);
        }
    }

    @Override
    public void showReservationRequestDeleteRoom(ReservationRequestDeleteRoom reservationRequestDeleteRoom) {
        if (reservationRequestDeleteRoom.getStatus().getStatus() == 200) {
            ResultRooms.remove(0);
            confirmReservationViewPagerAdapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), "اتاق مورد نظر حذف شد !", Toast.LENGTH_LONG).show();
            Log.e("delete", "room");
        }
    }




    @Override
    public void showHotelReservationStatusList(ReservationRequestList reservationRequestList) {

    }

    @Override
    public void showHotelReservationBundleStatus(ResultBundleStatus resultBundleStatus) {

    }

    @Override
    public void showError(String message) {
        Toast.makeText(getApplicationContext(), "در عملیات ثبت اتاق اشکالی بوجود آمده است", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showComplete() {

    }

    @Override
    public void showProgress() {
        progressDialog = Util.showProgressDialog(getApplicationContext(), "لطفا منتظر بمانید", this);
    }

    @Override
    public void dismissProgress() {
        Util.dismissProgress(progressDialog);
    }

    @Override
    public void showResultReservationReqStatus(ResultReservationReqStatus resultReservationReqStatus) {
        Intent intent = new Intent(getApplicationContext(), HotelReservationStatusActivity.class);
        List<ResultReqCount> resultReqCountList = resultReservationReqStatus.getResultReqCountBundle().getResultReqCount();
        List<ResultReqBundle> resultReqBundleList = resultReservationReqStatus.getResultReqCountBundle().getResultReqBundle();
        intent.putExtra("resultReqCountList", (Serializable) resultReqCountList);
        intent.putExtra("resultReqBundleList", (Serializable) resultReqBundleList);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (bundleFrom.equals("ActivityReservationRegisterRoom")) {
            Intent intent = new Intent(ActivityHotelReservationConfirm.this,
                    ShowRoomActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else if (bundleFrom.equals("HotelReservationStatusActivity")) {
            super.onBackPressed();
        }
    }
}
