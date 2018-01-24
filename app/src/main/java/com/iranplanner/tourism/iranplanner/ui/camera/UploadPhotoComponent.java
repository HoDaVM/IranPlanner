package com.iranplanner.tourism.iranplanner.ui.camera;


import com.iranplanner.tourism.iranplanner.di.data.component.NetComponent;
import com.iranplanner.tourism.iranplanner.di.model.CustomScope;
import com.iranplanner.tourism.iranplanner.ui.activity.attractionDetails.AttractionDetailModule;
import com.iranplanner.tourism.iranplanner.ui.activity.attractionDetails.attractionDetailActivity;

import dagger.Component;

/**
 * Created by Hoda on 11-May-16.
 */
@CustomScope
@Component(dependencies = NetComponent.class, modules = {UploadPhotoModule.class})
public interface UploadPhotoComponent {
    void inject(PhotoCropFragment fragment);
}



