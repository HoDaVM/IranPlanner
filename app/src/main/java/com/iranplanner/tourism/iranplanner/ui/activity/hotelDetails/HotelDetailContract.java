package com.iranplanner.tourism.iranplanner.ui.activity.hotelDetails;


import com.iranplanner.tourism.iranplanner.ui.presenter.Presenter;

import entity.ResultCommentList;
import entity.ResultLodgingRoomList;
import entity.ResultSendPhone;
import entity.ShowAtractionDetailMore;
import entity.ShowAttractionListMore;

/**
 * Created by Hoda
 */
public abstract class HotelDetailContract extends Presenter<HotelDetailContract.View> {
    public interface View {
        void showError(String message);

        void showComplete();

        void showProgress();

        void dismissProgress();
        void setLodgingRoomList(ResultLodgingRoomList resultLodgingRoomList);

    }

    public abstract void   getResultLodgingRoomList(
            String action,
            String id,
            String fromdate,
            String todate);

}
