package com.iranplanner.tourism.iranplanner.ui.activity.moreItemItinerary;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import com.iranplanner.tourism.iranplanner.ui.activity.moreItemItinerary.ItineraryContract;
import com.iranplanner.tourism.iranplanner.ui.presenter.RouteDecode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import entity.InterestResult;
import entity.ResultCommentList;
import entity.ResultItineraryAttractionList;
import entity.ResultWidgetFull;


import entity.ShowAttractionListItinerary;
import entity.map.DestinationResult;
import entity.map.Leg;
import entity.map.Route;
import entity.map.StartLocation_;
import entity.map.Step;
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

public class ItineraryPresenter extends ItineraryContract {

    public Retrofit retrofit;
    ItineraryContract.View mView;
    RotateAnimation rotate;

    @Inject
    public ItineraryPresenter(Retrofit retrofit, ItineraryContract.View mView) {
        this.retrofit = retrofit;
        this.mView = mView;
    }

    @Override
    public void getItineraryAttractionList(String action, String lang, String id, String cid, String andId) {
        retrofit.create(AttractionService.class)
                .getItineraryAttractionList(action, lang, id, cid, andId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResultItineraryAttractionList>() {

                    @Override
                    public void onCompleted() {
                        mView.showComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(ResultItineraryAttractionList resultItineraryList) {
//                        mView.showItineraries(resultItineraryList, "fromCityToCity");
                        mView.showAttraction(resultItineraryList);

                    }
                });
    }

    @Override
    public void getItineraryAttractionListDay(String action, String lang, String id, String cid, String andId) {
        retrofit.create(AttractionService.class)
                .getItineraryAttractionListDay(action, lang, id, cid, andId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ShowAttractionListItinerary>() {

                    @Override
                    public void onCompleted() {
                        mView.showComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(ShowAttractionListItinerary resultItineraryList) {
//                        mView.showItineraries(resultItineraryList, "fromCityToCity");
                        mView.showAttractionDay(resultItineraryList);

                    }
                });
    }

    @Override
    public void getItineraryCommentList(String action, String nId, String nType, String offset, String cid, String andId) {
        retrofit.create(AttractionService.class)
                .getItineraryCommentList(action, nId, nType, offset, cid, andId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResultCommentList>() {

                    @Override
                    public void onCompleted() {
                        mView.showComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(ResultCommentList resultCommentList) {
//                        mView.showAttraction(resultCommentList);
                        mView.showItineraryComment(resultCommentList, "Itinerary");

                    }
                });

    }

    @Override
    public void getWidgetResult(String action, String id, String uid, String ntype, String cid, String andId) {
        retrofit.create(AttractionService.class)
                .getWidgetResult(action, id, uid, ntype, cid, andId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResultWidgetFull>() {

                    @Override
                    public void onCompleted() {
//                        mView.showComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(ResultWidgetFull resultWidgetFull) {
//                        mView.showAttraction(resultCommentList);
                        mView.setLoadWidget(resultWidgetFull);

                    }
                });
    }

    @Override
    public void getInterest(String action, String uid, String cid, String ntype, String nid, String gtype, String gvalue, String andId) {
        retrofit.create(AttractionService.class)
                .getInterest(action, uid, cid, ntype, nid, gtype, gvalue, andId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<InterestResult>() {

                    @Override
                    public void onCompleted() {
//                        rotate.setRepeatCount(0);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
//                        rotate.setRepeatCount(0);
                    }

                    @Override
                    public void onNext(InterestResult interestResult) {
                        mView.setIntrestedWidget(interestResult);
//                        rotate.setRepeatCount(0);

                    }
                });
    }

    @Override
    public void doWaitingAnimation(ImageView image) {

        rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setRepeatCount(5);
        rotate.setDuration(3000);
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
        mAnimatorSet.setDuration(700);
        mAnimatorSet.start();
        return false;

    }

    @Override
    public boolean doTranslateAnimationDown(RelativeLayout relativeLayout, RelativeLayout relativeLayout2, ImageView imageView, int height) {
        AnimatorSet mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(
                ObjectAnimator.ofFloat(relativeLayout, "translationY", height),
                ObjectAnimator.ofFloat(relativeLayout2, "translationY", height),
                ObjectAnimator.ofFloat(imageView, "translationY", -55));
        mAnimatorSet.setDuration(700);
        mAnimatorSet.start();
        return true;
    }

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
                                MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_logo_foreground));
                                mView.showDirectionOnMap(rectLine);

                            }
                        }
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

        @GET("/maps/api/directions/json")
        Observable<DestinationResult> getDirection(@Query("origin") String origin,
                                                   @Query("destination") String destination);


//        https://api.parsdid.com/iranplanner/app/api-itinerary.php?action=attractionday&id=29458&lang=fa&offset=20
        @GET("api-itinerary.php")
        Observable<ShowAttractionListItinerary> getItineraryAttractionListDay(
                @Query("action") String action,
                @Query("lang") String lang,
                @Query("id") String id,
                @Query("cid") String cid,
                @Query("andId") String andId
        );
    }
}
