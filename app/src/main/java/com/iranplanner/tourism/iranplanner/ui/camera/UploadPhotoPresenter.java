package com.iranplanner.tourism.iranplanner.ui.camera;

import android.graphics.Color;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import javax.inject.Inject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
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

public class UploadPhotoPresenter extends UploadPhotoContract {

    public Retrofit retrofit;
    View mView;

    @Inject
    public UploadPhotoPresenter(Retrofit retrofit, View mView) {
        this.retrofit = retrofit;
        this.mView = mView;
    }



//    @Override
//    public void upload(RequestBody description, MultipartBody.Part file) {
//        retrofit.create(UploadService.class)
//                .upload(description,file).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(new Observer<ResponseBody>() {
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
//                    public void onNext(ResponseBody resultParamUser) {
//                        Log.e("Ddd","dfds");
//                    }
//                });
//    }


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
    public void upload(RequestBody description,RequestBody nid, RequestBody ntype, RequestBody uid,MultipartBody.Part file) {
        retrofit.create(UploadService.class)
                .upload(description,nid,ntype,uid,file).subscribeOn(Schedulers.io())
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
                        Log.e("upload","upload image");
                    }
                });
    }



    public interface UploadService {

//        https://api.parsdid.com/iranplanner/app/api-upload.php?action=test

        @Multipart
        @POST("api-upload.php?action=image")
        Observable<ResponseBody> upload(@Part("description") RequestBody description,
                                        @Part("nid") RequestBody nid,@Part("ntype") RequestBody ntype,@Part("uid") RequestBody uid,
                                        @Part MultipartBody.Part file);
    }



}
