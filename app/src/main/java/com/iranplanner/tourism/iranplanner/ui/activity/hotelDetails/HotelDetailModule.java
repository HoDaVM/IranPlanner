package com.iranplanner.tourism.iranplanner.ui.activity.hotelDetails;


import com.iranplanner.tourism.iranplanner.di.model.CustomScope;
import com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore.AttractionListMoreContract;
import com.iranplanner.tourism.iranplanner.ui.activity.attractionDetails.AttractionDetailContract;
import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentContract;
import com.iranplanner.tourism.iranplanner.ui.activity.dynamicItinerary.DynamicItineraryContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hoda
 */
@Module
public class HotelDetailModule {
    private final  HotelDetailContract.View  hotelDetailContract;
    private final CommentContract.View commentView;
    private final DynamicItineraryContract.View dynamicItineraryView;


    public HotelDetailModule( HotelDetailContract.View  hotelDetailContract, CommentContract.View commentView,DynamicItineraryContract.View dynamicItineraryView) {
        this.hotelDetailContract=hotelDetailContract;
        this.commentView=commentView;
        this.dynamicItineraryView =dynamicItineraryView;
    }

    @CustomScope
    @Provides
    HotelDetailContract.View attractionV() {
        return hotelDetailContract;
    }
    @CustomScope
    @Provides
    CommentContract.View commentV() {
        return commentView;
    }
    @CustomScope
    @Provides
    DynamicItineraryContract.View getDynamicItineraryView() {
        return dynamicItineraryView;
    }
}
