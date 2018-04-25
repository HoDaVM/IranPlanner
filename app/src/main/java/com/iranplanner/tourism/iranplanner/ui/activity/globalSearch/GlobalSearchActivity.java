package com.iranplanner.tourism.iranplanner.ui.activity.globalSearch;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.RecyclerItemOnClickListener;
import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore.AttractionListMorePresenter;
import com.iranplanner.tourism.iranplanner.ui.activity.attractionDetails.attractionDetailActivity;

import com.iranplanner.tourism.iranplanner.ui.activity.hotelDetails.ReservationHotelDetailActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.hotelReservationListOfCity.ReservationContract;
import com.iranplanner.tourism.iranplanner.ui.activity.mainActivity.MainActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.reservationHotelList.ReservationHotelListPresenter;
import com.iranplanner.tourism.iranplanner.ui.activity.restaurant.RestaurantContract;
import com.iranplanner.tourism.iranplanner.ui.activity.restaurant.RestaurantDetailActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.restaurant.RestaurantPresenter;
import com.iranplanner.tourism.iranplanner.ui.activity.souvenirFood.SouvenirFoodActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.souvenirFood.SouvenirFoodContract;
import com.iranplanner.tourism.iranplanner.ui.activity.souvenirFood.SouvenirFoodPresenter;
import com.iranplanner.tourism.iranplanner.ui.fragment.blog.BlogContract;
import com.iranplanner.tourism.iranplanner.ui.fragment.blog.BlogDetailActivity;
import com.iranplanner.tourism.iranplanner.ui.fragment.blog.BlogPresenter;
import com.iranplanner.tourism.iranplanner.ui.fragment.home.HomeContract;
import com.iranplanner.tourism.iranplanner.ui.fragment.home.HomePresenter;
import com.iranplanner.tourism.iranplanner.ui.fragment.itinerarySearch.MainSearchPresenter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import entity.GetHomeResult;
import entity.GetResultLocalFood;
import entity.GetResultSouvenir;
import entity.HomeAndBlog;
import entity.RestaurantList;
import entity.ResulAttraction;
import entity.ResultAttractionList;
import entity.ResultBlogFull;
import entity.ResultBlogList;
import entity.ResultCommentList;
import entity.ResultEvents;
import entity.ResultGlobalSearch;

import entity.ResultItineraryList;
import entity.ResultLocalfoodFull;
import entity.ResultLodging;
import entity.ResultLodgingHotel;
import entity.ResultLodgingList;
import entity.ResultRestaurantFull;
import entity.ShowAtractionDetailMore;
import entity.ShowAttractionListMore;
import tools.Constants;
import tools.Util;


/**
 * Created by h.vahidimehr on 21/02/2017.
 */

