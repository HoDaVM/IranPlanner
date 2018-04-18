package com.iranplanner.tourism.iranplanner.ui.activity.souvenirFood;


import com.iranplanner.tourism.iranplanner.di.model.CustomScope;
import com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore.AttractionListMoreContract;
import com.iranplanner.tourism.iranplanner.ui.activity.globalSearch.GlobalSearchContract;
import com.iranplanner.tourism.iranplanner.ui.activity.hotelReservationListOfCity.ReservationContract;
import com.iranplanner.tourism.iranplanner.ui.activity.reservationHotelList.ReservationHotelListContract;
import com.iranplanner.tourism.iranplanner.ui.activity.restaurant.RestaurantContract;
import com.iranplanner.tourism.iranplanner.ui.fragment.blog.BlogContract;
import com.iranplanner.tourism.iranplanner.ui.fragment.itinerarySearch.MainSearchContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hoda
 */
@Module
public class SouvenirFoodModule {
    private final SouvenirFoodContract.View mView;


    public SouvenirFoodModule(SouvenirFoodContract.View mView) {
        this.mView = mView;

    }

    @CustomScope
    @Provides
    SouvenirFoodContract.View providesView() {
        return mView;
    }

}
