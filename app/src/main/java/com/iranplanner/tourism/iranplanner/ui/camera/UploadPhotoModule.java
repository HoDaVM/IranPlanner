package com.iranplanner.tourism.iranplanner.ui.camera;


import com.iranplanner.tourism.iranplanner.di.model.CustomScope;


import dagger.Module;
import dagger.Provides;

/**
 * Created by Hoda
 */
@Module
public class UploadPhotoModule {
    private final UploadPhotoContract.View mView;

    public UploadPhotoModule(UploadPhotoContract.View mView) {
        this.mView = mView;
    }

    @CustomScope
    @Provides
    UploadPhotoContract.View a() {
        return mView;
    }

}
