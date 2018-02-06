package com.iranplanner.tourism.iranplanner.ui.activity.attractionDetails;

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
import entity.ResultParamUser;
import entity.ResultRatePost;
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

public class AttractionDetailPresenter extends AttractionDetailContract {

    public Retrofit retrofit;
    View mView;
    RotateAnimation rotate;

    @Inject
    public AttractionDetailPresenter(Retrofit retrofit, View mView) {
        this.retrofit = retrofit;
        this.mView = mView;
    }


//    @Override
//    public void getAttractionCommentList(String action, String nId, String nType, String offset, String cid, String andId) {
//        mView.showProgress();
//        retrofit.create(AttractionService.class)
//                .getItineraryCommentList(action, nId, nType, offset, cid, andId).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(new Observer<ResultCommentList>() {
//
//                    @Override
//                    public void onCompleted() {
//                        mView.showComplete();
//                        mView.dismissProgress();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        mView.showError(e.getMessage());
//                        mView.dismissProgress();
//                    }
//
//                    @Override
//                    public void onNext(ResultCommentList resultCommentList) {
//                        mView.showComment(resultCommentList, "Attraction");
//                        mView.dismissProgress();
//                    }
//                });
//
//    }
//
//    @Override
//    public void getWidgetResult(String action, String id, String uid, String ntype, String cid, String andId) {
//        retrofit.create(AttractionService.class)
//                .getWidgetResult(action, id, uid, ntype, cid, andId).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(new Observer<ResultWidgetFull>() {
//
//                    @Override
//                    public void onCompleted() {
////                        mView.showComplete();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        mView.showError(e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(ResultWidgetFull resultWidgetFull) {
////                        mView.showAttraction(resultCommentList);
//                        mView.setLoadWidget(resultWidgetFull);
//
//                    }
//                });
//    }
//
//    @Override
//    public void getInterest(String action, String uid, String cid, String ntype, String nid, String gtype, String gvalue, String andId) {
//        retrofit.create(AttractionService.class)
//                .getInterest(action, uid, cid, ntype, nid, gtype, gvalue, andId).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(new Observer<InterestResult>() {
//
//                    @Override
//                    public void onCompleted() {
////                        rotate.setRepeatCount(0);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        mView.showError(e.getMessage());
////                        rotate.setRepeatCount(0);
//                    }
//
//                    @Override
//                    public void onNext(InterestResult interestResult) {
//                        mView.setIntrestedWidget(interestResult);
////                        rotate.setRepeatCount(0);
//
//                    }
//                });
//    }

    @Override
    public void doWaitingAnimation(ImageView image) {

        rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setRepeatCount(5);
        rotate.setDuration(5000);
        rotate.setInterpolator(new LinearInterpolator());
        image.startAnimation(rotate);
        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                mView.showAnimationWhenWaiting();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public boolean doTranslateAnimationUp(RelativeLayout relativeLayout, RelativeLayout relativeLayout2, ImageView imageView) {
        AnimatorSet mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(
                ObjectAnimator.ofFloat(relativeLayout, "translationY", 0),
                ObjectAnimator.ofFloat(relativeLayout2, "translationY", 0),
                ObjectAnimator.ofFloat(imageView, "translationY", 0));
        mAnimatorSet.setDuration(1000);
        mAnimatorSet.start();
        return false;

    }

//    @Override
//    public boolean doTranslateAnimationDown(RelativeLayout relativeLayout, RelativeLayout relativeLayout2, ImageView imageView, int height) {
//        AnimatorSet mAnimatorSet = new AnimatorSet();
//        mAnimatorSet.playTogether(
//                ObjectAnimator.ofFloat(relativeLayout, "translationY", height),
//                ObjectAnimator.ofFloat(relativeLayout2, "translationY", height),
//                ObjectAnimator.ofFloat(imageView, "translationY", -55));
//        mAnimatorSet.setDuration(1000);
//        mAnimatorSet.start();
//        return true;
//    }




//    @Override
//    public void rateSend(SendParamUser request, String cid, String andId) {
//        retrofit.create(AttractionService.class)
//                .rateSend(request, cid, andId).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(new Observer<ResultParamUser>() {
//
//                    @Override
//                    public void onCompleted() {
////                        mView.showComplete();
//                        Log.e("direction path", "complete");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
////                        mView.showError(e.getMessage());
//                        Log.e("e", e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(ResultParamUser resultParamUser) {
//                        mView.setRate(resultParamUser);
//                    }
//                });
//
//    }
//
//    @Override
//    public void getRate(SendParamUser request, String cid, String andId) {
//        retrofit.create(AttractionService.class)
//                .rateSend(request, cid, andId).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(new Observer<ResultParamUser>() {
//
//                    @Override
//                    public void onCompleted() {
////                        mView.showComplete();
//                        Log.e("direction path", "complete");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
////                        mView.showError(e.getMessage());
//                        Log.e("e", e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(ResultParamUser resultParamUser) {
//                        mView.setRateUser(resultParamUser);
//                    }
//                });
//
//    }

    @Override
    public void getDirection(String origin, String destination) {
        retrofit.create(AttractionService.class)
                .getDirection(origin, destination).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<DestinationResult>() {

                    @Override
                    public void onCompleted() {
//                        mView.showComplete();
                        Log.e("direction path", "complete");
                    }

                    @Override
                    public void onError(Throwable e) {
//                        mView.showError(e.getMessage());
                        Log.e("e", e.getMessage());
                    }

                    @Override
                    public void onNext(DestinationResult directionResults) {
//                        mView.showAttraction(resultCommentList);
//                        mView.showItineraryComment(resultCommentList, "Itinerary");
                        ArrayList<LatLng> routelist = new ArrayList<LatLng>();
                        ArrayList<LatLng> decodelist;
                        Route routeA = directionResults.getRoutes().get(0);
                        Leg legs = routeA.getLegs().get(0);
                        List<Leg> legses = routeA.getLegs();
                        StartLocation_ location = null;
                        String polyline;
                        List<Step> steps = directionResults.getRoutes().get(0).getLegs().get(0).getSteps();
                        for (Step step : steps) {
                            location = step.getStartLocation();
                            routelist.add(new LatLng(location.getLat(), location.getLng()));
                            Log.i("zacharia", "Start Location :" + location.getLat() + ", " + location.getLng());
                            polyline = step.getPolyline().getPoints();
                            decodelist = RouteDecode.decodePoly(polyline);
                            routelist.addAll(decodelist);
                            Log.i("zacharia", "routelist size : " + routelist.size());
                            if (routelist.size() > 0) {
                                PolylineOptions rectLine = new PolylineOptions().width(10).color(
                                        Color.RED);
                                for (int i = 0; i < routelist.size(); i++) {
                                    rectLine.add(routelist.get(i));
                                }
                                // Adding route on the map
                                MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
                                mView.showDirectionOnMap(rectLine);

                            }
                        }
                    }
                });

    }

