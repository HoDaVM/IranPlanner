package com.iranplanner.tourism.iranplanner.ui.activity.event;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.RecyclerItemOnClickListener;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import entity.HomeEvent;
import entity.ResultEvent;

public class EventListActivity extends StandardActivity implements RecyclerItemOnClickListener.OnItemClickListener {
    private List<ResultEvent> ResultEvent;
    private String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        getExtra();
        initToolbar();
        init();
    }

    private void getExtra() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ResultEvent = (List<ResultEvent>) bundle.getSerializable("ResultEvent");
        cityName = bundle.getString("title");
        Log.e("amin", ResultEvent.get(0).getEventInfo().getEventCityTitle() + " ");
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("رویدادهای " + cityName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void init() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setAdapter(new EventListAdapter(ResultEvent, this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addOnItemTouchListener(new RecyclerItemOnClickListener(this, this));
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(EventListActivity.this, EventActivity.class);
        intent.putExtra("ResultEvent", (Serializable) ResultEvent.get(position));
        startActivity(intent);
    }

    private ArrayList<HomeEvent> getEvents() {
        ArrayList<HomeEvent> events = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            HomeEvent event = new HomeEvent();
            event.setEventTitle("رویداد شماره ی " + String.valueOf(i));
            event.setEventDuration(String.valueOf(i));
            events.add(event);
        }
        return events;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_event_list;
    }
}