public class GlobalSearchActivity extends StandardActivity implements GlobalSearchContract.View, TextWatcher, ReservationContract.View, AttractionListMorePresenter.View, ReservationHotelListPresenter.View
        , MainSearchPresenter.View, RestaurantContract.View, BlogContract.View, HomeContract.View, SouvenirFoodContract.View {

    RecyclerView recyclerView;
    AutoCompleteTextView autoGlobalSearch;
    long delay = 1000; // 1 seconds after user stops typing
    long last_text_edit = 0;
    Handler handler = new Handler();
    @Inject
    GlobalSearchPresenter globalSearchPresenter;
    private ProgressDialog progressBar;
    ImageView imgMagnifier;
    List<ResultGlobalSearch> resultGlobalSearch;
    @Inject
    AttractionListMorePresenter attractionListMorePresenter;
    @Inject
    ReservationHotelListPresenter reservationHotelListPresenter;
    @Inject
    MainSearchPresenter mainSearchPresenter;
    @Inject
    RestaurantPresenter restaurantPresenter;
    @Inject
    BlogPresenter blogPresenter;
    @Inject
    HomePresenter homePresenter;
    @Inject
    SouvenirFoodPresenter souvenirFoodPresenter;
    boolean itemIsClicked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = findViewById(R.id.listGlobalRecyclerView);
        autoGlobalSearch = findViewById(R.id.autoGlobalSearch);
        imgMagnifier = findViewById(R.id.imgMagnifier);
        autoGlobalSearch.addTextChangedListener(this);
        imgMagnifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalSearchPresenter.getGlobalSearch("globalsearch", editableText, "10");
            }
        });
        DaggerGlobalSearchComponent.builder().netComponent(((App) getApplicationContext().getApplicationContext()).getNetComponent())
                .globalSearchModule(new GlobalSearchModule(this, this, this, this,
                        this, this
                        , this, this, this))
                .build().injectGlobalSearch(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        itemIsClicked = true;
    }

    private void setResultGlobalSearch() {
        GlobalSearchAdapter globalSearchAdapter = new GlobalSearchAdapter(resultGlobalSearch, getApplicationContext());
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        recyclerView.setAdapter(globalSearchAdapter);
        globalSearchAdapter.notifyDataSetChanged();
        recyclerView.addOnItemTouchListener(new RecyclerItemOnClickListener(getApplicationContext(), new RecyclerItemOnClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                if (itemIsClicked) {
                    itemIsClicked = false;
                    if (resultGlobalSearch.get(position).getType().equals("attraction")) {
                        attractionListMorePresenter.getAttractionDetailNear("full", resultGlobalSearch.get(position).getId(), "fa", "0", Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                    } else if (resultGlobalSearch.get(position).getType().equals("restaurant")) {
                        restaurantPresenter.getRestaurantFull("full", resultGlobalSearch.get(position).getId(), Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                    } else if (resultGlobalSearch.get(position).getType().equals("lodging")) {
                        reservationHotelListPresenter.getHotelReserve("full", String.valueOf(resultGlobalSearch.get(position).getId()), "20", "0", Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                    } else if (resultGlobalSearch.get(position).getType().equals("blog")) {
                        blogPresenter.getBlogFull("full", resultGlobalSearch.get(position).getId(), Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                    } else if (resultGlobalSearch.get(position).getType().equals("city")) {
                        homePresenter.getHomeAndBlog("home", "city", resultGlobalSearch.get(position).getId(), Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()), "list");
                    } else if (resultGlobalSearch.get(position).getType().equals("province")) {
                        homePresenter.getHomeAndBlog("home", "province", resultGlobalSearch.get(position).getId(), Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()), "list");
                    } else if (resultGlobalSearch.get(position).getType().equals("souvenir")) {
                        souvenirFoodPresenter.getSouvenirFull("full", resultGlobalSearch.get(position).getId());
                    } else if (resultGlobalSearch.get(position).getType().equals("localfood")) {
                        souvenirFoodPresenter.getLocalFoodFull("full", resultGlobalSearch.get(position).getId());
                    }
                }
            }
        }));

    }

    @Override
    protected int getLayoutId() {
        return R.layout.global_search_list;
    }


    @Override
    public void showHotelReserveList(ResultLodgingHotel resultLodgingHotel) {
        if (resultLodgingHotel != null) {
            ResultLodging resultLodgingHotelDetail = resultLodgingHotel.getResultLodging();
            Intent intent = new Intent(getApplicationContext(), ReservationHotelDetailActivity.class);
            intent.putExtra("resultLodgingHotelDetail", (Serializable) resultLodgingHotelDetail);
            Date todayDate = new Date();
            intent.putExtra("startOfTravel", todayDate);
            intent.putExtra("durationTravel", Constants.durationTravel);
            intent.putExtra("todayDate", todayDate);
            intent.putExtra("cityName", "");
            startActivity(intent);
        }
    }

    @Override
    public void showItineraries(ResultItineraryList resultItineraryList, String typeOfSearch) {

    }

    @Override
    public void showLodgingList(ResultLodgingList resultLodgingList) {

    }

    @Override
    public void showComments(ResultCommentList resultCommentList) {

    }

    @Override
    public void sendCommentMessage(ResultCommentList resultCommentList) {

    }

    @Override
    public void showError(String message) {
        itemIsClicked = true;
    }

    @Override
    public void commentResult(String message) {

    }

    @Override
    public void showComplete() {
        itemIsClicked = true;
    }

    @Override
    public void ShowHomeResult(GetHomeResult GetHomeResult) {

    }

    @Override
    public void ShowEventLists(ResultEvents resultEvents) {

    }

    @Override
    public void ShowEventDetail(ResultEvents resultEvent) {

    }

    @Override
    public void showProgress() {
        if (progressBar != null && progressBar.isShowing()) {

        } else {
            progressBar = Util.showProgressDialog(getApplicationContext(), "لطفا منتظر بمانید", this);
        }
    }

    @Override
    public void showLodgingList(ResultLodgingList resultLodgingList, String filter) {

    }

    @Override
    public void dismissProgress() {
        try {
            Util.dismissProgress(progressBar);
        } catch (Exception e) {
        }
    }

    @Override
    public void showFullSouvenir(GetResultSouvenir getResultSouvenir) {
        if (getResultSouvenir.getResultSouvenirFull() != null) {
            Intent intent = new Intent(this, SouvenirFoodActivity.class);
            intent.putExtra("ResultSouvenirFull", getResultSouvenir.getResultSouvenirFull());
            intent.putExtra("ResultLocalFood", (ResultLocalfoodFull) null);

            startActivity(intent);
        }
    }

    @Override
    public void showFullLocalFood(GetResultLocalFood getResultLocalFood) {
        if (getResultLocalFood.getResultLocalfoodFull() != null) {
            Intent intent = new Intent(this, SouvenirFoodActivity.class);
            intent.putExtra("ResultLocalFood", (ResultLocalfoodFull) getResultLocalFood.getResultLocalfoodFull());
            intent.putExtra("ResultSouvenirFull", (ResultLocalfoodFull) null);

            startActivity(intent);
        }
    }


    @Override
    public void ShowItineryDetail(ResultItineraryList resultItineraryList) {

    }

    @Override
    public void showHomeAndBlog(HomeAndBlog homeAndBlog) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("HomeResult", homeAndBlog.getHomeResult());
        intent.putExtra("ResultBlogList", homeAndBlog.getBlogList());
