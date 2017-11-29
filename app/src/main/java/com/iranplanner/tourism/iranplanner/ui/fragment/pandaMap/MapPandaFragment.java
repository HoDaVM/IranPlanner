package com.iranplanner.tourism.iranplanner.ui.fragment.pandaMap;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.RecyclerItemOnClickListener;
import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.standard.StandardFragment;
import com.iranplanner.tourism.iranplanner.ui.activity.MapWrapperLayout;
import com.iranplanner.tourism.iranplanner.ui.activity.MySupportMapFragmen;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import autoComplet.MyFilterableAdapterCityProvince;
import autoComplet.ReadJsonCityProvince;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import entity.CityProvince;
import entity.PandaMapList;

import entity.ResultPandaAutoComplete;
import entity.ResultPandaMap;
import entity.ResultPandaMapSearch;
import entity.ResultPandaMaps;
import tools.Constants;
import tools.Util;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by h.vahidimehr on 11/11/2017.
 */

public class MapPandaFragment extends StandardFragment implements OnMapReadyCallback, MapWrapperLayout.OnDragListener,
        MapPandaPresenter.View, TextWatcher,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    @Inject
    MapPandaPresenter mapPandaPresenter;

    private GoogleMap mMap;
    ProgressDialog progressBar;
    LatLng Iran;
    @BindView(R.id.reservationListRecyclerView)
    RecyclerView recyclerView;
    MySupportMapFragmen mapFragment;
    Projection projection;
    public double latitude;
    public double longitude;
    private boolean drawing = false;
    private Polygon polygon;
    private List<LatLng> PolylinePoints;
    List<LatLng> markerPoints;
    List<String> markerType;
    Polyline polyline;
    boolean flagUp = false;
    Boolean Is_MAP_Moveable = false; // to detect map is movable
    PandaAdapter adapter;
    private boolean screenLeave = false;
    int source = 0;
    int destination = 1;
    private List<ResultPandaMap> resultPandaMapList;
    private List<String> markerNames;
    LinearLayoutManager horizontalLayoutManagaer;
    private boolean isResultForDraw = false;
    private Button btnFilter;
    private AutoCompleteTextView search, searchRange;
    PandaMapList PandaMapList;
    private ImageView imgMyLocation;

    public static MapPandaFragment newInstance() {
        MapPandaFragment fragment = new MapPandaFragment();
        return fragment;
    }

    String chooseHotel, chooseAttraction;
    boolean setDraw = true;
    SnapHelper snapHelper;

    private void setUpRecyclerView(List<ResultPandaMap> resultPandaMapList) {
        recyclerView.setHasFixedSize(true);

        horizontalLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);

        adapter = new PandaAdapter(getActivity(), resultPandaMapList, getContext());
        recyclerView.setAdapter(adapter);
        if (snapHelper == null) {
            snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(recyclerView);
        }
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {

                    View centerView = snapHelper.findSnapView(horizontalLayoutManagaer);
                    int positions = horizontalLayoutManagaer.getPosition(centerView);

//                    showMarkers(markerPoints);
                    if (markerNames.size() > 0) {
                        Marker marker = mMap.addMarker(new MarkerOptions().position(markerPoints.get(positions))
                                .title(markerNames.get(positions))/*.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_blue_pin))*/);
                        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.hotel);
                        if (markerType.get(positions).equals("attraction")) {
                            icon = BitmapDescriptorFactory.fromResource(R.drawable.attraction);

                        } else if (markerType.get(positions).equals("lodging")) {
                            icon = BitmapDescriptorFactory.fromResource(R.drawable.hotel);

                        } else if (markerType.get(positions).equals("city")) {
                            icon = BitmapDescriptorFactory.fromResource(R.drawable.city);

                        }
                        marker.setIcon(icon);
                        marker.showInfoWindow();
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(markerPoints.get(positions)));
                        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
                        mMap.animateCamera(zoom);
                    }

                }
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemOnClickListener(getContext(), new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                showMarkers(markerPoints, markerType);
                //open
                String offset = "0";

            }
        }));


    }

    private void cleanSearchText() {
        search.setText("");
    }

    private void cleanMapAndRecyclerView() {
        markerPoints.clear();
        markerNames.clear();
        markerType.clear();
        isResultForDraw = false;
        PolylinePoints.clear();

        snapHelper = null;
        try {
            resultPandaMapList.clear();
            adapter.notifyDataSetChanged();

        } catch (Exception e) {

        }

        try {
            PolylinePoints.clear();
            mMap.clear();
        } catch (Exception e) {

        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map_panda, container, false);
        mapFragment = (MySupportMapFragmen) getChildFragmentManager()
                .findFragmentById(R.id.mapView);

        recyclerView = rootView.findViewById(R.id.reservationListRecyclerView);
        btnFilter = rootView.findViewById(R.id.btnFilter);
        search = rootView.findViewById(R.id.search);
        searchRange = rootView.findViewById(R.id.searchRange);
        search.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        imgMyLocation = rootView.findViewById(R.id.imgMyLocation);
        imgMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                final LocationManager manager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    buildAlertMessageNoGps(1);

                } else {
//                    openMapFull(position, resulAttraction);
                    mMap.getUiSettings().setMyLocationButtonEnabled(true);
                    Location location= mMap.getMyLocation();
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    if (location != null)
                    {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                                .zoom(17)                   // Sets the zoom
                                .bearing(90)                // Sets the orientation of the camera to east
                                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                                .build();                   // Creates a CameraPosition from the builder
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }

                }
