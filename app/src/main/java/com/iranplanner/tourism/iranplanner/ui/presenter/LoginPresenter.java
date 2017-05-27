package com.iranplanner.tourism.iranplanner.ui.presenter;


import com.iranplanner.tourism.iranplanner.ui.presenter.abs.LoginContract;
import com.iranplanner.tourism.iranplanner.ui.presenter.abs.RegisterContract;

import javax.inject.Inject;

import entity.CommentSend;
import entity.LoginReqSend;
import entity.LoginResult;
import entity.ResultCommentList;
import entity.ResultRegister;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Hoda on 11-May-16.
 */
public class LoginPresenter extends LoginContract {

    public Retrofit retrofit;
    View mView;

    @Inject
    public LoginPresenter(Retrofit retrofit, View mView) {
        this.retrofit = retrofit;
        this.mView = mView;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }


    @Override
    public void getLoginResult(String action, String email, String password, String token, String androidId) {
        mView.showProgress();
        retrofit.create(LoginService.class).getLoginResult(action, email, password, token, androidId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<LoginResult>() {

                    @Override
                    public void onCompleted() {
                        mView.dismissProgress();
                        mView.showComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissProgress();
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(LoginResult loginResult) {
                        mView.showLoginResult(loginResult);
                        mView.dismissProgress();
                    }
                });
    }

    @Override
    public void getLoginPostResul(LoginReqSend loginReqSend) {
        mView.showProgress();
        retrofit.create(LoginService.class).getLoginPostResul(loginReqSend)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<LoginResult>() {

                    @Override
                    public void onCompleted() {
                        mView.dismissProgress();
                        mView.showComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissProgress();
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(LoginResult loginResult) {
                        mView.showLoginResult(loginResult);
                        mView.dismissProgress();
                    }
                });
    }

    public interface LoginService {


        @GET("api-user.php")
        Observable<LoginResult> getLoginResult(@Query("action") String action,
                                               @Query("email") String email,
                                               @Query("password") String password,
                                               @Query("cid") String token,
                                               @Query("andId") String androidId);
        @POST("api-user.php?action=login")
        Observable<LoginResult> getLoginPostResul(@Body LoginReqSend loginReqSend);

    }
}
