package com.iranplanner.tourism.iranplanner.ui.fragment.myaccount;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coinpany.core.android.widget.Utils;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.iranplanner.tourism.iranplanner.R;

import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.showcaseview.CustomShowcaseView;
import com.iranplanner.tourism.iranplanner.standard.StandardFragment;
import com.iranplanner.tourism.iranplanner.ui.activity.SplashActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.editprofile.EditProfileActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.login.LoginActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.ScrollingActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.reqestHotelStatus.HotelReservationStatusActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.reqestHotelStatus.HotelReservationStatusContract;
import com.iranplanner.tourism.iranplanner.ui.activity.reqestHotelStatus.HotelReservationStatusListPresenter;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import entity.GetInfoReqSend;
import entity.GetInfoResult;
import entity.ReservationRequestList;
import entity.ResultBundleStatus;
import entity.ResultReqBundle;
import entity.ResultReqCount;
import entity.ResultReservationReqStatus;
import server.Config;
import tools.Constants;
import tools.Util;

/**
 * Created by Hoda on 10/01/2017.
 */
public class SettingFragment extends StandardFragment implements View.OnClickListener, SettingContract.View ,HotelReservationStatusContract.View  {

    TextView txtProfileName, btnEditProfile, txtHotelReservationStatus;
    RelativeLayout LayoutShowProfileHolder, exitFromAccount,HotelReservationStatusHolder;
    @Inject
    SettingPresenter settingPresenter;
    private String tagFrom;
    ProgressDialog progressDialog;
    String cid;
    String andId;
    String uid;
    View view;
    @Inject
    HotelReservationStatusListPresenter hotelReservationStatusListPresenter;
    private RelativeLayout TutorialHolder;

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    private void getSharedpreferences() {
        cid = Util.getTokenFromSharedPreferences(getContext());
        andId = Util.getAndroidIdFromSharedPreferences(getContext());
        uid = Util.getUseRIdFromShareprefrence(getContext());
    }

    private void requestGetUser() {

        settingPresenter.getUserInfoPostResult(new GetInfoReqSend(uid), cid, andId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        txtProfileName = (TextView) view.findViewById(R.id.txtProfileName);
        btnEditProfile = (TextView) view.findViewById(R.id.btnEditProfile);
        txtHotelReservationStatus = (TextView) view.findViewById(R.id.txtHotelReservationStatus);
        HotelReservationStatusHolder = (RelativeLayout) view.findViewById(R.id.HotelReservationStatusHolder);
        LayoutShowProfileHolder = (RelativeLayout) view.findViewById(R.id.LayoutShowProfileHolder);
        exitFromAccount = (RelativeLayout) view.findViewById(R.id.exitFromAccount);
        TutorialHolder = (RelativeLayout) view.findViewById(R.id.TutorialHolder);
        btnEditProfile.setOnClickListener(this);
        LayoutShowProfileHolder.setOnClickListener(this);
        exitFromAccount.setOnClickListener(this);
        HotelReservationStatusHolder.setOnClickListener(this);
        TutorialHolder.setOnClickListener(this);
        setLoginName();
        getSharedpreferences();
        DaggerSettingComponent.builder().netComponent(((App) getActivity().getApplicationContext()).getNetComponent())
                .settingModule(new SettingModule(this,this))
                .build().inject(this);
//        boolean responseBoolean = Boolean.parseBoolean(Util.getFromPreferences(Constants.PREF_SHOWCASE_PASSED_SETTINGFRAGMENT, "false", false,getContext()));
//
//        if (!responseBoolean) {
//            setShowCase();
//        }
        return view;
    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        boolean responseBoolean = Boolean.parseBoolean(Util.getFromPreferences(Constants.PREF_SHOWCASE_PASSED_SETTINGFRAGMENT, "false", false,getContext()));
//
//        if (!responseBoolean) {
//            setShowCase();
//        }
//    }

    private void setLoginName() {
        if (!Util.getUseRIdFromShareprefrence(getContext()).equals("")) {
            txtProfileName.setText(Util.getUserNameFromShareprefrence(getContext()) + " خوش آمدید");
        }
    }

    private void getRerReservation() {
        showProgress();
        hotelReservationStatusListPresenter.getResultReservationReqStatus("req_user_count_bundle", uid, "fa", cid, andId);
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnEditProfile:
//                requestGetUser();
//                tagFrom = "editKey";
                Intent intenta = new Intent(getActivity(), ScrollingActivity.class);
                startActivity(intenta);

                break;
            case R.id.LayoutShowProfileHolder:
                requestGetUser();
                tagFrom = "showKey";
                break;
            case R.id.exitFromAccount:
                clearSharedprefrence();
                Intent intent = new Intent(getActivity(), SplashActivity.class);
                startActivity(intent);
                break;
            case R.id.HotelReservationStatusHolder:
                getRerReservation();
                break;
            case R.id.TutorialHolder: {
                Util.saveInPreferences(Constants.PREF_SHOWCASE_PASSED_HOMEfRAGMENT, String.valueOf(false), false,getContext());
                Util.saveInPreferences(Constants.PREF_SHOWCASE_PASSED_SETTINGFRAGMENT, String.valueOf(false), false,getContext());
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.custom_alert, null);
                TextView tv = (TextView) dialogView.findViewById(R.id.alertDescription);
                TextView txtAlertTitle = (TextView) dialogView.findViewById(R.id.txtAlertTitle);

                txtAlertTitle.setText("توجه");
                tv.setText(R.string.txtCloseAppTutorial);
                dialogBuilder.setView(dialogView);
                TextView txtNo = (TextView) dialogView.findViewById(R.id.txtNo);
                txtNo.setText("خیر");
                TextView txtOk = (TextView) dialogView.findViewById(R.id.txtOk);
                txtOk.setText("بله");
                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
                txtOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.finishAffinity(getActivity());
                    }
                });

                txtNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
                break;
            }


        }
    }



    private void clearSharedprefrence() {
        SharedPreferences settings = getContext().getSharedPreferences(Config.SHARED_PREF_USER, Context.MODE_PRIVATE);
        settings.edit().clear().commit();
    }


    @Override
    public void showInfoUserResult(GetInfoResult infoResult) {
        Intent intentShowProfile = new Intent(getActivity(), EditProfileActivity.class);
        intentShowProfile.putExtra("from", tagFrom);
        intentShowProfile.putExtra("infoUserResult", (Serializable) infoResult.getResultUserInfo());
        startActivity(intentShowProfile);
    }

    @Override
    public void showHotelReservationStatusList(ReservationRequestList reservationRequestList) {

    }

    @Override
    public void showHotelReservationBundleStatus(ResultBundleStatus resultBundleStatus) {

    }

    @Override
    public void showError(String message) {
        Log.e("complete", "get attraction list");
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showComplete() {
        Log.e("complete", "get attraction list");
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(getActivity());
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

    @Override
    public void showResultReservationReqStatus(ResultReservationReqStatus resultReservationReqStatus) {
        dismissProgress();
        List<ResultReqCount> resultReqCountList = resultReservationReqStatus.getResultReqCountBundle().getResultReqCount();
        List<ResultReqBundle> resultReqBundleList = resultReservationReqStatus.getResultReqCountBundle().getResultReqBundle();
//        initRequestStatusRecyclerView(view, resultReqCountList);
        Intent intent=new Intent(getActivity(), HotelReservationStatusActivity.class);
        intent.putExtra("resultReqCountList", (Serializable) resultReqCountList);
        intent.putExtra("resultReqBundleList", (Serializable) resultReqBundleList);
        startActivity(intent);
    }
    ShowcaseView showcaseView;



    private void setShowCase() {
        Button customButton = (Button) getActivity().getLayoutInflater().inflate(R.layout.showcase_custom_button, null);
        CustomShowcaseView showcaseDrawer = new CustomShowcaseView(getResources());
        float width = getResources().getDimension(R.dimen.custom_showcase_width);
        float height = getResources().getDimension(R.dimen.custom_showcase_height);
        showcaseDrawer.customShowcaseSize(width, height);

        showcaseView = new ShowcaseView.Builder(getActivity())
                .setTarget(new ViewTarget(txtHotelReservationStatus))
                .setShowcaseDrawer(showcaseDrawer)
                .blockAllTouches()
                .replaceEndButton(customButton)
                .build();
        Util.saveInPreferences(Constants.PREF_SHOWCASE_PASSED_SETTINGFRAGMENT, String.valueOf(true), false,getContext());
        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lps.addRule(RelativeLayout.CENTER_IN_PARENT);
        int margin = Utils.dp(getContext(), 16);
        lps.setMargins(0, 0, 0, margin);
        showcaseView.setButtonPosition(lps);
        final int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        showcaseView.setButtonText(getString(R.string.tutorialNext));
        showcaseView.setContentText(getString(R.string.tutorialWhereToText));
        showcaseView.setContentTitle(getString(R.string.tutorialWhereToTitle));
        showcaseView.forceTextPosition(ShowcaseView.BELOW_SHOWCASE);
    }
}
