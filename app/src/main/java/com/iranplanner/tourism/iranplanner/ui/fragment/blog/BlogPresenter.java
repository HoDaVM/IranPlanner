package com.iranplanner.tourism.iranplanner.ui.fragment.blog;


import com.iranplanner.tourism.iranplanner.ui.fragment.pandaMap.MapPandaContract;

import javax.inject.Inject;

import entity.PandaMapList;
import entity.ResultBlogFull;
import entity.ResultBlogList;
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
public class BlogPresenter extends BlogContract {

    public Retrofit retrofit;
    View mView;

    @Inject
    public BlogPresenter(Retrofit retrofit, View mView) {
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
    public void getBlogList(String action, String offset,
                            String cid, String androidId) {
        mView.showProgress();
        retrofit.create(BlogService.class).getBlogList(action,offset, cid, androidId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResultBlogList>() {

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
                    public void onNext(ResultBlogList resultBlogList) {
                        mView.dismissProgress();
                        mView.showBlogList(resultBlogList);
                    }
                });
    }

    @Override
    public void getBlogFull(String action, String idBlog, String cid, String androidId) {
        mView.showProgress();
        retrofit.create(BlogService.class).getBlogFull(action, idBlog, cid, androidId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResultBlogFull>() {

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
                    public void onNext(ResultBlogFull resultBlogFull) {
                        mView.dismissProgress();
                        mView.showBlogFull(resultBlogFull);
                    }
                });
    }


    public interface BlogService {

        //https://api.parsdid.com/iranplanner/app/api-blog.php?action=list
        @GET("api-blog.php?")
        Observable<ResultBlogList> getBlogList(
                @Query("action") String action,
                @Query("offset") String offset,
                @Query("cid") String cid,
                @Query("andId") String androidId);

        //https://api.parsdid.com/iranplanner/app/api-blog.php?action=full&id=37872
        @GET("api-blog.php?")
        Observable<ResultBlogFull> getBlogFull(
                @Query("action") String action,
                @Query("id") String idBlog,
                @Query("cid") String cid,
                @Query("andId") String androidId);


    }
}
