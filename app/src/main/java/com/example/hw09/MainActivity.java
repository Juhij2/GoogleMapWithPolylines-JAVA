package com.example.hw09;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.hw09.models.Path;
//import com.example.hw09.models.PointLocation;
import com.example.hw09.models.PathResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
 Name: Juhi Jadhav, Saifuddin Mohammed
  Assignment: HW09
  file: MainActivity.java
  Group: 05
 */


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ArrayList<LatLng> mPath = new ArrayList<>();

    LatLng startMarker, endMarker;
    PathResponse pathResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        PolylineOptions options = new PolylineOptions().width(10).color(Color.BLUE).geodesic(true);
        getLatLng(googleMap);

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                googleMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Charlotte"));
            }
        });

    }


    private final OkHttpClient client = new OkHttpClient();

    void getLatLng(GoogleMap googleMap){
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/map/route")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String body = response.body().string();
                                Gson gson = new Gson();
                                pathResponse = gson.fromJson(body,PathResponse.class);
                                mPath.clear();

                                for(Path path : pathResponse.getPath()){
                                    Double lat = Double.valueOf(path.getLatitude());
                                    Double lon = Double.valueOf(path.getLongitude());
                                    LatLng newPath = new LatLng(lat,lon);
                                    mPath.add(newPath);
                                }
                                int size = pathResponse.getPath().size();

                                startMarker = getLongLat(pathResponse.getPath().get(0));
                                endMarker = getLongLat(pathResponse.getPath().get(size-1));

                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startMarker, 10));
                                mMap.addMarker(new MarkerOptions()
                                        .position(startMarker)
                                        .title("Start"));

                                mMap.addMarker(new MarkerOptions()
                                        .position(endMarker)
                                        .title("End"));



                                for(LatLng path : mPath){
                                    Log.d("demo", "onMapReady: " + path);
                                }

                                mMap = googleMap;

                                mMap.addPolyline((new PolylineOptions()).addAll(mPath)
                                        .width(5)
                                        .color(Color.RED)
                                        .geodesic(true));

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }

            }
        });

    }

    public LatLng getLongLat(Path path){

        Double lat = Double.valueOf(path.getLatitude());
        Double lon = Double.valueOf(path.getLongitude());
        LatLng latLng = new LatLng(lat, lon);
        return latLng;

    }

}