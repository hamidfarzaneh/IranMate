package com.hackatrip.iranmate.iranmate.main_page;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hackatrip.iranmate.iranmate.R;
import com.hackatrip.iranmate.iranmate.main_page.group.CreateGroupActivity;
/*
    *** on this activity all the tags and group should be separated and shown
    *  it should have filter for each kind
    *  after clicking on each tag some thing should happen (like entering a group)
    *



 */

public class ShowMapActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GoogleMap googleMap;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {                                         // creating new group
            @Override
            public void onClick(View view) {
                Intent createGroupIntent = new Intent(ShowMapActivity.this, CreateGroupActivity.class);
                startActivity(createGroupIntent);
            }
        });

        // map thing

        SupportMapFragment mapFragment = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.bigMap));
        mapFragment.getMapAsync(this);
        googleMap = mapFragment.getMap();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);



        } else {
            // Show rationale and request permission.
            Toast.makeText(this, "Shit no", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
                                                                                                    // show the user location
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        LatLng MELBOURNE = new LatLng(latitude, longitude);
        Marker melbourne = map.addMarker(new MarkerOptions()
                .position(MELBOURNE)
                .alpha(0.7f).title("Shiraz"));


    }
    @Override
    public void onLocationChanged(Location location) {
        int lat = (int) (location.getLatitude());
        int lng = (int) (location.getLongitude());
        Toast.makeText(this, String.valueOf(lat),Toast.LENGTH_SHORT).show();
        Toast.makeText(this, String.valueOf(lng),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

}
