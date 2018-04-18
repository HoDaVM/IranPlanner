package com.iranplanner.tourism.iranplanner.ui.activity.globalSearch;


import com.iranplanner.tourism.iranplanner.di.data.component.NetComponent;
import com.iranplanner.tourism.iranplanner.di.model.CustomScope;
import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentListActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentModule;

import dagger.Component;

/**
 * Created by Hoda on 11-May-16.
 */
@CustomScope
@Component(dependencies = NetComponent.class, modules = {GlobalSearchModule.class})
public interface GlobalSearchComponent {
    void injectGlobalSearch(GlobalSearchActivity globalSearchActivity);
}



