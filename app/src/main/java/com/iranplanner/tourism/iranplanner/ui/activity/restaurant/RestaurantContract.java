package com.iranplanner.tourism.iranplanner.ui.activity.restaurant;


import com.iranplanner.tourism.iranplanner.ui.presenter.Presenter;

import entity.RestaurantList;
import entity.ResultLodgingRoomList;
import entity.ResultRestaurantFull;

/**
 * Created by Hoda
 */
public abstract class RestaurantContract extends Presenter<RestaurantContract.View> {
    public interface View {
        void showError(String message);

        void showComplete();

        void showProgress();

        void dismissProgress();

        void setRestaurantFull(ResultRestaurantFull resultRestaurantFull);
        void setRestaurantList(RestaurantList restaurantList);
    }


    public abstract void getRestaurantFull(String action,
                                           String restaurantID,
                                           String token,
                                           String androidId);

    public abstract void getRestaurantList(String action,
                                           String id,
                                            String placetype,
                                            String limit,
                                           String offset,
                                           String token,
                                           String androidId);
}
