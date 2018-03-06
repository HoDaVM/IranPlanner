package com.iranplanner.tourism.iranplanner.ui.fragment.home;

import com.iranplanner.tourism.iranplanner.ui.presenter.Presenter;

import entity.GetHomeResult;
import entity.HomeAndBlog;
import entity.ResultEvents;
import entity.ResultItineraryList;


/**
 * Created by Hoda
 */
public abstract class HomeContract extends Presenter<HomeContract.View> {
    public interface View {

        void showError(String message);

        void showComplete();

        void ShowHomeResult(GetHomeResult GetHomeResult);

//        void ShowAttractionLists(ShowAttractionListMore getAttractionList);

        void ShowEventLists(ResultEvents resultEvents);

        void ShowEventDetail(ResultEvents resultEvent);

        void showProgress();

        void dismissProgress();

        void ShowItineryDetail(ResultItineraryList resultItineraryList);

        void showHomeAndBlog(HomeAndBlog homeAndBlog);
    }


    public abstract void getHome(String action,
                                 String type,
                                 String value,
                                 String token,
                                 String androidId);

//    public abstract void getAttractionMore(String action,
//                                           String lang,
//                                           String value,
//                                           String placetype,
//                                           String offset,
//                                           String cid,
//                                           String androidId,
//                                           String attractionType);


    public abstract void getEventMore(
            String action,
            String lang,
            String id,
            String type,
            String cid,
            String androidId);

    public abstract void getEventDetail(
            String action,
            String lang,
            String id,
            String cid,
            String androidId);

    public abstract void getItineraryDetail(String action,
                                            String id,
                                            String lang,
                                            String cid,
                                            String androidId);
}
