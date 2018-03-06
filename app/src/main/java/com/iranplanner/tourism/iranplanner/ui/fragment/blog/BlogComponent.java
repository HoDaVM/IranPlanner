package com.iranplanner.tourism.iranplanner.ui.fragment.blog;


import com.iranplanner.tourism.iranplanner.di.data.component.NetComponent;
import com.iranplanner.tourism.iranplanner.di.model.CustomScope;
import com.iranplanner.tourism.iranplanner.ui.activity.SplashActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.mainActivity.MainActivity;


import dagger.Component;

/**
 * Created by Hoda on 11-May-16.
 */
@CustomScope
@Component(dependencies = NetComponent.class, modules = {BlogModule.class})
public interface BlogComponent {
    void inject(BlogFragment  blogFragment);
    void inject(MainActivity mainActivity);
    void inject(BlogDetailActivity blogDetailActivity);

}



