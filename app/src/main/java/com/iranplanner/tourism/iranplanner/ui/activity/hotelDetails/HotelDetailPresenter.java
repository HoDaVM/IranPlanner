package com.iranplanner.tourism.iranplanner.ui.activity.hotelDetails;

import android.graphics.Color;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.ui.activity.attractionDetails.AttractionDetailContract;
import com.iranplanner.tourism.iranplanner.ui.presenter.RouteDecode;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import entity.InterestResult;
import entity.ResultCommentList;
import entity.ResultItineraryAttractionList;
import entity.ResultLodgingRoomList;
import entity.ResultParamUser;
import entity.ResultWidgetFull;
import entity.SendParamUser;
import entity.map.DestinationResult;
import entity.map.Leg;
import entity.map.Route;
import entity.map.StartLocation_;
import entity.map.Step;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by h.vahidimehr on 12/05/2017.
 */

public class HotelDetailPresenter extends HotelDetailContract {

    public Retrofit retrofit;
    View mView;
    RotateAnimation rotate;

    @Inject
    public HotelDetailPresenter(Retrofit retrofit, View mView) {
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
    public void getResultLodgingRoomList(String action, String id, String fromdate, String todate) {
        mView.showProgress();
        retrofit.create(HotelDetailService.class).getResultLodgingRoomList(action, id,  fromdate,  todate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResultLodgingRoomList>() {

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
                    public void onNext(ResultLodgingRoomList resultLodgingRoomList) {
                        mView.setLodgingRoomList(resultLodgingRoomList);
                        mView.dismissProgress();

                    }
                });
    }


    public interface HotelDetailService {


        @GET("api-lodging.php")
        Observable<ResultLodgingRoomList> getResultLodgingRoomList(
                @Query("action") String action,
                @Query("id") String id,
                @Query("fromdate") String fromdate,
                @Query("todate") String todate);
    }



}
