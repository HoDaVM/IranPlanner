package com.iranplanner.tourism.iranplanner.ui.fragment.pandaMap;


import com.iranplanner.tourism.iranplanner.ui.activity.login.LoginContract;

import javax.inject.Inject;

import entity.GoogleLoginReqSend;
import entity.LoginReqSend;
import entity.LoginResult;
import entity.PandaMapList;
import entity.ResultPandaMap;
import entity.ResultPandaMapSearch;
import entity.ResultPandaMaps;
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
public class MapPandaPresenter extends MapPandaContract {

    public Retrofit retrofit;
    View mView;

    @Inject
    public MapPandaPresenter(Retrofit retrofit, View mView) {
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

//    @Override
//    public void getDrawResult(PandaMapList pandaMapList, String token, String androidId) {
//        mView.showProgress();
//        retrofit.create(MapPandaService.class).getDrawResult(pandaMapList, token, androidId)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(new Observer<ResultPandaMaps>() {
//
//                    @Override
//                    public void onCompleted() {
//                        mView.dismissProgress();
//                        mView.showComplete();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        mView.dismissProgress();
//                        mView.showError(e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(ResultPandaMaps resultPandaMaps) {
//                        mView.dismissProgress();
//                        mView.showPointOnMap(resultPandaMaps);
//                    }
//                });
//    }

    @Override
    public void getDrawResult(PandaMapList pandaMapList, String valueSearch, String attractionFilter, String lodgingFilter, String position1, String position2, String token, String androidId) {
        mView.showProgress();
        retrofit.create(MapPandaService.class).getDrawResult( pandaMapList,  valueSearch,  attractionFilter,  lodgingFilter,  position1,  position2,  token,  androidId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResultPandaMaps>() {

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
                    public void onNext(ResultPandaMaps resultPandaMaps) {
                        mView.dismissProgress();
                        mView.showPointOnMap(resultPandaMaps);
                    }
                });
    }

    @Override
    public void getPandaSearch(String action, String value) {
//        mView.showProgress();
        retrofit.create(MapPandaService.class).getPandaSearch(action,value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResultPandaMapSearch>() {

                    @Override
                    public void onCompleted() {
//                        mView.dismissProgress();
//                        mView.showComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
//                        mView.dismissProgress();
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(ResultPandaMapSearch resultPandaMapSearch) {
//                        mView.dismissProgress();
                        mView.showPandaSearch(resultPandaMapSearch);
                    }
                });
    }


    public interface MapPandaService {
   //https://api.parsdid.com/iranplanner/app/api-field.php?action=pandamap
        @POST("api-field.php?action=pandamap")
        Observable<ResultPandaMaps> getDrawResult(@Body PandaMapList pandaMapList,
                                                  @Query("valueSearch") String valueSearch,
                                                  @Query("attractionFilter") String attractionFilter,
                                                  @Query("lodgingFilter") String lodgingFilter,
                                                  @Query("position1") String position1,
                                                  @Query("position2") String position2,
                                                  @Query("cid") String token,
                                                  @Query("andId") String androidId);

   //https://api.parsdid.com/iranplanner/app/api-field.php?action=pandaautocomplete&value=غار
        @GET("api-field.php?")
        Observable<ResultPandaMapSearch> getPandaSearch(
                @Query("action") String action,
                @Query("value") String value);
//                @Query("lang") String lang,
//                @Query("cid") String cid,
//                @Query("andId") String androidId);


    }
}
