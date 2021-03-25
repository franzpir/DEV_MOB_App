package com.example.dev_mob_houet_piron.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import com.example.dev_mob_houet_piron.R;
import com.example.dev_mob_houet_piron.model.LocatorUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PlacePickerActivity extends FragmentActivity implements OnMapReadyCallback {
    //On met un nom dont on est certain qu'il sera unique au seins du système android
    //Une bonne pratique est de mettre l'identifiant de l'app
    static String PICKER_LATITUDE = "com.example.dev_mob_houet_piron.view.picker_latitude";
    static String PICKER_LONGITUDE = "com.example.dev_mob_houet_piron.view.picker_longitude";
    private GoogleMap map;

    private FloatingActionButton coordValidationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_picker);
        coordValidationBtn = findViewById(R.id.place_pick_validation);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.place_picker_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng userPosition = LocatorUtil.getCurrentLocation(this);
        map.moveCamera(CameraUpdateFactory.zoomTo(15));
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(userPosition.latitude, userPosition.longitude)));
    }

    @Override
    public void onStart() {
        super.onStart();
        coordValidationBtn.setOnClickListener(v -> {
            LatLng coordinates = map.getCameraPosition().target;
            setCoordinatesResult(coordinates.longitude, coordinates.latitude);
        });
    }

    private void setCoordinatesResult(double longitude, double latitude) {
        setResult(Activity.RESULT_OK, new Intent().putExtra(PICKER_LONGITUDE, longitude).putExtra(PICKER_LATITUDE, latitude));
        finish();
    }

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, PlacePickerActivity.class);
    }
}
