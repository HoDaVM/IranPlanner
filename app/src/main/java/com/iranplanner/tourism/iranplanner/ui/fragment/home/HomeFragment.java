package com.iranplanner.tourism.iranplanner.ui.fragment.home;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.RecyclerItemOnClickListener;
import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.standard.ClickableViewPager;
import com.iranplanner.tourism.iranplanner.standard.DataTransferInterface;
import com.iranplanner.tourism.iranplanner.standard.StandardFragment;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore.AttractionListMorePresenter;
import com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore.ShowAttractionListMoreActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.attractionDetails.attractionDetailActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.event.EventActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.event.EventListActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.hotelDetails.ReservationHotelDetailActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.hotelReservationListOfCity.ReservationContract;
import com.iranplanner.tourism.iranplanner.ui.activity.hotelReservationListOfCity.ReservationPresenter;
import com.iranplanner.tourism.iranplanner.ui.activity.mainActivity.MainActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.moreItemItinerary.MoreItemItineraryActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.reservationHotelList.ReservationHotelListActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.reservationHotelList.ReservationHotelListPresenter;
import com.iranplanner.tourism.iranplanner.ui.fragment.FirstItem;
import com.iranplanner.tourism.iranplanner.ui.fragment.itineraryList.ItineraryListFragment;
import com.iranplanner.tourism.iranplanner.ui.fragment.itinerarySearch.MainSearchPresenter;
import com.iranplanner.tourism.iranplanner.ui.navigationDrawer.AboutUsActivity;
import com.iranplanner.tourism.iranplanner.ui.navigationDrawer.AboutCityDialog;
import com.iranplanner.tourism.iranplanner.ui.navigationDrawer.ContactUsActivity;
import com.iranplanner.tourism.iranplanner.ui.navigationDrawer.NavigationFunctionsHelper;
import com.iranplanner.tourism.iranplanner.ui.tutorial.TutorialActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import autoComplet.MyFilterableAdapterCityProvince;
import autoComplet.ReadJsonCityProvince;
import butterknife.ButterKnife;
import butterknife.InjectView;
import entity.CityProvince;
import entity.GetHomeResult;
import entity.HomeAttraction;
import entity.HomeCountryProvince;
import entity.HomeEvent;
import entity.HomeImage;
import entity.HomeInfo;
import entity.HomeItinerary;
import entity.HomeLocalfood;
import entity.HomeLodging;
import entity.HomeNeighborCity;
import entity.HomeSouvenir;
import entity.ResulAttraction;
import entity.ResultAttractionList;
import entity.ResultCommentList;
import entity.ResultEvent;
import entity.ResultEvents;
import entity.ResultHome;
import entity.ResultItinerary;
import entity.ResultItineraryList;
import entity.ResultLodging;
import entity.ResultLodgingHotel;
import entity.ResultLodgingList;
import entity.ShowAtractionDetailMore;
import entity.ShowAttractionListMore;
import entity.ShowAttractionMoreList;
import me.relex.circleindicator.CircleIndicator;
import tools.Constants;
import tools.Util;

import static android.R.attr.data;


/**
 * Created by h.vahidimehr on 10/01/2017.
 */