//                LocationManager locationManager = (LocationManager)
//                        getActivity().getSystemService(Context.LOCATION_SERVICE);
//                Criteria criteria = new Criteria();
//
//                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return;
//                }




            }
        });
        search.addTextChangedListener(this);
        autoCompleteProvince(searchRange, null);
        chooseAttraction = "1";
        chooseHotel = "1";
        ImageButton navigateBtn = rootView.findViewById(R.id.nearBtn);

        navigateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LocationManager manager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    buildAlertMessageNoGps(1);

                } else {
//                    openMapFull(position, resulAttraction);


                }
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog mBottomSheetDialog = new Dialog(getActivity(), R.style.MaterialDialogSheet);
                mBottomSheetDialog.setContentView(R.layout.fragment_panda_filter);
                CheckBox filterHotelCheckBox = ((Dialog) mBottomSheetDialog).findViewById(R.id.filterHotelCheckBox);
                CheckBox filterAttrctionCheckBox = ((Dialog) mBottomSheetDialog).findViewById(R.id.filterAttrctionCheckBox);
                filterHotelCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            Log.e("hotel", "selected");
                            chooseHotel = "1";
                        } else {
                            chooseHotel = "0";
                        }
                    }
                });
                filterAttrctionCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            Log.e("attraction", "selected");
                            chooseAttraction = "1";

                        } else {
                            chooseAttraction = "0";
                        }
                    }
                });


                mBottomSheetDialog.setCancelable(true);
                mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
                mBottomSheetDialog.show();
            }
        });
        Button btnSelectPolygon = rootView.findViewById(R.id.btnSelectPolygon);
        btnSelectPolygon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setText("");
                setDrawable(setDraw);
                cleanMapAndRecyclerView();

            }
        });
        mapFragment.getMapAsync(this);
        mapFragment.setOnDragListener(this);
        ButterKnife.bind(this, rootView);

        return rootView;

    }

      private void buildAlertMessageNoGps(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("موقعیت یاب شما فعال نیست، آیا تمایل به روشن کردن آن دارید؟")
                .setCancelable(false)
//                // TODO: 06/02/2017  below
                // toye startActivityForResult be jaye code request posotion ro ferestam . ye joor kalak .
                .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), position);
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

    private void hiddenKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    List<entity.CityProvince> CityProvince;


    public void autoCompleteProvince(AutoCompleteTextView textProvience, final String type) {

        ReadJsonCityProvince readJsonProvince = new ReadJsonCityProvince();
        CityProvince = readJsonProvince.getListOfCityProvience(getContext());
        MyFilterableAdapterCityProvince adapter = new MyFilterableAdapterCityProvince(getContext(), android.R.layout.simple_list_item_1, CityProvince);
        textProvience.setAdapter(adapter);
        textProvience.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cleanMapAndRecyclerView();
                cleanSearchText();
                hiddenKeyboard();
                if (CityProvince.get(position).getType().equals("city")) {
                    zoomCamera(new LatLng(Double.valueOf(CityProvince.get(position).getPosition_lat()), Double.valueOf(CityProvince.get(position).getPosition_lon())), 12f);

                } else {
                    zoomCamera(new LatLng(Double.valueOf(CityProvince.get(position).getPosition_lat()), Double.valueOf(CityProvince.get(position).getPosition_lon())), 9f);

                }
                searchRange.setText(CityProvince.get(position).getTitle());

            }

        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//         Obtain the SupportMapFragment and get notified when the map is ready to be used.

        List<LatLng> nn = new ArrayList<>();
        nn.add(Iran);
        Iran = new LatLng(35.6887931, 51.3891646);
        nn.add(Iran);
        markerPoints = nn;
        PolylinePoints = new ArrayList<LatLng>();

        DaggerMapPandaComponent.builder().netComponent(((App) getContext().getApplicationContext()).getNetComponent())
                .mapPandaModule(new MapPandaModule(this))
                .build().inject(this);
        markerNames = new ArrayList<>();
        markerType = new ArrayList<>();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        mMap.getUiSettings().setScrollGesturesEnabled(false);
        setDrawable(setDraw);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Iran, 10.0f));
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }



    }

    GoogleApiClient mGoogleApiClient;

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private void setDrawable(boolean drawable) {
        if (drawable) {
            mMap.getUiSettings().setScrollGesturesEnabled(drawable);
            setDraw = false;

        } else {
            mMap.getUiSettings().setScrollGesturesEnabled(drawable);
            setDraw = true;
        }


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {


            @Override
            public boolean onMarkerClick(Marker marker) {
                int c = 0;
                int markerPosition = 0;
                for (ResultPandaMap resultLodging : resultPandaMapList) {
                    String s[] = marker.getTitle().split("-");
                    if (resultLodging.getPoint().getTitle().equals(s[s.length - 1])) {
                        showMarkers(markerPoints, markerType);
                        mMap.addMarker(new MarkerOptions().position(markerPoints.get(c))
                                .title(markerNames.get(c))/*.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_blue_pin))*/);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(markerPoints.get(c)));
                        Log.e("marker", marker.getTitle());
                        markerPosition = c;
                    }
                    c++;
                }
                recyclerView.getLayoutManager().scrollToPosition(markerPosition);

                return false;
            }
        });

    }

    private void draw_final_polygon() {

        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.addAll(PolylinePoints);
        polygonOptions.strokeWidth(8);
        polygonOptions.strokeColor(R.color.main_blue);
        polygonOptions.fillColor(ContextCompat.getColor(getContext(), R.color.map));
        polygon = mMap.addPolygon(polygonOptions);
        PandaMapList = new PandaMapList(polygon.getPoints());
        VisibleRegion visibleRegion = mMap.getProjection().getVisibleRegion();
        LatLng farLeft = visibleRegion.farLeft;
        LatLng nearRight = visibleRegion.nearRight;

        String searchText = search.getText().toString();
        mapPandaPresenter.getDrawResult(PandaMapList, searchText, chooseAttraction, chooseHotel, farLeft.toString(), nearRight.toString(), Util.getTokenFromSharedPreferences(getContext()), Util.getAndroidIdFromSharedPreferences(getContext()));

    }

    public void Draw_Map() {


//        specify latitude and longitude of both source and destination Polyline

        if (PolylinePoints.size() > 1) {
            polyline = mMap.addPolyline(new PolylineOptions().add(PolylinePoints.get(source), PolylinePoints.get(destination)).width(8));
            source++;
            destination++;
        }


    }

    private void showMarkers(List<LatLng> draw, List<String> markerType) {

        int index = 0;
        for (LatLng latLng : draw) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(markerNames.get(index));
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.hotel);
            if (markerType.get(index).equals("attraction")) {
                icon = BitmapDescriptorFactory.fromResource(R.drawable.attraction);

            } else if (markerType.get(index).equals("lodging")) {
                icon = BitmapDescriptorFactory.fromResource(R.drawable.hotel);

            } else if (markerType.get(index).equals("city")) {
                icon = BitmapDescriptorFactory.fromResource(R.drawable.city);

            }
            markerOptions.icon(icon);

            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            Marker marker = mMap.addMarker(markerOptions);
            index++;
        }

    }


    @Override
    public void onDrag(MotionEvent motionEvent) {
        if (setDraw && !isResultForDraw) {
            Log.d("ON_DRAG", String.format("ME: %s", motionEvent));
            Log.d("ON_DRAG", String.format("ME: %s", motionEvent));
            // Handle motion event:

            Log.i("ON_DRAG", "X:" + String.valueOf(motionEvent.getX()));
            Log.i("ON_DRAG", "Y:" + String.valueOf(motionEvent.getY()));

            float x = motionEvent.getX();
            float y = motionEvent.getY();

            int x_co = Integer.parseInt(String.valueOf(Math.round(x)));
            int y_co = Integer.parseInt(String.valueOf(Math.round(y)));

            projection = mMap.getProjection();
            Point x_y_points = new Point(x_co, y_co);
            LatLng latLng = mMap.getProjection().fromScreenLocation(x_y_points);
            latitude = latLng.latitude;
            longitude = latLng.longitude;

            Log.i("ON_DRAG", "lat:" + latitude);
            Log.i("ON_DRAG", "long:" + longitude);
            LatLng point = new LatLng(latitude, longitude);

            int eventaction = motionEvent.getAction();
            switch (eventaction) {
                case MotionEvent.ACTION_DOWN:
//                    if (PolylinePoints.size() > 0) {
//                        PolylinePoints.clear();
//                        mMap.clear();
//                    }

                    // finger touches the screen
                    screenLeave = false;
//                            System.out.println("ACTION_DOWN");

//                            val.add(new LatLng(latitude, longitude));

                case MotionEvent.ACTION_MOVE:
                    // finger moves on the screen
//                            System.out.println("ACTION_MOVE");
                          /*  if (val.size()==3){
                                val.remove(1);
                            }*/

                    PolylinePoints.add(new LatLng(latitude, longitude));
                    screenLeave = false;
                    Draw_Map();


                case MotionEvent.ACTION_UP:

//                            System.out.println("ACTION_UP");
                    if (!screenLeave) {
                        screenLeave = true;
                    } else {
                        System.out.println("ACTION_UP ELSE CAse");
                        Is_MAP_Moveable = false; // to detect map is movable
                        source = 0;
                        destination = 1;
                        draw_final_polygon();
                    }


                    // finger leaves the screen
//                            Is_MAP_Moveable = false; // to detect map is movable
//                            Draw_Map();
                    break;
                default:
                    break;
            }
        } else {
            setDrawable(true);
            searchRange.setText("");
            searchRange.setHint("محدوده نمایش نقشه");
//            cleanMapAndRecyclerView();
//            cleanSearchText();
        }


    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showComplete() {

    }

    @Override
    public void showProgress() {
        progressBar = Util.showProgressDialog(getContext(), "لطفا منتظر بمانید", getActivity());

    }

    @Override
    public void dismissProgress() {
        Util.dismissProgress(progressBar);

    }

    @Override
    public void showPointOnMap(ResultPandaMaps resultPandaMaps) {

        resultPandaMapList = resultPandaMaps.getResultPandaMap();
        markerPoints.clear();
        int counter = 1;
        if (resultPandaMapList.size() > 0) {
            for (ResultPandaMap resultPandaMap : resultPandaMapList) {
                LatLng point = new LatLng(Double.valueOf(resultPandaMap.getPoint().getLatitude()), Double.valueOf(resultPandaMap.getPoint().getLongitude()));
                markerPoints.add(point);
                markerNames.add(Util.persianNumbers(String.valueOf(counter)) + "-" + resultPandaMap.getPoint().getTitle());
                markerType.add(resultPandaMap.getPoint().getType());
                counter++;
            }
        }
        isResultForDraw = true;
        showMarkers(markerPoints, markerType);

        setUpRecyclerView(resultPandaMapList);
//        onWindowFocusChanged(markerPoints);

    }

    private void zoomCamera(LatLng point, Float zoom) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point, zoom));

    }

    public void onWindowFocusChanged(List<LatLng> points) {

        try {

            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            for (LatLng latLng : points) {
                builder.include(latLng);
            }
            LatLngBounds bounds = builder.build();
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (width * 0.20); // offset from edges of the map 12% of screen
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
            mMap.animateCamera(cu);

        } catch (Exception e) {

        }

    }


    @Override
    public void showPandaSearch(ResultPandaMaps resultPandaMapSearch) {
        List<ResultPandaMap> re = resultPandaMapSearch.getResultPandaMap();
        List<String> titleArray = new ArrayList<>();
        for (ResultPandaMap resultPandaMap : re) {
            titleArray.add(resultPandaMap.getPoint().getTitle());

        }
//        for (ResultPandaAutoComplete resultPandaAutoComplete : re) {
//            titleArray.add(resultPandaAutoComplete.getTitle());
//        }
        ArrayAdapter<String> adapteo = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, titleArray.toArray(new String[0]));
        search.setAdapter(adapteo);
        adapteo.notifyDataSetChanged();
    }

