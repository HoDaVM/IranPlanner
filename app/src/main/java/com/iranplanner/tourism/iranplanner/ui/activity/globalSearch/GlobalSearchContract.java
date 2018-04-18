package com.iranplanner.tourism.iranplanner.ui.activity.globalSearch;


import android.net.Uri;

import com.iranplanner.tourism.iranplanner.ui.presenter.Presenter;

import java.util.List;

import entity.CommentSend;
import entity.InterestResult;
import entity.ResultCommentList;
import entity.ResultGlobalSearch;
import entity.ResultImageList;
import entity.ResultParamUser;
import entity.ResultWidgetFull;
import entity.SendParamUser;

/**
 * Created by Hoda
 */
public abstract class GlobalSearchContract extends Presenter<GlobalSearchContract.View> {
    public interface View {


        void showError(String message);

        void showProgress();

        void dismissProgress();
        void showSearchResult(List<ResultGlobalSearch> resultGlobalSearch);

    }



    public abstract void getGlobalSearch(String action,
                                         String value,
                                         String offset);
}