public class HomeFragment extends StandardFragment implements DataTransferInterface, View.OnClickListener,
        AppBarLayout.OnOffsetChangedListener, NestedScrollView.OnScrollChangeListener,
        HomeContract.View, ReservationContract.View, AttractionListMorePresenter.View, ReservationHotelListPresenter.View
        , MainSearchPresenter.View {

    public static final String TAG_HOMEFRAGMENT = "homefragmnet";

    protected String[] mNavigationDrawerItemTitles;
    List<CityProvince> CityProvince;
    String selectId, SelectedType;
    GetHomeResult homeResult;
    protected static int buildVersion;
    @Inject
    HomePresenter homePresenter;
    @Inject
    ReservationPresenter reservationPresenter;
    @Inject
    AttractionListMorePresenter attractionListMorePresenter;
    @Inject
    ReservationHotelListPresenter reservationHotelListPresenter;
    @Inject
    MainSearchPresenter mainSearchPresenter;
    //    protected TextView toolbarTitle;
    List<HomeNeighborCity> homeNeighborCity;
    int width;
    int height;
    boolean anim = false;
    boolean showContentProvince = true;

    int toolbarHeight = 0;

    private ProgressDialog progressDialog;
    List<ResultHome> resultHomes;
    @InjectView(R.id.app_bar)
    AppBarLayout appBarLayout;
    @InjectView(R.id.SelectHolder)
    RelativeLayout SelectHolder;
    @InjectView(R.id.aboutCityBtn)
    RelativeLayout aboutCityBtn;
    @InjectView(R.id.imgHome)
    ImageView imgHome;
    @InjectView(R.id.toolbarBack)
    ImageView toolbarBack;
    @InjectView(R.id.txtWhereGo)
    TextView txtWhereGo;
    @InjectView(R.id.txtMoreTitleAttraction)
    TextView txtMoreTitleAttraction;
    @InjectView(R.id.TypeAttractionHolder)
    RelativeLayout TypeAttractionHolder;
    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @InjectView(R.id.EventsViewPager)
    ClickableViewPager eventsViewPager;
    @InjectView(R.id.indicator)
    CircleIndicator indicator;
    @InjectView(R.id.toolbarToggle)
    ImageView toolbarToggle;
    @InjectView(R.id.toolbarToggleLeft)
    ImageView toolbarToggleLeft;
    @InjectView(R.id.featureListRelativeLayout)
    RelativeLayout frameLayout;
    @InjectView(R.id.featureListHolder)
    LinearLayout featureListHolder;
    @InjectView(R.id.toolbarFeatureElevation)
    View toolbarFeatureElevation;
    @InjectView(R.id.toolbarTitle)
    TextView toolbarTitle;
    @InjectView(R.id.nestedScrollView)
    NestedScrollView scroller;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    //    @InjectView(R.id.left_drawer)
//    ListView mDrawerList;
    @InjectView(R.id.recyclerViewProvinceShow)
    RecyclerView recyclerViewProvinceShow;
    @InjectView(R.id.recyclerBestHotel)
    RecyclerView recyclerBestHotel;
    @InjectView(R.id.recyclerBestAttraction)
    RecyclerView recyclerBestAttraction;
    @InjectView(R.id.recyclerItinerary)
    RecyclerView recyclerItinerary;
    @InjectView(R.id.recyclerSouvenir)
    RecyclerView recyclerSouvenir;
    @InjectView(R.id.recyclerCity)
    RecyclerView recyclerCity;
    @InjectView(R.id.recyclerLocalFood)
    RecyclerView recyclerLocalFood;
    @InjectView(R.id.viewPagerEventsHolder)
    LinearLayout viewPagerEventsHolder;
    @InjectView(R.id.hotelsTypeHolder)
    RelativeLayout hotelsTypeHolder;
    @InjectView(R.id.tvEventShowAll)
    TextView tvEventShowAll;
    @InjectView(R.id.viewPagerBestHolder)
    RelativeLayout viewPagerBestHolder;
    @InjectView(R.id.attracttionTypeHolder)
    RelativeLayout attracttionTypeHolder;
    @InjectView(R.id.bestAttractionHolder)
    RelativeLayout bestAttractionHolder;
    @InjectView(R.id.itineraryHomeHolder)
    RelativeLayout itineraryHomeHolder;
    @InjectView(R.id.localFoodHomeHolder)
    RelativeLayout localFoodHomeHolder;
    @InjectView(R.id.souvenirHomeHolder)
    RelativeLayout souvenirHomeHolder;
    @InjectView(R.id.TypeHotelHolder)
    RelativeLayout TypeHotelHolder;
    @InjectView(R.id.hotelHolderGrouping)
    RelativeLayout hotelHolderGrouping;
    @InjectView(R.id.hotelTraditionalHolderGrouping)
    RelativeLayout hotelTraditionalHolderGrouping;
    @InjectView(R.id.hotelBoomgardiHolderGrouping)
    RelativeLayout hotelBoomgardiHolderGrouping;
    @InjectView(R.id.hotelَAppartementHolderGrouping)
    RelativeLayout hotelَAppartementHolderGrouping;
    @InjectView(R.id.txtMoreTitleHotel)
    TextView txtMoreTitleHotel;
    @InjectView(R.id.attractionHistoricalHolder)
    RelativeLayout attractionHistoricalHolder;
    @InjectView(R.id.attractionNaturalHolder)
    RelativeLayout attractionNaturalHolder;
    @InjectView(R.id.attractionSportHolder)
    RelativeLayout attractionSportHolder;
    @InjectView(R.id.attractionRelgonHolder)
    RelativeLayout attractionRelgonHolder;
    @InjectView(R.id.toolbarTitleParent)
    LinearLayout toolbarTitleParent;
    @InjectView(R.id.txtEventsTitle)
    TextView tvEventsTitle;
    @InjectView(R.id.overlapImageItineraryHolder)
    RelativeLayout overlapImageItineraryHolder;
    @InjectView(R.id.homeNavAttraction)
    ImageView homeNavAttraction;
    @InjectView(R.id.txtCityrTitle)
    TextView txtCityrTitle;
    @InjectView(R.id.provinceHomeHolder)
    RelativeLayout provinceHomeHolder;

    private String cityName = "city name";


    public HomeFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, rootView);
        toolbarBack.setVisibility(View.GONE);
        appBarLayout.addOnOffsetChangedListener(this);
        if (scroller != null) {
            scroller.setOnScrollChangeListener(this);
        }
        aboutCityBtn.setOnClickListener(this);
        homeNavAttraction.setOnClickListener(this);
        txtMoreTitleAttraction.setOnClickListener(this);
        TypeAttractionHolder.setOnClickListener(this);
        TypeHotelHolder.setOnClickListener(this);
        hotelHolderGrouping.setOnClickListener(this);
        hotelَAppartementHolderGrouping.setOnClickListener(this);
        hotelBoomgardiHolderGrouping.setOnClickListener(this);
        hotelTraditionalHolderGrouping.setOnClickListener(this);

        rootView.findViewById(R.id.homeFragmentWhereToView).setOnClickListener(this);

        attractionHistoricalHolder.setOnClickListener(this);
        attractionNaturalHolder.setOnClickListener(this);
        attractionSportHolder.setOnClickListener(this);
        attractionRelgonHolder.setOnClickListener(this);
        txtMoreTitleHotel.setOnClickListener(this);
        overlapImageItineraryHolder.setOnClickListener(this);

        tvEventShowAll.setOnClickListener(this);

        //home nav views
        rootView.findViewById(R.id.homeNavAttraction).setOnClickListener(this);
        rootView.findViewById(R.id.homeNavPinAttraction).setOnClickListener(this);
        rootView.findViewById(R.id.homeNavFlightAttraction).setOnClickListener(this);
        rootView.findViewById(R.id.homeNavHotelHolder).setOnClickListener(this);

        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_items);
        DaggerHomeComponent.builder().netComponent(((App) getContext().getApplicationContext()).getNetComponent())
                .homeModule(new HomeModule(this, this, this, this, this))
                .build().inject(this);
        setupToolbar();
        getfeatureHolderHight();
        showDrawer();
