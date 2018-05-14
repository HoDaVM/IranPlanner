package com.iranplanner.tourism.iranplanner.ui.activity.dynamicItinerary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.iranplanner.tourism.iranplanner.ui.activity.OnDynamicListListener;
import java.io.Serializable;
import java.util.List;
import entity.ItnDaily;
import tools.Util;

/**
 * Created by Hoda on 20/01/2017.
 */
public class ShowDynamicItineraryDayFragmentAdapter extends FragmentStatePagerAdapter implements Serializable  {
    List<ItnDaily> intdaily;
    OnDynamicListListener onDynamicListListener;

    public ShowDynamicItineraryDayFragmentAdapter(FragmentManager fm, List<ItnDaily> intdaily , OnDynamicListListener onDynamicListListener) {
        super(fm);
        this.intdaily = intdaily;
        this.onDynamicListListener =onDynamicListListener;
    }

    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("dayPosition", (Serializable) intdaily.get(position).getDayPosition());
        bundle.putSerializable("OnDynamicListListener", onDynamicListListener);
        ItemDragFragment fragment = new ItemDragFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int getCount() {
        if(intdaily.size()>0) {
            return intdaily.size();
        }else {
            return 0;
        }
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

    @Override
    public int getItemPosition(Object object) {
        int position = intdaily.indexOf(object);
        return position == -1 ? POSITION_NONE : position;
    }

}
