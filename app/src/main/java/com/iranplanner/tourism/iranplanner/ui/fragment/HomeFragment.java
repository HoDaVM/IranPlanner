package com.iranplanner.tourism.iranplanner.ui.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iranplanner.tourism.iranplanner.MainActivity;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.adapter.HomeProvinceListAdapter;
import com.iranplanner.tourism.iranplanner.adapter.NavigationItemsAdapter;
import com.iranplanner.tourism.iranplanner.adapter.ShowHomeBestAttractionAdapter;
import com.iranplanner.tourism.iranplanner.adapter.ShowHomeBestLodgingAdapter;
import com.iranplanner.tourism.iranplanner.adapter.ShowHomeEventAdapter;
import com.iranplanner.tourism.iranplanner.di.DaggerHomeComponent;
import com.iranplanner.tourism.iranplanner.di.HomeModule;
import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.standard.ClickableViewPager;
import com.iranplanner.tourism.iranplanner.standard.DataTransferInterface;
import com.iranplanner.tourism.iranplanner.standard.StandardFragment;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;
import com.iranplanner.tourism.iranplanner.ui.presenter.HomePresenter;
import com.iranplanner.tourism.iranplanner.ui.presenter.ReservationPresenter;
import com.iranplanner.tourism.iranplanner.ui.presenter.abs.HomeContract;
import com.iranplanner.tourism.iranplanner.ui.presenter.abs.ReservationContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import autoComplet.MyFilterableAdapterCityProvince;
import autoComplet.ReadJsonCityProvince;
import entity.CityProvince;
import entity.Data;
import entity.GetHomeResult;
import entity.HomeAttraction;
import entity.HomeCountryProvince;
import entity.HomeEvent;
import entity.HomeImage;
import entity.HomeInfo;
import entity.HomeLodging;
import entity.ResultHome;
import entity.ResultLodgingList;
import me.relex.circleindicator.CircleIndicator;
import tools.Util;


/**
 * Created by h.vahidimehr on 10/01/2017.
 */
public class HomeFragment extends StandardFragment implements DataTransferInterface,View.OnClickListener, AppBarLayout.OnOffsetChangedListener, NestedScrollView.OnScrollChangeListener, HomeContract.View, ReservationContract.View {

    protected String[] mNavigationDrawerItemTitles;
    protected DrawerLayout mDrawerLayout;
    protected ListView mDrawerList;
    protected Toolbar toolbar;
    protected CharSequence mDrawerTitle;
    protected CharSequence mTitle;
    List<CityProvince> CityProvince;
    String selectId;
    protected ImageView toolbarToggle;
    protected ImageView toolbarToggleLeft;

    protected static int buildVersion;
    @Inject
    HomePresenter homePresenter;
    @Inject
    ReservationPresenter reservationPresenter;
    protected TextView toolbarTitle;

    int width;
    int height;
    boolean anim = false;
    Button aboutCityBtn;
    LinearLayout card_view_province_list;
    TextView txtWhereGo;
    boolean showContentProvince = true;
    View toolbarFeatureElevation;
    String cityProvinceName;
    private String cityProvinceId;
    List<CityProvince> tempCityProvince;
    NestedScrollView scroller;
    ImageView imageView, test;
    int toolbarHeight = 0;
    RelativeLayout SelectHolder;
    RelativeLayout frameLayout;
    LinearLayout featureListHolder;
    private ProgressDialog progressDialog;
    ImageView imgHome;
    ClickableViewPager BestHotelViewPager, BestAttractionViewPager;
    ClickableViewPager eventsViewPager;
    CircleIndicator indicator;
    RelativeLayout viewPagerEventsHolder;
    RecyclerView recyclerViewProvinceShow;

