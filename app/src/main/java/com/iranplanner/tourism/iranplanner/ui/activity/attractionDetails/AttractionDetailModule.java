package com.iranplanner.tourism.iranplanner.ui.activity.attractionDetails;


import com.iranplanner.tourism.iranplanner.di.model.CustomScope;
import com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore.AttractionListMoreContract;
import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentContract;
import com.iranplanner.tourism.iranplanner.ui.activity.dynamicItinerary.DynamicItineraryContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hoda
 */
@Module
public class AttractionDetailModule {
    private final AttractionDetailContract.View mView;
    private final AttractionListMoreContract.View mViewAttractionListMoreContract;
    private final CommentContract.View commentView;
    private final DynamicItineraryContract.View dynamicItineraryView;


    public AttractionDetailModule(AttractionDetailContract.View mView,
                                  AttractionListMoreContract.View mViewAttractionListMoreContract,
                                  CommentContract.View commentView,
                                  DynamicItineraryContract.View dynamicItineraryView) {
        this.mView = mView;
        this.mViewAttractionListMoreContract = mViewAttractionListMoreContract;
        this.commentView = commentView;
        this.dynamicItineraryView = dynamicItineraryView;
    }

    @CustomScope
    @Provides
    AttractionDetailContract.View a() {
        return mView;
    }
    @CustomScope
    @Provides
    AttractionListMoreContract.View attractionV() {
        return mViewAttractionListMoreContract;
    }
    @CustomScope
    @Provides
    CommentContract.View commentV() {
        return commentView;
    }

    @CustomScope
    @Provides
    DynamicItineraryContract.View dynamicItineraryView() {
        return dynamicItineraryView;
    }
}
