package com.iranplanner.tourism.iranplanner.ui.fragment.home;


import com.iranplanner.tourism.iranplanner.di.model.CustomScope;
import com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore.AttractionListMoreContract;
import com.iranplanner.tourism.iranplanner.ui.activity.attractionDetails.AttractionDetailContract;
import com.iranplanner.tourism.iranplanner.ui.activity.hotelReservationListOfCity.ReservationContract;
import com.iranplanner.tourism.iranplanner.ui.activity.reservationHotelList.ReservationHotelListContract;
import com.iranplanner.tourism.iranplanner.ui.activity.reservationHotelList.ReservationHotelListModule;
import com.iranplanner.tourism.iranplanner.ui.fragment.blog.BlogContract;
import com.iranplanner.tourism.iranplanner.ui.fragment.itinerarySearch.MainSearchContract;
import com.iranplanner.tourism.iranplanner.ui.fragment.itinerarySearch.MainSearchPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hoda
 */
@Module
public class HomeModule {
    private final HomeContract.View mView;
    private final ReservationContract.View mViewReservation;
    private final AttractionListMoreContract.View mViewAttractionListMoreContract;
    private final ReservationHotelListContract.View mViewReservationHotelListContract;
    private final MainSearchContract.View mViewMainScreenContractView;


    public HomeModule(HomeContract.View mView,ReservationContract.View mViewReservation,AttractionListMoreContract.View mViewAttractionListMoreContract,ReservationHotelListContract.View mViewReservationHotelListContract,MainSearchContract.View mViewMainScreenContractView) {
        this.mView = mView;
        this.mViewReservation=mViewReservation;
        this.mViewAttractionListMoreContract=mViewAttractionListMoreContract;
        this.mViewReservationHotelListContract=mViewReservationHotelListContract;
        this.mViewMainScreenContractView=mViewMainScreenContractView;
    }

    @CustomScope
    @Provides
    HomeContract.View a() {
        return mView;
    }
    @CustomScope
    @Provides
    ReservationContract.View sa() {
        return mViewReservation;
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
    MainSearchContract.View providesMainScreenContractView() {
        return mViewMainScreenContractView;
    }
}
