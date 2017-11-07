package com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore;


import com.iranplanner.tourism.iranplanner.di.model.CustomScope;
import com.iranplanner.tourism.iranplanner.ui.activity.hotelReservationListOfCity.ReservationContract;
import com.iranplanner.tourism.iranplanner.ui.fragment.home.HomeContract;
import com.iranplanner.tourism.iranplanner.ui.fragment.home.HomeFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hoda
 */
@Module
public class AttractionListMoreModule {
    private final AttractionListMoreContract.View mView;


    public AttractionListMoreModule(AttractionListMoreContract.View mView) {
        this.mView = mView;
    }

    @CustomScope
    @Provides
    AttractionListMoreContract.View a() {
        return mView;
    }

}
