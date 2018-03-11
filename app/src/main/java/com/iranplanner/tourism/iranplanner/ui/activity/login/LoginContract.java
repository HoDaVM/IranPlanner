package com.iranplanner.tourism.iranplanner.ui.activity.login;

import android.app.Activity;

import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentPresenter;
import com.iranplanner.tourism.iranplanner.ui.presenter.Presenter;

import entity.LoginReqSend;
import entity.LoginResult;
import entity.GoogleLoginReqSend;


/**
 * Created by Hoda
 */
public abstract class LoginContract extends Presenter<LoginContract.View> {
    public interface View {
        void showLoginResult(LoginResult loginResult);

        void showError(String message);

        void showComplete();

        void showProgress();

        void dismissProgress();
    }


    public abstract void getLoginResult(String action,
                                        String email,
                                        String password,
                                        String token,
                                        String androidId);

    public abstract void getLoginPostResul(LoginReqSend request, String cid,
                                           String androidId);
    public abstract void getGoogleLoginPostResult(Activity activity,
                                                  OnLoginFinishListener onLoginFinishListener, GoogleLoginReqSend GoogleLoginReqSend, String cid,
                                                  String androidId);
}
