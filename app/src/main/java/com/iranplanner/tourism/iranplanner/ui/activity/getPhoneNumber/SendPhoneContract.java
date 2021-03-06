package com.iranplanner.tourism.iranplanner.ui.activity.getPhoneNumber;

import com.iranplanner.tourism.iranplanner.ui.presenter.Presenter;

import entity.ResultSendPhone;


/**
 * Created by Hoda
 */
public abstract class SendPhoneContract extends Presenter<SendPhoneContract.View> {
    public interface View {

        void showError(String message);

        void showComplete();

        void showProgress();

        void dismissProgress();

        void getPhoneResult(ResultSendPhone resultSendPhone);
    }

    public abstract void sendPhoneResult(String action,
                                         String phone,
                                         String cid,
                                         String androidID);

}
