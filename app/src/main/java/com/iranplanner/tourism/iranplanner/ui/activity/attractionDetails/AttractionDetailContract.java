package com.iranplanner.tourism.iranplanner.ui.activity.attractionDetails;

import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.model.PolylineOptions;
import com.iranplanner.tourism.iranplanner.ui.presenter.Presenter;

import entity.InterestResult;
import entity.ResultCommentList;
import entity.ResultParamUser;
import entity.ResultRatePost;
import entity.ResultWidgetFull;
import entity.SendParamUser;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;


/**
 * Created by Hoda
 */
public abstract class AttractionDetailContract extends Presenter<AttractionDetailContract.View> {
    public interface View {

        void showError(String message);

        void showComplete();

        void showComment(ResultCommentList resultCommentList, String commentType);

        void setLoadWidget(ResultWidgetFull resultWidgetFull);

        void setIntrestedWidget(InterestResult InterestResult);

        void showAnimationWhenWaiting();

        void setIntrestValue(InterestResult InterestResult);

        ///--------------map
        void showProgress();

        public void showDirectionOnMap(PolylineOptions rectLine);

        void dismissProgress();

        void setRate(ResultParamUser resultParamUser);
        void setRateUser(ResultParamUser resultParamUser);
    }


    public abstract void getAttractionCommentList(String action, String nId, String nType, String offset, String cid, String andId);

    public abstract void getWidgetResult(String action, String id, String uid, String ntype, String cid, String andId);

    public abstract void getInterest(String action, String uid, String cid, String ntype, String nid, String gtype, String gvalue, String andId);

    public abstract void doWaitingAnimation(ImageView image);

    public abstract boolean doTranslateAnimationUp(RelativeLayout relativeLayout, RelativeLayout relativeLayout2, ImageView imageView);

    public abstract boolean doTranslateAnimationDown(RelativeLayout relativeLayout, RelativeLayout relativeLayout2, ImageView imageView, int height);

    public abstract void rateSend(SendParamUser request, String cid, String andId);

    public abstract void getRate(SendParamUser request, String cid, String andId);
    //------------------------map


    //    public abstract void getDirection(String origin, String destination);
    public abstract void getDirection(String origin, String destination);


    public abstract void upload( RequestBody description, MultipartBody.Part file);
}
