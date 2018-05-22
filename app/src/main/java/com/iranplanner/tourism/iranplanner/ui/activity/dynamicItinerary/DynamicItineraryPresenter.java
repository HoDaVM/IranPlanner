package com.iranplanner.tourism.iranplanner.ui.activity.dynamicItinerary;


import javax.inject.Inject;

import entity.LoginResult;
import entity.MyItineraryAdd;
import entity.MyItineraryList;
import entity.ResultEditDynamicItinerary;
import entity.ResultPositionAddItinerary;
import entity.SendParamSaveDynamicItinerary;
import entity.SendParamToAddItinerary;
import entity.SendParamUsetToGetItinerary;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Hoda on 11-May-16.
 */
public class DynamicItineraryPresenter extends DynamicItineraryContract {

    public Retrofit retrofit;
    View mView;

    @Inject
    public DynamicItineraryPresenter(Retrofit retrofit, View mView) {
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
    public void getDynamicItineraryList(SendParamUsetToGetItinerary sendParamUserToGetItinerary, String token, String androidId) {
        mView.showProgress();
        retrofit.create(DynamicItineraryService.class).getDynamicItineraryList(sendParamUserToGetItinerary, token, androidId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<MyItineraryList>() {

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
                    public void onNext(MyItineraryList myItineraryList) {
                        mView.showDynamicItineraryList(myItineraryList);
                        mView.dismissProgress();
                    }
                });
    }

    @Override
    public void addNewDynamicItinerary(String action, SendParamToAddItinerary sendParamToAddItinerary, String token, String androidId) {
        mView.showProgress();
        retrofit.create(DynamicItineraryService.class).addNewDynamicItinerary(action, sendParamToAddItinerary, token, androidId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<MyItineraryAdd>() {

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
                    public void onNext(MyItineraryAdd MyItineraryAdd) {
                        mView.confirmationAddDynamicItinerary(MyItineraryAdd);
                        mView.dismissProgress();
                    }
                });
    }

    @Override
    public void addPosition(SendParamUsetToGetItinerary sendParamUsetToGetItinerary, String token, String androidId) {
        mView.showProgress();
        retrofit.create(DynamicItineraryService.class).addPosition(sendParamUsetToGetItinerary, token, androidId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResultPositionAddItinerary>() {

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
                    public void onNext(ResultPositionAddItinerary resultPositionAddItinerary) {
                        mView.confirmationAddDynamicPosition(resultPositionAddItinerary);
                        mView.dismissProgress();
                    }
                });
    }

    @Override
    public void getResultEditDynamicItinerary(SendParamUsetToGetItinerary sendParamUsetToGetItinerary, String token, String androidId) {
        mView.showProgress();
        retrofit.create(DynamicItineraryService.class).getResultEditDynamicItinerary(sendParamUsetToGetItinerary, token, androidId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResultEditDynamicItinerary>() {

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
                    public void onNext(ResultEditDynamicItinerary resultEditDynamicItinerary) {
                        mView.showResultEditDynamicItinerary(resultEditDynamicItinerary);
                        mView.dismissProgress();
                    }
                });
    }

    @Override
    public void sendParamForSaveDynamicItinerary(SendParamSaveDynamicItinerary SendParamSaveDynamicItinerary, String token, String androidId) {
        mView.showProgress();
        retrofit.create(DynamicItineraryService.class).sendParamForSaveDynamicItinerary(SendParamSaveDynamicItinerary, token, androidId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResultEditDynamicItinerary>() {

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
                    public void onNext(ResultEditDynamicItinerary resultEditDynamicItinerary) {
                        mView.showResultEditDynamicItinerary(resultEditDynamicItinerary);
                        mView.dismissProgress();
                    }
                });
    }


    public interface DynamicItineraryService {

        //      https://api.parsdid.com/iranplanner/app/api-itn.php?action=itn_list_user
        @POST("api-itn.php?action=itn_list_user")
        Observable<MyItineraryList> getDynamicItineraryList(@Body SendParamUsetToGetItinerary sendParamUserToGetItinerary,
                                                            @Query("cid") String token,
                                                            @Query("andId") String androidId);

        //      https://api.parsdid.com/iranplanner/app/api-itn.php?action=itn_add
        //      https://api.parsdid.com/iranplanner/app/api-itn.php?action=clone_itinerary
        @POST("api-itn.php")
        Observable<MyItineraryAdd> addNewDynamicItinerary(@Query("action") String action,
                                                          @Body SendParamToAddItinerary sendParamToAddItinerary,
                                                          @Query("cid") String token,
                                                          @Query("andId") String androidId);

        //      https://api.parsdid.com/iranplanner/app/api-itn.php?action=position_add
        @POST("api-itn.php?action=position_add")
        Observable<ResultPositionAddItinerary> addPosition(@Body SendParamUsetToGetItinerary sendParamUsetToGetItinerary,
                                                           @Query("cid") String token,
                                                           @Query("andId") String androidId);

        //      https://api.parsdid.com/iranplanner/app/api-itn.php?action=itn_full_user
        @POST("api-itn.php?action=itn_full_user")
        Observable<ResultEditDynamicItinerary> getResultEditDynamicItinerary(@Body SendParamUsetToGetItinerary sendParamUsetToGetItinerary,
                                                                             @Query("cid") String token,
                                                                             @Query("andId") String androidId);

        //      https://api.parsdid.com/iranplanner/app/api-itn.php?action=itn_position
        @POST("api-itn.php?action=itn_position")
        Observable<ResultEditDynamicItinerary> sendParamForSaveDynamicItinerary(@Body SendParamSaveDynamicItinerary SendParamSaveDynamicItinerary,
                                                                                @Query("cid") String token,
                                                                                @Query("andId") String androidId);
    }
}