    public HomeFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        AppBarLayout appBarLayout = (AppBarLayout) rootView.findViewById(R.id.app_bar);
        SelectHolder = (RelativeLayout) rootView.findViewById(R.id.SelectHolder);
        aboutCityBtn = (Button) rootView.findViewById(R.id.aboutCityBtn);
        imgHome = (ImageView) rootView.findViewById(R.id.imgHome);
        card_view_province_list = (LinearLayout) rootView.findViewById(R.id.card_view_province_list);
        txtWhereGo = (TextView) rootView.findViewById(R.id.txtWhereGo);
        test = (ImageView) rootView.findViewById(R.id.test);
//        LinearLayout btnShowProvince = (LinearLayout) rootView.findViewById(R.id.btnShowProvince);
        BestHotelViewPager = (ClickableViewPager) rootView.findViewById(R.id.BestHotelViewPager);
        BestAttractionViewPager = (ClickableViewPager) rootView.findViewById(R.id.BestAttractionViewPager);
        mDrawerLayout = (DrawerLayout) rootView.findViewById(R.id.drawer_layout);
        eventsViewPager = (ClickableViewPager) rootView.findViewById(R.id.EventsViewPager);
        indicator = (CircleIndicator) rootView.findViewById(R.id.indicator);
        toolbarToggle = (ImageView) rootView.findViewById(R.id.toolbarToggle);
        toolbarToggleLeft = (ImageView) rootView.findViewById(R.id.toolbarToggleLeft);
        frameLayout = (RelativeLayout) rootView.findViewById(R.id.featureListRelativeLayout);
        featureListHolder = (LinearLayout) rootView.findViewById(R.id.featureListHolder);
        toolbarFeatureElevation = (View) rootView.findViewById(R.id.toolbarFeatureElevation);
        toolbarTitle = (TextView) rootView.findViewById(R.id.toolbarTitle);
        scroller = (NestedScrollView) rootView.findViewById(R.id.nestedScrollView);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        mDrawerList = (ListView) rootView.findViewById(R.id.left_drawer);
        viewPagerEventsHolder = (RelativeLayout) rootView.findViewById(R.id.viewPagerEventsHolder);
        recyclerViewProvinceShow = (RecyclerView) rootView.findViewById(R.id.recyclerViewProvinceShow);

//        btnShowProvince.setOnClickListener(this);
        test.setOnClickListener(this);
        appBarLayout.addOnOffsetChangedListener(this);
        if (scroller != null) {
            scroller.setOnScrollChangeListener(this);
        }
        aboutCityBtn.setOnClickListener(this);
        txtWhereGo.setOnClickListener(this);


        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_items);

        setupToolbar();
        getfeatureHolderHight();
        showDrawer();

        return rootView;
    }

    private void getfeatureHolderHight() {
        ViewTreeObserver vto = SelectHolder.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    SelectHolder.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    SelectHolder.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                width = SelectHolder.getMeasuredWidth();
                height = SelectHolder.getMeasuredHeight();

            }
        });
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    private void showDrawer() {

//      NavigationItemsAdapter adapter = new NavigationItemsAdapter
//                (this, R.layout.nav_list_row, drawerItem);
//        mDrawerList.setAdapter(adapter);
//        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//
//        toolbarToggle = (ImageView) findViewById(R.id.toolbarToggle);
//        toolbarToggleLeft = (ImageView) findViewById(R.id.toolbarToggleLeft);
//        toolbarToggle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                mDrawerLayout.openDrawer(GravityCompat.END);
//
//            }
//        });
        Data[] drawerItem = new Data[3];

        drawerItem[0] = new Data(R.drawable.ic_google, "ایتم اول");
        drawerItem[1] = new Data(R.drawable.ic_google, "ایتم دوم");
        drawerItem[2] = new Data(R.drawable.ic_google, "ایتم سوم");

        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);

        NavigationItemsAdapter adapter = new NavigationItemsAdapter
                (getContext(), R.layout.nav_list_row, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


        toolbarToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDrawerLayout.openDrawer(GravityCompat.END);

            }
        });


        buildVersion = Build.VERSION.SDK_INT;

        Log.i("hi", String.valueOf(buildVersion));


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aboutCityBtn:
                showAboutCityOrProvince();
                break;