//        ShowHomeResult("country", "311");
        ShowHomeResult(homeResult);

        //Initializing the navigation layout
        initNav(rootView.findViewById(R.id.nav_layout));

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

    private void initNav(View view) {
        View root = view.findViewById(R.id.nav_layout);

        root.findViewById(R.id.navAboutUsTv).setOnClickListener(navClickListener);
        root.findViewById(R.id.navContactUsTv).setOnClickListener(navClickListener);
        root.findViewById(R.id.navCommentTv).setOnClickListener(navClickListener);
        root.findViewById(R.id.navRecommendTv).setOnClickListener(navClickListener);
        root.findViewById(R.id.navTutorialTv).setOnClickListener(navClickListener);
    }

    private View.OnClickListener navClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.navAboutUsTv:
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(getActivity(), AboutUsActivity.class));
                        }
                    });
                    break;
                case R.id.navContactUsTv:
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(getActivity(), ContactUsActivity.class));
                        }
                    });
                    break;
                case R.id.navCommentTv:

                    break;
                case R.id.navRecommendTv:
                    NavigationFunctionsHelper.getInstance(getActivity()).sendShareIntent();
                    break;
                case R.id.navTutorialTv:
                    startActivity(new Intent(getActivity(), TutorialActivity.class));
                    break;
            }
            hideDrawer();
        }
    };

    public static HomeFragment newInstance(GetHomeResult homeResult) {
        HomeFragment fragment = new HomeFragment();
        fragment.homeResult = homeResult;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        homeResult = (GetHomeResult) args.getSerializable("HomeResult");
        SelectedType="country";
        selectId="311";
    }

    private void getAttractionResults() {
        getAttractionMore("");
    }

    private void getAttractionMore(String type) {
        if ( SelectedType.equals("country") ) {
            openCustomSearchDialog(Constants.homeAttraction);
            frameLayout.setVisibility(View.INVISIBLE);

        } else {
            homePresenter.getAttractionMore("search", "fa", selectId, SelectedType, "0", Util.getTokenFromSharedPreferences(getContext()), Util.getAndroidIdFromSharedPreferences(getContext()), type);

        }
    }

    String offset = "0";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aboutCityBtn:
                showAboutCityOrProvince();
                break;
            case R.id.homeNavAttraction:
                if (SelectedType != null && SelectedType.equals("city"))
                    mainSearchPresenter.loadItineraryFromCity("list", "fa", selectId, "20", offset, selectId, Util.getTokenFromSharedPreferences(getContext()), Util.getAndroidIdFromSharedPreferences(getContext()));
                else if (SelectedType != null && SelectedType.equals("province"))
                    mainSearchPresenter.loadItineraryFromProvince("searchprovince", selectId, offset, Util.getTokenFromSharedPreferences(getContext()), Util.getAndroidIdFromSharedPreferences(getContext()));
                else
                    ((ViewPager) getActivity().findViewById(R.id.main_view_pager)).setCurrentItem(0);
                break;
            case R.id.attractionHistoricalHolder:
                getAttractionMore(Constants.attractionHistoricalCode);
                break;
            case R.id.attractionNaturalHolder:
                getAttractionMore(Constants.attractionNaturalCode);
                break;
            case R.id.attractionSportHolder:
                getAttractionMore(Constants.attractionSortCode);
                break;
            case R.id.attractionRelgonHolder:
                getAttractionMore(Constants.attractionRelegonCode);
                break;
            case R.id.txtMoreTitleAttraction:
                getAttractionResults();
                break;
            case R.id.TypeAttractionHolder:

            case R.id.homeNavPinAttraction:
                getAttractionResults();
                break;
            case R.id.hotelHolderGrouping:
                getHotelResults(SelectedType, selectId, Constants.hotelCode);
                break;
            case R.id.hotelTraditionalHolderGrouping:
                getHotelResults(SelectedType, selectId, Constants.hotelTraditionalCode);
                break;
            case R.id.hotelBoomgardiHolderGrouping:
                getHotelResults(SelectedType, selectId, Constants.hotelboomgardiCode);
                break;
            case R.id.hotelَAppartementHolderGrouping:
                getHotelResults(SelectedType, selectId, Constants.hotelApartmentCode);
                break;
            case R.id.txtMoreTitleHotel:
                getHotelResults(SelectedType, selectId, "");
                break;
            case R.id.TypeHotelHolder:
            case R.id.homeNavHotelHolder:
                if (SelectedType != null && SelectedType.equals("city") && selectId != null) {
//                    reservationPresenter.getLodgingList("list", selectId,  Util.getTokenFromSharedPreferences(getContext()), "20","0", Util.getAndroidIdFromSharedPreferences(getContext()),"");
                    getHotelResults(SelectedType, selectId, "");

                } else {
                    openCustomSearchDialog(Constants.homeHotel);
                    frameLayout.setVisibility(View.INVISIBLE);
                }

                break;
            case R.id.homeFragmentWhereToView:
                openCustomSearchDialog(Constants.homeSearch);
                frameLayout.setVisibility(View.INVISIBLE);
                break;
            case R.id.overlapImageItineraryHolder:

                if (SelectedType != null && SelectedType.equals("city")) {
                    Log.e("itinerary", "city clicked");
                    mainSearchPresenter.loadItineraryFromCity("list", "fa", selectId, "20", offset, selectId, Util.getTokenFromSharedPreferences(getContext()), Util.getAndroidIdFromSharedPreferences(getContext()));
                } else if (SelectedType != null && SelectedType.equals("province")) {
                    mainSearchPresenter.loadItineraryFromProvince("searchprovince", selectId, offset, Util.getTokenFromSharedPreferences(getContext()), Util.getAndroidIdFromSharedPreferences(getContext()));

                } else {
                    ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.main_view_pager);
                    viewPager.setCurrentItem(0);

                }
                break;
            case R.id.tvEventShowAll:
                //        https://api.parsdid.com/iranplanner/app/api-event.php?action=list&lang=fa&id=342&type=city

                homePresenter.getEventMore("list", "fa", selectId, SelectedType, Util.getTokenFromSharedPreferences(getContext()), Util.getAndroidIdFromSharedPreferences(getContext()));

                break;

        }
    }

    private void openCustomSearchDialog(String type) {
        CustomDialogSearchLocation customDialogSearchLocation = new CustomDialogSearchLocation(getActivity(), type);
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
        /*Bundle bundle = new Bundle();
        bundle.putSerializable("homeInfo", resultHomes.get(0).getHomeInfo());

        bundle.putSerializable("url", resultHomes.get(0).getHomeImages().get(0).getImgUrl());
        AboutCityFragment aboutCityFragment = AboutCityFragment.newInstance();
        aboutCityFragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.containerHomeFragment, aboutCityFragment);
        transaction.addToBackStack(null);
        transaction.commit();*/

        AboutCityDialog dialog = new AboutCityDialog(getActivity());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.show();

        dialog.setTitle(resultHomes.get(0).getHomeInfo().getTitle());
        dialog.setImageResource(resultHomes.get(0).getHomeImages().get(0).getImgUrl());
        dialog.setDesc(resultHomes.get(0).getHomeInfo().getBody());

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
            toolbarTitleParent.setVisibility(View.VISIBLE);
        } else if (verticalOffset > (-1) * appBarLayout.getTotalScrollRange() + toolbarHeight) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.transparent));
            toolbarTitleParent.setVisibility(View.INVISIBLE);
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
        List<ResultLodging> resultLodgings = resultLodgingList.getResultLodging();
        Intent intent = new Intent(getContext(), ReservationHotelListActivity.class);
        intent.putExtra("resultLodgings", (Serializable) resultLodgings);
        long time = System.currentTimeMillis();
        Date startOfTravel = new Date(time);
        intent.putExtra("nextOffset", resultLodgingList.getStatistics().getOffsetNext().toString());

        intent.putExtra("startOfTravel", startOfTravel);
        intent.putExtra("durationTravel", 3);
        intent.putExtra("todayDate", resultLodgingList.getStatistics().getDateNow());
        intent.putExtra("cityName", cityName);

        startActivity(intent);
    }

    @Override
    public void showComments(ResultCommentList resultCommentList) {

    }

    @Override
    public void sendCommentMessage(ResultCommentList resultCommentList) {

    }

    @Override
    public void showHotelReserveList(ResultLodgingHotel resultLodgingHotel) {
        if (resultLodgingHotel != null) {
            ResultLodging resultLodgingHotelDetail = resultLodgingHotel.getResultLodging();
            Intent intent = new Intent(getContext(), ReservationHotelDetailActivity.class);
            intent.putExtra("resultLodgingHotelDetail", (Serializable) resultLodgingHotelDetail);
            Date todayDate = new Date();

            intent.putExtra("startOfTravel", todayDate);
            intent.putExtra("durationTravel", Constants.durationTravel);
            intent.putExtra("todayDate", todayDate);
            intent.putExtra("cityName", cityName);
            startActivity(intent);
        }
    }

    @Override
    public void showItineraries(ResultItineraryList resultItineraryList, String typeOfSearch) {

        List<ResultItinerary> data = resultItineraryList.getResultItinerary();

        Intent intent = new Intent(getActivity(), ItineraryListFragment.class);
        intent.putExtra("resuliItineraryList", (Serializable) data);
        intent.putExtra("fromWhere", typeOfSearch);
        intent.putExtra("nextOffset", resultItineraryList.getStatistics().getOffsetNext().toString());
        intent.putExtra("provinceId", "");
        intent.putExtra("attractionId", "");
        if (typeOfSearch.equals("fromCityToCity")) {
            intent.putExtra("endCity", "");
        } else {
            intent.putExtra("endCity", "");
        }

        startActivity(intent);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), "مقصد مورد نظر یافت نشد", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void commentResult(String message) {

    }

    @Override
    public void showComplete() {

    }

    @Override
    public void onResume() {
        Log.e("backpress","true");
        super.onResume();

    }

    @Override
    public void ShowHomeResult(GetHomeResult getHomeResult) {
        if(SelectedType.equals("country")){
            txtCityrTitle.setText("مقاصد محبوب");
        }else if(SelectedType.equals("province")){
            txtCityrTitle.setText("شهرهای استان");
        }else if(SelectedType.equals("city")){
            txtCityrTitle.setText("شهرهای مجاور");
        }
        b = true;

        selectId=getHomeResult.getResultHome().get(0).getHomeInfo().getId();
        SelectedType=getHomeResult.getResultHome().get(0).getHomeInfo().getType();

        resultHomes = getHomeResult.getResultHome();
        toolbarTitleSetName(resultHomes.get(0).getHomeInfo().getTitle(), resultHomes.get(0).getHomeInfo().getId());
        setNamePicture(resultHomes.get(0).getHomeInfo(), resultHomes.get(0).getHomeImages());
        if (resultHomes.get(0).getHomeLodging().size() != 0) {
            hotelsTypeHolder.setVisibility(View.VISIBLE);
            viewPagerBestHolder.setVisibility(View.VISIBLE);
            setViewPagerLodging(resultHomes.get(0).getHomeLodging());
        } else {
            hotelsTypeHolder.setVisibility(View.GONE);
            viewPagerBestHolder.setVisibility(View.GONE);

        }
        if (resultHomes.get(0).getHomeAttraction().size() != 0) {
            attracttionTypeHolder.setVisibility(View.VISIBLE);
            bestAttractionHolder.setVisibility(View.VISIBLE);
            setViewPagerAttraction(resultHomes.get(0).getHomeAttraction());
        } else {
            attracttionTypeHolder.setVisibility(View.GONE);
            bestAttractionHolder.setVisibility(View.GONE);
        }


        if (resultHomes.get(0).getHomeCountryProvince().size() != 0) {
            setListProvince(resultHomes.get(0).getHomeCountryProvince());
        } else {
            provinceHomeHolder.setVisibility(View.GONE);

        }
        if (resultHomes.get(0).getHomeSouvenirs().size() != 0) {
            souvenirHomeHolder.setVisibility(View.VISIBLE);
            setSouvenir(resultHomes.get(0).getHomeSouvenirs());
        } else {
            souvenirHomeHolder.setVisibility(View.GONE);

        }
        if (resultHomes.get(0).getHomeItinerary().size() != 0) {
            itineraryHomeHolder.setVisibility(View.VISIBLE);
            setItinerary(resultHomes.get(0).getHomeItinerary());
        } else {
            itineraryHomeHolder.setVisibility(View.GONE);

        }
        if (resultHomes.get(0).getHomeLocalfood().size() != 0) {
            localFoodHomeHolder.setVisibility(View.VISIBLE);
            setLocalFood(resultHomes.get(0).getHomeLocalfood());
        } else {
            localFoodHomeHolder.setVisibility(View.GONE);

        }
        if (resultHomes.get(0).getHomeEvent().size() != 0) {
            viewPagerEventsHolder.setVisibility(View.VISIBLE);
            setViewPagerEvent(resultHomes.get(0).getHomeEvent());
        } else {
            viewPagerEventsHolder.setVisibility(View.GONE);

        }
        homeNeighborCity = resultHomes.get(0).getHomeNeighborCity();
        if (resultHomes.get(0).getHomeNeighborCity().size() != 0) {
            recyclerCity.setVisibility(View.VISIBLE);
            setViewPagerNeighborCity();
        } else {
            viewPagerEventsHolder.setVisibility(View.GONE);

        }
    }

    @Override
    public void ShowAttractionLists(ShowAttractionListMore getAttractionList) {
        List<ResultAttractionList> resultLodgings = getAttractionList.getResultAttractionList();
        Intent intentA = new Intent(getContext(), ShowAttractionListMoreActivity.class);
        intentA.putExtra("attractionsList", (Serializable) resultLodgings);
        intentA.putExtra("nextOffset", getAttractionList.getStatistics().getOffsetNext());
        startActivity(intentA);
    }

    @Override
    public void ShowEventLists(ResultEvents resultEvents) {
        List<ResultEvent> resultEvnt = resultEvents.getResultEvent();
        Intent intent = new Intent(getActivity(), EventListActivity.class);
        intent.putExtra("ResultEvent", (Serializable) resultEvnt);
        startActivity(intent);

    }

    @Override
    public void ShowEventDetail(ResultEvents resultEvent) {
        Log.e("get","eventDetail");
        Intent intent = new Intent(getContext(), EventActivity.class);
        intent.putExtra("ResultEvent", (Serializable) resultEvent.getResultEvent().get(0));
        startActivity(intent);
    }

    private void setViewPagerEvent(final List<HomeEvent> homeEvents) {
        ShowHomeEventAdapter showHomeEventAdapter = new ShowHomeEventAdapter(getContext(), getActivity(), homeEvents);
        eventsViewPager.setAdapter(showHomeEventAdapter);
        indicator.setViewPager(eventsViewPager);
        eventsViewPager.setCurrentItem(homeEvents.size()-1);
        eventsViewPager.setOnItemClickListener(new ClickableViewPager.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("event","clicked");
                homePresenter.getEventDetail("full","fa",homeEvents.get(position).getEventId(),Util.getTokenFromSharedPreferences(getContext()),Util.getAndroidIdFromSharedPreferences(getContext()));
            }
        });
    }

    boolean b = true;

    private void setViewPagerNeighborCity() {
        showNeiborCityHomeAdapter attractionHomeAdapter = new showNeiborCityHomeAdapter(getActivity(), this, homeNeighborCity, getContext(), R.layout.content_home_best_hotel_show);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        recyclerCity.setLayoutManager(horizontalLayoutManagaer);
        attractionHomeAdapter.notifyDataSetChanged();
        recyclerCity.setAdapter(attractionHomeAdapter);
        recyclerCity.invalidate();
        recyclerCity.addOnItemTouchListener(new RecyclerItemOnClickListener(getContext(), new RecyclerItemOnClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, final int position) {
                if (b) {

                    getHomeResult("city", homeNeighborCity.get(position).getItemId());
                    b = false;
                }
            }
        }));
    }

    private void setListProvince(final List<HomeCountryProvince> homeCountryProvince) {
        recyclerViewProvinceShow.setLayoutManager(new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false));
        HomeProvinceListAdapter homeProvinceAdapter = new HomeProvinceListAdapter(getActivity(), this, homeCountryProvince, getContext(), R.layout.content_province_list);
        recyclerViewProvinceShow.setAdapter(homeProvinceAdapter);
        recyclerViewProvinceShow.setNestedScrollingEnabled(false);
        recyclerViewProvinceShow.addOnItemTouchListener(new RecyclerItemOnClickListener(getContext(), new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                selectId = homeCountryProvince.get(position).getProvinceId();
                SelectedType = "province";
                getHomeResult("province", homeCountryProvince.get(position).getProvinceId());
            }
        }));
    }

    private void setItinerary(final List<HomeItinerary> homeItineraries) {

        HomeItineraryAdapter homeItineraryAdapter = new HomeItineraryAdapter(homeItineraries, getContext());
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerItinerary.setLayoutManager(horizontalLayoutManagaer);
        recyclerItinerary.setAdapter(homeItineraryAdapter);
        recyclerItinerary.addOnItemTouchListener(new RecyclerItemOnClickListener(getContext(), new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
//                https://api.parsdid.com/iranplanner/app/api-itinerary.php?action=full&id=28439&lang=fa
                homePresenter.getItineraryDetail("full",homeItineraries.get(position).getItineraryId(),"fa",Util.getTokenFromSharedPreferences(getContext()),Util.getAndroidIdFromSharedPreferences(getContext()));
//                String cityid = homeItineraries.get(position).getItineraryId();
//                String name = Util.getUseRIdFromShareprefrence(getContext());
//                Intent intent = new Intent(getActivity(), MoreItemItineraryActivity.class);
//                intent.putExtra("itineraryData", (Serializable) homeItineraries.get(position));
//                intent.putExtra("duration", homeItineraries.get(position).getItineraryDurationTitle());
//                startActivity(intent);
//                ("full", String.valueOf(homeLodgings.get(position).getLodgingId()), "20", "0", Util.getTokenFromSharedPreferences(getContext()), Util.getAndroidIdFromSharedPreferences(getContext()));
            }
        }));
    }

    private void setViewPagerLodging(final List<HomeLodging> homeLodgings) {
        HomeReservationHotelAdapter horizontalAdapter = new HomeReservationHotelAdapter(getActivity(), this, homeLodgings, getContext(), R.layout.content_home_best_hotel_show);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerBestHotel.setLayoutManager(horizontalLayoutManagaer);
        recyclerBestHotel.setAdapter(horizontalAdapter);
        recyclerBestHotel.addOnItemTouchListener(new RecyclerItemOnClickListener(getContext(), new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                reservationHotelListPresenter.getHotelReserve("full", String.valueOf(homeLodgings.get(position).getLodgingId()), "20", "0", Util.getTokenFromSharedPreferences(getContext()), Util.getAndroidIdFromSharedPreferences(getContext()));
            }
        }));
    }

    private void setViewPagerAttraction(final List<HomeAttraction> homeAttractions) {
        AttractionHomeAdapter attractionHomeAdapter = new AttractionHomeAdapter(homeAttractions, getContext());
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerBestAttraction.setLayoutManager(horizontalLayoutManagaer);
        recyclerBestAttraction.setAdapter(attractionHomeAdapter);
        recyclerBestAttraction.addOnItemTouchListener(new RecyclerItemOnClickListener(getContext(), new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                attractionListMorePresenter.getAttractionDetailNear("full", homeAttractions.get(position).getAttractionId(), "fa", "0", Util.getTokenFromSharedPreferences(getContext()), Util.getAndroidIdFromSharedPreferences(getContext()));
            }
        }));
    }

    private void setSouvenir(List<HomeSouvenir> homeSouvenir) {
        souvenirHomeAdapter attractionHomeAdapter = new souvenirHomeAdapter(homeSouvenir, getContext());
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerSouvenir.setLayoutManager(horizontalLayoutManagaer);
        recyclerSouvenir.setAdapter(attractionHomeAdapter);

    }

    private void setLocalFood(List<HomeLocalfood> homeLocalfood) {
        LocalFoodHomeAdapter attractionHomeAdapter = new LocalFoodHomeAdapter(homeLocalfood, getContext());
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerLocalFood.setLayoutManager(horizontalLayoutManagaer);
        recyclerLocalFood.setAdapter(attractionHomeAdapter);
    }

    private void setNamePicture(HomeInfo homeInfo, List<HomeImage> homeImage) {
        if (!homeInfo.getId().equals("311")) {
            txtWhereGo.setText(homeInfo.getTitle());
        }

        if (homeImage.get(0).getImgUrl() != null) {
            Util.setImageView(homeImage.get(0).getImgUrl(), getContext(), imgHome, null);
        }

    }

    @Override
    public void showProgress() {
        progressDialog = Util.showProgressDialog(getContext(), "لطفا منتظر بمانید", getActivity());
    }

    @Override
    public void showLodgingList(ResultLodgingList resultLodgingList, String filter) {

    }

    @Override
    public void dismissProgress() {
        Util.dismissProgress(progressDialog);
    }

    @Override
    public void ShowItineryDetail(ResultItineraryList resultItineraryList) {
        Intent intent = new Intent(getActivity(), MoreItemItineraryActivity.class);
        intent.putExtra("itineraryData", (Serializable) resultItineraryList.getResultItinerary().get(0));
        intent.putExtra("duration", "");
        startActivity(intent);
    }

    @Override
    public void ShowAttractionLists(ShowAttractionMoreList showAttractionList) {

    }

    @Override
    public void showAttractionDetail(ShowAtractionDetailMore showAttractionFull) {
        ResulAttraction resulAttraction = showAttractionFull.getResultAttractionFull().getResulAttraction();
        List<ResultAttractionList> resultAttractions = (List<ResultAttractionList>) showAttractionFull.getResultAttractionFull().getResultAttractionList();
        Intent intent = new Intent(getActivity(), attractionDetailActivity.class);
        intent.putExtra("resulAttraction", (Serializable) resulAttraction);
        intent.putExtra("resultAttractionList", (Serializable) resultAttractions);
        startActivity(intent);
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
        String type;

        public CustomDialogSearchLocation(Activity a, String type) {
            super(a);
            this.c = a;
            this.type = type;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

            setContentView(R.layout.dialog_home_search_location);
            AutoCompleteTextView autoTextWhere = (AutoCompleteTextView) findViewById(R.id.autoTextWhere);
//            listd = (ListView) findViewById(R.id.listd);
            no = (TextView) findViewById(R.id.txtNo);
            no.setOnClickListener(this);
            /*tempCityProvince =*/
            autoCompleteProvince(autoTextWhere, type);

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


        public void autoCompleteProvince(AutoCompleteTextView textProvience, final String type) {
            ReadJsonCityProvince readJsonProvince = new ReadJsonCityProvince();
            CityProvince = readJsonProvince.getListOfCityProvience(getContext());
            MyFilterableAdapterCityProvince adapter = new MyFilterableAdapterCityProvince(getContext(), android.R.layout.simple_list_item_1, CityProvince);
            textProvience.setAdapter(adapter);
            textProvience.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


//                    toolbarTitleSetName(CityProvince.get(position).getTitle(),"");
                    if (type == Constants.homeSearch) {
                        selectId = CityProvince.get(position).getId();
                        SelectedType = CityProvince.get(position).getType();
                        getHomeResult(SelectedType, CityProvince.get(position).getId());
                        cityName = CityProvince.get(position).getTitle();
                        dismiss();
                    } else if (type == Constants.homeHotel) {
                        cityName = CityProvince.get(position).getTitle();
                        getHotelResults(CityProvince.get(position).getType(), CityProvince.get(position).getId(), "");
                        dismiss();
                    }
                    else if (type == Constants.homeAttraction) {
                        cityName = CityProvince.get(position).getTitle();
                        homePresenter.getAttractionMore("search", "fa", CityProvince.get(position).getId(), CityProvince.get(position).getType(), "0", Util.getTokenFromSharedPreferences(getContext()), Util.getAndroidIdFromSharedPreferences(getContext()), "");
//                        homePresenter.getAttractionMore("search", "fa", selectId, SelectedType, "0", Util.getTokenFromSharedPreferences(getContext()), Util.getAndroidIdFromSharedPreferences(getContext()), type);

                        dismiss();
                    }
                }

            });
        }

    }

    @Override
    public boolean onBackPressed() {
        return super.onBackPressed();
    }

    private void getHotelResults(String destination, String selectId, String typeOfHotel) {
        if (destination != null && destination.equals("city")) {
            reservationPresenter.getLodgingList("list", selectId, Util.getTokenFromSharedPreferences(getContext()), "20", "0", Util.getAndroidIdFromSharedPreferences(getContext()), typeOfHotel);
        } else {
            Toast.makeText(getContext(), "شهر انتخاب شود", Toast.LENGTH_LONG).show();
        }
    }

    private void getHomeResult(String destination, String selectId) {

        String cid = Util.getTokenFromSharedPreferences(getContext());
        String andId = Util.getAndroidIdFromSharedPreferences(getContext());
        homePresenter.getHome("home", destination, selectId, cid, andId);

    }

    private void toolbarTitleSetName(String name, String homeInfoId) {
        if (!homeInfoId.equals("311")) {
            toolbarTitle.setText(name);
        }
    }

    void setupToolbar() {
        ((StandardActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setBackground(getResources().getDrawable(R.drawable.dark_shadow_top));

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

    private void hideDrawer() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        }, 1000);
    }

    private void showDrawer() {
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);

        toolbarTitleParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCustomSearchDialog(Constants.homeSearch);
            }
        });

        toolbarToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        buildVersion = Build.VERSION.SDK_INT;

        Log.i("Build Version : ", String.valueOf(buildVersion));


    }
}
