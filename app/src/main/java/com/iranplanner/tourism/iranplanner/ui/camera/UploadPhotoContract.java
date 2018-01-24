package com.iranplanner.tourism.iranplanner.ui.camera;

import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.model.PolylineOptions;
import com.iranplanner.tourism.iranplanner.ui.presenter.Presenter;

import entity.InterestResult;
import entity.ResultCommentList;
import entity.ResultParamUser;
import entity.ResultWidgetFull;
import entity.SendParamUser;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Created by Hoda
 */
public abstract class UploadPhotoContract extends Presenter<UploadPhotoContract.View> {
    public interface View {

        void showError(String message);

        void showComplete();

        void showProgress();

        void dismissProgress();

    }




    public abstract void upload( RequestBody description, MultipartBody.Part file);
}
