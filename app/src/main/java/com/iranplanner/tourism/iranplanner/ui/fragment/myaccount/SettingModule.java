package com.iranplanner.tourism.iranplanner.ui.fragment.myaccount;


import com.iranplanner.tourism.iranplanner.di.model.CustomScope;
import com.iranplanner.tourism.iranplanner.ui.activity.dynamicItinerary.DynamicItineraryContract;
import com.iranplanner.tourism.iranplanner.ui.activity.reqestHotelStatus.HotelReservationStatusContract;
import com.iranplanner.tourism.iranplanner.ui.fragment.myaccount.SettingContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hoda
 */
@Module
public class SettingModule {
    private final SettingContract.View mView;
    private final HotelReservationStatusContract.View HotelReservationStatusView;
    private final DynamicItineraryContract.View dynamicView;


    public SettingModule(SettingContract.View mView, HotelReservationStatusContract.View HotelReservationStatusView,DynamicItineraryContract.View dynamicView) {
        this.mView = mView;
        this.HotelReservationStatusView = HotelReservationStatusView;
        this.dynamicView=dynamicView;
    }

    public SettingModule(SettingContract.View mView) {
        this.mView = mView;
        this.HotelReservationStatusView = null;
        this.dynamicView=null;
    }
    @CustomScope
    @Provides
    SettingContract.View a() {
        return mView;
    }

    @CustomScope
    @Provides
    HotelReservationStatusContract.View getHotelReservationStatusView() {
        return HotelReservationStatusView;
    }
    @CustomScope
    @Provides
    DynamicItineraryContract.View getDynamicView() {
        return dynamicView;
    }

}
