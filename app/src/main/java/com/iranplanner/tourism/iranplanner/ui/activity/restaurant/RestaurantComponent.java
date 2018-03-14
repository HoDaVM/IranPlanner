package com.iranplanner.tourism.iranplanner.ui.activity.restaurant;


import com.iranplanner.tourism.iranplanner.di.data.component.NetComponent;
import com.iranplanner.tourism.iranplanner.di.model.CustomScope;
import com.iranplanner.tourism.iranplanner.ui.activity.hotelDetails.HotelDetailModule;
import com.iranplanner.tourism.iranplanner.ui.activity.hotelDetails.ReservationHotelDetailActivity;

import dagger.Component;

/**
 * Created by Hoda on 11-May-16.
 */
@CustomScope
@Component(dependencies = NetComponent.class, modules = {RestaurantModule.class})
public interface RestaurantComponent {
    void inject(RestaurantDetailActivity activity);
    void inject(RestaurantListActivity activity);

}



