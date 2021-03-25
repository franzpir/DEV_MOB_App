package com.example.dev_mob_houet_piron.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dev_mob_houet_piron.R;
import com.example.dev_mob_houet_piron.presenter.CacheDisplayPresenter;
import com.example.dev_mob_houet_piron.view.interfaces.IDisplayCache;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CacheDisplayActivity extends AppCompatActivity implements OnMapReadyCallback, IDisplayCache {
    private static String CACHE_UUID = "com.example.dev_mob_houet_piron.cache_uuid";

    private GoogleMap map;
    private CacheDisplayPresenter presenter;
    private TextView cacheTitle;
    private TextView cacheDescription;
    private TextView cacheCoordinates;
    private TextView cacheType;
    private Button cacheFoundBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache_display);
        this.presenter = new CacheDisplayPresenter(this, getIntent().getStringExtra(CACHE_UUID));

        this.cacheTitle = findViewById(R.id.cache_title);
        this.cacheDescription = findViewById(R.id.cache_description);
        this.cacheCoordinates = findViewById(R.id.cache_coordinates);
        this.cacheType = findViewById(R.id.cache_type);
        this.cacheFoundBtn = findViewById(R.id.cache_found_button);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.cache_display_map);
        mapFragment.getMapAsync(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;

        map.getUiSettings().setScrollGesturesEnabled(false);
        map.getUiSettings().setZoomGesturesEnabled(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        cacheFoundBtn.setOnClickListener(v -> presenter.setPlaceFound());
    }

    public static Intent newIntent(Context packageContext, String uuid) {
        return new Intent(packageContext, CacheDisplayActivity.class).putExtra(CACHE_UUID, uuid);
    }

    @Override
    public void showCache(String name, String description, double latitude, double longitude, String type) {
        Log.d("CacheDisplayer", "I Was asked to display the place " + name);
        cacheTitle.setText(name);
        cacheDescription.setText(description);
        cacheCoordinates.setText(String.format("%f, %f", latitude, longitude));
        cacheType.setText((type.contentEquals("official"))? "Cache officielle" : "Cache non-officielle ");
        try {
            map.moveCamera(CameraUpdateFactory.zoomTo(15));
            map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));
            map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
        } catch(NullPointerException ex) {
            Log.e("CacheDisplayActivity", "Move Camera error");
        }
    }

    @Override
    public void setCompletitionAvailability(int i) {
        if(i == 0)
            setFoundButtonStyle("Déjà trouvée", R.drawable.grey_gradient_button, false); //Already done
        else if(i == 1)
            setFoundButtonStyle("Vous êtes trop éloigné", R.drawable.grey_gradient_button, false); //Too far
        else if(i == 2)
            setFoundButtonStyle("J'ai trouvé!", R.drawable.green_gradient_button, true); //Available
    }

    private void setFoundButtonStyle(String message, int style, boolean clickable) {
        this.cacheFoundBtn.setText(message);
        this.cacheFoundBtn.setBackgroundResource(style);
        this.cacheFoundBtn.setClickable(clickable);
    }
}