//            case R.id.btnShowProvince:
//                onClickShowProvince(card_view_province_list);
//                break;
            case R.id.test:
                test.setVisibility(View.INVISIBLE);
                break;
            case R.id.txtWhereGo:
                openCustomSearchDialog();
                break;
        }
    }

    private void openCustomSearchDialog() {
        CustomDialogSearchLocation customDialogSearchLocation = new CustomDialogSearchLocation(getActivity());
        customDialogSearchLocation.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(customDialogSearchLocation.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        customDialogSearchLocation.show();
        customDialogSearchLocation.getWindow().setAttributes(lp);
        customDialogSearchLocation.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
    }

    private void showAboutCityOrProvince() {
        AboutCityFragment aboutCityFragment = AboutCityFragment.newInstance();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.containerHomeFragment, aboutCityFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void onClickShowProvince(LinearLayout card_view_province) {
        if (showContentProvince) {
            card_view_province.setVisibility(View.VISIBLE);
            showContentProvince = false;
        } else {
            card_view_province.setVisibility(View.GONE);
            showContentProvince = true;
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        toolbarHeight = ((MainActivity) getActivity()).getSupportActionBar().getHeight();
        if (verticalOffset <= (-1) * appBarLayout.getTotalScrollRange() + toolbarHeight) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.main_blue));
        } else if (verticalOffset > (-1) * appBarLayout.getTotalScrollRange() + toolbarHeight) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.transparent));
        }
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY > oldScrollY) {
            Log.i("Scroll DOWN", "Scroll DOWN");
            if (scrollY > height - toolbarHeight && anim == false) {
                frameLayout.setBackgroundColor(getResources().getColor(R.color.background));
                Animation animate1 = new AlphaAnimation(0.2f, 1.0f);
                animate1.setDuration(1000);
                animate1.setFillAfter(true);
                featureListHolder.startAnimation(animate1);
                featureListHolder.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.VISIBLE);
                toolbarFeatureElevation.setVisibility(View.VISIBLE);
                anim = true;

            }
        }
        if (scrollY < oldScrollY && scrollY < height) {
            Log.i("Scroll UP", "Scroll UP");
            frameLayout.setVisibility(View.INVISIBLE);
            featureListHolder.setVisibility(View.GONE);
            anim = false;
        }

        if (scrollY == 0) {
            Log.i("TOP DOWN", "TOP SCROLL");
        }

        if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
            Log.i("SCROLL", "BOTTOM SCROLL");
        }
    }

    @Override
    public void showLodgingList(ResultLodgingList resultLodgingList) {

    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), "مقصد مورد نظر یافت نشد", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showComplete() {

    }

    @Override
    public void getHomeResult(GetHomeResult getHomeResult) {
        List<ResultHome> resultHomes = getHomeResult.getResultHome();
        setNamePicture(resultHomes.get(0).getHomeInfo(), resultHomes.get(0).getHomeImages());
        setViewPagerLodging(resultHomes.get(0).getHomeLodging());
        setViewPagerAttaction(resultHomes.get(0).getHomeAttraction());
        setViewPagerEvent(resultHomes.get(0).getHomeEvent());
        if (resultHomes.get(0).getHomeCountryProvince().size() != 0) {
            setListProvince(resultHomes.get(0).getHomeCountryProvince());
        }
    }

    private void setViewPagerEvent(List<HomeEvent> homeEvents) {
        ShowHomeEventAdapter showHomeEventAdapter = new ShowHomeEventAdapter(getContext(), getActivity(), homeEvents);
        eventsViewPager.setAdapter(showHomeEventAdapter);
        indicator.setViewPager(eventsViewPager);
        eventsViewPager.setCurrentItem(2);
    }

    private void setListProvince(List<HomeCountryProvince> homeCountryProvince) {
//        HomeProvinceAdapter homeProvinceAdaptera=new HomeProvinceAdapter()
//        recyclerViewProvinceShow.setLayoutManager(new RecyclerView.LayoutManager(this) {
//            @Override
//            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
//                return null;
//            }
//        });
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewProvinceShow.setLayoutManager(mLayoutManager);
        HomeProvinceListAdapter homeProvinceAdapter = new HomeProvinceListAdapter(getActivity(), this, homeCountryProvince, getContext(), R.layout.content_province_list);
        recyclerViewProvinceShow.setAdapter(homeProvinceAdapter);
//        ShowHomeEventAdapter showHomeEventAdapter = new ShowHomeEventAdapter(getContext(), getActivity(), homeEvents);
//
//        eventsViewPager.setAdapter(showHomeEventAdapter);
//        indicator.setViewPager(eventsViewPager);
//        eventsViewPager.setCurrentItem(2);


    }

    private void setViewPagerLodging(List<HomeLodging> homeLodgings) {
        ShowHomeBestLodgingAdapter showTavelToolsAdapter = new ShowHomeBestLodgingAdapter(getContext(), getActivity(), homeLodgings);
        BestHotelViewPager.setAdapter(showTavelToolsAdapter);
        BestHotelViewPager.setCurrentItem(homeLodgings.size() - 1);
        BestHotelViewPager.setClipToPadding(false);
    }

    private void setViewPagerAttaction(List<HomeAttraction> homeAttractions) {
        ShowHomeBestAttractionAdapter showTavelToolsAdapter = new ShowHomeBestAttractionAdapter(getContext(), getActivity(), homeAttractions);
        BestAttractionViewPager.setAdapter(showTavelToolsAdapter);
        BestAttractionViewPager.setCurrentItem(homeAttractions.size() - 1);
        BestAttractionViewPager.setClipToPadding(false);
    }

    private void setNamePicture(HomeInfo homeInfo, List<HomeImage> homeImage) {
        txtWhereGo.setText(homeInfo.getTitle());
//        imgHome.setImageBitmap();
        if (homeImage.get(0).getImgUrl() != null) {
            Util.setImageView(homeImage.get(0).getImgUrl(), getContext(), imgHome);
        }

//        reservationPresenter.getLodgingList("list", "483", Util.getTokenFromSharedPreferences(getContext()),Util.getAndroidIdFromSharedPreferences(getContext()));

    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme);
        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("لطفا منتظر بمانید");
        progressDialog.show();
    }

    @Override
    public void dismissProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void setValues(ArrayList<String> al) {
        al.get(0);
    }


    public class CustomDialogSearchLocation extends Dialog implements
            View.OnClickListener {

        public Activity c;
        public Dialog d;
        public TextView no;
        ListView listd;

        public CustomDialogSearchLocation(Activity a) {
            super(a);
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

//            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_home_search_location);
            AutoCompleteTextView autoTextWhere = (AutoCompleteTextView) findViewById(R.id.autoTextWhere);
            listd = (ListView) findViewById(R.id.listd);
            no = (TextView) findViewById(R.id.txtNo);
            no.setOnClickListener(this);
            /*tempCityProvince =*/
            autoCompleteProvince(autoTextWhere);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.txtNo:
                    dismiss();
                    break;
                default:
                    break;
            }
            dismiss();
        }


        public void autoCompleteProvince(AutoCompleteTextView textProvience) {
            ReadJsonCityProvince readJsonProvince = new ReadJsonCityProvince();
            CityProvince = readJsonProvince.getListOfCityProvience(getContext());
            MyFilterableAdapterCityProvince adapter = new MyFilterableAdapterCityProvince(getContext(), android.R.layout.simple_list_item_1, CityProvince);
            textProvience.setAdapter(adapter);
            textProvience.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectId = CityProvince.get(position).getId();
                    getHomeResult("country", "311");
                    dismiss();
                }
            });
        }

    }

    private void getHomeResult(String destination, String selectId) {
        DaggerHomeComponent.builder().netComponent(((App) getContext().getApplicationContext()).getNetComponent())
                .homeModule(new HomeModule(this, this))
                .build().inject(this);
        String cid = Util.getTokenFromSharedPreferences(getContext());
        String andId = Util.getAndroidIdFromSharedPreferences(getContext());
        homePresenter.getHome("home", destination, selectId, cid, andId);

    }

    void setupToolbar() {
        ((StandardActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.transparent));
        ((StandardActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbarHeight = ((MainActivity) getActivity()).getSupportActionBar().getHeight();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick
                (AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:

                fragment = new FirstItem();
                break;
            case 1:
                fragment = new FirstItem();
                break;
            case 2:
                fragment = new FirstItem();
                break;

            default:
                break;
        }

        if (fragment != null) {
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
//
//            mDrawerList.setItemChecked(position, true);
//            mDrawerList.setSelection(position);
//            setTitle(mNavigationDrawerItemTitles[position]);
//            mDrawerLayout.closeDrawer(GravityCompat.END);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
}
