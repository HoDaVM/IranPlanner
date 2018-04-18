package com.iranplanner.tourism.iranplanner.ui.activity.souvenirFood;


import com.iranplanner.tourism.iranplanner.di.data.component.NetComponent;
import com.iranplanner.tourism.iranplanner.di.model.CustomScope;
import com.iranplanner.tourism.iranplanner.ui.activity.globalSearch.GlobalSearchActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.globalSearch.GlobalSearchModule;

import dagger.Component;

/**
 * Created by Hoda on 11-May-16.
 */
@CustomScope
@Component(dependencies = NetComponent.class, modules = {SouvenirFoodModule.class})
public interface SouvenirFoodComponent {
    void inject(SouvenirFoodActivity souvenirFoodActivity);
}



