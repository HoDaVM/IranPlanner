package com.iranplanner.tourism.iranplanner.ui.fragment.myaccount;


import com.iranplanner.tourism.iranplanner.di.model.CustomScope;
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
    ;


    public SettingModule(SettingContract.View mView, HotelReservationStatusContract.View HotelReservationStatusView) {
        this.mView = mView;
        this.HotelReservationStatusView = HotelReservationStatusView;
    }

    public SettingModule(SettingContract.View mView) {
        this.mView = mView;
        this.HotelReservationStatusView = null;
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

}
