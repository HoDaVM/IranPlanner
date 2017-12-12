package com.iranplanner.tourism.iranplanner.ui.fragment.itinerarySearch;


import javax.inject.Inject;

import entity.ResultItineraryList;
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
public class MainSearchPresenter extends MainSearchContract {

    public Retrofit retrofit;
    MainSearchContract.View mView;

    @Inject
    public MainSearchPresenter(Retrofit retrofit, MainSearchContract.View mView) {
        this.retrofit = retrofit;
        this.mView = mView;
    }

    @Override
    public void loadItineraryFromCity(String action, String lang, String from, String limit, String offset, String to, String cid,
                                      String andID) {
        mView.showProgress();

        retrofit.create(ItineraryService.class)
                .getItinerariesFromCity(action, lang, from, limit, offset, to, cid, andID).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResultItineraryList>() {

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
                    public void onNext(ResultItineraryList resultItineraryList) {
                        mView.dismissProgress();
                        mView.showItineraries(resultItineraryList, "fromCityToCity");
                    }
                });
    }

    @Override
    public void loadItineraryFromProvince(String action, String province, String offset, String cid,
                                          String andID) {
        mView.showProgress();
        retrofit.create(ItineraryService.class)
                .getItinerariesFromProvince(action, province, offset, cid, andID).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResultItineraryList>() {

                    @Override
                    public void onCompleted() {
                        mView.showComplete();
                        mView.dismissProgress();

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                        mView.dismissProgress();

                    }

                    @Override
                    public void onNext(ResultItineraryList resultItineraryList) {
                        mView.showItineraries(resultItineraryList, "fromProvince");
                        mView.dismissProgress();

                    }
                });
    }

    @Override
    public void loadItineraryFromAttraction(String action, String lang, String from, String limit, String offset, String attraction, String cid,
                                            String andID) {
        mView.showProgress();
        retrofit.create(ItineraryService.class).getItinerariesAttraction(action, lang, from, limit, offset, attraction, cid, andID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResultItineraryList>() {

                    @Override
                    public void onCompleted() {
                        mView.showComplete();
                        mView.dismissProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                        mView.dismissProgress();

                    }

                    @Override
                    public void onNext(ResultItineraryList resultItineraryList) {
                        mView.showItineraries(resultItineraryList, "fromAttraction");
                        mView.dismissProgress();



                    }
                });
    }

    public interface ItineraryService {
        @GET("api-itinerary.php")
        Observable<ResultItineraryList> getItinerariesFromCity(@Query("action") String action,
                                                               @Query("lang") String lang,
                                                               @Query("from") String from,
                                                               @Query("limit") String limit,
                                                               @Query("offset") String offset,
                                                               @Query("to") String to,
                                                               @Query("cid") String cid,
                                                               @Query("andId") String andId);

        @GET("api-itinerary.php")
        Observable<ResultItineraryList> getItinerariesFromProvince(@Query("action") String action,
                                                                   @Query("province") String province,
                                                                   @Query("offset") String offset,
                                                                   @Query("cid") String cid,
                                                                   @Query("andId") String andId);

        @GET("api-itinerary.php")
        Observable<ResultItineraryList> getItinerariesAttraction(@Query("action") String action,
                                                                 @Query("lang") String lang,
                                                                 @Query("from") String from,
                                                                 @Query("limit") String limit,
                                                                 @Query("offset") String offset,
                                                                 @Query("attraction") String attraction,
                                                                 @Query("cid") String cid,
                                                                 @Query("andId") String andId
        );

    }
}
