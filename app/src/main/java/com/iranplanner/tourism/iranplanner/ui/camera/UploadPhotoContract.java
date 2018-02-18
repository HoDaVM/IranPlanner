package com.iranplanner.tourism.iranplanner.ui.camera;

import com.iranplanner.tourism.iranplanner.ui.presenter.Presenter;

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




    public abstract void upload( RequestBody description,RequestBody nid, RequestBody ntype, RequestBody uid, MultipartBody.Part file);
}
