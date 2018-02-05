package com.iranplanner.tourism.iranplanner.ui.activity.comment;


import com.iranplanner.tourism.iranplanner.di.model.CustomScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hoda
 */
@Module
public class CommentModule {
    private final CommentContract.View mView;


    public CommentModule(CommentContract.View mView) {
        this.mView = mView;
    }

    @CustomScope
    @Provides
    CommentContract.View providesMainScreenContractView() {
        return mView;
    }
}
