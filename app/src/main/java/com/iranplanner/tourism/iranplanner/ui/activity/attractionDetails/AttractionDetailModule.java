package com.iranplanner.tourism.iranplanner.ui.activity.attractionDetails;


import com.iranplanner.tourism.iranplanner.di.model.CustomScope;
import com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore.AttractionListMoreContract;
import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentComponent;
import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentContract;

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



    public AttractionDetailModule(AttractionDetailContract.View mView, AttractionListMoreContract.View  mViewAttractionListMoreContract, CommentContract.View commentView) {
        this.mView = mView;
        this.mViewAttractionListMoreContract=mViewAttractionListMoreContract;
        this.commentView=commentView;
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
}
