package com.iranplanner.tourism.iranplanner.ui.activity.restaurant;

import android.view.animation.RotateAnimation;

import com.iranplanner.tourism.iranplanner.ui.activity.hotelDetails.HotelDetailContract;

import javax.inject.Inject;

import entity.RestaurantFull;
import entity.RestaurantList;
import entity.ResultLodgingRoomList;
import entity.ResultRestaurantFull;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by h.vahidimehr on 12/05/2017.
 */

public class RestaurantPresenter extends RestaurantContract {

    public Retrofit retrofit;
    View mView;


    @Inject
    public RestaurantPresenter(Retrofit retrofit, View mView) {
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
    public void getRestaurantFull(String action, String restaurantID, String token, String androidId) {
        mView.showProgress();
        retrofit.create(RestaurantService.class).getRestaurantFull(action, restaurantID, token, androidId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<RestaurantFull>() {

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
                    public void onNext(RestaurantFull resultRestaurantFull) {
                        mView.setRestaurantFull(resultRestaurantFull.getResultRestaurantFull());
                        mView.dismissProgress();

                    }
                });
    }

    @Override
    public void getRestaurantList(String action, String id, String placetype, String limit, String offset, String token, String androidId) {
        mView.showProgress();
        retrofit.create(RestaurantService.class).getRestaurantList(action,id,placetype, limit, offset, token, androidId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<RestaurantList>() {

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
                    public void onNext(RestaurantList restaurantList) {
                        mView.setRestaurantList(restaurantList);
                        mView.dismissProgress();
                    }
                });
    }




    public interface RestaurantService {
        //        https://api.parsdid.com/iranplanner/app/api-restaurant.php?action=full&id=38882

        @GET("api-restaurant.php")
        Observable<RestaurantFull> getRestaurantFull(@Query("action") String action,
                                                     @Query("id") String restaurantID,
                                                     @Query("cid") String token,
                                                     @Query("andId") String androidId);
//       https://api.parsdid.com/iranplanner/app/api-restaurant.php?action=search&lang=fa&id=344&placetype=city

        @GET("api-restaurant.php")
        Observable<RestaurantList> getRestaurantList(@Query("action") String action,
                                                     @Query("id") String id,
                                                     @Query("placetype") String placetype,
                                                     @Query("limit") String limit,
                                                     @Query("offset") String offset,
                                                     @Query("cid") String token,
                                                     @Query("andId") String androidId);
    }


}