//        if (nTypeNotification != null && idNotification != null) {
//            intent.putExtra("nTypeNotification", nTypeNotification);
//            intent.putExtra("idNotification", idNotification);
//        }
        startActivity(intent);
    }

    @Override
    public void showBlogList(ResultBlogList resultBlogList) {

    }

    @Override
    public void showBlogFull(ResultBlogFull resultBlogFull) {
        if (resultBlogFull.getResultPostFull() != null) {
            Intent intent = new Intent(getApplicationContext(), BlogDetailActivity.class);
            intent.putExtra("ResultPostFull", (Serializable) resultBlogFull.getResultPostFull());
            startActivity(intent);
        }
    }

    @Override
    public void showAttractionDetail(ShowAtractionDetailMore showAttractionFull) {
        ResulAttraction resulAttraction = showAttractionFull.getResultAttractionFull().getResulAttraction();
        List<ResultAttractionList> resultAttractions = (List<ResultAttractionList>) showAttractionFull.getResultAttractionFull().getResultAttractionList();
        Intent intent = new Intent(getApplicationContext(), attractionDetailActivity.class);
        intent.putExtra("resulAttraction", (Serializable) resulAttraction);
        intent.putExtra("resultAttractionList", (Serializable) resultAttractions);
        startActivity(intent);
    }

    @Override
    public void ShowAttractionLists(ShowAttractionListMore getAttractionList) {

    }

    @Override
    public void setRestaurantFull(ResultRestaurantFull resultRestaurantFull) {
        if (resultRestaurantFull.getResultRestaurant() != null) {
            Intent intent = new Intent(getApplicationContext(), RestaurantDetailActivity.class);
            intent.putExtra("resultRestaurantFull", resultRestaurantFull);
            startActivity(intent);
        }
    }

    @Override
    public void setRestaurantList(RestaurantList restaurantList) {

    }

    @Override
    public void showSearchResult(List<ResultGlobalSearch> resultGlobalSearch) {
        if (resultGlobalSearch != null && resultGlobalSearch.size() > 0) {
            this.resultGlobalSearch = resultGlobalSearch;
            setResultGlobalSearch();
        } else {
            Toast.makeText(getApplicationContext(), "با واژه مورد نظر موردی یافت نشد", Toast.LENGTH_SHORT).show();
        }
    }


    private Runnable input_finish_checker = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                // TODO: do what you need here
                globalSearchPresenter.getGlobalSearch("globalsearch", editableText, "10");
            }
        }
    };

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }


    @Override

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        handler.removeCallbacks(input_finish_checker);
    }

    String editableText;

    @Override
    public void afterTextChanged(Editable s) {
        editableText = s.toString();
        if (s.length() > 0) {
            last_text_edit = System.currentTimeMillis();
            handler.postDelayed(input_finish_checker, delay);
        } else {

        }
    }


}
