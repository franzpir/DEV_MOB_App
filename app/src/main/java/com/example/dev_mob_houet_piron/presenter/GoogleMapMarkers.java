package com.example.dev_mob_houet_piron.presenter;

import com.example.dev_mob_houet_piron.model.Place;
import com.google.android.gms.maps.GoogleMap;

import java.util.List;

public interface GoogleMapMarkers {

    void addUnOfficialMarkers(GoogleMap map, List<Place> places);

    void addOfficialMarkers(GoogleMap map, List<Place> places);
}
