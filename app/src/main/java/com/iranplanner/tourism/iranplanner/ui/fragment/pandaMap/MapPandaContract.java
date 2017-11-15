package com.iranplanner.tourism.iranplanner.ui.fragment.pandaMap;

import com.iranplanner.tourism.iranplanner.ui.presenter.Presenter;

import entity.GoogleLoginReqSend;
import entity.LoginReqSend;
import entity.LoginResult;
import entity.PandaMapList;
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
    }


    public abstract void getDrawResult(PandaMapList pandaMapList, String token, String androidId);

    public abstract void getPandaSearch(
            String action,
            String value);
}
