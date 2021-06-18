package com.asi.yalla_passanger_eg;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.asi.yalla_passanger_eg.MapWork.FetchUrl;
import com.asi.yalla_passanger_eg.Models.CompletedTripsDitalModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CompletedTripDetial extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    private GoogleMap mMap;
    ArrayList<LatLng> MarkerPoints;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;

    private PolylineOptions lineOptions;
    private ArrayList<LatLng> latLngs;


    TextView duration,distance,waitTime,passangerName,fare,tvStatingPoint,tvArrivalPosint;
    private String PickupLatitude;
    ImageView PassengerPic;
    String PickupLongitude;
    String DropLatitude;
    String DropLongitude;
    LinearLayout loadingLayout,layoutholder;
    private ImageView imageView;
    RatingBar review_ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_completed_trip_detial);
        ini();



    }

    private void ini() {
        duration= (TextView) findViewById(R.id.tvduration);
        distance= (TextView) findViewById(R.id.tvDistance);
        waitTime= (TextView) findViewById(R.id.tv_watting_time) ;
        passangerName= (TextView) findViewById(R.id.tvpassengername);
        PassengerPic= (ImageView) findViewById(R.id.ivPassanger);
        layoutholder= (LinearLayout) findViewById(R.id.layoutholder);
        loadingLayout= (LinearLayout) findViewById(R.id.loadingLayout);
        review_ratingBar= (RatingBar) findViewById(R.id.review_ratingBar);
        tvStatingPoint= (TextView) findViewById(R.id.tvStatingPoint);
        tvArrivalPosint= (TextView) findViewById(R.id.tvArrivalPosint);
        fare= (TextView) findViewById(R.id.tvFare);
        TripDetial();
        //imageView= (ImageView) findViewById(R.id.map);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        // Initializing
        MarkerPoints = new ArrayList<>();
        latLngs = new ArrayList<>();
        lineOptions = new PolylineOptions();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }

    private void TripDetial() {

        loadingLayout.setVisibility(View.VISIBLE);
        final String TAG="ASI";
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("TripId",getIntent().getStringExtra("tid"));
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.BASE_URL+"PassengerTripDetails", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        loadingLayout.setVisibility(View.GONE);
                        layoutholder.setVisibility(View.VISIBLE);
                        Log.e(TAG, response.toString());
                        CompletedTripsDitalModel trip = new Gson().fromJson(response.toString(), CompletedTripsDitalModel.class);
                        String Distance=trip.getDistance();
                        DropLatitude=trip.getDropLatitude();
                        DropLongitude=trip.getDropLongitude();
                        PickupLatitude=trip.getPickupLatitude();
                        PickupLongitude=trip.getPickupLongitude();
                        String PickUpPlace=trip.getPickUpPlace();
                        String DropPlace=trip.getDropPlace();
                        String DriverName=trip.getDriverName();
                        String driverImage=trip.getDriverProfileImage();
                        String EstimatedTime=trip.getEstimatedTime();
                        String WaitingTime=trip.getWaitingTime();
                        String Fare=trip.getFare();
                        String Rating=trip.getRating();
                        review_ratingBar.setRating(Float.parseFloat(Rating));
                        duration.setText(EstimatedTime +" "+" Min");
                        distance.setText(Distance +" "+"KM");
                        passangerName.setText(DriverName);
                        waitTime.setText(WaitingTime +" "+" Min");
                        tvStatingPoint.setText(PickUpPlace);
                        tvArrivalPosint.setText(DropPlace);
                        fare.setText(Fare+" "+"SAR");
                        Glide.with(CompletedTripDetial.this).load(driverImage)
                                .thumbnail(0.5f)
                                .crossFade()
                                .placeholder(R.drawable.user)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(PassengerPic);
                        if (PassengerPic==null)
                        {
                            PassengerPic.setImageResource(R.drawable.user);
                        }
                        //DrawLin();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hideProgressDialog();
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,"TAG");
    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        mLastLocation = location;

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    public void DrawLin() {

        LatLng picLocation=new LatLng(Float.parseFloat(PickupLatitude), Float.parseFloat(PickupLongitude));
        LatLng droplocation=new LatLng(Float.parseFloat(DropLatitude), Float.parseFloat(DropLongitude));
        // Already two locations
        if (MarkerPoints.size() > 1) {
            MarkerPoints.clear();
            mMap.clear();
        }

        // Adding new item to the ArrayList

        MarkerPoints.add(picLocation);
        MarkerPoints.add(droplocation);

        // Creating MarkerOptions
        MarkerOptions options = new MarkerOptions();
        MarkerOptions options2 = new MarkerOptions();


        // Setting the position of the marker
        options.position(picLocation);
        options2.position(droplocation);

        /**
         * For the start location, the color of marker is GREEN and
         * for the end location, the color of marker is RED.
         */
        if (MarkerPoints.size() == 1) {
            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_piclocation));
        } else if (MarkerPoints.size() == 2) {
            options2.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_piclocation));
            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_currentlocation));
        }
        // Add new marker to the Google Map Android API V2
        mMap.addMarker(options);
        mMap.addMarker(options2);

        // Checks, whether start and end locations are captured
        if (MarkerPoints.size() >= 2) {
            LatLng origin = MarkerPoints.get(0);
            LatLng dest = MarkerPoints.get(1);

            // Getting URL to the Google Directions API
            String url = new FetchUrl(CompletedTripDetial.this,mMap).getUrl(origin, dest);
            FetchUrl FetchUrl = new FetchUrl(CompletedTripDetial.this,mMap);
            // Start downloading json data from Google Directions API
            FetchUrl.execute(url);
            //move map camera
            mMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
            //mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(picLocation);
            builder.include(droplocation);
            LatLngBounds bounds = builder.build();
            int padding =30; // offset from edges of the map in pixels
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            mMap.animateCamera(cu);
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //mMap.getUiSettings().setZoomControlsEnabled(false);

    }
}
