package com.example.dev_mob_houet_piron.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dev_mob_houet_piron.R;
import com.example.dev_mob_houet_piron.model.LocatorUtil;
import com.example.dev_mob_houet_piron.presenter.BadgeDisplayPresenter;
import com.example.dev_mob_houet_piron.view.adapter.BadgeAdapter;
import com.example.dev_mob_houet_piron.view.adapter.PlaceAdapter;
import com.example.dev_mob_houet_piron.view.interfaces.IDisplayBadge;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class BadgeDisplayActivity extends AppCompatActivity implements OnMapReadyCallback, IDisplayBadge {
    private static String BADGE_ID = "com.example.dev_mob_houet_piron.badge_id";

    private TextView titleTV, descriptionTV;
    private RecyclerView cachesFromTheBadge;
    private GoogleMap map;

    private BadgeDisplayPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badge_display);

        this.presenter = new BadgeDisplayPresenter(this, getIntent().getStringExtra(BADGE_ID));

        titleTV = findViewById(R.id.badgeDetails_title);
        descriptionTV = findViewById(R.id.badgeDetails_description);
        cachesFromTheBadge = findViewById(R.id.badgeDetails_cachesRV);
        cachesFromTheBadge.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.badgeDetails_map);
        mapFragment.getMapAsync(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;

        map.getUiSettings().setScrollGesturesEnabled(false);
        map.getUiSettings().setZoomGesturesEnabled(false);
        LatLng userPosition = LocatorUtil.getCurrentLocation(getApplicationContext());
        map.moveCamera(CameraUpdateFactory.zoomTo(12));
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(userPosition.latitude, userPosition.longitude)));
    }

    public static Intent newIntent(Context packageContext, String badgeId) {
        return new Intent(packageContext, BadgeDisplayActivity.class).putExtra(BADGE_ID, badgeId);
    }

    @Override
    public void showBadge(String title, String description) {
        titleTV.setText(title);
        descriptionTV.setText(description);
    }

    @Override
    public void loadView() {
        cachesFromTheBadge.setAdapter(new PlaceAdapter(this, presenter));
    }

    @Override
    public void addMarker(double latitude, double longitude) {
        if (map != null) {
            LatLng coordinates = new LatLng(latitude, longitude);
            map.addMarker(new MarkerOptions().position(coordinates));
            map.moveCamera(CameraUpdateFactory.newLatLng(coordinates));
        }
    }
}
