package com.hackatrip.iranmate.iranmate.main_page.group;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hackatrip.iranmate.iranmate.Model.MainApp;
import com.hackatrip.iranmate.iranmate.R;
import com.hackatrip.iranmate.iranmate.main_page.MainActivity;

public class CreateGroupActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final MainApp mainModel  = (MainApp) MainApp.getContext();               // link to application module
    // request for place picker ( use for getting result)
    int PLACE_PICKER_REQUEST = 1;

    // group items
    LatLng chosenLatLng;
    EditText newGroupNameEditText;
    CheckBox checkBox;
    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();                    // using placepicker api

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);                  // run plcae picker activity to choose a place
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        newGroupNameEditText = (EditText) findViewById(R.id.newGpName);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        SupportMapFragment mapFragment = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                                                                                                        // using a thread to create a group
               createTaskAndGo();


            }
        });

    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.googleMap=map;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {                 // saving chosen place for creating group
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                this.chosenLatLng = place.getLatLng();
                googleMap.addMarker(new MarkerOptions().position(chosenLatLng).title("Chosen place"));

            }
        }
    }

    public void createTaskAndGo(){
                                                                                                    // after a place is picked a map will be shown
                                                                                                    // that have a mark showing the chosen place
                                                                                                    // then , choose a name
        Thread thread = new Thread() {              // save the group
            @Override
            public void run() {
                mainModel.addNewGroup(newGroupNameEditText.getText().toString(), mainModel.getLogedInUser(), chosenLatLng, checkBox.isChecked());
            }
        };

        thread.start();
        Intent goToMainIntent = new Intent(CreateGroupActivity.this, MainActivity.class);
        goToMainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        goToMainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(goToMainIntent);
    }





}
