package com.coinpany.core.android.widget;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coinpany.core.android.widget.observablescrollview.CacheFragmentStatePagerAdapter;
import com.iranplanner.tourism.iranplanner.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mahdi.
 *         Created on 9/30/2015.
 */
public class CPagerAdapter extends CacheFragmentStatePagerAdapter implements CPagerSlidingTabStrip.CustomTabProvider {

    private FragmentActivity activity;
//    Context app;

    private List<TabPage> tabPages;
    private int accentColor = Color.parseColor("#7F7F7F");
//    private int accentColor = Color.parseColor("#FFFFFF");


    public CPagerAdapter(FragmentActivity activity, List<TabPage> tabPages) {
        super(activity.getSupportFragmentManager());
        this.activity = activity;
//        this.app = activity;
//        this.module=module;

        this.tabPages = new ArrayList<>(tabPages);
    }


    private TabPage getTabPage(int position) {
        return tabPages.get(tabPages.size() - position - 1);
    }


    @Override
    protected Fragment createItem(int position) {
        return getTabPage(position).getFragment();
    }


    @Override
    public int getCount() {
        return tabPages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getTabPage(position).getTitle();
    }

    public int getPageIcon(int position) {
        return getTabPage(position).getIcon();
    }

    protected View getTabLayoutView(int position, final int accentColor) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.module_option_tab_item, null);

        TabPage tabPage = getTabPage(position);

        view.setBackgroundColor(tabPage.getMainColor());

        ImageView icon = (ImageView) view.findViewById(R.id.tabModuleIcon);
        icon.setColorFilter(accentColor, PorterDuff.Mode.SRC_ATOP);
        icon.setImageResource(tabPage.getIcon());

        TextView text = (TextView) view.findViewById(R.id.tabModuleName);
        text.setText(tabPage.getTitle());
        text.setTextColor(accentColor);
        text.setSingleLine(true);

//        text.setVisibility(View.GONE);

        return view;
    }

    @Override
    public int getTabBackgroundColor(int index) {
        return getTabPage(index).getMainColor();
    }

    @Override
    public int getTabBackgroundColorHighlight(int index) {
//        return Utils.highlightColor(getTabBackgroundColor(index));
        return getTabBackgroundColor(index);
    }

    @Override
    public View getTabLayoutView(int position) {
        return getTabLayoutView(position, getAccentColor(position));
    }


    @Override
    public View getTabLayoutViewHighlighted(int index) {
        return getTabLayoutView(index, getAccentColorHighlighted(index));
    }

    protected int getAccentColor(int position) {
        return accentColor;
    }
    protected int getAccentColorHighlighted(int position) {
        return accentColor;
    }


    public static class TabPage {
        private Fragment fragment;
        private CharSequence title;
        private int icon;
        private int mainColor;

        public TabPage(Fragment fragment, String title, int icon, int mainColor) {
            this.fragment = fragment;
            this.title = title;
            this.icon = icon;
            this.mainColor = mainColor;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public void setFragment(Fragment fragment) {
            this.fragment = fragment;
        }

        public CharSequence getTitle() {
            return title;
        }

        public void setTitle(CharSequence title) {
            this.title = title;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public int getMainColor() {
            return mainColor;
        }

        public void setMainColor(int mainColor) {
            this.mainColor = mainColor;
        }
    }
}