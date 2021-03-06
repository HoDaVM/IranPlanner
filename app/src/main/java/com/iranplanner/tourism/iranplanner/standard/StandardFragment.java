package com.iranplanner.tourism.iranplanner.standard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.google.firebase.crash.FirebaseCrash;
import com.iranplanner.tourism.iranplanner.ui.fragment.home.HomeFragment;

import java.io.Serializable;

public abstract class StandardFragment extends Fragment implements Serializable {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseCrash.logcat(Log.ERROR, "firebaseCrash", "NPE caught");
        FirebaseCrash.report(new Exception("opss"));
    }

    public void fragmentBecameVisible() {

    }

    public void fragmentBecameInvisible() {

    }

    public boolean onBackPressed() {
        try {
            ((HomeFragment)this).onBackPressed();
        } catch (Exception e ){
            e.printStackTrace();
        }
        return false;
    }
}
