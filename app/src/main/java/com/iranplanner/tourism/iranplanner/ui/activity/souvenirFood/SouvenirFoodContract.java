package com.iranplanner.tourism.iranplanner.ui.activity.souvenirFood;


import com.iranplanner.tourism.iranplanner.ui.presenter.Presenter;

import java.util.List;

import entity.GetResultSouvenir;
import entity.ResultGlobalSearch;

/**
 * Created by Hoda
 */
public abstract class SouvenirFoodContract extends Presenter<SouvenirFoodContract.View> {
    public interface View {


        void showError(String message);

        void showProgress();

        void dismissProgress();

        void showFullSouvenir(GetResultSouvenir getResultSouvenir);

    }


    public abstract void getSouvenirFull(
            String action,
            String value);
}
