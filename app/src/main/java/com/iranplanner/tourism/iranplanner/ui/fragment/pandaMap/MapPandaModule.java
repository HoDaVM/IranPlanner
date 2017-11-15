package com.iranplanner.tourism.iranplanner.ui.fragment.pandaMap;


import com.iranplanner.tourism.iranplanner.di.model.CustomScope;
import com.iranplanner.tourism.iranplanner.ui.activity.login.LoginContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hoda
 */
@Module
public class MapPandaModule {
    private final MapPandaContract.View mView;


    public MapPandaModule(MapPandaContract.View mView) {
        this.mView = mView;
    }

    @CustomScope
    @Provides
    MapPandaContract.View a() {
        return mView;
    }
}
