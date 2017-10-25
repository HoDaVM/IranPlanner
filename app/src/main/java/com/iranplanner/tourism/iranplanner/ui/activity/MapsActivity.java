package com.iranplanner.tourism.iranplanner.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.iranplanner.tourism.iranplanner.R;

import java.util.ArrayList;
import java.util.List;

import entity.ItineraryLodgingCity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tools.MapDirection;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener/*, Callback<DirectionResults>*/ {

    private GoogleMap mMap;
    MarkerOptions markerOptions;
    LatLng sydney;
    LatLng sydney1;
    LatLng sydney2;
    LatLng sydney3;
    LatLng te;
    LatLng te11;
    MySupportMapFragmen mapFragment;
    Projection projection;
    public double latitude;
    public double longitude;
    private boolean drawing = false;
    private Polygon polygon;
    private List<LatLng> points ;
    List<LatLng> markerPoints;
    Polyline polyline;
    boolean flagUp = false;
    Boolean Is_MAP_Moveable = false; // to detect map is movable

    private boolean screenLeave = false;
    int source = 0;
    int destination = 1;


    private void prepareMarkers() {
//        if (markerPoints.size() > 1) {
//            markerPoints.clear();
//            mMap.clear();
//        }
        if (markerPoints != null) {

            showMarkers(markerPoints);
//            MapDirection mapDirection = new MapDirection(mMap, getApplicationContext(), lodgingCities, MarkerPoints);
//            // Already two locations
//            markers = mapDirection.readytoDirect();
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    Log.e("map is ckicked", "true");
                }
            });

        }
        onWindowFocusChanged(true);
    }
    @Override
    public void onLocationChanged(Location location) {

    }
    private void showMarkers(List<LatLng> points) {
        for (LatLng point : points) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(point);
            markerOptions.title("موقعیت شما");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney1));

            mMap.addMarker(markerOptions);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_filter);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (MySupportMapFragmen) getSupportFragmentManager()
                .findFragmentById(R.id.map);
         sydney = new LatLng(-34, 151);
         sydney1 = new LatLng(-34.554, 151.475);
         sydney2 = new LatLng(-34.7474757, 151.757587585);
         sydney3 = new LatLng(-33.7474757, 150.757587585);
        List<LatLng> nn=new ArrayList<>();
        nn.add(sydney);

        LatLng te = new LatLng(35.6887931, 51.3891646);
        nn.add(te);
        nn.add(sydney1);
        nn.add(sydney2);
        nn.add(sydney3);

       markerPoints= nn;

        points = new ArrayList<LatLng>();
//        mapFragment.setOnDragListener(new MapWrapperLayout.OnDragListener() {
//            @Override
//            public void onDrag(MotionEvent motionEvent) {
//                Log.d("ON_DRAG", String.format("ME: %s", motionEvent));
//                // Handle motion event:
//            }
//        });


        mapFragment.setOnDragListener(new MapWrapperLayout.OnDragListener() {

            @Override
            public void onDrag(MotionEvent motionEvent) {
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
                        if (points.size() > 0) {
                            points.clear();
                            mMap.clear();
                        }

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

                        points.add(new LatLng(latitude, longitude));
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


//                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                    if(points!=null){
//                        polygon.setPoints(points);
//                    }
//                }
//                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//
//                    if (polyline != null) {
//                        polyline.remove();
//                        PolylineOptions rectOptions = new PolylineOptions().width(2)
//                                .add(point);
//                        polyline = mMap.addPolyline(rectOptions);
//                        points.clear();
//
//                        polygon.remove();
//                        PolygonOptions rectOption = new PolygonOptions()
//                                .strokeWidth(2)
//                                .fillColor(0x80000000)
//                                .add(point);
//                        polygon = mMap.addPolygon(rectOption);
//                    } else {
//                        PolylineOptions rectOptions = new PolylineOptions().width(2)
//                                .add(point);
//                        polyline = mMap.addPolyline(rectOptions);
//                        PolygonOptions rectOption = new PolygonOptions()
//                                .strokeWidth(2)
//                                .fillColor(0x80000000)
//                                .add(point);
//                        polygon = mMap.addPolygon(rectOption);
//                    }
//                }
//                if (points .size()==0) {
//                    points.add(point);
//                } else  {
//                    for (LatLng p : points) {
//                        if (!(p.equals(point))) {
//
////                            polygon.setPoints(points);
////                            points.clear();
////                            points.add(point);
//                            points.add(point);
//
//                        }
////                        else {
////                            points.add(point);
////                        }
//                    }


//                }

//                if (!drawing) {
//                    drawing = true;
//                } else {
//                    polyline.setPoints(points);
//                }
//                points.add(point);
//                if (!drawing) {
//                    mMap.addMarker(new MarkerOptions()
//                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
//                            .anchor(0.2f, 1.0f)
//                            .position(point));
//
//                    PolygonOptions rectOptions = new PolygonOptions()
//                            .strokeWidth(2)
//                            .fillColor(0x80000000)
//                            .add(point);
//                    polygon = mMap.addPolygon(rectOptions);
//                    drawing = true;
//                } else {
//                    polygon.setPoints(points);
//                }
            }
        });
        mapFragment.getMapAsync(this);


    }

    public void Draw_Map() {


//        specify latitude and longitude of both source and destination Polyline

        if (points.size() > 1) {
            polyline = mMap.addPolyline(new PolylineOptions().add(points.get(source), points.get(destination)).width(8));
            source++;
            destination++;
        }


    }

    private void draw_final_polygon() {

        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.addAll(points);
        polygonOptions.strokeWidth(8);
        polygonOptions.fillColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        polygon = mMap.addPolygon(polygonOptions);


    }
