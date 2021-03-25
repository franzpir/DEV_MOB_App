package com.example.dev_mob_houet_piron.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.example.dev_mob_houet_piron.R;
import com.example.dev_mob_houet_piron.model.LocatorUtil;
import com.example.dev_mob_houet_piron.presenter.MapPresenter;
import com.example.dev_mob_houet_piron.view.interfaces.IFragmentDisplayer;
import com.example.dev_mob_houet_piron.view.interfaces.IMapDisplayer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, IMapDisplayer {

    private MapPresenter presenter;
    private GoogleMap map;
    private FloatingActionButton addMarkerBtn;
    private IFragmentDisplayer fragmentDisplayer;
    private Context context;
    private static final int REQUEST_ACCESS_FINE = 444719;
    private static final int PERMISSION_CODE = 1002;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        addMarkerBtn = view.findViewById(R.id.add_marker_button);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.presenter = new MapPresenter(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void updateUnOfficials() {
        //TODO: Si la map n'a pas chargé avant l'appel, ne chargera pas les marqueurs
        if(map != null) {
            Log.d("AddCache", "Map was not null, asking to load unofficials");
            presenter.loadUnofficials(this.map);
        }
        else
            Log.d("AddCache", "Map was null, aborting operation");
    }

    @Override
    public void updateOfficials() {
        if(map != null)
            presenter.loadOfficials(this.map);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.fragmentDisplayer = (IFragmentDisplayer)context;
        this.context = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        addMarkerBtn.setOnClickListener(v -> fragmentDisplayer.loadFragment(AddCacheFragment.newInstance()));
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap map) {
        MapFragment.this.map = map;
        presenter.loadUnofficials(this.map);
        presenter.loadOfficials(this.map);
        centerCamera(map);
        if (!(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE);
        } else {
            map.setMyLocationEnabled(true);
            map.setOnMyLocationButtonClickListener(this);
            map.setOnMarkerClickListener(marker -> {
                startActivity(CacheDisplayActivity.newIntent(context, marker.getTitle()));
                return true;
            });
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, getResources().getString(R.string.firstLaunch_permissionNotGrantedToast), Toast.LENGTH_LONG).show();
                return;
            }
        }
    }

    private void centerCamera(GoogleMap map) {
        LatLng userPosition = LocatorUtil.getCurrentLocation(context);
        map.moveCamera(CameraUpdateFactory.zoomTo(15));
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(userPosition.latitude, userPosition.longitude)));
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }
}