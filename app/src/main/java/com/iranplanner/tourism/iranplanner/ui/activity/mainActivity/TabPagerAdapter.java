package com.iranplanner.tourism.iranplanner.ui.activity.mainActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.ui.fragment.OnVisibleShowCaseViewListener;
import com.iranplanner.tourism.iranplanner.ui.fragment.blog.BlogFragment;
import com.iranplanner.tourism.iranplanner.ui.fragment.pandaMap.MapPandaFragment;
import com.iranplanner.tourism.iranplanner.ui.fragment.home.HomeFragment;
import com.iranplanner.tourism.iranplanner.ui.fragment.itinerarySearch.MainSearchFragment;
import com.iranplanner.tourism.iranplanner.standard.StandardFragment;
import com.iranplanner.tourism.iranplanner.ui.fragment.myaccount.SettingFragment;

import java.util.List;

import entity.GetHomeResult;
import entity.ResultBlogList;
import entity.ResultPostList;

/**
 * Created by Hoda on 10/01/2017.
 */
public class TabPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    public final int PAGE_COUNT = 5;
    GetHomeResult homeResult;
    List<ResultPostList> resultPostList;
    OnVisibleShowCaseViewListener onVisibleShowCaseViewListener;
    private int[] mTabsIcons = {
            R.mipmap.ic_home,
            R.mipmap.ic_profile_grey,
            R.mipmap.ic_barnamesafar,
            R.mipmap.ic_map_safar,
            R.mipmap.ic_magazine_foreground


    };
    private StandardFragment currentTab;
    //    StandardFragment
    private final String[] mTabsTitle = {"خانه","پروفایل","سفریاب","نقشه","مجله"};

    public TabPagerAdapter(FragmentManager fm, Context context, GetHomeResult homeResult, List<ResultPostList> resultPostList,
                           OnVisibleShowCaseViewListener onVisibleShowCaseViewListener) {
        super(fm);
        this.context = context;
        this.homeResult = homeResult;
        this.onVisibleShowCaseViewListener=onVisibleShowCaseViewListener;
        this.resultPostList=resultPostList;
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(mTabsTitle[position]);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        icon.setImageResource(mTabsIcons[position]);
        return view;
    }



    @Override
    public Fragment getItem(int pos) {
        currentTab = null;
        switch (pos) {

            case 0:

                currentTab = HomeFragment.newInstance(homeResult,onVisibleShowCaseViewListener);
                Bundle bundle = new Bundle();
                bundle.putSerializable("HomeResult", homeResult);
                currentTab.setArguments(bundle);
                return currentTab;

            case 1:

                currentTab = SettingFragment.newInstance(onVisibleShowCaseViewListener);
                return currentTab;

            case 2:
                currentTab = MainSearchFragment.newInstance(onVisibleShowCaseViewListener);
                return currentTab;
            case 3:
                currentTab = MapPandaFragment.newInstance(onVisibleShowCaseViewListener);
                return currentTab;
            case 4:
                currentTab = BlogFragment.newInstance(onVisibleShowCaseViewListener, resultPostList);
                return currentTab;
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabsTitle[position];
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

}

