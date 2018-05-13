package com.iranplanner.tourism.iranplanner.ui.activity;

import android.graphics.Bitmap;

import java.io.Serializable;

import entity.DayPosition;

/**
 * Created by h.vahidimehr on 10/12/2017.
 */

public interface OnDynamicListListener extends Serializable{
    void onDelete(DayPosition dayPosition);
}
