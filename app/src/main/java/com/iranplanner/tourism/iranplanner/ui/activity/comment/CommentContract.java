package com.iranplanner.tourism.iranplanner.ui.activity.comment;


import android.net.Uri;

import com.iranplanner.tourism.iranplanner.ui.presenter.Presenter;

import entity.CommentSend;
import entity.InterestResult;
import entity.ResultCommentList;
import entity.ResultImageList;
import entity.ResultParamUser;
import entity.ResultWidgetFull;
import entity.SendParamUser;

/**
 * Created by Hoda
 */
public abstract class CommentContract extends Presenter<CommentContract.View> {
    public interface View {
        void showComments(ResultCommentList resultCommentList);

        void sendCommentMessage(ResultCommentList resultCommentList);

        void showError(String message);

        void commentResult(String message);

        void showComplete();

        void setIntrestedWidget(InterestResult interestResult);

        void setLoadWidget(ResultWidgetFull resultWidgetFull);

        void setRate(ResultParamUser resultParamUser);
        void setRateUser(ResultParamUser resultParamUser);

        void showProgress();

        void dismissProgress();

        void showMoreImages(ResultImageList resultImageList);
        void setImageUri(Uri uri);
    }

    public abstract void getCommentList(
            String action,
            String nid,
            String ntype,
            String offset);

    public abstract void callInsertComment(CommentSend commentSend, String cid, String andId);

    public abstract void getWidgetResult(String action, String id, String uid, String ntype, String cid, String andId);

    public abstract void getInterest(String action, String uid, String cid, String ntype, String nid, String gtype, String gvalue, String andId);

    public abstract void rateSend(String action, SendParamUser request, String cid, String andId);

    public abstract void getRate(String action, SendParamUser request, String cid, String andId);

    public abstract void getImages(String action, String nid, String ntype);
}
