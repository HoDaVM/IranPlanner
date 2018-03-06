package com.iranplanner.tourism.iranplanner.ui.fragment.blog;


import com.iranplanner.tourism.iranplanner.di.model.CustomScope;
import com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore.AttractionListMoreContract;
import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentContract;
import com.iranplanner.tourism.iranplanner.ui.activity.reservationHotelList.ReservationHotelListContract;
import com.iranplanner.tourism.iranplanner.ui.fragment.home.HomeContract;
import com.iranplanner.tourism.iranplanner.ui.fragment.pandaMap.MapPandaContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hoda
 */
@Module
public class BlogModule {
    private final BlogContract.View blogViewContract;
    private final AttractionListMoreContract.View mViewAttractionListMoreContract;
    private final CommentContract.View commentView;
    public BlogModule(BlogContract.View blogViewContract, AttractionListMoreContract.View  mViewAttractionListMoreContract, CommentContract.View commentView) {
        this.blogViewContract = blogViewContract;
        this.mViewAttractionListMoreContract=mViewAttractionListMoreContract;
        this.commentView=commentView;
    }

    @CustomScope
    @Provides
    BlogContract.View show() {
        return blogViewContract;
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
