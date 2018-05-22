package com.iranplanner.tourism.iranplanner.ui.activity.restaurant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.RecyclerItemOnClickListener;
import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.standard.DataTransferInterface;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore.AttractionsMoreListAdapter;
import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentPresenter;
import com.iranplanner.tourism.iranplanner.ui.activity.dynamicItinerary.DynamicItineraryContract;

import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import entity.InterestResult;
import entity.MyItineraryAdd;
import entity.MyItineraryList;
import entity.RestaurantList;
import entity.ResultCommentList;
import entity.ResultEditDynamicItinerary;
import entity.ResultImageList;
import entity.ResultParamUser;
import entity.ResultPositionAddItinerary;
import entity.ResultRestaurantFull;
import entity.ResultRestaurantList;
import entity.ResultWidgetFull;
import tools.Util;

/**
 * Created by h.vahidimehr on 13/03/2018.
 */

public class RestaurantListActivity extends StandardActivity implements RestaurantContract.View, DataTransferInterface, CommentPresenter.View ,DynamicItineraryContract.View{

    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;
    @BindView(R.id.listRecyclerView)
    RecyclerView listRecyclerView;
    @Inject
    CommentPresenter commentPresenter;
    @Inject
    RestaurantPresenter restaurantPresenter;
    List<ResultRestaurantList> resultRestaurantLists;
    private String nextOffset;
    private String selectedType;
    private LinearLayoutManager mLayoutManager;
    RestaurantListAdapter restaurantListAdapter;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private ProgressDialog progressDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_restaurant_list;
    }


    private void getExtra() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        resultRestaurantLists = (List<ResultRestaurantList>) bundle.getSerializable("restaurantList");
        nextOffset = bundle.getString("nextOffset");
        selectedType = bundle.getString("selectedType");

    }


    private void setToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("رستوران های " + resultRestaurantLists.get(0).getResultRestaurant().getCityTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void showProgress() {
        progressDialog = Util.showProgressDialog(getApplicationContext(), "منتظر بمانید", RestaurantListActivity.this);
    }

    @Override
    public void dismissProgress() {
        Util.dismissProgress(progressDialog);
    }

    @Override
    public void showDynamicItineraryList(MyItineraryList myItineraryList) {

    }

    @Override
    public void confirmationAddDynamicItinerary(MyItineraryAdd myItineraryAdd) {

    }

    @Override
    public void confirmationAddDynamicPosition(ResultPositionAddItinerary resultPositionAddItinerary) {

    }

    @Override
    public void showResultEditDynamicItinerary(ResultEditDynamicItinerary resultEditDynamicItinerary) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setUpRecyclerView() {
        listRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listRecyclerView.setLayoutManager(layoutManager);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());

        listRecyclerView.setLayoutManager(mLayoutManager);

        if (resultRestaurantLists != null && resultRestaurantLists.size() > 0) {
            restaurantListAdapter = new RestaurantListAdapter(this, this, resultRestaurantLists, getApplicationContext(), R.layout.content_attraction_list);
//            listRecyclerView.addItemDecoration(new AttractionListMoreItemDecoration(this));
            listRecyclerView.setAdapter(restaurantListAdapter);

        }
        listRecyclerView.addOnItemTouchListener(new RecyclerItemOnClickListener(getApplicationContext(), new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                restaurantPresenter.getRestaurantFull("full", resultRestaurantLists.get(0).getResultRestaurant().getRestaurantId(), Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));

            }
        }));


        listRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && loading) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = false;
                        restaurantPresenter.getRestaurantList("list", resultRestaurantLists.get(0).getResultRestaurant().getCityId(), selectedType, "20", nextOffset, Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                    }
                }
            }
//            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        DaggerRestaurantComponent.builder().netComponent(((App) getApplicationContext()).getNetComponent())
                .restaurantModule(new RestaurantModule(this, this,this))
                .build().inject(this);

        getExtra();
        setToolbar();
        setUpRecyclerView();

    }


    @Override
    public void showComments(ResultCommentList resultCommentList) {

    }

    @Override
    public void sendCommentMessage(ResultCommentList resultCommentList) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void commentResult(String message) {

    }

    @Override
    public void showComplete() {

    }

    @Override
    public void setIntrestedWidget(InterestResult interestResult) {

    }

    @Override
    public void setLoadWidget(ResultWidgetFull resultWidgetFull) {

    }

    @Override
    public void setRate(ResultParamUser resultParamUser) {

    }

    @Override
    public void setRateUser(ResultParamUser resultParamUser) {

    }

    @Override
    public void showMoreImages(ResultImageList resultImageList) {

    }

    @Override
    public void setImageUri(Uri uri) {

    }

    @Override
    public void setRestaurantFull(ResultRestaurantFull resultRestaurantFull) {
        if (resultRestaurantFull.getResultRestaurant() != null) {
            Intent intent = new Intent(RestaurantListActivity.this, RestaurantDetailActivity.class);
            intent.putExtra("resultRestaurantFull", resultRestaurantFull);
            startActivity(intent);
        }
    }

    @Override
    public void setRestaurantList(RestaurantList restaurantList) {


        if (!nextOffset.equals(restaurantList.getStatistics().getOffsetNext())) {
            resultRestaurantLists.addAll(restaurantList.getResultRestaurantList());
            restaurantListAdapter.notifyDataSetChanged();
            nextOffset = restaurantList.getStatistics().getOffsetNext().toString();
            if (restaurantList.getResultRestaurantList().size() > 0) {
                loading = true;
            }

        }

    }

    @Override
    public void setValues(ArrayList<String> al) {
        al.get(0);
    }
}