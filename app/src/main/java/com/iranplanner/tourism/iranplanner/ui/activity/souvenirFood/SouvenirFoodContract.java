package com.iranplanner.tourism.iranplanner.ui.activity.souvenirFood;


import com.iranplanner.tourism.iranplanner.ui.presenter.Presenter;
import entity.GetResultLocalFood;
import entity.GetResultSouvenir;

/**
 * Created by Hoda
 */
public abstract class SouvenirFoodContract extends Presenter<SouvenirFoodContract.View> {
    public interface View {


        void showError(String message);

        void showProgress();

        void dismissProgress();

        void showFullSouvenir(GetResultSouvenir getResultSouvenir);
        void showFullLocalFood(GetResultLocalFood getResultLocalFood);

    }


    public abstract void getSouvenirFull(
            String action,
            String value);

    public abstract void getLocalFoodFull(
            String action,
            String value);
}
