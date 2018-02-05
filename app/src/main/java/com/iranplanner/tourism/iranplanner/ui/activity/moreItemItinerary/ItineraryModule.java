package com.iranplanner.tourism.iranplanner.ui.activity.moreItemItinerary;


import dagger.Module;
import dagger.Provides;

import com.iranplanner.tourism.iranplanner.di.model.CustomScope;
import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentContract;

/**
 * Created by Hoda
 */
@Module
public class ItineraryModule {
    private final ItineraryContract.View mView;
    private final CommentContract.View commentView;


    public ItineraryModule(ItineraryContract.View mView,CommentContract.View commentView) {
        this.mView = mView;
        this.commentView=commentView;
    }
@CustomScope
    @Provides
    ItineraryContract.View a() {
        return mView;
    }
    @Provides
    CommentContract.View commentV() {
        return commentView;
    }
}
