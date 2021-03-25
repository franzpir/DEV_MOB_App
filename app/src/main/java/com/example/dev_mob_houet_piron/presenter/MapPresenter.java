package com.example.dev_mob_houet_piron.presenter;

import android.util.Log;

import com.example.dev_mob_houet_piron.data.firebase.FirebasePlaceRepository;
import com.example.dev_mob_houet_piron.data.room.repository.LocalPlaceRepository;
import com.example.dev_mob_houet_piron.model.Place;
import com.example.dev_mob_houet_piron.view.interfaces.IMapDisplayer;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapPresenter implements GoogleMapMarkers {

    private List<Place> unOfficialPlaces;
    private List<Place> officialPlaces;

    public MapPresenter(IMapDisplayer mapDisplayer) {
        initUnOfficialsObserver(mapDisplayer);
        initOfficialsObserver(mapDisplayer);
    }

    private void initUnOfficialsObserver(IMapDisplayer mapDisplayer) {
        LocalPlaceRepository.getInstance().getPlaces().observeForever(places -> {
            Log.d("AddCache", "Found new, updating map");
            MapPresenter.this.unOfficialPlaces = places;
            mapDisplayer.updateUnOfficials();
        });
    }

    private void initOfficialsObserver(IMapDisplayer mapDisplayer) {
        FirebasePlaceRepository.getInstance().getPlaces().observeForever(places -> {
            MapPresenter.this.officialPlaces = places;
            mapDisplayer.updateOfficials();
        });
    }

    public void loadUnofficials(GoogleMap map) {
        addUnOfficialMarkers(map, this.unOfficialPlaces);
    }

    public void loadOfficials(GoogleMap map) {
        addOfficialMarkers(map, this.officialPlaces);
    }

    @Override
    public void addUnOfficialMarkers(GoogleMap map, List<Place> places) {
        if(places != null){
            for(Place place : places){
                map.addMarker(getUnOfficialMarker(place));
            }
        }
    }

    private MarkerOptions getUnOfficialMarker(Place place) {
        LatLng pos = new LatLng(place.getLatitude(), place.getLongitude());
        return new MarkerOptions()
                .position(pos)
                .title(place.getId())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
    }

    @Override
    public void addOfficialMarkers(GoogleMap map, List<Place> places) {
        if(places != null){
            for(Place place : places){
                map.addMarker(getOfficialMarker(place));
            }
        }
    }

    private MarkerOptions getOfficialMarker(Place place) {
        LatLng pos = new LatLng(place.getLatitude(), place.getLongitude());
        return new MarkerOptions()
                .position(pos)
                .title(place.getId())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
    }
}
