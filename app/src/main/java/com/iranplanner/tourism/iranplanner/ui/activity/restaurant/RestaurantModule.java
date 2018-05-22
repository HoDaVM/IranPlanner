package com.iranplanner.tourism.iranplanner.ui.activity.restaurant;


import com.iranplanner.tourism.iranplanner.di.model.CustomScope;
import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentContract;
import com.iranplanner.tourism.iranplanner.ui.activity.dynamicItinerary.DynamicItineraryContract;
import com.iranplanner.tourism.iranplanner.ui.activity.hotelDetails.HotelDetailContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hoda
 */
@Module
public class RestaurantModule {
    private final  RestaurantContract.View  restaurantContract;
    private final CommentContract.View commentView;
    private final DynamicItineraryContract.View dynamicView;

    public RestaurantModule(RestaurantContract.View  restaurantContract, CommentContract.View commentView,DynamicItineraryContract.View dynamicView) {
        this.restaurantContract=restaurantContract;
        this.commentView=commentView;
        this.dynamicView=dynamicView;
    }

    @CustomScope
    @Provides
    RestaurantContract.View restView() {
        return restaurantContract;
    }
    @CustomScope
    @Provides
    CommentContract.View commentV() {
        return commentView;
    }
    @CustomScope
    @Provides
    DynamicItineraryContract.View getDynamicView() {
        return dynamicView;
    }
}