    @Override
    public void upload(RequestBody description, MultipartBody.Part file) {
        retrofit.create(AttractionService.class)
                .upload(description,file).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {

                    @Override
                    public void onCompleted() {
//                        mView.showComplete();
                        Log.e("direction path", "complete");
                    }

                    @Override
                    public void onError(Throwable e) {
//                        mView.showError(e.getMessage());
                        Log.e("e", e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody resultParamUser) {
                        Log.e("Ddd","dfds");
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


    public interface AttractionService {
        @GET("api-itinerary.php")
        Observable<ResultItineraryAttractionList> getItineraryAttractionList(
                @Query("action") String action,
                @Query("lang") String lang,
                @Query("id") String id,
                @Query("cid") String cid,
                @Query("andId") String andId
        );

        @GET("api-data.php")
        Observable<ResultCommentList> getItineraryCommentList(
                @Query("action") String action,
                @Query("nid") String nId,
                @Query("ntype") String nType,
                @Query("offset") String offset,
                @Query("cid") String cid,
                @Query("andId") String andId);


        @GET("api-data.php")
        Observable<ResultWidgetFull> getWidgetResult(@Query("action") String action,
                                                     @Query("id") String id,
                                                     @Query("uid") String uid,
                                                     @Query("ntype") String ntype,
                                                     @Query("cid") String cid,
                                                     @Query("andId") String andId);

        @GET("api-data.php")
        Observable<InterestResult> getInterest(
                @Query("action") String action,
                @Query("uid") String uid,
                @Query("cid") String cid,
                @Query("ntype") String ntype,
                @Query("nid") String nid,
                @Query("gtype") String gtype,
                @Query("gvalue") String gvalue,
                @Query("andId") String andId);

        //        https://maps.googleapis.com/maps/api/directions/json?origin=35.6859016418457,51.38629913330078&destination=36.40290069580078,55.01570129394531&sensor=false
        @GET("/maps/api/directions/json")
        Observable<DestinationResult> getDirection(@Query("origin") String origin,
                                                   @Query("destination") String destination);

        //https://api.parsdid.com/iranplanner/app/api-data.php?action=rate
        @POST("api-data.php?action=rate")
        Observable<ResultParamUser> rateSend(@Body SendParamUser request, @Query("cid") String cid, @Query("andId") String andId);


        //        https://api.parsdid.com/iranplanner/app/api-data.php?action=rateinfo
        @POST("api-data.php?action=rateinfo")
        Observable<ResultParamUser> getRate(@Body SendParamUser request, @Query("cid") String cid, @Query("andId") String andId);

//        https://api.parsdid.com/iranplanner/app/api-upload.php?action=test

        @Multipart
        @POST("api-upload.php?action=test")
        Observable<ResponseBody> upload(@Part("description") RequestBody description,
                                        @Part MultipartBody.Part file);
    }



}
