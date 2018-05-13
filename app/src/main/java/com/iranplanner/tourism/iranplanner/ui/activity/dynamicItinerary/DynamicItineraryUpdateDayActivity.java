package com.iranplanner.tourism.iranplanner.ui.activity.dynamicItinerary;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.RecyclerItemOnClickListener;
import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.ui.activity.OnDynamicListListener;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import entity.DayPosition;
import entity.ItnDaily;
import entity.ItnPosition;
import entity.MyItineraryAdd;
import entity.MyItineraryList;
import entity.ResultEditDynamicItinerary;
import entity.ResultItineraryAttraction;
import entity.ResultPositionAddItinerary;
import entity.SendParamSaveDynamicItinerary;
import tools.Util;

/**
 * Created by Hoda on 20/01/2017.
 */
public class DynamicItineraryUpdateDayActivity extends StandardActivity implements DynamicItineraryContract.View, OnDynamicListListener {

    ShowDynamicItineraryDayFragmentAdapter adapterViewPager;
    private ResultEditDynamicItinerary resultEditDynamicItinerary;
    int selectedDayToAdd = 0;
    TabLayout tabLayout;
    RecyclerView recyclerViewUnplanned;
    ShowDynamicUnplannListAdapter showDynamicUnplannListAdapter;
    RelativeLayout unplannedHolder;
    FloatingActionButton fabBtn;
    int dayCount = 0;
    List<ItnDaily> itnDailies;
    ViewPager vpPager;
    List<DayPosition> unPlanned;
    Button btnSaveItinerary;
    @Inject
    DynamicItineraryPresenter dynamicItineraryPresenter;
    DaggerDynamicItineraryComponent.Builder builder;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itinerary_day_viewpager);
        vpPager = findViewById(R.id.vpPager);
        recyclerViewUnplanned = findViewById(R.id.recyclerViewUnplanned);
        fabBtn = findViewById(R.id.fabBtn);
        btnSaveItinerary = findViewById(R.id.btnSaveItinerary);
        unplannedHolder = findViewById(R.id.unplannedHolder);
        unplannedHolder.setVisibility(View.VISIBLE);
        Bundle extras = getIntent().getExtras();
        List<ResultItineraryAttraction> itineraryActionList = null;
        builder = DaggerDynamicItineraryComponent.builder()
                .netComponent(((App) getApplicationContext()).getNetComponent())
                .dynamicItineraryModule(new DynamicItineraryModule(this));
        builder.build().inject(this);

        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dayCount == 1) {
                    dayCount = 0;
                }
                addDay(newDayPosition("", "", ""));
            }
        });

        btnSaveItinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendParam();
            }
        });

        if (extras != null) {
            resultEditDynamicItinerary = (ResultEditDynamicItinerary) extras.getSerializable("ResultEditDynamicItinerary");
            dayCount = resultEditDynamicItinerary.getResultUserItnFull().getItnDaily().size();
        }
        unPlanned = resultEditDynamicItinerary.getResultUserItnFull().getItnDaily().get(0).getDayPosition();
        setRecyclerViewUnplanned();
        if (resultEditDynamicItinerary.getResultUserItnFull().getItnDaily() != null && resultEditDynamicItinerary.getResultUserItnFull().getItnDaily().size() >= 1) {
            itnDailies = resultEditDynamicItinerary.getResultUserItnFull().getItnDaily();
            itnDailies.remove(0);
            adapterViewPager = new ShowDynamicItineraryDayFragmentAdapter(getSupportFragmentManager(), itnDailies, this);
            vpPager.setAdapter(adapterViewPager);
            adapterViewPager.notifyDataSetChanged();
            tabLayout = findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(vpPager);
            vpPager.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            setCustomFont();
        }
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

    private void setRecyclerViewUnplanned() {
        if (resultEditDynamicItinerary.getResultUserItnFull().getItnDaily().size() != 0) {
            showDynamicUnplannListAdapter = new ShowDynamicUnplannListAdapter(this, resultEditDynamicItinerary.getResultUserItnFull().getItnDaily().get(0).getDayPosition(), getApplicationContext());
            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerViewUnplanned.setLayoutManager(horizontalLayoutManagaer);
            recyclerViewUnplanned.setAdapter(showDynamicUnplannListAdapter);
            recyclerViewUnplanned.addOnItemTouchListener(new RecyclerItemOnClickListener(getApplicationContext(), new RecyclerItemOnClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, final int position) {
                    CustomDialogDay customDialogDay = new CustomDialogDay(DynamicItineraryUpdateDayActivity.this, "d", position);
                    customDialogDay.show();
                }
            }));

        }
    }

    private DayPosition newDayPosition(String nodeType, String nodeId, String title) {
        DayPosition day = new DayPosition();
        day.setNodeType(nodeType);
        day.setNodeId(nodeId);
        day.setTitle(title);
        return day;

    }

    private void addDay(DayPosition day) {
        ItnDaily itnDaily = new ItnDaily();
        itnDaily.setDayDescription("");
        itnDaily.setDayNumber(String.valueOf(dayCount));
        itnDailies.add(itnDaily);
        dayCount++;
        updateDataset();
    }

    private void updateDataset() {
        vpPager.getAdapter().notifyDataSetChanged();
        setCustomFont();
    }

    private void addToDay(DayPosition day, int addPosition) {
//           check first add day
        if (itnDailies.size() == 0) {
            addDay(day);
            addItemToDay(day, addPosition);

        } else {
            // add to empty list or not
            addItemToDay(day, addPosition);
        }
        updateDataset();
    }

    private void addItemToDay(DayPosition day, int addPosition) {
        if (itnDailies.get(addPosition).getDayPosition() == null) {
            List<DayPosition> dayPositions = new ArrayList<>();
            itnDailies.get(addPosition).setDayPosition(dayPositions);
            itnDailies.get(addPosition).getDayPosition().add(day);

        } else {
            itnDailies.get(addPosition).getDayPosition().add(day);
        }
    }

    private void removeItemFromUnplanned(int deletePosition) {
        unPlanned.remove(deletePosition);
        recyclerViewUnplanned.getAdapter().notifyDataSetChanged();
    }

    private void sendParam() {
        SendParamSaveDynamicItinerary sendParamSaveDynamicItinerary = new SendParamSaveDynamicItinerary();
        List<ItnPosition> itnPositionList = new ArrayList<>();
//        ItnPosition itnPosition = new ItnPosition();
        // check all of days
//--------------------- planned
        int dayNumber = 1;
        if (itnDailies != null) {
            for (ItnDaily daily : itnDailies) {
                int count = 0;
                Log.e("Day", dayNumber + "");
                if (daily.getDayPosition() != null) {
                    for (DayPosition dayPosition : daily.getDayPosition()) {
                        ItnPosition itnPosition = new ItnPosition();
                        Log.e("order", count + "");
                        itnPosition.setDay(String.valueOf(dayNumber)); //0 = unplanned
                        itnPosition.setNid(dayPosition.getNodeId());
                        itnPosition.setNtype(dayPosition.getNodeType());
                        itnPosition.setOrder(String.valueOf(count));
                        count++;
                        itnPositionList.add(itnPosition);
                    }
                    dayNumber++;
                }
            }

            Log.e("finish", "check planned");
        }


        //------------------------------unplanned
        int countUnplanned = 0;
        if (unPlanned != null) {
            for (DayPosition unplannDay : unPlanned) {
                ItnPosition itnPosition = new ItnPosition();
                itnPosition.setDay(String.valueOf(0)); //0 = unplanned
                itnPosition.setNid(unplannDay.getNodeId());
                itnPosition.setNtype(unplannDay.getNodeType());
                itnPosition.setOrder(String.valueOf(countUnplanned));
                countUnplanned++;
                itnPositionList.add(itnPosition);
            }
        }


        sendParamSaveDynamicItinerary.setDayCount(itnDailies.size());
        sendParamSaveDynamicItinerary.setItnId(resultEditDynamicItinerary.getResultUserItnFull().getItnId());
        sendParamSaveDynamicItinerary.setUid(Util.getUseRIdFromShareprefrence(getApplicationContext()));
        sendParamSaveDynamicItinerary.setItnPosition(itnPositionList);
        dynamicItineraryPresenter.sendParamForSaveDynamicItinerary(sendParamSaveDynamicItinerary, Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
    }

    private void openAddDialog(Context context) {
        final Dialog mBottomSheetDialog = new Dialog(context, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(R.layout.content_addoption_update_itinerary_);
        Button btnAddDay = ((Dialog) mBottomSheetDialog).findViewById(R.id.btnAddDay);
        Button btnAddItem = ((Dialog) mBottomSheetDialog).findViewById(R.id.btnAddItem);

//        Button okFilter = ((Dialog) mBottomSheetDialog).findViewById(R.id.okFilter);
//        okFilter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("filter", "clicked");
//                cleanMapAndRecyclerView();
//                getResultDraw();
//                mBottomSheetDialog.dismiss();
//            }
//        });

        btnAddDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();

            }
        });
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mBottomSheetDialog.dismiss();

            }
        });

        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
    }


    private void openCustomSearchDialog(String type) {

    }

    @Override
    public void onDelete(DayPosition dayPosition) {
        unPlanned.add(dayPosition);
        recyclerViewUnplanned.getAdapter().notifyDataSetChanged();
    }


    public class CustomDialogDay extends Dialog implements
            View.OnClickListener {

        public Activity c;
        public Dialog d;
        //        public TextView no;
        ListView listd;
        String type;
        int position1;

        public CustomDialogDay(Activity a, String type, int position1) {
            super(a);
            this.c = a;
            this.type = type;
            this.position1 = position1;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

            setContentView(R.layout.add_new_itinerary);

            Button addNewBtn = findViewById(R.id.addNewBtn);
            RecyclerView recyclerShowDynamicItinerary = findViewById(R.id.recyclerShowDynamicItinerary);
            ListView mainListView = findViewById(R.id.mainListView);
            recyclerShowDynamicItinerary.setVisibility(View.GONE);
            mainListView.setVisibility(View.VISIBLE);

//            no = (TextView) findViewById(R.id.txtNo);
            ArrayAdapter<String> listAdapter;

//            no.setOnClickListener(this);
            /*tempCityProvince =*/
            addNewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }


            });

            ArrayList<String> planetList = new ArrayList<String>();
            int position = 1;

            for (ItnDaily itnDaily : itnDailies) {
                planetList.add("روز" + position);
                position++;
            }
            // Create ArrayAdapter using the planet list.
            listAdapter = new ArrayAdapter<String>(c, android.R.layout.simple_list_item_1, planetList);


            // Set the ArrayAdapter as the ListView's adapter.
            mainListView.setAdapter(listAdapter);
            mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectedDayToAdd = position;
                    Log.e("unplannedItem position", position + "");
                    dismiss();
                    addToDay(newDayPosition(unPlanned.get(position1).getNodeType(), unPlanned.get(position1).getNodeId(), unPlanned.get(position1).getTitle()), selectedDayToAdd);
                    removeItemFromUnplanned(position1);

                }
            });
        }

        @Override
        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.txtNo:
//                    dismiss();
//                    break;
//                default:
//                    break;
//            }
            dismiss();
        }


    }

    //----------------------implement presenter
    @Override
    public void showError(String message) {

    }

    @Override
    public void showComplete() {

    }

    @Override
    public void showProgress() {
        progressDialog = Util.showProgressDialog(getApplicationContext(), "منتظر بمانید", DynamicItineraryUpdateDayActivity.this);
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
        updateDataset();
    }
}
