package com.iranplanner.tourism.iranplanner.ui.fragment.itinerarySearch;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.iranplanner.tourism.iranplanner.R;

import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.standard.StandardFragment;
import com.iranplanner.tourism.iranplanner.ui.fragment.OnVisibleShowCaseViewListener;
import com.iranplanner.tourism.iranplanner.ui.fragment.itineraryList.ItineraryListFragment;
import com.ramotion.foldingcell.FoldingCell;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import autoComplet.MyFilterableAdapterAttraction;
import autoComplet.MyFilterableAdapterCity;
import autoComplet.MyFilterableAdapterProvince;
import autoComplet.ReadJsonAttraction;
import autoComplet.ReadJsonProvince;
import autoComplet.readJsonCity;
import entity.Attraction;
import entity.City;
import entity.Province;
import entity.ResultItinerary;
import entity.ResultItineraryList;
import tools.Util;


public class MainSearchFragment extends StandardFragment implements MainSearchContract.View, View.OnClickListener/*, Callback<ResultItineraryList>*/ {
    public static final String TAG_ITINERARY = "ioigbs";
    @Inject
    MainSearchPresenter mainPresenter;
    TextView txtitinerary_name_city_city,
            city_name,
            itinerary_name,
            itinerary_name_attraction;
    FoldingCell /*fcProvince, *//*folding_cell_City_City,*/ folding_cell_city,folding_cell_City_City/*, folding_cell_attraction*/;
    List<Province> provinces;
    String provinceName;
    String provinceId = null;
    ProgressDialog progressDialog;
    List<Province> tempProvince;
    List<City> tempCity1, tempCity2, tempcity;
    AutoCompleteTextView fromCity_city, endCity_city, fromCity/* fromCity_attraction,*/ /*endAttraction*/;
    List<City> cities;
    Button searchOk_city_city, searchOk_city/* searchOk_attraction*/;
    String cityCityFrom, cityEnd, cityFrom, cityFromAttraction;
    boolean checkfragment = false;
    LinearLayout city_city_layout, city_layout, /*province_layout, */
            events_layout;
    private final static String TAG_FRAGMENT = "TAG_FRAGMENT";
    ItineraryListFragment itineraryListFragment;
    List<Attraction> attractions;
    List<City> tempAttractionCity;
    List<Attraction> tempAttraction;
    String attractionEnd;
    Button mmActivity;
    FrameLayout cell_title_view_theme, cell_title_view_events;
    FrameLayout cell_title_view_city_city;

    public static MainSearchFragment newInstance(OnVisibleShowCaseViewListener onVisibleShowCaseViewListener) {
        MainSearchFragment fragment = new MainSearchFragment();
        fragment.onVisibleShowCaseViewListener = onVisibleShowCaseViewListener;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        View view = inflater.inflate(R.layout.search_main_fragment, container, false);
        city_city_layout = (LinearLayout) view.findViewById(R.id.city_city_layout);
        city_layout = (LinearLayout) view.findViewById(R.id.city_layout);

        txtitinerary_name_city_city = view.findViewById(R.id.txtitinerary_name_city_city);
        city_name = view.findViewById(R.id.city_name);
//        itinerary_name = view.findViewById(R.id.itinerary_name);

        fromCity_city = (AutoCompleteTextView) view.findViewById(R.id.fromCity_city);
        endCity_city = (AutoCompleteTextView) view.findViewById(R.id.endCity_city);
        searchOk_city_city = (Button) view.findViewById(R.id.searchOk_city_city);
        cell_title_view_city_city = view.findViewById(R.id.cell_title_view_city_city);
        fromCity_city.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        tempCity1 = autoCompleteCity(fromCity_city);
        tempCity2 = autoCompleteCity(endCity_city);
        folding_cell_City_City = (FoldingCell) view.findViewById(R.id.folding_cell_City_City);
        // -------------attach click listener to folding cell
        cell_title_view_city_city.setOnClickListener(this);
        searchOk_city_city.setOnClickListener(this);

        fromCity = (AutoCompleteTextView) view.findViewById(R.id.fromCity);
        searchOk_city = (Button) view.findViewById(R.id.searchOk_city);
        tempcity = autoCompleteCity(fromCity);
        folding_cell_city = (FoldingCell) view.findViewById(R.id.folding_cell_city);

        // -------------attach click listener to folding cell
        folding_cell_city.setOnClickListener(this);
        searchOk_city.setOnClickListener(this);
        folding_cell_City_City.setOnClickListener(this);

        return view;
    }

