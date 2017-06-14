package com.iranplanner.tourism.iranplanner.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.UserInfo;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.di.DaggerSettingComponent;
import com.iranplanner.tourism.iranplanner.di.SettingModule;
import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.standard.StandardFragment;
import com.iranplanner.tourism.iranplanner.ui.activity.EditProfileActivity;
import com.iranplanner.tourism.iranplanner.ui.presenter.abs.SettingContract;
import com.iranplanner.tourism.iranplanner.ui.presenter.abs.SettingPresenter;

import java.io.Serializable;

import javax.inject.Inject;

import entity.GetInfoReqSend;
import entity.GetInfoResult;
import entity.InfoResult;
import entity.LoginResult;
import server.Config;
import tools.Util;

/**
 * Created by Hoda on 10/01/2017.
 */
public class SettingFragment extends StandardFragment implements View.OnClickListener, SettingContract.View {

    TextView txtProfileName, btnEditProfile;
    RelativeLayout LayoutShowProfileHolder, exitFromAccount;
    @Inject
    SettingPresenter settingPresenter;
    private String tagFrom;

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    private void requestGetUser() {
        DaggerSettingComponent.builder().netComponent(((App) getActivity().getApplicationContext()).getNetComponent())
                .settingModule(new SettingModule(this))
                .build().inject(this);
        String cid = Util.getTokenFromSharedPreferences(getContext());
        String andId = Util.getAndroidIdFromSharedPreferences(getContext());
        String uid = Util.getUseRIdFromShareprefrence(getContext());
        settingPresenter.getUserInfoPostResult(new GetInfoReqSend(uid), cid, andId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        txtProfileName = (TextView) view.findViewById(R.id.txtProfileName);
        btnEditProfile = (TextView) view.findViewById(R.id.btnEditProfile);
        LayoutShowProfileHolder = (RelativeLayout) view.findViewById(R.id.LayoutShowProfileHolder);
        exitFromAccount = (RelativeLayout) view.findViewById(R.id.exitFromAccount);
        btnEditProfile.setOnClickListener(this);
        LayoutShowProfileHolder.setOnClickListener(this);
        exitFromAccount.setOnClickListener(this);
        setLoginName();
        return view;
    }

    private void setLoginName() {
        if (!Util.getUseRIdFromShareprefrence(getContext()).equals("")) {
            txtProfileName.setText(Util.getUserNameFromShareprefrence(getContext()) + " خوش آمدید");
        }
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnEditProfile:
                requestGetUser();
                tagFrom="editKey";

                break;
            case R.id.LayoutShowProfileHolder:
                requestGetUser();
                tagFrom="showKey";
                break;
            case R.id.exitFromAccount:
                clearSharedprefrence();
                break;

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
    public void showError(String message) {

    }

    @Override
    public void showComplete() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }
}