//    @Override
//    public void showPandaSearch(ResultPandaMapSearch resultPandaMapSearch) {
//
//        List<ResultPandaAutoComplete> re = resultPandaMapSearch.getResultPandaAutoComplete();
//        List<String> titleArray = new ArrayList<>();
//        for (ResultPandaAutoComplete resultPandaAutoComplete : re) {
//            titleArray.add(resultPandaAutoComplete.getTitle());
//        }
//        ArrayAdapter<String> adapteo = new ArrayAdapter<String>(getContext(),
//                android.R.layout.simple_dropdown_item_1line, titleArray.toArray(new String[0]));
//        search.setAdapter(adapteo);
//        adapteo.notifyDataSetChanged();
//
////    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        cleanMapAndRecyclerView();
        String lastValue = "";
        String newValue = s.getFilters().toString();
        if (!newValue.equals(lastValue) && s.length() >= 2) {
            VisibleRegion visibleRegion = mMap.getProjection().getVisibleRegion();
            LatLng farLeft = visibleRegion.farLeft;
            LatLng nearRight = visibleRegion.nearRight;
            mapPandaPresenter.getDrawResult(s.toString(), chooseAttraction, chooseHotel, farLeft.toString(), nearRight.toString(), Util.getTokenFromSharedPreferences(getContext()), Util.getAndroidIdFromSharedPreferences(getContext()));

        }

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        Log.e("Dfd", "fsdfsd");

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(), location.getLongitude()))
                .zoom(17)
                .bearing(90)
                .tilt(40)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }
}