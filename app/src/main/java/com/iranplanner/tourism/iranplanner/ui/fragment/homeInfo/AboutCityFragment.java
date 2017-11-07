package com.iranplanner.tourism.iranplanner.ui.fragment.homeInfo;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.coinpany.core.android.widget.CTouchyWebView;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.standard.StandardFragment;
import com.iranplanner.tourism.iranplanner.ui.activity.mainActivity.MainActivity;

import entity.HomeInfo;
import tools.Util;


/**
 * Created by h.vahidimehr on 10/01/2017.
 */
public class AboutCityFragment extends StandardFragment {
    protected CTouchyWebView contentFullDescription;

    HomeInfo homeInfo;
    Toolbar toolbar;

    public AboutCityFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_about_city, container, false);
        contentFullDescription = (CTouchyWebView) rootView.findViewById(R.id.contentFullDescription);
        ImageView img = (ImageView) rootView.findViewById(R.id.img);
        homeInfo = (HomeInfo) getArguments().getSerializable("homeInfo");
        String url = (String) getArguments().getSerializable("url");
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.toolbar_layout);

        if (url != null) {
            Util.setImageView(url, getContext(), img, null);
        }
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setTitle(homeInfo.getTitle());
        setWebViewContent(homeInfo.getBody());
        return rootView;
    }

    public static AboutCityFragment newInstance() {
        AboutCityFragment fragment = new AboutCityFragment();
        return fragment;
    }

    private void setWebViewContent(String myData) {
        contentFullDescription.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        contentFullDescription.setLongClickable(false);
        contentFullDescription.setHapticFeedbackEnabled(false);


        String pish = "<html><head><style type=\"text/css\">@font-face {color:#737373;font-family: MyFont;src: url(\"file:///android_asset/fonts/IRANSansMobile.ttf\")}body {font-family: MyFont;font-size: small;text-align: justify;direction:rtl}</style></head><body>";
        String pas = "</body></html>";
        String myHtmlString = pish + myData + pas;
        contentFullDescription.loadDataWithBaseURL(null, myHtmlString, "text/html", "UTF-8", null);
    }
}
