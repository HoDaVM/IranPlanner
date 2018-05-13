package com.iranplanner.tourism.iranplanner.ui.activity.dynamicItinerary;


import com.iranplanner.tourism.iranplanner.di.data.component.NetComponent;
import com.iranplanner.tourism.iranplanner.di.model.CustomScope;


import dagger.Component;

/**
 * Created by Hoda on 11-May-16.
 */
@CustomScope
@Component(dependencies = NetComponent.class, modules = {DynamicItineraryModule.class})
public interface DynamicItineraryComponent {
    void inject(AddNewDynamicItinerary addNewDynamicItinerary);
    void inject(DynamicItineraryUpdateDayActivity dynamicItineraryUpdateDayActivity);
}



