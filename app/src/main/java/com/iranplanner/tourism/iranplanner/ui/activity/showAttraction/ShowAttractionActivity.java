package com.iranplanner.tourism.iranplanner.ui.activity.showAttraction;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entity.ResulAttraction;
import entity.ResultAttractionList;
import entity.ResultItineraryAttraction;
import entity.ResultItineraryAttractionDay;

/**
 * Created by Hoda on 20/01/2017.
 */
public class ShowAttractionActivity extends StandardActivity {

    FragmentPagerAdapter adapterViewPager;
    private List<ResultItineraryAttractionDay> resultItineraryAttractionDays;
    ResulAttraction resulAttraction;
    List<ResultAttractionList> resultAttractionList;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itinerary_day_viewpager);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        Bundle extras = getIntent().getExtras();
        List<ResultItineraryAttraction> itineraryActionList = null;

        if (extras != null) {
            itineraryActionList = (List<ResultItineraryAttraction>) extras.getSerializable("ResultItineraryAttraction");
            resultItineraryAttractionDays = (List<ResultItineraryAttractionDay>) extras.getSerializable("resultItineraryAttractionDays");
        }
        getDayGroups(resultItineraryAttractionDays);
        adapterViewPager = new ShowDetailItineraryDayAdapter(getSupportFragmentManager(), getDayGroups(resultItineraryAttractionDays));
        vpPager.setAdapter(adapterViewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(vpPager);
        vpPager.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        setCustomFont();
    }


    public void setCustomFont() {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();

        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);

            int tabChildsCount = vgTab.getChildCount();

            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/IRANSansMobile.ttf"));
                }
            }
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.itinerary_day_viewpager;
    }

    // TODO: 8/16/2017 avaz kardan webservice vase itinerary
    public HashMap<String, List<ResultItineraryAttraction>> getDayGroup(List<ResultItineraryAttraction> itineraryActionList) {
        HashMap<String, List<ResultItineraryAttraction>> map = new HashMap<String, List<ResultItineraryAttraction>>();
        for (ResultItineraryAttraction att : itineraryActionList) {
            String key = att.getItineraryDayplanName();
            if (map.containsKey(key)) {
                List<ResultItineraryAttraction> list = map.get(key);
                list.add(att);
            } else {
                List<ResultItineraryAttraction> list = new ArrayList<ResultItineraryAttraction>();
                list.add(att);
                map.put(key, list);
            }
        }
        return map;
    }

    public HashMap<String, List<ResultItineraryAttractionDay>> getDayGroups(List<ResultItineraryAttractionDay> rr) {
        HashMap<String, List<ResultItineraryAttractionDay>> maps = new HashMap<String, List<ResultItineraryAttractionDay>>();
        for (ResultItineraryAttractionDay atts : rr) {
            String key = atts.getItineraryDayplanName();
            if (maps.containsKey(key)) {
                List<ResultItineraryAttractionDay> list = maps.get(key);
                list.add(atts);
            } else {
                List<ResultItineraryAttractionDay> list = new ArrayList<ResultItineraryAttractionDay>();
                list.add(atts);
                maps.put(key, list);
            }
        }
        return maps;
    }
}