    /*----Method to Check GPS is enable or disable ----- */
    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getContext()
                .getContentResolver();
        boolean gpsStatus = Settings.Secure
                .isLocationProviderEnabled(contentResolver,
                        LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;

        } else {
            return false;
        }
    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("GPS شما فعال نیست. آیا تمایل به روشن کردن آن دارید")
                .setCancelable(false)
                .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });

        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onClick(View v) {

        DaggerMainScreenComponent.builder()
                .netComponent(((App) getActivity().getApplicationContext()).getNetComponent())
                .mainSearchnModule(new MainSearchnModule(this))
                .build().injectionMainSearchFragment(this);

        switch (v.getId()) {

            case R.id.cell_title_view_city_city:
                folding_cell_City_City.toggle(false);
                folding_cell_city.fold(false);
//                CustomDialogSearchItinerary customDialogSearchItinerary = new CustomDialogSearchItinerary(getActivity(), R.layout.dialog_main_search_itinerary_2);
//                customDialogSearchItinerary.show();
//                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//                lp.copyFrom(customDialogSearchItinerary.getWindow().getAttributes());
//                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                customDialogSearchItinerary.show();
//                customDialogSearchItinerary.getWindow().setAttributes(lp);
//                customDialogSearchItinerary.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                break;
            case R.id.folding_cell_City_City:
                folding_cell_City_City.toggle(false);
                folding_cell_city.fold(false);
            case R.id.searchOk_attraction:
                hideKeyBoard();
                showAttraction();
                break;

            case R.id.searchOk_city:
                hideKeyBoard();
                showCity();
                break;

            case R.id.folding_cell_city:
                folding_cell_city.toggle(false);
                folding_cell_City_City.fold(false);
                break;

            case R.id.searchOk_city_city:
                hideKeyBoard();
                showCityCity();
                break;

            case R.id.searchOk_provience:
                hideKeyBoard();
                showProvience();
                break;
//            case R.id.cell_title_view_theme:
//                Toast.makeText(getContext(), "به زودی ", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.cell_title_view_events:
//                Toast.makeText(getContext(), "به زودی ", Toast.LENGTH_SHORT).show();
//                break;
        }
    }

    @Override
    public void showError(String message) {

        if (message.contains("Unable to resolve host ") || message.contains("Software caused connection abort")) {
            Toast.makeText(getContext(), "عدم دسترسی به اینترنت", Toast.LENGTH_LONG).show();
        }
        if (message.contains("HTTP 400 BAD REQUEST")) {
            Toast.makeText(getContext(), "در این مسیر برنامه سفری یافت نشد", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void showComplete() {

    }

    @Override
    public void showProgress() {
        progressDialog = Util.showProgressDialog(getContext(), "لطفا منتظر بمانید", getActivity());
    }

    @Override
    public void dismissProgress() {
        Util.dismissProgress(progressDialog);
    }

    public class GPSTracker extends Service implements LocationListener {

        private final Context mContext;

        // Flag for GPS status
        boolean isGPSEnabled = false;

        // Flag for network status
        boolean isNetworkEnabled = false;

        // Flag for GPS status
        boolean canGetLocation = false;

        Location location; // Location

        double latitude; // Latitude
        double longitude; // Longitude
        // The minimum distance to change Updates in meters
        private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

        // The minimum time between updates in milliseconds
        private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

        // Declaring a Location Manager
        protected LocationManager locationManager;

        public GPSTracker(Context context) {
            this.mContext = context;
            getLocation();
        }

        public Location getLocation() {
            try {
                locationManager = (LocationManager) mContext
                        .getSystemService(LOCATION_SERVICE);

                // Getting GPS status
                isGPSEnabled = locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);

                // Getting network status
                isNetworkEnabled = locationManager
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (!isGPSEnabled && !isNetworkEnabled) {
                    // No network provider is enabled
                } else {
                    this.canGetLocation = true;
                    if (isNetworkEnabled) {
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return null;
                        }
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("Network", "Network");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                    // If GPS enabled, get latitude/longitude using GPS Services
                    if (isGPSEnabled) {
                        if (location == null) {
                            locationManager.requestLocationUpdates(
                                    LocationManager.GPS_PROVIDER,
                                    MIN_TIME_BW_UPDATES,
                                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                            Log.d("GPS Enabled", "GPS Enabled");
                            if (locationManager != null) {
                                location = locationManager
                                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (location != null) {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return location;
        }

        /**
         * Stop using GPS listener
         * Calling this function will stop using GPS in your app.
         */
        public void stopUsingGPS() {
            if (locationManager != null) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.removeUpdates(GPSTracker.this);
            }
        }


        /**
         * Function to get latitude
         */
        public double getLatitude() {
            if (location != null) {
                latitude = location.getLatitude();
            }

            // return latitude
            return latitude;
        }


        /**
         * Function to get longitude
         */
        public double getLongitude() {
            if (location != null) {
                longitude = location.getLongitude();
            }

            // return longitude
            return longitude;
        }


        /**
         * Function to check GPS/Wi-Fi enabled
         *
         * @return boolean
         */
        public boolean canGetLocation() {
            return this.canGetLocation;
        }

        /**
         * Function to show settings alert dialog.
         * On pressing the Settings button it will launch Settings Options.
         */
        public void showSettingsAlert() {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

            // Setting Dialog Title
            alertDialog.setTitle("GPS is settings");

            // Setting Dialog Message
            alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

            // On pressing the Settings button.
            alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    mContext.startActivity(intent);
                }
            });

            // On pressing the cancel button
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            // Showing Alert Message
            alertDialog.show();
        }


        @Override
        public void onLocationChanged(Location location) {
        }


        @Override
        public void onProviderDisabled(String provider) {
        }


        @Override
        public void onProviderEnabled(String provider) {
        }


        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }


        @Override
        public IBinder onBind(Intent arg0) {
            return null;
        }


    }

    //province method
    public List<Province> autoCompleteProvince(AutoCompleteTextView textProvience) {
        ReadJsonProvince readJsonProvince = new ReadJsonProvince();
        provinces = readJsonProvince.getListOfProvience(getContext());
        MyFilterableAdapterProvince adapter = new MyFilterableAdapterProvince(getContext(), android.R.layout.simple_list_item_1, provinces);
        textProvience.setAdapter(adapter);
        return provinces;
    }

//    private void showProgressDialog() {
//        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("لطفا منتظر بمانید");
//        progressDialog.show();
//        progressDialog.setCancelable(false);
//        progressDialog.setCanceledOnTouchOutside(false);
//    }

    public String returnProvinceId(AutoCompleteTextView textView, List<Province> provinceList) {

        for (Province p : provinceList) {
            Log.e("Province", p.getProvinceName());
            if (p.getProvinceName().equals(textView.getText().toString())) {
                provinceId = p.getProvinceId();
//                showProgressDialog();
            }
        }
        return provinceId;
    }

    //-------------------city_city
    public List<City> autoCompleteCity(AutoCompleteTextView city) {
        readJsonCity readJsonCity = new readJsonCity();
        cities = readJsonCity.getListOfCity(getContext());
        MyFilterableAdapterCity adapter = new MyFilterableAdapterCity(getContext(), android.R.layout.simple_list_item_1, cities);
        city.setAdapter(adapter);
        return cities;
    }

    public String returnCityId(AutoCompleteTextView textView, List<City> cityList) {
        String cityId = null;
        for (City c : cityList) {
            Log.e("city", c.getCityName());
            if (c.getCityName().equals(textView.getText().toString())) {
                cityId = c.getCityId();
            }
        }
        return cityId;
    }

    public List<Attraction> autoCompleteAttraction(AutoCompleteTextView city) {
        ReadJsonAttraction readJsonAttraction = new ReadJsonAttraction();
        attractions = readJsonAttraction.getListOfAttractin(getContext());
        MyFilterableAdapterAttraction adapter = new MyFilterableAdapterAttraction(getContext(), android.R.layout.simple_list_item_1, attractions);
        city.setAdapter(adapter);
        return attractions;
    }

    public String returnAttractionId(AutoCompleteTextView textView, List<Attraction> attractionList) {
        String attractionId = null;
        for (Attraction attraction : attractionList) {
            Log.e("city", attraction.getAttractionName());
            if (attraction.getAttractionName().equals(textView.getText().toString())) {
                attractionId = attraction.getAttractionId();
            }
        }
        return attractionId;
    }

    @Override
    public boolean onBackPressed() {

        return super.onBackPressed();
    }


    @Override
    public void showItineraries(ResultItineraryList resultItineraryList, String typeOfSearch) {
        List<ResultItinerary> data = resultItineraryList.getResultItinerary();
        itineraryListFragment = new ItineraryListFragment();
        Intent intent = new Intent(getActivity(), ItineraryListFragment.class);
        intent.putExtra("resuliItineraryList", (Serializable) data);
        intent.putExtra("fromWhere", typeOfSearch);
        intent.putExtra("nextOffset", resultItineraryList.getStatistics().getOffsetNext().toString());
        intent.putExtra("provinceId", provinceId);
        intent.putExtra("attractionId", attractionEnd);
        if (typeOfSearch.equals("fromCityToCity")) {
            intent.putExtra("endCity", cityEnd);
        } else {
            intent.putExtra("endCity", "");
        }

        startActivity(intent);
        cityEnd = "";
        checkfragment = true;
//        progressDialog.dismiss();
    }


    //---------------------------
    private void showAttraction() {
//        folding_cell_attraction.fold(false);
//        cityFromAttraction = returnCityId(fromCity_attraction, tempAttractionCity);
//        attractionEnd = returnAttractionId(endAttraction, tempAttraction);
        if (cityFromAttraction != null && attractionEnd != null) {
            String offset = "0";
            mainPresenter.loadItineraryFromAttraction("searchattractioncity", "fa", cityFromAttraction, "10", offset, attractionEnd, Util.getTokenFromSharedPreferences(getContext()), Util.getAndroidIdFromSharedPreferences(getContext()));
//            showProgressDialog();
//        } else {
//            Toast.makeText(getActivity(), "نام شهر یا جاذبه ثبت نشده است", Toast.LENGTH_SHORT).show();
        }
        Log.d("search ok clicked", "true");
    }

    private void showCity() {
        folding_cell_city.fold(false);
        cityFrom = returnCityId(fromCity, tempcity);
        cityEnd = cityFrom;
        if (cityFrom != null) {
            String offset = "0";
            mainPresenter.loadItineraryFromCity("list", "fa", cityFrom, "20", offset, cityEnd, Util.getTokenFromSharedPreferences(getContext()), Util.getAndroidIdFromSharedPreferences(getContext()));
//            showProgressDialog();
        } else {
            Toast.makeText(getActivity(), "لطفا نام شهر را اصلاح کنید", Toast.LENGTH_SHORT).show();
        }
        Log.d("search ok clicked", "true");
    }

    private void showCityCity() {
//        folding_cell_City_City.fold(false);

        cityCityFrom = returnCityId(fromCity_city, tempCity1);
        cityEnd = returnCityId(endCity_city, tempCity2);
        if (endCity_city.getText() == null) {
            cityEnd = "";
        }
        if (cityCityFrom != null) {
//                  getItinerary(cityCityFrom, "0", false, cityEnd);
            String offset = "0";
            mainPresenter.loadItineraryFromCity("list", "fa", cityCityFrom, "20", offset, cityEnd, Util.getTokenFromSharedPreferences(getContext()), Util.getAndroidIdFromSharedPreferences(getContext()));
//            showProgressDialog();

        } else {
            Toast.makeText(getActivity(), "لطفا نام شهر را اصلاح کنید", Toast.LENGTH_SHORT).show();
        }
        Log.d("search ok clicked", "true");
    }

    private void showProvience() {
//        fcProvince.fold(false);
//        provinceName = returnProvinceId(textProvience, tempProvince);
        if (provinceName != null) {
//                    getItinerary(provinceName, "0");
            String offset = "0";
            mainPresenter.loadItineraryFromProvince("searchprovince", provinceName, offset, Util.getTokenFromSharedPreferences(getContext()), Util.getAndroidIdFromSharedPreferences(getContext()));
        } else {
            Toast.makeText(getActivity(), "لطفا نام استان را اصلاح کنید ", Toast.LENGTH_SHORT).show();
        }
        Log.d("search ok clicked", "true");
    }

    //------------------------------
    private void hideKeyBoard() {
        // Check if no view has focus:
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    OnVisibleShowCaseViewListener onVisibleShowCaseViewListener;

    public void setOnVisibleShowCaseViewListener() {
        if (onVisibleShowCaseViewListener != null) {
            List<View> views = new ArrayList<>();
            views.add(txtitinerary_name_city_city);
            views.add(city_name);
            views.add(itinerary_name);
            views.add(itinerary_name_attraction);
            onVisibleShowCaseViewListener.onVisibleShowCase("ItineraryFragment", views);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setOnVisibleShowCaseViewListener();
    }

    //------------------------
    public class CustomDialogSearchItinerary extends Dialog implements
            View.OnClickListener {

        public Activity c;
        public Dialog d;
        public TextView no;
        ListView listd;
        String type;
        int layout;

        public CustomDialogSearchItinerary(Activity a, int layout) {
            super(a);
            this.c = a;
            this.layout = layout;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            setContentView(layout);

            AutoCompleteTextView fromCity_city = (AutoCompleteTextView)findViewById(R.id.fromCity_city);
            AutoCompleteTextView endCity_city = (AutoCompleteTextView) findViewById(R.id.endCity_city);
            Button searchOk_city_city = (Button) findViewById(R.id.searchOk_city_city);
            tempCity1 = autoCompleteCity(fromCity_city);
            tempCity2 = autoCompleteCity(endCity_city);


            searchOk_city_city.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideKeyBoard();
                    showCityCity();
                }
            });
////            listd = (ListView) findViewById(R.id.listd);
//            no = (TextView) findViewById(R.id.txtNo);
//            no.setOnClickListener(this);
//            /*tempCityProvince =*/
////            autoCompleteProvince(autoTextWhere, type);


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
//                case R.id.txtNo:
//                    dismiss();
//                    break;
                default:
                    break;
            }
            dismiss();
        }
    }
}
