package com.iranplanner.tourism.iranplanner.ui.activity.globalSearch;


import com.iranplanner.tourism.iranplanner.di.model.CustomScope;
import com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore.AttractionListMoreContract;
import com.iranplanner.tourism.iranplanner.ui.activity.hotelReservationListOfCity.ReservationContract;
import com.iranplanner.tourism.iranplanner.ui.activity.reservationHotelList.ReservationHotelListContract;
import com.iranplanner.tourism.iranplanner.ui.activity.restaurant.RestaurantContract;
import com.iranplanner.tourism.iranplanner.ui.activity.souvenirFood.SouvenirFoodContract;
import com.iranplanner.tourism.iranplanner.ui.fragment.blog.BlogContract;
import com.iranplanner.tourism.iranplanner.ui.fragment.home.HomeContract;
import com.iranplanner.tourism.iranplanner.ui.fragment.itinerarySearch.MainSearchContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hoda
 */
@Module
public class GlobalSearchModule {
    private final GlobalSearchContract.View mView;
    private final ReservationContract.View mViewReservation;
    private final AttractionListMoreContract.View mViewAttractionListMoreContract;
    private final ReservationHotelListContract.View mViewReservationHotelListContract;
    private final MainSearchContract.View mViewMainScreenContractView;
    private final RestaurantContract.View mViewRestaurantContractView;
    private final BlogContract.View blogvPresenter;
    private final HomeContract.View mContractView;
    private final SouvenirFoodContract.View mSouvenirFoodContractContractView;


    public GlobalSearchModule(GlobalSearchContract.View mView, ReservationContract.View mViewReservation, AttractionListMoreContract.View mViewAttractionListMoreContract,
                              ReservationHotelListContract.View mViewReservationHotelListContract, MainSearchContract.View mViewMainScreenContractView
            , RestaurantContract.View mViewRestaurantContractView, BlogContract.View blogvPresenter, HomeContract.View mContractView,SouvenirFoodContract.View mSouvenirFoodContractContractView) {
        this.mView = mView;
        this.mViewReservation = mViewReservation;
        this.mViewAttractionListMoreContract = mViewAttractionListMoreContract;
        this.mViewReservationHotelListContract = mViewReservationHotelListContract;
        this.mViewMainScreenContractView = mViewMainScreenContractView;
        this.mViewRestaurantContractView = mViewRestaurantContractView;
        this.blogvPresenter = blogvPresenter;
        this.mContractView = mContractView;
        this.mSouvenirFoodContractContractView=mSouvenirFoodContractContractView;

    }

    @CustomScope
    @Provides
    GlobalSearchContract.View providesSearchContractView() {
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
    @CustomScope
    @Provides
    RestaurantContract.View restaurantContractView() {
        return mViewRestaurantContractView;
    }
    @CustomScope
    @Provides
    BlogContract.View blogContractView() {
        return blogvPresenter;
    }

    @CustomScope
    @Provides
    HomeContract.View mHomeContractView() {
        return mContractView;
    }
    @CustomScope
    @Provides
    SouvenirFoodContract.View SouvenirFoodContractContractView (){
        return mSouvenirFoodContractContractView;
    }
}
