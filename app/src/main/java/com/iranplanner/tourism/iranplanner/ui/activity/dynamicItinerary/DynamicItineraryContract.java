package com.iranplanner.tourism.iranplanner.ui.activity.dynamicItinerary;


import com.iranplanner.tourism.iranplanner.ui.presenter.Presenter;

import entity.MyItineraryAdd;
import entity.MyItineraryList;
import entity.ResultEditDynamicItinerary;
import entity.ResultPositionAddItinerary;
import entity.SendParamSaveDynamicItinerary;
import entity.SendParamToAddItinerary;
import entity.SendParamUsetToGetItinerary;


/**
 * Created by Hoda
 */
public abstract class DynamicItineraryContract extends Presenter<DynamicItineraryContract.View> {
    public interface View {

        void showError(String message);

        void showComplete();

        void showProgress();

        void dismissProgress();

        void showDynamicItineraryList(MyItineraryList myItineraryList);

        void confirmationAddDynamicItinerary(MyItineraryAdd myItineraryAdd);

        void confirmationAddDynamicPosition(ResultPositionAddItinerary resultPositionAddItinerary);

        void showResultEditDynamicItinerary(ResultEditDynamicItinerary resultEditDynamicItinerary);
    }


    public abstract void getDynamicItineraryList(SendParamUsetToGetItinerary sendParamUserToGetItinerary, String token,
                                                 String androidId);

    public abstract void addNewDynamicItinerary(String action,SendParamToAddItinerary sendParamToAddItinerary,
                                                String token,
                                                String androidId);

    public abstract void addPosition(SendParamUsetToGetItinerary sendParamUsetToGetItinerary,
                                     String token,
                                     String androidId);

    public abstract void getResultEditDynamicItinerary(SendParamUsetToGetItinerary sendParamUsetToGetItinerary,
                                                       String token,
                                                       String androidId);

    public abstract void sendParamForSaveDynamicItinerary(SendParamSaveDynamicItinerary SendParamSaveDynamicItinerary,
                                                          String token,
                                                          String androidId);
}
