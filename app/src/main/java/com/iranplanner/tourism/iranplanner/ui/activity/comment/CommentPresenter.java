package com.iranplanner.tourism.iranplanner.ui.activity.comment;


import android.util.Log;

import com.iranplanner.tourism.iranplanner.ui.activity.attractionDetails.AttractionDetailPresenter;

import javax.inject.Inject;

import entity.CommentSend;
import entity.InterestResult;
import entity.ResultCommentList;
import entity.ResultParamUser;
import entity.ResultWidgetFull;
import entity.SendParamUser;
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
 * Created by Hoda on 11-May-16.
 */
public class CommentPresenter extends CommentContract {

    public Retrofit retrofit;
    View mView;

    @Inject
    public CommentPresenter(Retrofit retrofit, View mView) {
        this.retrofit = retrofit;
        this.mView = mView;
    }


    @Override
    public void getCommentList(String action, String nid, String ntype, String offset) {
        retrofit.create(CommentService.class)
                .getItineraryCommentList(action, nid, ntype, offset).subscribeOn(Schedulers.io())
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
                    public void onNext(ResultCommentList resultItineraryList) {
                        mView.showComments(resultItineraryList);
                    }
                });
//        retrofit.create(ItineraryPresenter.AttractionService.class)
//                .getItineraryCommentList(action, nid, ntype, offset).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(new Observer<ResultCommentList>() {
//
//                    @Override
//                    public void onCompleted() {
//                        mView.showComplete();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        mView.showError(e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(ResultCommentList resultCommentList) {
//                        mView.showComments(resultCommentList);
//                        Log.e("fds","fsfs");
//                    }
//                });

    }

    @Override
    public void callInsertComment(CommentSend commentSend,String cid,String andId) {
        retrofit.create(CommentService.class)
                .callInsertComment(commentSend,cid,andId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResultCommentList>() {

                    @Override
                    public void onCompleted() {
                        mView.showComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.commentResult(e.getMessage());
                    }

                    @Override
                    public void onNext(ResultCommentList resultItineraryList) {
                        mView.sendCommentMessage(resultItineraryList);
                    }
                });
    }


    @Override
    public void getWidgetResult(String action, String id, String uid, String ntype, String cid, String andId) {
        retrofit.create(CommentService.class)
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
        retrofit.create(CommentService.class)
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
    public void rateSend(String action,SendParamUser request, String cid, String andId) {
        retrofit.create(CommentService.class)
                .rateSend(action,request, cid, andId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResultParamUser>() {

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
                    public void onNext(ResultParamUser resultParamUser) {
                        mView.setRate(resultParamUser);
                    }
                });

    }

    @Override
    public void getRate(String action,SendParamUser request, String cid, String andId) {
        retrofit.create(CommentService.class)
                .getRate( action,request, cid, andId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResultParamUser>() {

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
                    public void onNext(ResultParamUser resultParamUser) {
                        mView.setRateUser(resultParamUser);
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

    public interface CommentService {
        @GET("api-data.php")
        Observable<ResultCommentList> getItineraryCommentList(
                @Query("action") String action,
                @Query("nid") String nId,
                @Query("ntype") String nType,
                @Query("offset") String offset);

        @POST("api-data.php?action=comment")
        Observable<ResultCommentList> callInsertComment(@Body CommentSend request, @Query("cid") String cid, @Query("andId") String andId);


        //-------------------------------
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

        //----------------
        @POST("api-data.php")
        Observable<ResultParamUser> rateSend(@Query("action") String action,@Body SendParamUser request, @Query("cid") String cid, @Query("andId") String andId);

        //        https://api.parsdid.com/iranplanner/app/api-data.php?action=rateinfo
        @POST("api-data.php?action=rateinfo")
        Observable<ResultParamUser> getRate(@Query("action") String action,@Body SendParamUser request, @Query("cid") String cid, @Query("andId") String andId);
    }
}
