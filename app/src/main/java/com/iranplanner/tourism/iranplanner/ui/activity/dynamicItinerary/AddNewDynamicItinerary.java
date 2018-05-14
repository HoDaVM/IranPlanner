package com.iranplanner.tourism.iranplanner.ui.activity.dynamicItinerary;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.RecyclerItemOnClickListener;
import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import entity.MyItineraryAdd;
import entity.MyItineraryList;
import entity.ResultEditDynamicItinerary;
import entity.ResultItnListUser;
import entity.ResultPositionAddItinerary;
import entity.SendParamToAddItinerary;
import entity.SendParamUsetToGetItinerary;
import tools.Util;

/**
 * Created by h.vahidimehr on 06/05/2018.
 */

public class AddNewDynamicItinerary extends StandardActivity implements DynamicItineraryContract.View, View.OnClickListener {
    List<ResultItnListUser> resultItnListUser;
    private ProgressDialog progressDialog;
    @BindView(R.id.recyclerShowDynamicItinerary)
    RecyclerView recyclerShowDynamicItinerary;
    @BindView(R.id.addNewBtn)
    Button addNewBtn;
    private LinearLayoutManager mLayoutManager;
    ShowDynamicListAdapter showDynamicListAdapter;
    DaggerDynamicItineraryComponent.Builder builder;
    @Inject
    DynamicItineraryPresenter dynamicItineraryPresenter;
    String nid;

    @Override
    protected int getLayoutId() {
        return R.layout.add_new_itinerary;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        try {
            resultItnListUser = (List<ResultItnListUser>) extras.getSerializable("ResultItnListUser");
            nid = extras.getString("nid");
            setUpRecyclerView();
        } catch (Exception e) {

        }
        setupToolbar();
        builder = DaggerDynamicItineraryComponent.builder()
                .netComponent(((App) getApplicationContext()).getNetComponent())
                .dynamicItineraryModule(new DynamicItineraryModule(this));
        builder.build().inject(this);
        addNewBtn.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("سفرهای من");
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showComplete() {

    }

    @Override
    public void showProgress() {
        progressDialog = Util.showProgressDialog(getApplicationContext(), "منتظر بمانید", AddNewDynamicItinerary.this);
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
        if (myItineraryAdd != null && myItineraryAdd.getStatus().getStatus() == 200) {
            ResultItnListUser rItinerary = new ResultItnListUser(myItineraryAdd.getResultItnAdd().getItnTitle(), myItineraryAdd.getResultItnAdd().getItnId(), myItineraryAdd.getResultItnAdd().getItnVisibility(), "0");
            resultItnListUser.add(0, rItinerary);
            showDynamicListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void confirmationAddDynamicPosition(ResultPositionAddItinerary resultPositionAddItinerary) {

    }

    @Override
    public void showResultEditDynamicItinerary(ResultEditDynamicItinerary resultEditDynamicItinerary) {
        if(resultEditDynamicItinerary!=null && resultEditDynamicItinerary.getResultUserItnFull()!=null){
            Intent intent = new Intent(this, DynamicItineraryUpdateDayActivity.class);
            intent.putExtra("ResultEditDynamicItinerary", resultEditDynamicItinerary);
            startActivity(intent);
        }
    }

    private void setUpRecyclerView() {
        recyclerShowDynamicItinerary.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerShowDynamicItinerary.setLayoutManager(layoutManager);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerShowDynamicItinerary.setLayoutManager(mLayoutManager);

        if (resultItnListUser != null && resultItnListUser.size() > 0) {
            showDynamicListAdapter = new ShowDynamicListAdapter(this, resultItnListUser, getApplicationContext());
//            recyclerShowDynamicItinerary.addItemDecoration(new AttractionListMoreItemDecoration(this));
            recyclerShowDynamicItinerary.setAdapter(showDynamicListAdapter);
        }
        recyclerShowDynamicItinerary.addOnItemTouchListener(new RecyclerItemOnClickListener(getApplicationContext(), new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
//                recyclerShowDynamicItinerarystaurantPresenter.getRestaurantFull("full", resultRestaurantLists.get(0).getResultRestaurant().getRestaurantId(), Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                final CheckBox checkBox = view.findViewById(R.id.checkBoxExist);
                final Button editBtn = view.findViewById(R.id.editBtn);

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
//                            {"uid":"792147600796866","itn_id":"20851525601128","ntype":"attraction","nid":"27350"}
                            final SendParamUsetToGetItinerary sendParamUserToGetItinerary = new SendParamUsetToGetItinerary(Util.getUseRIdFromShareprefrence(getApplicationContext()), "", "attraction", nid, resultItnListUser.get(position).getItnId());
                            dynamicItineraryPresenter.addPosition(sendParamUserToGetItinerary, Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                        }
                        if (!isChecked) {
                            checkBox.setChecked(true);
                        }
                    }
                });
                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final SendParamUsetToGetItinerary sendParamUserToGetItinerary = new SendParamUsetToGetItinerary(Util.getUseRIdFromShareprefrence(getApplicationContext()), "", "", "", resultItnListUser.get(position).getItnId());
                        dynamicItineraryPresenter.getResultEditDynamicItinerary(sendParamUserToGetItinerary, Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                    }
                });

            }
        }));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addNewBtn:
                CustomDialogAlert customDialogAlert = new CustomDialogAlert(this);
                customDialogAlert.show();
                break;
        }
    }

    public class CustomDialogAlert extends Dialog implements
            View.OnClickListener {

        public Activity c;
        public Dialog d;
        //        public ResultParamUser resultParamUser;
        Button okBtn, cancelBtn;
        AutoCompleteTextView autoTextAddNewItinerary;
        CheckBox checkBoxGlobalShow;


        public CustomDialogAlert(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
//            this.resultParamUser = resultParamUser;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
//            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.add_dynamic_itinerary);
            okBtn = findViewById(R.id.okBtn);
            cancelBtn = findViewById(R.id.cancelBtn);
            autoTextAddNewItinerary = findViewById(R.id.autoTextAddNewItinerary);
            checkBoxGlobalShow = findViewById(R.id.checkBoxGlobalShow);


            okBtn.setOnClickListener(this);
            cancelBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.okBtn:
                    SendParamToAddItinerary s = new SendParamToAddItinerary(Util.getUseRIdFromShareprefrence(c.getApplicationContext()), autoTextAddNewItinerary.getText().toString(), checkBoxGlobalShow.isChecked() ? "1" : "0", "");
                    dynamicItineraryPresenter.addNewDynamicItinerary(s, Util.getTokenFromSharedPreferences(c.getApplicationContext()), Util.getAndroidIdFromSharedPreferences(c.getApplicationContext()));
                    break;
                case R.id.cancelBtn:
                    dismiss();

                    break;
            }
            dismiss();
        }
    }
}
