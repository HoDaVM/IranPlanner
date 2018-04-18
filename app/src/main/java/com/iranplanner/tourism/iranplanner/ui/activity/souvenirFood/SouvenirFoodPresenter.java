package com.iranplanner.tourism.iranplanner.ui.activity.souvenirFood;


import android.util.Log;

import com.iranplanner.tourism.iranplanner.ui.activity.globalSearch.GlobalSearchContract;

import java.io.Serializable;

import javax.inject.Inject;

import entity.GetResultSouvenir;
import entity.GlobalResult;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Hoda on 11-May-16.
 */
public class SouvenirFoodPresenter extends SouvenirFoodContract implements Serializable {

    public Retrofit retrofit;
    View mView;

    @Inject
    public SouvenirFoodPresenter(Retrofit retrofit, View mView) {
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
    public void getSouvenirFull(String action, String value) {
        mView.showProgress();
        retrofit.create(SouvenirFoodhService.class)
                .getSouvenirFull(action, value).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())

                .subscribe(new Observer<GetResultSouvenir>() {

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
                    public void onNext(GetResultSouvenir getResultSouvenir) {
                        mView.showFullSouvenir(getResultSouvenir);
                        mView.dismissProgress();
                    }
                });
    }

    public interface SouvenirFoodhService {
        //        https://api.parsdid.com/iranplanner/app/api-souvenir.php?action=full&id=28297
        @GET("api-souvenir.php")
        Observable<GetResultSouvenir> getSouvenirFull(
                @Query("action") String action,
                @Query("id") String value);
    }
}
