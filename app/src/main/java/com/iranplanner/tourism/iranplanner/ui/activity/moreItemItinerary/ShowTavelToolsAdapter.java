package com.iranplanner.tourism.iranplanner.ui.activity.moreItemItinerary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.ui.activity.GridActivity;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import entity.ResultSouvenir;
import entity.ResultSouvenirList;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import server.Config;
import server.getJsonInterface;

/**
 * Created by h.vahidimehr on 19/02/2017.
 */

public class ShowTavelToolsAdapter extends PagerAdapter {
    @Override
    public float getPageWidth(int position) {
        return (0.65f);
    }

    public final int PAGE_COUNT = 3;
    Context context;
    Activity activity;
    String itineraryID;

    //    public ShowTavelToolsAdapter(FragmentManager fm) {
//        super(fm);
//    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        ((ViewPager) container).removeView((View) view);
    }

    public ShowTavelToolsAdapter(Context context, Activity activity,String itineraryID) {
        this.context = context;
        this.activity = activity;
        this.itineraryID = itineraryID;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) container.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View page = inflater.inflate(R.layout.fragmen_tools_travel_show, null);
        TextView toolsText = (TextView) page.findViewById(R.id.toolsText);
        ImageView toolsImg = (ImageView) page.findViewById(R.id.toolsImg);
        if (position == 2) {
            toolsText.setText("غذای محلی");
            toolsImg.setImageDrawable(container.getContext().getResources().getDrawable(R.drawable.hotel_home));
        } else if (position == 1) {
            toolsText.setText("صنایع دستی");
            toolsImg.setImageDrawable(container.getContext().getResources().getDrawable(R.drawable.hotel_home));

        } else if (position == 0) {
            toolsText.setText("پیشنهادات");
            toolsImg.setImageDrawable(container.getContext().getResources().getDrawable(R.drawable.hotel_home));

        }


        page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    Intent intent = new Intent(activity, GridActivity.class);
                    activity.startActivity(intent);
                } else if (position == 1) {
//                    Intent intent = new Intent(activity, GridActivity.class);
//                    activity.startActivity(intent);
                    getResultOfSurvier(itineraryID);

                } else if (position == 2) {
                    Intent intent = new Intent(activity, GridActivity.class);
                    activity.startActivity(intent);
                }

            }
        });
        ((ViewPager) container).addView(page, 0);
        return page;


//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String a="dd";
//            }
//        });
//        return view;

    }

    private void startActivity(Intent intent) {
    }

    //    @Override
//    public Fragment getItem(int position) {
//        switch (position) {
//            case 2:
//                Bundle bundleHandcraft = new Bundle();
//                bundleHandcraft.putString("type", "handcraft");
//                ShowTravelToolsFragment shohandcraft=  new ShowTravelToolsFragment();
//                shohandcraft.setArguments(bundleHandcraft);
//                return shohandcraft;
//            case 1:
//                Bundle bundleFood = new Bundle();
//                bundleFood.putString("type", "food");
//                ShowTravelToolsFragment showfood=  new ShowTravelToolsFragment();
//                showfood.setArguments(bundleFood);
//                return showfood;
//            case 0:
//                Bundle bundleSuggestion = new Bundle();
//                bundleSuggestion.putString("type", "suggestion");
//                ShowTravelToolsFragment showsuggestion=  new ShowTravelToolsFragment();
//                showsuggestion.setArguments(bundleSuggestion);
//                return showsuggestion;
//        }
//        return null;
//    }

    private OkHttpClient setHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }

    public void getResultOfSurvier(String itineraryId) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(setHttpClient())
                .baseUrl(Config.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getJsonInterface getJsonInterface = retrofit.create(server.getJsonInterface.class);
        Call<ResultSouvenirList> callc = getJsonInterface.getSouvenirList("souvenir", itineraryId);
        callc.enqueue(new Callback<ResultSouvenirList>() {
            @Override
            public void onResponse(Call<ResultSouvenirList> call, Response<ResultSouvenirList> response) {
                if (response.body() != null) {
                    ResultSouvenirList jsonResponse = response.body();
                    List<ResultSouvenir> resultSouvenirs = jsonResponse.getResultSouvenir();
                    Intent intent = new Intent(activity, GridActivity.class);
                    intent.putExtra("resultSouvenirs", (Serializable) resultSouvenirs);
                    intent.putExtra("fromOpen", "Souvenirs");
                    activity.startActivity(intent);
                } else {
                    Log.e("comment body", "null");
                }
            }

            @Override
            public void onFailure(Call<ResultSouvenirList> call, Throwable t) {
                Log.e("result of intresting", "false");
            }
        });
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
