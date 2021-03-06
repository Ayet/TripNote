package com.tbuonomo.jawgmapsample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.IconCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.Context.LOCATION_SERVICE;
import static android.support.v4.app.ActivityCompat.startActivityForResult;
import static com.tbuonomo.jawgmapsample.R.id.icon;
import static com.tbuonomo.jawgmapsample.R.id.mapView;


public class MapFragment extends Fragment {

    private MapView mapView;
    private FloatingActionButton cameraRepositioning;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Mapbox.getInstance(getContext(), BuildConfig.MAP_BOX_TOKEN);
        //get the variable from the frameLayout
        mapView = view.findViewById(R.id.mapView);
        cameraRepositioning=view.findViewById(R.id.camera_repositioning);
        //set the configuration
        mapView.setStyleUrl("https://tile.jawg.io/jawg-streets.json?access-token=" + BuildConfig.JAWG_API_KEY);
        mapView.onCreate(savedInstanceState);

        //Check if we have the permission and create a location manager
        if (ContextCompat.checkSelfPermission(getContext(), ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
            LocationManager mLocationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

            //add fake points of stories (temporary as we would need to get real data later)
            List<MarkerOptions> markerOptionsList=new ArrayList<MarkerOptions>();
            for (int i=1;i<20;i=i+1)
            {
                markerOptionsList.add(new MarkerOptions().position(new LatLng(45.7880-(((i*i)+0.1)/1000.0),4.86975+(((i*i)+0.1)/1000.0)))
                        .title("Story "+i)
                        .snippet("Click on markers to show stories"));
            }
            //add listener for the button and location

            final MapLocationListener mLocationListener = new MapLocationListener(mapView,markerOptionsList){
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {
                    Intent intent = new Intent(getContext(), MediaActivity.class);
                    startActivity(intent);
                    return true;
                }
            };
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, mLocationListener);
            cameraRepositioning.setOnClickListener(mLocationListener);

        }

    }



}
