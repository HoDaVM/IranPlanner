package com.iranplanner.tourism.iranplanner.ui.activity.dynamicItinerary;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
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
public class DynamicItineraryUpdateDayActivity extends StandardActivity implements DynamicItineraryContract.View, OnDynamicListListener, View.OnClickListener {

    ShowDynamicItineraryDayFragmentAdapter adapterViewPager;
    private ResultEditDynamicItinerary resultEditDynamicItinerary;
    int selectedDayToAdd = 0;
    TabLayout tabLayout;
    RecyclerView recyclerViewUnplanned;
    ImageButton btnDeleteDay;
    ShowDynamicUnplannListAdapter showDynamicUnplannListAdapter;
    RelativeLayout unplannedHolder;
    FloatingActionButton fabBtn;
    int dayCount = 0;
    //itnDailies use : lists in view pager
    List<ItnDaily> itnDailies;
    ViewPager vpPager;
    List<DayPosition> unPlanned;
    Button btnSaveItinerary;
    TextView txtDelete;
    @Inject
    DynamicItineraryPresenter dynamicItineraryPresenter;
    DaggerDynamicItineraryComponent.Builder builder;
    private ProgressDialog progressDialog;
    int positionDayDelete = 0;
    private Toolbar toolbar;

    private void init() {
        vpPager = findViewById(R.id.vpPager);
        recyclerViewUnplanned = findViewById(R.id.recyclerViewUnplanned);
        fabBtn = findViewById(R.id.fabBtn);
        btnSaveItinerary = findViewById(R.id.btnSaveItinerary);
        unplannedHolder = findViewById(R.id.unplannedHolder);
        btnDeleteDay = findViewById(R.id.btnDeleteDay);
        tabLayout = findViewById(R.id.tabs);
        txtDelete = findViewById(R.id.txtDelete);
        unplannedHolder.setVisibility(View.VISIBLE);
        fabBtn.setVisibility(View.VISIBLE);
        btnSaveItinerary.setVisibility(View.VISIBLE);
        List<ResultItineraryAttraction> itineraryActionList = null;
        builder = DaggerDynamicItineraryComponent.builder()
                .netComponent(((App) getApplicationContext()).getNetComponent())
                .dynamicItineraryModule(new DynamicItineraryModule(this));
        builder.build().inject(this);


    }

    private void setToolbar() {
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(resultEditDynamicItinerary.getResultUserItnFull().getItnTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void getExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            resultEditDynamicItinerary = (ResultEditDynamicItinerary) extras.getSerializable("ResultEditDynamicItinerary");
            dayCount = resultEditDynamicItinerary.getResultUserItnFull().getItnDaily().size();
            unPlanned = resultEditDynamicItinerary.getResultUserItnFull().getItnDaily().get(0).getDayPosition();
        }
    }

