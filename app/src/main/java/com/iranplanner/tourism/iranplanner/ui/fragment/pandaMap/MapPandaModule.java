package com.iranplanner.tourism.iranplanner.ui.fragment.pandaMap;


import com.iranplanner.tourism.iranplanner.di.model.CustomScope;
import com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore.AttractionListMoreContract;
import com.iranplanner.tourism.iranplanner.ui.activity.login.LoginContract;
import com.iranplanner.tourism.iranplanner.ui.activity.reservationHotelList.ReservationHotelListContract;
import com.iranplanner.tourism.iranplanner.ui.fragment.home.HomeContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hoda
 */
@Module
public class MapPandaModule {
    private final HomeContract.View homeViewContract;
    private final MapPandaContract.View mView;
    private final AttractionListMoreContract.View mViewAttractionListMoreContract;
    private final ReservationHotelListContract.View mViewReservationHotelListContract;


    public MapPandaModule(MapPandaContract.View mView, AttractionListMoreContract.View mViewAttractionListMoreContract,ReservationHotelListContract.View mViewReservationHotelListContract,HomeContract.View homeViewContract) {
        this.mView = mView;
        this.mViewAttractionListMoreContract = mViewAttractionListMoreContract;
        this.mViewReservationHotelListContract=mViewReservationHotelListContract;
        this.homeViewContract= homeViewContract;
    }

    @CustomScope
    @Provides
    MapPandaContract.View a() {
        return mView;
    }

    @CustomScope
    @Provides
    AttractionListMoreContract.View attractionV() {
        return mViewAttractionListMoreContract;
    }
    @CustomScope
    @Provides
    ReservationHotelListContract.View reservationV() {
        return mViewReservationHotelListContract;
    }
    @CustomScope
    @Provides
    HomeContract.View homeV() {
        return homeViewContract;
    }
}
