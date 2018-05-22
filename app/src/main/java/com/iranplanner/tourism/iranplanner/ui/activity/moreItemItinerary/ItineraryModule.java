package com.iranplanner.tourism.iranplanner.ui.activity.moreItemItinerary;


import dagger.Module;
import dagger.Provides;

import com.iranplanner.tourism.iranplanner.di.model.CustomScope;
import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentContract;
import com.iranplanner.tourism.iranplanner.ui.activity.dynamicItinerary.DynamicItineraryContract;

/**
 * Created by Hoda
 */
@Module
public class ItineraryModule {
    private final ItineraryContract.View mView;
    private final CommentContract.View commentView;
    private final DynamicItineraryContract.View dynamicView;


    public ItineraryModule(ItineraryContract.View mView, CommentContract.View commentView, DynamicItineraryContract.View dynamicView) {
        this.mView = mView;
        this.commentView = commentView;
        this.dynamicView=dynamicView;
    }

    @CustomScope
    @Provides
    ItineraryContract.View a() {
        return mView;
    }
    @CustomScope
    @Provides
    DynamicItineraryContract.View getDynamicView() {
        return dynamicView;
    }

    @Provides
    CommentContract.View commentV() {
        return commentView;
    }
}
