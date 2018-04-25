package com.iranplanner.tourism.iranplanner.ui.fragment.myaccount;


import com.iranplanner.tourism.iranplanner.di.data.component.NetComponent;
import com.iranplanner.tourism.iranplanner.di.model.CustomScope;
import com.iranplanner.tourism.iranplanner.ui.navigationDrawer.ContactUsActivity;

import dagger.Component;

/**
 * Created by Hoda on 11-May-16.
 */
@CustomScope
@Component(dependencies = NetComponent.class, modules = {SettingModule.class})
public interface SettingComponent {
    void inject(SettingFragment settingFragment);
    void injectUser(ContactUsActivity contactUsActivity);
}



