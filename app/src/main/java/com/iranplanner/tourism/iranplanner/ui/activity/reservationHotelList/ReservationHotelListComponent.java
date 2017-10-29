package com.iranplanner.tourism.iranplanner.ui.activity.reservationHotelList;


import com.iranplanner.tourism.iranplanner.di.data.component.NetComponent;
import com.iranplanner.tourism.iranplanner.di.model.CustomScope;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.filterMap.FilterMap;
import com.iranplanner.tourism.iranplanner.ui.fragment.itineraryList.ItineraryListFragment;
import com.iranplanner.tourism.iranplanner.ui.fragment.itinerarySearch.MainSearchFragment;
import com.iranplanner.tourism.iranplanner.ui.fragment.itinerarySearch.MainSearchnModule;

import dagger.Component;

/**
 * Created by Hoda on 11-May-16.
 */
@CustomScope
@Component(dependencies = NetComponent.class, modules = {ReservationHotelListModule.class})
public interface ReservationHotelListComponent {
    void injectReservationHotelList(ReservationHotelListActivity reservationHotelListActivity);
    void inject(FilterMap filterMap);
}