    private void setViewPagersData() {
        if (resultEditDynamicItinerary.getResultUserItnFull().getItnDaily() != null && resultEditDynamicItinerary.getResultUserItnFull().getItnDaily().size() >= 1) {
            itnDailies = resultEditDynamicItinerary.getResultUserItnFull().getItnDaily();
            itnDailies.remove(0);
            adapterViewPager = new ShowDynamicItineraryDayFragmentAdapter(getSupportFragmentManager(), itnDailies, this);
            vpPager.setAdapter(adapterViewPager);
            adapterViewPager.notifyDataSetChanged();
            tabLayout.setupWithViewPager(vpPager);
            vpPager.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            setBtnVisibility();
            setCustomFont();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabBtn:
                openAddDialog();
                break;

            case R.id.btnSaveItinerary:
                sendParam();
                break;
            case R.id.btnDeleteDay:
                deleteDay(positionDayDelete);

                break;
            case R.id.txtDelete:
                deleteDay(positionDayDelete);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itinerary_day_viewpager);
        init();

        getExtras();  setToolbar();
        setRecyclerViewUnplanned();
        setViewPagersData();

        fabBtn.setOnClickListener(this);
        btnSaveItinerary.setOnClickListener(this);
        btnDeleteDay.setOnClickListener(this);
        txtDelete.setOnClickListener(this);


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                positionDayDelete = tab.getPosition();
                txtDelete.setText("حذف روز " + Util.persianNumbers(String.valueOf(positionDayDelete + 1)));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
                   final LinearLayout linearLayout= view.findViewById(R.id.delAddHolder);
                   ImageButton addBtn= view.findViewById(R.id.addBtn);
                   ImageButton delBtn= view.findViewById(R.id.delBtn);
                   linearLayout.setVisibility(View.VISIBLE);

                   addBtn.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           CustomDialogDay customDialogDay = new CustomDialogDay(DynamicItineraryUpdateDayActivity.this, "d", position);
                           customDialogDay.show();
                           Util.buildDialog(DynamicItineraryUpdateDayActivity.this);
                           linearLayout.setVisibility(View.INVISIBLE);

                       }
                   });

                   delBtn.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Log.e("Del","del");
                           unPlanned.remove(position);
                           recyclerViewUnplanned.getAdapter().notifyDataSetChanged();
                           linearLayout.setVisibility(View.INVISIBLE);

                       }
                   });

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
        tabLayout.getTabAt(tabLayout.getTabCount() - 1).select();
    }

    private void updateDataset() {
        vpPager.getAdapter().notifyDataSetChanged();
        setCustomFont();
        setBtnVisibility();
    }

    private void setBtnVisibility() {
        if (tabLayout.getTabCount() == 0) {
            btnDeleteDay.setVisibility(View.INVISIBLE);
            txtDelete.setVisibility(View.INVISIBLE);

        } else {
            btnDeleteDay.setVisibility(View.VISIBLE);
            txtDelete.setVisibility(View.VISIBLE);
        }
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

     private void openAddDialog() {
        final Dialog mBottomSheetDialog = new Dialog(DynamicItineraryUpdateDayActivity.this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(R.layout.content_addoption_update_itinerary_);
        Button btnAddDay = ((Dialog) mBottomSheetDialog).findViewById(R.id.btnAddDay);
        Button btnAddItem = ((Dialog) mBottomSheetDialog).findViewById(R.id.btnAddItem);
        btnAddDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dayCount == 1) {
                    dayCount = 0;
                }
                addDay(newDayPosition("", "", ""));
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



    @Override
    public void onDelete(DayPosition dayPosition) {
        addToUnplanned(dayPosition);
    }

    private void addToUnplanned(DayPosition dayPosition) {
        unPlanned.add(dayPosition);
        recyclerViewUnplanned.getAdapter().notifyDataSetChanged();
    }

    private void deleteDay(int position) {
        if (itnDailies.get(position).getDayPosition() != null && itnDailies.get(position).getDayPosition().size() > 0) {
            for (DayPosition dayPosition : itnDailies.get(position).getDayPosition()) {
                addToUnplanned(dayPosition);
            }
        }
        //------------
        itnDailies.remove(position);
        updateDataset();
    }


    public class CustomDialogDay extends Dialog /*implements
            View.OnClickListener*/ {

        public Activity activity;
        public Dialog d;
        //        public TextView no;
        ListView listd;
        String type;
        int position1;
        List<String> addDaysList, nd;
        RecyclerView recyclerSelectDayDynamicItinerary;
        ShowSelectDayDynamicListAdapter showSelectDayDynamicListAdapter;
        LinearLayout footer;
        ShowSelectDayDynamicListAdapter ss;

        public CustomDialogDay(Activity a, String type, int position1) {
            super(a);
            this.activity = a;
            this.type = type;
            this.position1 = position1;
        }

        private List<String> getDayList() {
            nd = new ArrayList<String>();
            int planetListCounter = 1;
            for (ItnDaily itnDaily : itnDailies) {
                nd.add(" روز " + planetListCounter);
                planetListCounter++;
            }
            return nd;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            setContentView(R.layout.select_day_itinerary);
            Button addNewBtn = findViewById(R.id.addNewBtn);
            recyclerSelectDayDynamicItinerary = findViewById(R.id.recyclerSelectDayDynamicItinerary);
            footer = findViewById(R.id.footer);
            addDaysList = getDayList();
            ss = new ShowSelectDayDynamicListAdapter(activity, addDaysList, activity.getApplicationContext());
            LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            recyclerSelectDayDynamicItinerary.setLayoutManager(horizontalLayoutManagaer);
            recyclerSelectDayDynamicItinerary.setAdapter(ss);
            recyclerSelectDayDynamicItinerary.addOnItemTouchListener(new RecyclerItemOnClickListener(getApplicationContext(), new RecyclerItemOnClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, final int position) {
                    selectedDayToAdd = position;
                    Log.e("unplannedItem position", position + "");
                    dismiss();
                    addToDay(newDayPosition(unPlanned.get(position1).getNodeType(), unPlanned.get(position1).getNodeId(), unPlanned.get(position1).getTitle()), selectedDayToAdd);
                    removeItemFromUnplanned(position1);
                }
            }));


            addNewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dayCount == 1) {
                        dayCount = 0;
                    }
                    addDay(newDayPosition("", "", ""));
                    addDaysList.clear();
                    addDaysList.addAll(getDayList());
                    ss.notifyDataSetChanged();
                }
            });
            footer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

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
