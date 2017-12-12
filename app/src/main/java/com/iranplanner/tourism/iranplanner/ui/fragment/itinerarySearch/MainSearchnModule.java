package com.iranplanner.tourism.iranplanner.ui.fragment.itinerarySearch;


import com.iranplanner.tourism.iranplanner.di.model.CustomScope;
import com.iranplanner.tourism.iranplanner.ui.fragment.itinerarySearch.MainSearchContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hoda
 */
@Module
public class MainSearchnModule {
    private final MainSearchContract.View mView;


    public MainSearchnModule(MainSearchContract.View mView) {
        this.mView = mView;
    }
@CustomScope
    @Provides
    MainSearchContract.View providesMainScreenContractView() {
        return mView;
    }
}