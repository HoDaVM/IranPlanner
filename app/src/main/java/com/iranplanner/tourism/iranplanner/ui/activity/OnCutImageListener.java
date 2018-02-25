package com.iranplanner.tourism.iranplanner.ui.activity;

import android.graphics.Bitmap;
import android.view.View;

import java.io.Serializable;
import java.util.List;

/**
 * Created by h.vahidimehr on 10/12/2017.
 */

public interface OnCutImageListener extends Serializable{
    void onCropImage(Bitmap bitmap);
}
