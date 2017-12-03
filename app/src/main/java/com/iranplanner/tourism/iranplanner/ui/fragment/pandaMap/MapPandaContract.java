package com.iranplanner.tourism.iranplanner.ui.fragment.pandaMap;

import com.iranplanner.tourism.iranplanner.ui.presenter.Presenter;

import entity.PandaMapList;
import entity.ResultPandaMapSearch;
import entity.ResultPandaMaps;


/**
 * Created by Hoda
 */
public abstract class MapPandaContract extends Presenter<MapPandaContract.View> {
    public interface View {

        void showError(String message);

        void showComplete();

        void showProgress();

        void dismissProgress();

        void showPointOnMap(ResultPandaMaps resultPandaMaps);

        void showPandaSearch(ResultPandaMaps resultPandaMapSearch);
    }


//    public abstract void getDrawResult(PandaMapList pandaMapList, String token, String androidId);

    public abstract void getDrawResult(PandaMapList pandaMapList,
                                       String valueSearch,
                                       String attractionFilter,
                                       String lodgingFilter,
                                       String eventFilter,
                                       String position1,
                                       String position2,
                                       String token,
                                       String androidId);
    public abstract void getDrawResult(
                                       String valueSearch,
                                       String attractionFilter,
                                       String lodgingFilter,
                                       String eventFilter,
                                       String position1,
                                       String position2,
                                       String token,
                                       String androidId);

    public abstract void getPandaSearch(
            String action,
            String value);
}