//    private void createSurroundingPolygon(List<LatLng> polygonPath) {
//        List<Coordination> coordinates = new ArrayList<>();
//        for (LatLng latLng : polygonPath) {
//            coordinates.add(new Coordination(latLng.longitude, latLng.latitude));
//        }
//
//        GeometryFactory factory = new GeometryFactory();
//        Geometry lineString = factory.createLineString(coordinates.toArray(new Coordinate[coordinates.size()]));
//        Polygon polygon = (Polygon) BufferOp.bufferOp(lineString, 0.0001);
//
//        Coordinate[] coordinatesSurroundingPolygon = polygon.getExteriorRing().getCoordinates();
//        List<LatLng> surroundingPolygon = new ArrayList<>();
//        for (int i = 0; i < coordinatesSurroundingPolygon.length; i++) {
//            surroundingPolygon.add(new LatLng(coordinatesSurroundingPolygon[i].y, coordinatesSurroundingPolygon[i].x));
//        }
//        drawPolygon(surroundingPolygon);
//    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.getUiSettings().setScrollGesturesEnabled(false);

        // Add a marker in Sydney and move the camera
//        sydney = new LatLng(-34, 151);
//        te = new LatLng(35.6887931, 51.3891646);
//        te11 = new LatLng(29.5916593, 52.583701);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
//        getMap();
        prepareMarkers();


    }

    String base_url = "http://maps.googleapis.com/";

//    public void getMap() {
//
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://maps.googleapis.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        MyApiRequestInterface getJsonInterface = retrofit.create(MyApiRequestInterface.class);
//        String oo = te.latitude + "," + te.longitude;
//        String oo1 = te11.latitude + "," + te11.longitude;
//        Call<DirectionResults> call = getJsonInterface.getdC(oo, oo1);
//        Call<DirectionResults> call = getJsonInterface.getd(te,te11);
//        call.enqueue(this);
//    }


//    @Override
//    public void onResponse(Call<DirectionResults> call, Response<DirectionResults> response) {
//        ArrayList<LatLng> routelist = new ArrayList<LatLng>();
//        ArrayList<LatLng> decodelist;
//        Route routeA = response.body().getRoutes().get(0);
//        Legs legs = routeA.getLegses().get(0);
//        if (routeA.getLegses().size() > 0) {
//            List<Steps> steps = legs.getSteps();
//            Log.i("zacharia", "Steps size :" + steps.size());
//            Steps step;
//            Location location;
//            String polyline;
//            for (int i = 0; i < steps.size(); i++) {
//                step = steps.get(i);
//                location = step.getStart_location();
//                routelist.add(new LatLng(location.getLat(), location.getLng()));
//                Log.i("zacharia", "Start Location :" + location.getLat() + ", " + location.getLng());
//                polyline = step.getPolyline().getPoints();
//                decodelist = RouteDecode.decodePoly(polyline);
//                routelist.addAll(decodelist);
//                location = step.getEnd_location();
//                routelist.add(new LatLng(location.getLat(), location.getLng()));
//                Log.i("zacharia", "End Location :" + location.getLat() + ", " + location.getLng());
//            }
//        }
//
//        Log.i("zacharia", "routelist size : " + routelist.size());
//        if (routelist.size() > 0) {
//            PolylineOptions rectLine = new PolylineOptions().width(10).color(
//                    Color.RED);
//
//
//            for (int i = 0; i < routelist.size(); i++) {
//                rectLine.add(routelist.get(i));
//            }
//            // Adding route on the map
//            mMap.addPolyline(rectLine);
//
//            markerOptions.position(te);
//            markerOptions.draggable(true);
//            mMap.addMarker(markerOptions);
//        }
//    }
//
//    @Override
//    public void onFailure(Call<DirectionResults> call, Throwable t) {
//        Log.e("fd", "fdfd");
//
//    }

//
//    @Override
//    public void onMapClick(LatLng point) {
//        points.add(point);
//        if (!drawing) {
//            mMap.addMarker(new MarkerOptions()
//                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
//                    .anchor(0.2f, 1.0f)
//                    .position(point));
//
//            PolygonOptions rectOptions = new PolygonOptions()
//                    .strokeWidth(2)
//                    .fillColor(0x80000000)
//                    .add(point);
//            polygon = mMap.addPolygon(rectOptions);
//            drawing = true;
//        } else {
//            polygon.setPoints(points);
//        }
//    }
}
