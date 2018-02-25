package com.iranplanner.tourism.iranplanner.ui.activity.comment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.iranplanner.tourism.iranplanner.BuildConfig;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.ui.activity.attractionDetails.AttractionDetailPresenter;
import com.iranplanner.tourism.iranplanner.ui.activity.attractionDetails.attractionDetailActivity;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import entity.CommentSend;
import entity.InterestResult;
import entity.ResultCommentList;
import entity.ResultImageList;
import entity.ResultParamUser;
import entity.ResultWidgetFull;
import entity.SendParamUser;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tools.Util;

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
        mView.showProgress();
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
                        mView.dismissProgress();
                    }

                    @Override
                    public void onNext(ResultCommentList resultItineraryList) {
                        mView.showComments(resultItineraryList);
                        mView.dismissProgress();
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
    public void callInsertComment(CommentSend commentSend, String cid, String andId) {
        retrofit.create(CommentService.class)
                .callInsertComment(commentSend, cid, andId).subscribeOn(Schedulers.io())
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
    public void rateSend(String action, SendParamUser request, String cid, String andId) {
        mView.showProgress();
        retrofit.create(CommentService.class)
                .rateSend(action, request, cid, andId).subscribeOn(Schedulers.io())
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
                        mView.dismissProgress();
                    }

                    @Override
                    public void onNext(ResultParamUser resultParamUser) {
                        mView.setRate(resultParamUser);
                        mView.dismissProgress();
                    }
                });

    }

    @Override
    public void getRate(String action, SendParamUser request, String cid, String andId) {
        mView.showProgress();
        retrofit.create(CommentService.class)
                .getRate(action, request, cid, andId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResultParamUser>() {

                    @Override
                    public void onCompleted() {
//                        mView.showComplete();
                        Log.e("direction path", "complete");
                        mView.dismissProgress();

                    }

                    @Override
                    public void onError(Throwable e) {
//                        mView.showError(e.getMessage());
                        Log.e("e", e.getMessage());
                        mView.dismissProgress();

                    }

                    @Override
                    public void onNext(ResultParamUser resultParamUser) {
                        mView.setRateUser(resultParamUser);
                        mView.dismissProgress();
                    }
                });

    }

    @Override
    public void getImages(String action, String nid, String ntype) {
        mView.showProgress();
        retrofit.create(CommentService.class)
                .getImages(action, nid, ntype).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResultImageList>() {

                    @Override
                    public void onCompleted() {
                        Log.e("direction path", "complete");
                        mView.dismissProgress();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("e", e.getMessage());
                        mView.dismissProgress();

                    }

                    @Override
                    public void onNext(ResultImageList resultImageList) {
                        mView.showMoreImages(resultImageList);
                        mView.dismissProgress();
                    }
                });
    }

    private static final int SELECT_FILE = 14;

    public void selectImage(final Activity activity) {
        final CharSequence[] items = {"از دوربین", "از گالری", "انصراف"};
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("انتخاب عکس");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("از دوربین")) {
                    dispatchTakePictureIntent(activity);
                } else if (items[item].equals("از گالری")) {
                    if (App.checkGroupPermissions(App.STORAGE_PERMISSIONS)) {
                        Intent intent = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        activity.startActivityForResult(
                                Intent.createChooser(intent, "انتخاب عکس"),
                                SELECT_FILE);
                    } else {
                        App.createPermissionDialog(activity, activity.getString(R.string.app_name), "permission");
                    }
                } else if (items[item].equals("انصراف")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    Intent CamIntent;
    private Uri mImageUri;
    private static final int REQUEST_CAMERA = 0;

    public void dispatchTakePictureIntent(Activity activity) {


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = null;

        try {
            photo = Util.createImageFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (photo != null) {
            if (Build.VERSION.SDK_INT > 21) {
                CamIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                mImageUri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".provider", photo);
                CamIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                mView.setImageUri(mImageUri);
                activity.startActivityForResult(CamIntent, REQUEST_CAMERA);
            } else {


            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
            //start camera intent
            mView.setImageUri(mImageUri);
            activity.startActivityForResult(intent, REQUEST_CAMERA);
        }
    }

}


    public Bitmap grabImage(Activity activity) {
        ContentResolver cr = activity.getContentResolver();
        Bitmap bitmap = null;
        int tryCount = 0;
        try {
            while ((bitmap = MediaStore.Images.Media.getBitmap(cr, mImageUri)) == null) {
                Thread.sleep(10);
                tryCount++;
                if (tryCount > 500) {
                    return null;
                }
            }

            bitmap = MediaStore.Images.Media.getBitmap(cr, mImageUri);
            int orientation = 0;
            Matrix matrix = new Matrix();
            try {
                ExifInterface ei = new ExifInterface(mImageUri.getPath());
                int exif = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                switch (exif) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        orientation = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        orientation = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        orientation = 270;
                        break;
                }
                matrix.preRotate(orientation);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Rotate image bitmap to correct orientation.
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            double ratio = (double) width / (double) height;


//        if ((width > height) && (width > MAX_IMAGE_TO_CROP_PIXEL)) {
//            width = MAX_IMAGE_TO_CROP_PIXEL;
//            height = (int) ((double) MAX_IMAGE_TO_CROP_PIXEL / ratio);
//        } else if (height > MAX_IMAGE_TO_CROP_PIXEL) {
//            height = MAX_IMAGE_TO_CROP_PIXEL;
//            width = (int) ((double) MAX_IMAGE_TO_CROP_PIXEL * ratio);
//        }
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
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

    //        https://api.parsdid.com/iranplanner/app/api-data.php?action=images
    @GET("api-data.php")
    Observable<ResultImageList> getImages(
            @Query("action") String action,
            @Query("nid") String nid,
            @Query("ntype") String ntype);

    //----------------
    @POST("api-data.php")
    Observable<ResultParamUser> rateSend(@Query("action") String action, @Body SendParamUser request, @Query("cid") String cid, @Query("andId") String andId);

    //        https://api.parsdid.com/iranplanner/app/api-data.php?action=rateinfo
    @POST("api-data.php")
    Observable<ResultParamUser> getRate(@Query("action") String action, @Body SendParamUser request, @Query("cid") String cid, @Query("andId") String andId);

}
}
