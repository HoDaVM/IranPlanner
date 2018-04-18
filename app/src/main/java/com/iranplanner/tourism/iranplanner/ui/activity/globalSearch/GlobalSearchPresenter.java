package com.iranplanner.tourism.iranplanner.ui.activity.globalSearch;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.iranplanner.tourism.iranplanner.BuildConfig;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentContract;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.inject.Inject;

import entity.CommentSend;
import entity.GlobalResult;
import entity.InterestResult;
import entity.ResultCommentList;
import entity.ResultImageList;
import entity.ResultParamUser;
import entity.ResultWidgetFull;
import entity.SendParamUser;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tools.Util;

/**
 * Created by Hoda on 11-May-16.
 */
public class GlobalSearchPresenter extends GlobalSearchContract implements Serializable {

    public Retrofit retrofit;
    View mView;

    @Inject
    public GlobalSearchPresenter(Retrofit retrofit, View mView) {
        this.retrofit = retrofit;
        this.mView = mView;
    }


    @Override
    public void getGlobalSearch(String action, String value, String offset) {

        mView.showProgress();

        retrofit.create(GlobalSearchService.class)
                .getGlobalSearch(action, value, offset).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())

                .subscribe(new Observer<GlobalResult>() {

                    @Override
                    public void onCompleted() {
                        Log.e("direction path", "complete");
                        mView.dismissProgress();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("e", e.getMessage());
                        mView.dismissProgress();

                    }

                    @Override
                    public void onNext(GlobalResult globalResult) {
                        mView.showSearchResult(globalResult.getResultGlobalSearch());
                        mView.dismissProgress();
                    }
                });



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

    public interface GlobalSearchService {


        //        https://api.parsdid.com/iranplanner/app/api-field.php?action=globalsearch&value=حسن
        @GET("api-field.php")
        Observable<GlobalResult> getGlobalSearch(
                @Query("action") String action,
                @Query("value") String value,
                @Query("offset") String offset);


    }
}
