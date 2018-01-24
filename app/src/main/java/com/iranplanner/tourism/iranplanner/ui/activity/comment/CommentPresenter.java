package com.iranplanner.tourism.iranplanner.ui.activity.comment;


import javax.inject.Inject;

import entity.CommentSend;
import entity.ResultCommentList;
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
    }
}
