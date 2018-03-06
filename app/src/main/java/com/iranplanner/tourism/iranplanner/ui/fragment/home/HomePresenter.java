package com.iranplanner.tourism.iranplanner.ui.fragment.home;


import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.iranplanner.tourism.iranplanner.ui.fragment.blog.BlogPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import entity.GetHomeResult;
import entity.HomeAndBlog;
import entity.ResultBlogList;
import entity.ResultEvents;
import entity.ResultItineraryList;
import entity.ShowAttractionListMore;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by Hoda on 11-May-16.
 */
public class HomePresenter extends HomeContract {

    public Retrofit retrofit;
    View mView;

    @Inject
    public HomePresenter(Retrofit retrofit, View mView) {
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
    public void getHome(String action, String type, String value, String token, String androidId) {
        mView.showProgress();
        retrofit.create(HomeService.class)
                .getHomeResult(action, type, value, token, androidId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())

                .subscribe(new Observer<GetHomeResult>() {

                    @Override
                    public void onCompleted() {
                        mView.showComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                        mView.dismissProgress();

                    }

                    @Override
                    public void onNext(GetHomeResult getHomeResult) {
                        mView.dismissProgress();
                        mView.ShowHomeResult(getHomeResult);
                    }
                });
    }


    @Override
    public void getEventMore(String action, String lang, String id, String type, String cid, String androidId) {
        mView.showProgress();
        retrofit.create(HomeService.class)
                .getEventMore(action, lang, id, type, cid, androidId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResultEvents>() {

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
                    public void onNext(ResultEvents ResultEvents) {
                        mView.ShowEventLists(ResultEvents);
                    }
                });
    }

    @Override
    public void getEventDetail(String action, String lang, String id, String cid, String androidId) {
        mView.showProgress();
        HomeService backendApi = retrofit.create(HomeService.class);
        List<Observable<?>> requests = new ArrayList<>();
        retrofit.create(HomeService.class)
                .getEventDetail(action, lang, id, cid, androidId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResultEvents>() {

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
                    public void onNext(ResultEvents ResultEvent) {
                        mView.ShowEventDetail(ResultEvent);
                    }
                });
    }

    @Override
    public void getItineraryDetail(String action, String id, String lang, String cid, String androidId) {
        mView.showProgress();
        retrofit.create(HomeService.class)
                .getItineraryDetail(action, id, lang, cid, androidId).subscribeOn(Schedulers.io())
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
                        mView.ShowItineryDetail(resultItineraryList);
                    }
                });
    }


    public void getHomeAndBlog(String actionHome, String type, String value, String token, String androidId, String actionBlog) {
        Observable<GetHomeResult> homeObs = retrofit
                .create(HomeService.class)
                .getHomeResult(actionHome, type, value, token, androidId).subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<ResultBlogList> blogObs = retrofit
                .create(BlogPresenter.BlogService.class).getBlogList(actionBlog,"0", token, androidId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<HomeAndBlog> combined = Observable.zip(homeObs, blogObs, new Func2<GetHomeResult, ResultBlogList, HomeAndBlog>() {
            @Override
            public HomeAndBlog call(GetHomeResult jsonObject, ResultBlogList jsonElements) {
                return new HomeAndBlog(jsonObject, jsonElements);
            }
        });

        combined.subscribe(new Subscriber<HomeAndBlog>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("HomeAndBlog", "error");
            }

            @Override
            public void onNext(HomeAndBlog homeAndBlog) {
                Log.e("HomeAndBlog", "success");
                mView.showHomeAndBlog(homeAndBlog);
            }
        });
    }

    //    action=home&type=city&value=309
    public interface HomeService {
        @GET("api-home.php")
        Observable<GetHomeResult> getHomeResult(
                @Query("action") String action,
                @Query("type") String type,
                @Query("value") String value,
                @Query("cid") String cid,
                @Query("andId") String androidId);

        //        https://api.parsdid.com//iranplanner/app/api-attraction.php?action=search&lang=fa&city=617
//        action=placetype&id=
        @GET("api-attraction.php")
        Observable<ShowAttractionListMore> getAttractionMore(
                @Query("action") String action,
                @Query("lang") String lang,
                @Query("id") String value,
                @Query("placetype") String placetype,
                @Query("offset") String offset,
                @Query("cid") String cid,
                @Query("andId") String androidId,
                @Query("type") String attractionType
        );

        //        https://api.parsdid.com/iranplanner/app/api-event.php?action=list&lang=fa&id=342&type=city
        @GET("api-event.php")
        Observable<ResultEvents> getEventMore(
                @Query("action") String action,
                @Query("lang") String lang,
                @Query("id") String id,
                @Query("type") String type,
                @Query("cid") String cid,
                @Query("andId") String androidId);

        //        https://api.parsdid.com/iranplanner/app/api-event.php?action=full&lang=fa&id=37750
        @GET("api-event.php")
        Observable<ResultEvents> getEventDetail(
                @Query("action") String action,
                @Query("lang") String lang,
                @Query("id") String id,
                @Query("cid") String cid,
                @Query("andId") String androidId);

        //        https://api.parsdid.com/iranplanner/app/api-itinerary.php?action=full&id=28439&lang=fa
        @GET("api-itinerary.php")
        Observable<ResultItineraryList> getItineraryDetail(
                @Query("action") String action,
                @Query("id") String id,
                @Query("lang") String lang,
                @Query("cid") String cid,
                @Query("andId") String androidId);


    }


}
