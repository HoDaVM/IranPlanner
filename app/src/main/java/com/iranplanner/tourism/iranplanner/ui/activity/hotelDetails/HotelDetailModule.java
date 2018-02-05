package com.iranplanner.tourism.iranplanner.ui.activity.hotelDetails;


import com.iranplanner.tourism.iranplanner.di.model.CustomScope;
import com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore.AttractionListMoreContract;
import com.iranplanner.tourism.iranplanner.ui.activity.attractionDetails.AttractionDetailContract;
import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hoda
 */
@Module
public class HotelDetailModule {
    private final  HotelDetailContract.View  hotelDetailContract;
    private final CommentContract.View commentView;

    public HotelDetailModule( HotelDetailContract.View  hotelDetailContract, CommentContract.View commentView) {
        this.hotelDetailContract=hotelDetailContract;
        this.commentView=commentView;
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
}
