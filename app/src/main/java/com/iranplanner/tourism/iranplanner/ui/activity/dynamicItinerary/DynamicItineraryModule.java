package com.iranplanner.tourism.iranplanner.ui.activity.dynamicItinerary;


import com.iranplanner.tourism.iranplanner.di.model.CustomScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hoda
 */
@Module
public class DynamicItineraryModule {
    private final DynamicItineraryContract.View mView;


    public DynamicItineraryModule(DynamicItineraryContract.View mView ) {
        this.mView = mView;
    }

    @CustomScope
    @Provides
    DynamicItineraryContract.View provideView() {
        return mView;
    }
}
