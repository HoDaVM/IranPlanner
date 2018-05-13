package com.iranplanner.tourism.iranplanner.ui.activity.showAttraction;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.iranplanner.tourism.iranplanner.ui.fragment.showAttractionList.ShowAttractionListFragment;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import entity.ResultItineraryAttractionDay;
import tools.Util;

/**
 * Created by Hoda on 20/01/2017.
 */
public class ShowDetailItineraryDayAdapter extends FragmentPagerAdapter {

    Map<String, List<ResultItineraryAttractionDay>> map;
    List<ResultItineraryAttractionDay> resultItineraryAttractionDays;

    public ShowDetailItineraryDayAdapter(FragmentManager fm, Map<String, List<ResultItineraryAttractionDay>> map) {
        super(fm);
        this.map = map;
    }

    @Override
    public Fragment getItem(int position) {

        resultItineraryAttractionDays = map.get(String.valueOf(position + 1));
        Bundle bundle = new Bundle();
        bundle.putSerializable("resultItineraryAttractionDays", (Serializable) resultItineraryAttractionDays);
        bundle.putInt("dayNumber", position + 1);
        bundle.putInt("allDays", map.size());
        ShowAttractionListFragment fragment = new ShowAttractionListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int getCount() {
        return map.size();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return "روز " + Util.persianNumbers(String.valueOf(position + 1));
    }

}
